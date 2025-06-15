package zoo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ocorrencia {
    private final String id;
    private String tipo;
    private String descricao;
    private LocalDateTime data;

    public Ocorrencia(String tipo, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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