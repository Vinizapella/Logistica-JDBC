package org.example.logistica.model;

import org.example.logistica.enuns.StatusEntrega;
import java.time.LocalDateTime;

public class Entrega {
    private int id;
    private int pedido_id;
    private int motorista_id;
    private LocalDateTime data_saida;
    private LocalDateTime data_entrega;
    private StatusEntrega status;

    public Entrega(int pedido_id, int motorista_id, LocalDateTime data_saida, StatusEntrega status) {
        this.pedido_id = pedido_id;
        this.motorista_id = motorista_id;
        this.data_saida = data_saida;
        this.status = status;
    }

    public Entrega(int id, int pedido_id, int motorista_id, LocalDateTime data_saida, LocalDateTime data_entrega, StatusEntrega status) {
        this(pedido_id, motorista_id, data_saida, status);
        this.id = id;
        this.data_entrega = data_entrega;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPedido_id() { return pedido_id; }
    public int getMotorista_id() { return motorista_id; }
    public LocalDateTime getData_saida() { return data_saida; }
    public LocalDateTime getData_entrega() { return data_entrega; }
    public StatusEntrega getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Entrega [ID: %d, Pedido ID: %d, Motorista ID: %d, Saída: %s, Status: %s]",
                id, pedido_id, motorista_id, data_saida, status);
    }
}