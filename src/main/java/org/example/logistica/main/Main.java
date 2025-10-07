package org.example.logistica.main;

import org.example.logistica.dao.*;
import org.example.logistica.enuns.StatusEntrega;
import org.example.logistica.enuns.StatusPedido;
import org.example.logistica.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();
    private static final EntregaDAO entregaDAO = new EntregaDAO();
    private static final HistoricoEntregaDAO historicoEntregaDAO = new HistoricoEntregaDAO();
    private static final RelatorioDAO relatorioDAO = new RelatorioDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                roteador(opcao, scanner);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== BEM VINDO AO SISTEMA DE LOGISTICA =====");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Cadastrar Motorista");
        System.out.println("3 - Criar Pedido");
        System.out.println("4 - Atribuir Pedido a Motorista (Gerar Entrega)");
        System.out.println("5 - Registrar Evento de Entrega (Histórico)");
        System.out.println("6 - Atualizar Status da Entrega");
        System.out.println("7 - Listar Todas as Entregas com Cliente e Motorista");
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("8 - Total de Entregas por Motorista");
        System.out.println("9 - Clientes com Maior Volume Entregue");
        System.out.println("10 - Pedidos Pendentes por Estado");
        System.out.println("11 - Entregas Atrasadas por Cidade");
        System.out.println("\n--- BUSCAS E AÇÕES ---");
        System.out.println("12 - Buscar Pedido por CPF/CNPJ do Cliente");
        System.out.println("13 - Cancelar Pedido");
        System.out.println("14 - Excluir Entrega");
        System.out.println("15 - Excluir Cliente");
        System.out.println("16 - Excluir Motorista");
        System.out.println("0 - Sair");
        System.out.print("==========================================\nEscolha uma opção: ");
    }

    private static void roteador(int opcao, Scanner scanner) throws SQLException {
        switch (opcao) {
            case 1 -> cadastrarCliente(scanner);
            case 2 -> cadastrarMotorista(scanner);
            case 3 -> criarPedido(scanner);
            case 4 -> atribuirPedido(scanner);
            case 5 -> registrarEvento(scanner);
            case 6 -> atualizarStatusEntrega(scanner);
            case 7 -> listarEntregasDetalhado();
            case 8 -> relatorioTotalEntregasPorMotorista();
            case 9 -> relatorioClientesMaiorVolume();
            case 10 -> relatorioPedidosPendentesPorEstado();
            case 11 -> relatorioEntregasAtrasadasPorCidade();
            case 12 -> buscarPedidoPorCpfCnpj(scanner);
            case 13 -> cancelarPedido(scanner);
            case 14 -> excluirEntrega(scanner);
            case 15 -> excluirCliente(scanner);
            case 16 -> excluirMotorista(scanner);
            case 0 -> System.out.println("Saindo do sistema...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void cadastrarCliente(Scanner scanner) throws SQLException {
        System.out.println("--- 1. Cadastrar Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF/CNPJ: ");
        String cpfCnpj = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado (UF): ");
        String estado = scanner.nextLine();
        clienteDAO.inserir(new Cliente(nome, cpfCnpj, endereco, cidade, estado));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarMotorista(Scanner scanner) throws SQLException {
        System.out.println("--- 2. Cadastrar Motorista ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CNH: ");
        String cnh = scanner.nextLine();
        System.out.print("Veículo: ");
        String veiculo = scanner.nextLine();
        System.out.print("Cidade Base: ");
        String cidadeBase = scanner.nextLine();
        motoristaDAO.inserir(new Motorista(nome, cnh, veiculo, cidadeBase));
        System.out.println("Motorista cadastrado com sucesso!");
    }

    private static void criarPedido(Scanner scanner) throws SQLException {
        System.out.println("--- 3. Criar Pedido ---");
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        clientes.forEach(System.out::println);
        System.out.print("ID do Cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        System.out.print("Volume (m³): ");
        double volume = Double.parseDouble(scanner.nextLine());
        System.out.print("Peso (kg): ");
        double peso = Double.parseDouble(scanner.nextLine());
        pedidoDAO.inserir(new Pedido(clienteId, LocalDate.now(), volume, peso, StatusPedido.PENDENTE));
        System.out.println("Pedido criado com sucesso!");
    }

    private static void atribuirPedido(Scanner scanner) throws SQLException {
        System.out.println("--- 4. Atribuir Pedido a Motorista ---");
        List<Pedido> pedidos = pedidoDAO.listarPendentes();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido pendente para atribuir.");
            return;
        }
        pedidos.forEach(System.out::println);
        System.out.print("ID do Pedido: ");
        int pedidoId = Integer.parseInt(scanner.nextLine());

        List<Motorista> motoristas = motoristaDAO.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista disponível.");
            return;
        }
        motoristas.forEach(System.out::println);
        System.out.print("ID do Motorista: ");
        int motoristaId = Integer.parseInt(scanner.nextLine());

        entregaDAO.inserir(new Entrega(pedidoId, motoristaId, LocalDateTime.now(), StatusEntrega.EM_ROTA));
        System.out.println("Entrega gerada e pedido atribuído com sucesso!");
    }

    private static void registrarEvento(Scanner scanner) throws SQLException {
        System.out.println("--- 5. Registrar Evento de Entrega ---");
        List<Entrega> entregas = entregaDAO.listarTodas();
        if(entregas.isEmpty()){
            System.out.println("Nenhuma entrega cadastrada.");
            return;
        }
        entregas.forEach(System.out::println);
        System.out.print("ID da Entrega: ");
        int entregaId = Integer.parseInt(scanner.nextLine());
        System.out.print("Descrição do Evento: ");
        String descricao = scanner.nextLine();
        historicoEntregaDAO.inserir(new HistoricoEntrega(entregaId, LocalDateTime.now(), descricao));
        System.out.println("Evento registrado com sucesso!");
    }

    private static void atualizarStatusEntrega(Scanner scanner) throws SQLException {
        System.out.println("--- 6. Atualizar Status da Entrega ---");
        List<Entrega> entregas = entregaDAO.listarTodas();
        if(entregas.isEmpty()){
            System.out.println("Nenhuma entrega para atualizar.");
            return;
        }
        entregas.forEach(System.out::println);
        System.out.print("ID da Entrega: ");
        int entregaId = Integer.parseInt(scanner.nextLine());

        System.out.println("Selecione o novo status:");
        for (StatusEntrega status : StatusEntrega.values()) {
            System.out.printf("%d - %s\n", status.ordinal(), status.name());
        }
        System.out.print("Opção de Status: ");
        int statusOpt = Integer.parseInt(scanner.nextLine());
        StatusEntrega novoStatus = StatusEntrega.values()[statusOpt];

        entregaDAO.atualizarStatus(entregaId, novoStatus);

        if (novoStatus == StatusEntrega.ENTREGUE) {
            System.out.println("Atenção: A entrega foi concluída. Atualize o status do pedido correspondente se necessário.");
        }
        System.out.println("Status da entrega atualizado com sucesso!");
    }

    private static void listarEntregasDetalhado() throws SQLException {
        System.out.println("--- 7. Lista de Entregas ---");
        List<String> entregas = entregaDAO.listarTodasDetalhado();
        if (entregas.isEmpty()) {
            System.out.println("Nenhuma entrega encontrada.");
        } else {
            entregas.forEach(System.out::println);
        }
    }

    private static void relatorioTotalEntregasPorMotorista() throws SQLException {
        System.out.println("--- 8. Relatório: Total de Entregas por Motorista ---");
        List<String> relatorio = relatorioDAO.totalEntregasPorMotorista();
        relatorio.forEach(System.out::println);
    }

    private static void relatorioClientesMaiorVolume() throws SQLException {
        System.out.println("--- 9. Relatório: Clientes com Maior Volume Entregue ---");
        List<String> relatorio = relatorioDAO.clientesMaiorVolumeEntregue();
        relatorio.forEach(System.out::println);
    }

    private static void relatorioPedidosPendentesPorEstado() throws SQLException {
        System.out.println("--- 10. Relatório: Pedidos Pendentes por Estado ---");
        List<String> relatorio = relatorioDAO.pedidosPendentesPorEstado();
        relatorio.forEach(System.out::println);
    }

    private static void relatorioEntregasAtrasadasPorCidade() throws SQLException {
        System.out.println("--- 11. Relatório: Entregas Atrasadas por Cidade ---");
        List<String> relatorio = relatorioDAO.entregasAtrasadasPorCidade();
        relatorio.forEach(System.out::println);
    }

    private static void buscarPedidoPorCpfCnpj(Scanner scanner) throws SQLException {
        System.out.println("--- 12. Buscar Pedido por CPF/CNPJ ---");
        System.out.print("Digite o CPF/CNPJ do cliente: ");
        String cpfCnpj = scanner.nextLine();
        List<String> pedidos = pedidoDAO.buscarPorCpfCnpj(cpfCnpj);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para este cliente.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }

    private static void cancelarPedido(Scanner scanner) throws SQLException {
        System.out.println("--- 13. Cancelar Pedido ---");
        List<Pedido> pedidos = pedidoDAO.listarPendentes();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido pendente para cancelar.");
            return;
        }
        pedidos.forEach(System.out::println);
        System.out.print("Digite o ID do pedido a ser cancelado: ");
        int pedidoId = Integer.parseInt(scanner.nextLine());
        pedidoDAO.atualizarStatus(pedidoId, StatusPedido.CANCELADO);
        System.out.println("Pedido cancelado com sucesso!");
    }

    private static void excluirEntrega(Scanner scanner) throws SQLException {
        System.out.println("--- 14. Excluir Entrega ---");
        List<Entrega> entregas = entregaDAO.listarTodas();
        if(entregas.isEmpty()){
            System.out.println("Nenhuma entrega cadastrada.");
            return;
        }
        entregas.forEach(System.out::println);
        System.out.print("Digite o ID da entrega a ser excluída: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (entregaDAO.temHistorico(id)) {
            System.out.println("Não é possível excluir. A entrega possui eventos no histórico.");
        } else {
            entregaDAO.excluir(id);
            System.out.println("Entrega excluída com sucesso!");
        }
    }

    private static void excluirCliente(Scanner scanner) throws SQLException {
        System.out.println("--- 15. Excluir Cliente ---");
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        clientes.forEach(System.out::println);
        System.out.print("Digite o ID do cliente a ser excluído: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (clienteDAO.temPedidos(id)) {
            System.out.println("Não é possível excluir. O cliente possui pedidos associados.");
        } else {
            clienteDAO.excluir(id);
            System.out.println("Cliente excluído com sucesso!");
        }
    }

    private static void excluirMotorista(Scanner scanner) throws SQLException {
        System.out.println("--- 16. Excluir Motorista ---");
        List<Motorista> motoristas = motoristaDAO.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado.");
            return;
        }
        motoristas.forEach(System.out::println);
        System.out.print("Digite o ID do motorista a ser excluído: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (motoristaDAO.temEntregas(id)) {
            System.out.println("Não é possível excluir. O motorista possui entregas associadas.");
        } else {
            motoristaDAO.excluir(id);
            System.out.println("Motorista excluído com sucesso!");
        }
    }
}