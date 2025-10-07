package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.enuns.StatusPedido;
import org.example.logistica.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    public void inserir(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (cliente_id, data_pedido, volume_m3, peso_kg, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pedido.getCliente_id());
            pstmt.setObject(2, pedido.getData_pedido());
            pstmt.setDouble(3, pedido.getVolume_m3());
            pstmt.setDouble(4, pedido.getPeso_kg());
            pstmt.setString(5, pedido.getStatus().name());
            pstmt.executeUpdate();
        }
    }

    public List<Pedido> listarPendentes() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE status = 'PENDENTE' AND id NOT IN (SELECT pedido_id FROM Entrega) ORDER BY data_pedido";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getDate("data_pedido").toLocalDate(),
                        rs.getDouble("volume_m3"),
                        rs.getDouble("peso_kg"),
                        StatusPedido.valueOf(rs.getString("status"))));
            }
        }
        return pedidos;
    }

    public void atualizarStatus(int pedidoId, StatusPedido status) throws SQLException {
        String sql = "UPDATE Pedido SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            pstmt.setInt(2, pedidoId);
            pstmt.executeUpdate();
        }
    }

    public List<String> buscarPorCpfCnpj(String cpfCnpj) throws SQLException {
        List<String> resultados = new ArrayList<>();
        String sql = """
            SELECT p.id, p.data_pedido, p.status, c.nome
            FROM Pedido p
            JOIN Cliente c ON p.cliente_id = c.id
            WHERE c.cpf_cnpj = ?
            ORDER BY p.data_pedido DESC
            """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpfCnpj);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultados.add(String.format("Pedido ID: %d, Data: %s, Status: %s, Cliente: %s",
                        rs.getInt("id"),
                        rs.getDate("data_pedido").toLocalDate(),
                        rs.getString("status"),
                        rs.getString("nome")));
            }
        }
        return resultados;
    }
}