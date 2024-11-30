package com.lpoo.atividade06;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private final String stmtInserir = "INSERT INTO livro (titulo, assunto, codigoISBN, dataPublicacao) VALUES (?, ?, ?, ?)";
    private final String stmtConsultar = "SELECT * FROM livro WHERE id = ?";
    private final String stmtListaLivroAutor = "SELECT * FROM livro";

    public void inserirLivro(Livro livro) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);

            stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAssunto());
            stmt.setString(3, livro.getCodigoISBN());
            stmt.setDate(4, new java.sql.Date(livro.getDataPublicacao().getTime()));

            stmt.executeUpdate();

            int idLivroGravado = lerIdLivro(stmt);
            livro.setId(idLivroGravado);

            gravarAutores(livro, con);

            con.commit();
        } catch (SQLException ex) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Erro ao tentar rollback. Ex=" + rollbackEx.getMessage());
            }
            throw new RuntimeException("Erro ao inserir um livro no banco de dados. Origem=" + ex.getMessage());
        } finally {
            fecharRecursos(con, stmt, null);
        }
    }

    private int lerIdLivro(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Erro ao recuperar o ID do livro inserido.");
    }

    public Livro consultarLivro(int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtConsultar);

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                List<Autor> listaAutores = lerAutores(id, con);
                Livro livroLido = new Livro(
                        rs.getString("titulo"),
                        listaAutores,
                        rs.getString("assunto"),
                        rs.getString("codigoISBN"),
                        rs.getDate("dataPublicacao")
                );
                livroLido.setId(rs.getInt("id"));
                return livroLido;
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar um livro no banco de dados. Origem=" + ex.getMessage());
        } finally {
            fecharRecursos(con, stmt, rs);
        }
    }

    private void gravarAutores(Livro livro, Connection con) throws SQLException {
        String sql = "INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, livro.getId());

            for (Autor autor : livro.getAutores()) {
                stmt.setInt(2, autor.getId());
                stmt.executeUpdate();
            }
        }
    }

    private List<Autor> lerAutores(int idLivro, Connection con) throws SQLException {
        String sql = "SELECT autor.id, autor.nome, autor.dataNascimento, autor.documento, autor.naturalidade "
                + "FROM autor "
                + "INNER JOIN livro_autor ON autor.id = livro_autor.id_autor "
                + "WHERE livro_autor.id_livro = ?";
        
        List<Autor> autores = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);

            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    Autor autorLido = new Autor(
                            resultado.getString("nome"),
                            resultado.getDate("dataNascimento"),
                            resultado.getString("documento"),
                            resultado.getString("naturalidade")
                    );
                    autorLido.setId(resultado.getInt("id"));
                    autores.add(autorLido);
                }
            }
        }
        return autores;
    }

    public List<Livro> listarLivroComAutores() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtListaLivroAutor);

            rs = stmt.executeQuery();

            List<Livro> listaLivros = new ArrayList<>();

            while (rs.next()) {
                List<Autor> listAutores = lerAutores(rs.getInt("id"), con);
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        listAutores,
                        rs.getString("assunto"),
                        rs.getString("codigoISBN"),
                        rs.getDate("dataPublicacao")
                );
                livro.setId(rs.getInt("id"));
                listaLivros.add(livro);
            }

            return listaLivros;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar livros com autores no banco de dados. Origem=" + ex.getMessage());
        } finally {
            fecharRecursos(con, stmt, rs);
        }
    }

    private void fecharRecursos(Connection con, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao fechar ResultSet. Ex=" + ex.getMessage());
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao fechar PreparedStatement. Ex=" + ex.getMessage());
        }
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao fechar Connection. Ex=" + ex.getMessage());
        }
    }
}
