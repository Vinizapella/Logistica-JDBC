package org.example.logistica.model;

import org.example.logistica.enuns.StatusPedido;
import java.time.LocalDate;

public class Pedido {
    private int id;
    private int cliente_id;
    private LocalDate data_pedido;
    private double volume_m3;
    private double peso_kg;
    private StatusPedido status;

    public Pedido(int cliente_id, LocalDate data_pedido, double volume_m3, double peso_kg, StatusPedido status) {
        this.cliente_id = cliente_id;
        this.data_pedido = data_pedido;
        this.volume_m3 = volume_m3;
        this.peso_kg = peso_kg;
        this.status = status;
    }

    public Pedido(int id, int cliente_id, LocalDate data_pedido, double volume_m3, double peso_kg, StatusPedido status) {
        this(cliente_id, data_pedido, volume_m3, peso_kg, status);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCliente_id() { return cliente_id; }
    public LocalDate getData_pedido() { return data_pedido; }
    public double getVolume_m3() { return volume_m3; }
    public double getPeso_kg() { return peso_kg; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Pedido [ID: %d, Cliente ID: %d, Data: %s, Volume: %.2fm³, Peso: %.2fkg, Status: %s]",
                id, cliente_id, data_pedido, volume_m3, peso_kg, status);
    }
}