package zoo.model;

import java.util.UUID;

public class Tarefa {
    private final String id;
    private String descricao;
    private boolean concluida;

    public Tarefa(String descricao) {
        this.id = UUID.randomUUID().toString();
        this.descricao = descricao;
        this.concluida = false;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
} 