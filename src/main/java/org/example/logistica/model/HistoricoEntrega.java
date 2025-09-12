package org.example.logistica.model;

import java.time.LocalDateTime;

public class HistoricoEntrega {
    private int id_Historico;
    private int id_entrega;
    private LocalDateTime data_evento;
    private String descricao;

    public HistoricoEntrega(int id_Historico, int id_entrega, LocalDateTime data_evento, String descricao) {
        this.id_Historico = id_Historico;
        this.id_entrega = id_entrega;
        this.data_evento = data_evento;
        this.descricao = descricao;
    }

    public int getId_Historico() {
        return id_Historico;
    }

    public void setId_Historico(int id_Historico) {
        this.id_Historico = id_Historico;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public LocalDateTime getData_evento() {
        return data_evento;
    }

    public void setData_evento(LocalDateTime data_evento) {
        this.data_evento = data_evento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
