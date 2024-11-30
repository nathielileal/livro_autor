package com.lpoo.atividade06;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    private final String stmtInserir = "INSERT INTO autor (nome, dataNascimento, documento, naturalidade) VALUES (?, ?, ?, ?)";
    private final String stmtConsultar = "SELECT * FROM autor WHERE id = ?";
    private final String stmtListar = "SELECT * FROM autor";

    public void inserirAutor(Autor autor) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = ConnectionFactory.getConnection();

            stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, autor.getNome());
            stmt.setDate(2, new java.sql.Date(autor.getDataNascimento().getTime()));
            stmt.setString(3, autor.getDocumento());
            stmt.setString(4, autor.getNaturalidade());
            stmt.executeUpdate();

            autor.setId(lerIdAutor(stmt));
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um autor no banco de dados. Origem=" + ex.getMessage());
        } finally {
            fecharRecursos(con, stmt, null);
        }
    }

    private int lerIdAutor(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Erro ao recuperar o ID do autor inserido.");
    }

    public Autor consultarAutor(int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();

            stmt = con.prepareStatement(stmtConsultar);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Autor autorLido = new Autor(
                        rs.getString("nome"),
                        rs.getDate("dataNascimento"),
                        rs.getString("documento"),
                        rs.getString("naturalidade")
                );
                autorLido.setId(rs.getInt("id"));

                return autorLido;
            } else {
                throw new RuntimeException("Não existe autor com este id. Id=" + id);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar um autor no banco de dados. Origem=" + ex.getMessage());
        } finally {
            fecharRecursos(con, stmt, rs);
        }
    }

    public List<Autor> listarAutores() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Autor> lista = new ArrayList<>();

        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement(stmtListar);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getString("nome"),
                        rs.getDate("dataNascimento"),
                        rs.getString("documento"),
                        rs.getString("naturalidade")
                );
                autor.setId(rs.getInt("id"));
                lista.add(autor);
            }
            return lista;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar autores no banco de dados. Origem=" + ex.getMessage());
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
