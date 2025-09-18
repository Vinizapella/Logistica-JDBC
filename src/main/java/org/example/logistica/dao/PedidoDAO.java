package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidoDAO {
    public void inserirPedido(Pedido pedido) throws SQLException {
        String query = """
                INSERT INTO Pedido
                (id_cliente, data_pedido, volume_m3, peso_kg, status_pedido)
                VALUES
                (?, ?, ?, ?, ? )
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, pedido.getId_cliente());
            stmt.setObject(2, pedido.getData_pedido());
            stmt.setDouble(3, pedido.getVolume_m3());
            stmt.setDouble(4, pedido.getPeso_kg());
            stmt.setString(5, pedido.getStatus_pedido().name());

            stmt.executeUpdate();
        }

    }
}
