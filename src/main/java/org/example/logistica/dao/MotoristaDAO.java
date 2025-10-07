package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.model.Motorista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDAO {
    public void inserir(Motorista motorista) throws SQLException {
        String sql = "INSERT INTO Motorista (nome, cnh, veiculo, cidade_base) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, motorista.getNome());
            pstmt.setString(2, motorista.getCnh());
            pstmt.setString(3, motorista.getVeiculo());
            pstmt.setString(4, motorista.getCidade_base());
            pstmt.executeUpdate();
        }
    }

    public List<Motorista> listarTodos() throws SQLException {
        List<Motorista> motoristas = new ArrayList<>();
        String sql = "SELECT * FROM Motorista ORDER BY nome";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                motoristas.add(new Motorista(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnh"),
                        rs.getString("veiculo"),
                        rs.getString("cidade_base")));
            }
        }
        return motoristas;
    }

    public boolean temEntregas(int motoristaId) throws SQLException {
        String sql = "SELECT 1 FROM Entrega WHERE motorista_id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, motoristaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void excluir(int motoristaId) throws SQLException {
        String sql = "DELETE FROM Motorista WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, motoristaId);
            pstmt.executeUpdate();
        }
    }
}