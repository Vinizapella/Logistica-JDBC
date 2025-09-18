package org.example.logistica.util;

import org.example.logistica.dao.ClienteDAO;

import java.sql.SQLException;
import java.util.Scanner;


public class Util {

    public static int buscarEValidarCliente(Scanner leia, ClienteDAO clienteDAO) {
        while (true) {
            System.out.println("Digite o CPF ou CNPJ do cliente: ");
            String cpfCnpjCliente = leia.nextLine().trim();

            try {
                int idCliente = clienteDAO.buscarIdClientePorCpfCnpj(cpfCnpjCliente);

                if (idCliente > 0) {
                    System.out.println(" Cliente encontrado (ID: " + idCliente + ").");
                    return idCliente;
                } else {
                    System.out.println(" ERRO: Cliente não encontrado. Por favor, tente novamente.");
                }
            } catch (SQLException e) {
                System.out.println(" Ocorreu um erro ao buscar o cliente no banco de dados!");
                e.printStackTrace();
            }
        }
    }
}
