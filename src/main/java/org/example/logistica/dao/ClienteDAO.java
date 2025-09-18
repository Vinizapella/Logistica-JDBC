package org.example.logistica.dao;

import org.example.logistica.database.Conexao;
import org.example.logistica.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    public void inserirCliente(Cliente cliente) throws SQLException{
        String query = """
                INSERT INTO Cliente
                (nome,cpf_cnpj,endereco,cidade,estado)
                VALUES
                (?,?,?,?,?)
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf_cnpj());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEstado());
            stmt.executeUpdate();
        }
    }

    public int buscarIdClientePorCpfCnpj(String cpfCnpj) throws SQLException {
        String query = """
                SELECT id_cliente
                FROM Cliente
                WHERE cpf_cnpj = ? 
                """;

        int idClienteEncontrado = 0;

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, cpfCnpj);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    idClienteEncontrado = rs.getInt("id_cliente");
                }
            }
        }

        return idClienteEncontrado;
    }

}
