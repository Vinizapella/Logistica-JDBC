package org.example.logistica.model;

import org.example.logistica.enuns.StatusPedido;

import java.sql.Date;

public class Pedido {
    private int id_pedido;
    private int id_cliente;
    private Date data_pedido;
    private double volume_m3;
    private double peso_kg;
    private StatusPedido status_pedido;

    public Pedido(int id_pedido, int id_cliente, Date data_pedido, double volume_m3, double peso_kg, StatusPedido status_pedido) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.data_pedido = data_pedido;
        this.volume_m3 = volume_m3;
        this.peso_kg = peso_kg;
        this.status_pedido = status_pedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Date getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Date data_pedido) {
        this.data_pedido = data_pedido;
    }

    public double getVolume_m3() {
        return volume_m3;
    }

    public void setVolume_m3(double volume_m3) {
        this.volume_m3 = volume_m3;
    }

    public double getPeso_kg() {
        return peso_kg;
    }

    public void setPeso_kg(double peso_kg) {
        this.peso_kg = peso_kg;
    }

    public StatusPedido getStatus_pedido() {
        return status_pedido;
    }

    public void setStatus_pedido(StatusPedido status_pedido) {
        this.status_pedido = status_pedido;
    }
}
