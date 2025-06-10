package zoo.model;

import java.util.UUID;

public class Funcionario {
    private final String id;
    private String login;
    private String senha;

    public Funcionario(String login, String senha) {
        this.id = UUID.randomUUID().toString();
        this.login = login;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
} 