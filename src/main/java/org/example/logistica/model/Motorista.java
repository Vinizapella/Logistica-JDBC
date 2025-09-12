package org.example.logistica.model;

public class Motorista {
    private int id_motorista;
    private String nome;
    private String cnh;
    private String veiculo;
    private String cidade_base;

    public Motorista(){
        this.id_motorista = 0;
        this.nome = "";
        this.cnh = "";
        this.veiculo = "";
        this.cidade_base = "";
    }

    public Motorista(int id_motorista, String nome, String cnh, String veiculo, String cidade_base) {
        this.id_motorista = id_motorista;
        this.nome = nome;
        this.cnh = cnh;
        this.veiculo = veiculo;
        this.cidade_base = cidade_base;
    }

    public int getId() {
        return id_motorista;
    }

    public void setId(int id) {
        this.id_motorista = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getCidade_base() {
        return cidade_base;
    }

    public void setCidade_base(String cidade_base) {
        this.cidade_base = cidade_base;
    }
}
