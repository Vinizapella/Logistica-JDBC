package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.enuns.StatusEntrega;
import org.example.logistica.model.Entrega;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {
    public void inserir(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO Entrega (pedido_id, motorista_id, data_saida, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entrega.getPedido_id());
            pstmt.setInt(2, entrega.getMotorista_id());
            pstmt.setObject(3, entrega.getData_saida());
            pstmt.setString(4, entrega.getStatus().name());
            pstmt.executeUpdate();
        }
    }

    public List<String> listarTodasDetalhado() throws SQLException {
        List<String> entregas = new ArrayList<>();
        String sql = """
            SELECT e.id, e.status, c.nome AS cliente_nome, m.nome AS motorista_nome
            FROM Entrega e
            JOIN Pedido p ON e.pedido_id = p.id
            JOIN Cliente c ON p.cliente_id = c.id
            JOIN Motorista m ON e.motorista_id = m.id
            ORDER BY e.id
            """;
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entregas.add(String.format("Entrega ID: %d, Cliente: %s, Motorista: %s, Status: %s",
                        rs.getInt("id"),
                        rs.getString("cliente_nome"),
                        rs.getString("motorista_nome"),
                        rs.getString("status")));
            }
        }
        return entregas;
    }

    public List<Entrega> listarTodas() throws SQLException {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM Entrega ORDER BY id";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Timestamp dataEntregaTimestamp = rs.getTimestamp("data_entrega");
                LocalDateTime dataEntrega = (dataEntregaTimestamp != null) ? dataEntregaTimestamp.toLocalDateTime() : null;

                entregas.add(new Entrega(
                        rs.getInt("id"),
                        rs.getInt("pedido_id"),
                        rs.getInt("motorista_id"),
                        rs.getTimestamp("data_saida").toLocalDateTime(),
                        dataEntrega,
                        StatusEntrega.valueOf(rs.getString("status"))));
            }
        }
        return entregas;
    }

    public void atualizarStatus(int entregaId, StatusEntrega status) throws SQLException {
        String sql = "UPDATE Entrega SET status = ?, data_entrega = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            if (status == StatusEntrega.ENTREGUE) {
                pstmt.setObject(2, LocalDateTime.now());
            } else {
                pstmt.setNull(2, Types.TIMESTAMP);
            }
            pstmt.setInt(3, entregaId);
            pstmt.executeUpdate();
        }
    }

    public boolean temHistorico(int entregaId) throws SQLException {
        String sql = "SELECT 1 FROM HistoricoEntrega WHERE entrega_id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entregaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void excluir(int entregaId) throws SQLException {
        String sql = "DELETE FROM Entrega WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entregaId);
            pstmt.executeUpdate();
        }
    }
}