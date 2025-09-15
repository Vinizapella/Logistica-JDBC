package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.model.Motorista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MotoristaDAO  {
    public void InserirMotorista(Motorista motorista) throws SQLException {
        String query ="""
                INSERT INTO Motorista
                (nome, cnh, veiculo, cidade_base)
                VALUES
                (?, ?, ?, ?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCnh());
            stmt.setString(3, motorista.getVeiculo());
            stmt.setString(4, motorista.getCidade_base());
            stmt.executeUpdate();
        }
    }
}
