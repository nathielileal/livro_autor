package com.lpoo.atividade06;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    private final String stmtInserir = "INSERT INTO autor (nome, data_nascimento, documento, naturalidade) VALUES (?, ?, ?, ?)";
    private final String stmtConsultar = "SELECT * FROM autor WHERE id = ?";
    private final String stmtListar = "SELECT * FROM autor";

    public void inserirAutor(Autor autor) {
        try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, autor.getNome());
            stmt.setDate(2, new java.sql.Date(autor.getDataNascimento().getTime()));
            stmt.setString(3, autor.getDocumento());
            stmt.setString(4, autor.getNaturalidade());
            stmt.executeUpdate();

            autor.setId(lerIdAutor(stmt));
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um autor no banco de dados. Origem=" + ex.getMessage());
        }
    }
    
    public void associarLivroAoAutor(Autor autor, Livro livro) {
        String sql = "INSERT INTO autor_livro (autor_id, livro_id) VALUES (?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autor.getId());  
            stmt.setInt(2, livro.getId());  
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
        ResultSet rs = null;

        try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(stmtConsultar);) {
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Autor autorLido = new Autor(
                        rs.getString("nome"),
                        rs.getDate("data_nascimento"),
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
        }
    }

    public List<Autor> listarAutores() {
        ResultSet rs = null;

        List<Autor> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(stmtListar);) {
            rs = stmt.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getString("nome"),
                        rs.getDate("data_nascimento"),
                        rs.getString("documento"),
                        rs.getString("naturalidade")
                );
                
                autor.setId(rs.getInt("id"));
                lista.add(autor);
            }
            
            return lista;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar autores no banco de dados. Origem=" + ex.getMessage());
        }
    }
}
