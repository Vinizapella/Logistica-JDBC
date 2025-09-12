package org.example.logistica.model;

public class Cliente {
    private int id_cliente;
    private String nome;
    private String cpf_cnpj;
    private String endereco;
    private String cidade;
    private String estado;

    public Cliente(){
        this.id_cliente = 0;
        this.nome = "";
        this.cpf_cnpj = "";
        this.endereco = "";
        this.cidade = "";
        this.estado = "";
    }

    public Cliente(int id_cliente, String nome, String cpf_cnpj, String endereco, String cidade, String estado) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.cpf_cnpj = cpf_cnpj;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getId() {
        return id_cliente;
    }

    public void setId(int id) {
        this.id_cliente = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
