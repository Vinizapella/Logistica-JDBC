package org.example.logistica.model;

import org.example.logistica.enuns.StatusEntrega;

import java.time.LocalDateTime;

public class Entrega {
    private int id_entrega;
    private int id_pedido;
    private int id_motorista;
    private LocalDateTime data_saida;
    private LocalDateTime data_entrega;
    private StatusEntrega statusEntrega;

    public Entrega(int id_entrega, int id_pedido, int id_motorista, LocalDateTime data_saida, LocalDateTime data_entrega, StatusEntrega statusEntrega) {
        this.id_entrega = id_entrega;
        this.id_pedido = id_pedido;
        this.id_motorista = id_motorista;
        this.data_saida = data_saida;
        this.data_entrega = data_entrega;
        this.statusEntrega = statusEntrega;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_motorista() {
        return id_motorista;
    }

    public void setId_motorista(int id_motorista) {
        this.id_motorista = id_motorista;
    }

    public LocalDateTime getData_saida() {
        return data_saida;
    }

    public void setData_saida(LocalDateTime data_saida) {
        this.data_saida = data_saida;
    }

    public LocalDateTime getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDateTime data_entrega) {
        this.data_entrega = data_entrega;
    }

    public StatusEntrega getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(StatusEntrega statusEntrega) {
        this.statusEntrega = statusEntrega;
    }
}
