package org.example.logistica.dao;

import org.example.logistica.database.Conexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<String> totalEntregasPorMotorista() throws SQLException {
        List<String> relatorio = new ArrayList<>();
        String sql = """
            SELECT m.nome, COUNT(e.id) AS total_entregas
            FROM Motorista m
            LEFT JOIN Entrega e ON m.id = e.motorista_id
            GROUP BY m.nome
            ORDER BY total_entregas DESC
            """;
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                relatorio.add(String.format("Motorista: %-30s | Total de Entregas: %d",
                        rs.getString("nome"), rs.getInt("total_entregas")));
            }
        }
        return relatorio;
    }

    public List<String> clientesMaiorVolumeEntregue() throws SQLException {
        List<String> relatorio = new ArrayList<>();
        String sql = """
            SELECT c.nome, SUM(p.volume_m3) AS volume_total
            FROM Cliente c
            JOIN Pedido p ON c.id = p.cliente_id
            WHERE p.status = 'ENTREGUE'
            GROUP BY c.nome
            ORDER BY volume_total DESC
            LIMIT 10
            """;
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                relatorio.add(String.format("Cliente: %-30s | Volume Total Entregue: %.2f m³",
                        rs.getString("nome"), rs.getDouble("volume_total")));
            }
        }
        return relatorio;
    }

    public List<String> pedidosPendentesPorEstado() throws SQLException {
        List<String> relatorio = new ArrayList<>();
        String sql = """
            SELECT c.estado, COUNT(p.id) AS pedidos_pendentes
            FROM Pedido p
            JOIN Cliente c ON p.cliente_id = c.id
            WHERE p.status = 'PENDENTE'
            GROUP BY c.estado
            ORDER BY pedidos_pendentes DESC
            """;
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                relatorio.add(String.format("Estado: %-10s | Pedidos Pendentes: %d",
                        rs.getString("estado"), rs.getInt("pedidos_pendentes")));
            }
        }
        return relatorio;
    }

    public List<String> entregasAtrasadasPorCidade() throws SQLException {
        List<String> relatorio = new ArrayList<>();
        String sql = """
            SELECT c.cidade, COUNT(e.id) AS entregas_atrasadas
            FROM Entrega e
            JOIN Pedido p ON e.pedido_id = p.id
            JOIN Cliente c ON p.cliente_id = c.id
            WHERE e.status = 'ATRASADA'
            GROUP BY c.cidade
            ORDER BY entregas_atrasadas DESC
            """;
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                relatorio.add(String.format("Cidade: %-25s | Entregas Atrasadas: %d",
                        rs.getString("cidade"), rs.getInt("entregas_atrasadas")));
            }
        }
        return relatorio;
    }
}