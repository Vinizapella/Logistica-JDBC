Documentação: Conexão Java com Maven e MySQL
Introdução
Para facilitar a criação do banco de dados de teste, siga os passos do artigo no link: https://medium.com/@alan-vieira/rodando-mysql-no-docker-e6e05b727196

Objetivo
Esta documentação descreve os passos para configurar um projeto Java utilizando Maven para se conectar a um banco de dados MySQL, realizar inserções, atualizações e exclusões de dados.

1. Criar o Projeto Maven
Crie um novo projeto Maven no IntelliJ.

2. Adicionar dependência do MySQL no pom.xml
Abra o arquivo pom.xml e adicione a dependência do conector JDBC do MySQL:

<dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
3. Criar Classe de Conexão
Crie uma classe Conexao.java para realizar a conexão com o banco:

package com.exemplo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/nomedobanco?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "sua_senha";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
4. Classe Principal
Crie a classe Main.java para testar a conexão:

package com.exemplo;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = Conexao.conectar()) {
            if (conn != null) {
                System.out.println("Conexão realizada com sucesso!");
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
5. Criar Tabela no MySQL
Execute a seguinte query diretamente no MySQL Workbench para criar a tabela de usuários:

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100)
);
6. Classe UsuarioDAO com Inserção
Crie a classe UsuarioDAO para realizar inserções de dados na tabela usuarios:

package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void inserir(String nome, String email) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();

            System.out.println("Usuário inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
7. Exercícios Práticos de Inserção
Exercício 1 – Tabela alunos
CREATE TABLE alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    matricula VARCHAR(20),
    curso VARCHAR(100)
);
Crie um método Java para inserir um aluno utilizando PreparedStatement.

Exercício 2 – Tabela produtos
CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    preco DECIMAL(10,2),
    quantidade INT
);
Implemente um método Java para inserir um produto.

Exercício 3 – Tabela pedidos
CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(100),
    data_pedido DATE,
    total DECIMAL(10,2)
);
Implemente um método Java para inserir um pedido.

Exercício 4 – Tabela livros
CREATE TABLE livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200),
    autor VARCHAR(100),
    ano_publicacao INT
);
Implemente um método Java para inserir um livro.

Exercício 5 – Tabela funcionarios
CREATE TABLE funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cargo VARCHAR(50),
    salario DECIMAL(10,2)
);
Implemente um método Java para inserir um funcionário.

8. Atualizar Registros com PreparedStatement
Você pode atualizar registros existentes utilizando a cláusula UPDATE.

Exemplo: Atualizar o email de um usuário
public void atualizarEmail(String nome, String novoEmail) {
    String sql = "UPDATE usuarios SET email = ? WHERE nome = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, novoEmail);
        stmt.setString(2, nome);
        stmt.executeUpdate();

        System.out.println("Email atualizado com sucesso!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
9. Exercícios Práticos de Atualização
Exercício 6 – Atualizar Curso de um Aluno
Atualize o curso de um aluno com base na matrícula.

Exercício 7 – Atualizar Preço de um Produto
Atualize o preço de um produto com base no nome.

Exercício 8 – Atualizar Valor Total de um Pedido
Atualize o valor total de um pedido com base no ID.

Exercício 9 – Atualizar Autor de um Livro
Atualize o autor de um livro com base no título.

Exercício 10 – Atualizar Salário de um Funcionário
Atualize o salário com base no nome do funcionário.

10. Deletar Registros com PreparedStatement
Para remover registros, utilize a cláusula DELETE.

Exemplo: Deletar um usuário pelo nome
public void deletarUsuario(String nome) {
    String sql = "DELETE FROM usuarios WHERE nome = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        stmt.executeUpdate();

        System.out.println("Usuário deletado com sucesso!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
11. Exercícios Práticos de Deleção
Exercício 11 – Deletar Aluno
Remova um aluno com base na matrícula.

Exercício 12 – Deletar Produto
Remova um produto com base no nome.

Exercício 13 – Deletar Pedido
Remova um pedido com base no ID.

Exercício 14 – Deletar Livro
Remova um livro com base no título.

Exercício 15 – Deletar Funcionário
Remova um funcionário com base no nome.

12. Buscar Registros com SELECT
Você pode utilizar o PreparedStatement também para fazer buscas no banco. Veja abaixo dois exemplos: listar todos os usuários e buscar por um usuário específico pelo ID.

Exemplo: Listar todos os usuários
public static List<Usuario> listar() {
    String sql = "SELECT id,nome,email FROM usuarios";
    List<Usuario> usuarios = new ArrayList<>();
    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");

            Usuario usuario = new Usuario(id, nome, email);
            usuarios.add(usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return usuarios;
}
Exemplo: Buscar um usuário por ID
public static Usuario listarPorId(int id) {
    String sql = "SELECT id, nome, email FROM usuarios WHERE id = ?";
    int newId = 0;
    String nome = "";
    String email = "";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            newId = rs.getInt("id");
            nome = rs.getString("nome");
            email = rs.getString("email");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new Usuario(newId, nome, email);
}
Exemplo de uso no Main
public class Main {
    public static void main(String[] args) {
        // Buscar um usuário por ID
        Usuario usuario = UsuarioDAO.listarPorId(3);
        System.out.println(usuario);

        // Listar todos os usuários
        List<Usuario> lista = UsuarioDAO.listar();
        for (Usuario u : lista) {
            System.out.println(u);
        }
    }
}
13. Inserção de múltiplos registros para prática
Utilize o script abaixo para popular a tabela usuarios com dados de teste:

INSERT INTO usuarios (nome, email) VALUES
('Ana Souza', 'ana.souza@email.com'),
('Bruno Lima', 'bruno.lima@email.com'),
('Carla Mendes', 'carla.mendes@email.com'),
('Daniel Rocha', 'daniel.rocha@email.com'),
('Eduarda Farias', 'eduarda.farias@email.com'),
('Felipe Martins', 'felipe.martins@email.com'),
('Gabriela Silva', 'gabriela.silva@email.com'),
('Henrique Alves', 'henrique.alves@email.com'),
('Isabela Duarte', 'isabela.duarte@email.com'),
('João Pedro', 'joao.pedro@email.com');
14. Atividades Práticas com SELECT
Atividade 1 – Listar todos os usuários
Implemente uma interface de terminal ou GUI (ex: JOptionPane) que exiba todos os usuários cadastrados.

Atividade 2 – Buscar um usuário por ID
Solicite ao usuário que digite um ID, e exiba na tela o nome e email correspondentes.

Atividade 3 – Exibir usuários com emails de um domínio específico
Adapte o método listar() para filtrar usuários cujo email termina com @email.com.

Atividade 4 – Contar quantos usuários estão cadastrados
Utilize SELECT COUNT(*) e exiba a quantidade de registros da tabela usuarios.

Considerações Finais
Utilizar PreparedStatement ajuda a prevenir ataques de SQL Injection e promove segurança nas operações com banco de dados. Pratique os exercícios propostos e evolua implementando métodos de busca (SELECT) em etapas futuras.
