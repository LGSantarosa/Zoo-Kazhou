package zoo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ocorrencia {
    private final String id;
    private String descricao;
    private LocalDateTime data;

    public Ocorrencia(String descricao) {
        this.id = UUID.randomUUID().toString();
        this.descricao = descricao;
        this.data = LocalDateTime.now();
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
} 