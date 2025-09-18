package org.example.logistica.main;
import org.example.logistica.dao.ClienteDAO;
import org.example.logistica.dao.MotoristaDAO;
import org.example.logistica.dao.PedidoDAO;
import org.example.logistica.model.Cliente;
import org.example.logistica.model.Motorista;
import org.example.logistica.model.Pedido;
import org.example.logistica.util.Util;

import java.sql.SQLException;
import java.util.Scanner;

import static org.example.logistica.util.Util.buscarEValidarCliente;

public class Main {
    static Scanner leia = new Scanner(System.in);

    public static void main(String[] args) {
        inicio();
    }

    public static void inicio() {
        boolean sair = false;
        System.out.println("""
                ===== BEM VINDO AO SISTEMA DE LOGISTICA =====
                Escolha uma opção: 
                1 - Cadastrar Cliente
                2 - Cadastrar Motorista
                3 - Criar Pedido
                4 - Atribuir Pedido a Motorista (Gerar Entrega)
                5 - Registrar Evento de Entrega (Histórico)
                6 - Atualizar Status da Entrega
                7 - Listar Todas as Entregas com Cliente e Motorista
                8 - Relatório: Total de Entregas por Motorista
                9 - Relatório: Clientes com Maior Volume Entregue
                10 - Relatório: Pedidos Pendentes por Estado
                11 - Relatório: Entregas Atrasadas por Cidade
                12 - Buscar Pedido por CPF/CNPJ do Cliente
                13 - Cancelar Pedido
                14 - Excluir Entrega (com validação)
                15 - Excluir Cliente (com verificação de dependência)
                16 - Excluir Motorista (com verificação de dependência)
                0 - Sair
                ==========================================
                """);
        int opcao = leia.nextInt();
        switch (opcao) {
            case 1: {
                cadastrarCliente(leia);
                break;
            }
            case 2: {
                cadastrarMotorista(leia);
                break;
            }
            case 3: {
                cadastrarPedido(leia);
                break;
            }
            case 4: {

                break;
            }
            case 5: {

                break;
            }
            case 6: {

                break;
            }
            case 7: {

                break;
            }
            case 8: {

                break;
            }
            case 9: {

                break;
            }
            case 10: {

                break;
            }
            case 11: {

                break;
            }
            case 12: {
                break;
            }
            case 13: {

                break;
            }
            case 14: {

                break;
            }
            case 15: {

                break;
            }
            case 16: {

                break;
            }
        }
        if (!sair) {
            inicio();
        }
    }

    public static void cadastrarCliente(Scanner leia) {
        leia.nextLine();
        System.out.println("===== CADASTRO DE CLIENTE =====");
        System.out.print("Digite o nome: ");
        String nome = leia.nextLine();
        System.out.print("Digite o CPF ou CNPJ: ");
        String cpf_cnpj = leia.nextLine();
        System.out.print("Digite o endereço: ");
        String endereco = leia.nextLine();
        System.out.print("Digite a cidade: ");
        String cidade = leia.nextLine();
        System.out.print("Digite o estado: ");
        String estado = leia.nextLine();
        var cliente = new Cliente(nome, cpf_cnpj, endereco, cidade, estado);
        var clienteDao = new ClienteDAO();
        try {
            clienteDao.inserirCliente(cliente);
            System.out.println("O usuário foi inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro no banco de dados!");
            e.printStackTrace();
        }
    }

    public static void cadastrarMotorista(Scanner leia) {
        leia.nextLine();
        System.out.println("===== CADASTRO DE MOTORISTA =====");
        System.out.println("Digite o nome: ");
        String nome = leia.nextLine();
        System.out.println("Digite a cnh: ");
        String cnh = leia.nextLine();
        System.out.println("Digite o seu veiculo: ");
        String veiculo = leia.nextLine();
        System.out.println("Digite a sua cidade base: ");
        String cidade_base = leia.nextLine();
        var motorista = new Motorista(nome, cnh, veiculo, cidade_base);
        var motoristaDao = new MotoristaDAO();
        try {
            motoristaDao.InserirMotorista(motorista);
            System.out.println("O motorista foi inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro no banco de dados!");
            e.printStackTrace();
        }
    }

    public static void cadastrarPedido(Scanner leia) {
        leia.nextLine();
        System.out.println("===== CADASTRO DE PEDIDOS =====");
        ClienteDAO clienteDAO = new ClienteDAO();
        int idCliente = buscarEValidarCliente(leia, clienteDAO);
        System.out.println("Prossiga com os dados do pedido.");
        System.out.println("Digite o volume em m³: ");
        double volume_m3 = leia.nextDouble();
        System.out.println("Digite o peso em kg: ");
        double peso_kg = leia.nextDouble();
        Pedido pedido = new Pedido(idCliente, volume_m3, peso_kg);
        PedidoDAO pedidoDao = new PedidoDAO();
        try {
            pedidoDao.inserirPedido(pedido);
        } catch (SQLException e) {
            System.out.println(" Ocorreu um erro ao inserir o pedido no banco de dados!");
            e.printStackTrace();
        }
    }
    }




