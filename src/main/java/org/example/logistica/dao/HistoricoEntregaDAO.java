package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.model.HistoricoEntrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoEntregaDAO {
    public void inserir(HistoricoEntrega historico) throws SQLException {
        String sql = "INSERT INTO HistoricoEntrega (entrega_id, data_evento, descricao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, historico.getEntrega_id());
            pstmt.setObject(2, historico.getData_evento());
            pstmt.setString(3, historico.getDescricao());
            pstmt.executeUpdate();
        }
    }
}