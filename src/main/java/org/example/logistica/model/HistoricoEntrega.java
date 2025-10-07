package org.example.logistica.model;

import java.time.LocalDateTime;

public class HistoricoEntrega {
    private int id;
    private int entrega_id;
    private LocalDateTime data_evento;
    private String descricao;

    public HistoricoEntrega(int entrega_id, LocalDateTime data_evento, String descricao) {
        this.entrega_id = entrega_id;
        this.data_evento = data_evento;
        this.descricao = descricao;
    }

    public HistoricoEntrega(int id, int entrega_id, LocalDateTime data_evento, String descricao) {
        this(entrega_id, data_evento, descricao);
        this.id = id;
    }

    public int getId() { return id; }
    public int getEntrega_id() { return entrega_id; }
    public LocalDateTime getData_evento() { return data_evento; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return String.format("Histórico [ID: %d, Entrega ID: %d, Data: %s, Descrição: %s]",
                id, entrega_id, data_evento, descricao);
    }
}