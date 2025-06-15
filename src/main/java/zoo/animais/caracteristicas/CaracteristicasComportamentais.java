package zoo.animais.caracteristicas;

import java.util.List;
import java.util.ArrayList;

public class CaracteristicasComportamentais {
    private boolean social; // se vive em grupo ou sozinho
    private boolean noturno; // se é ativo à noite
    private List<String> comportamentos; // lista de comportamentos específicos
    private String nivelAgressividade; // baixo, médio, alto
    private String nivelInteligencia; // baixo, médio, alto

    public CaracteristicasComportamentais(boolean social, boolean noturno, String nivelAgressividade, String nivelInteligencia) {
        this.social = social;
        this.noturno = noturno;
        this.nivelAgressividade = nivelAgressividade;
        this.nivelInteligencia = nivelInteligencia;
        this.comportamentos = new ArrayList<>();
    }

    // Getters e Setters
    public boolean isSocial() { return social; }
    public void setSocial(boolean social) { this.social = social; }
    public boolean isNoturno() { return noturno; }
    public void setNoturno(boolean noturno) { this.noturno = noturno; }
    public List<String> getComportamentos() { return comportamentos; }
    public void adicionarComportamento(String comportamento) { this.comportamentos.add(comportamento); }
    public String getNivelAgressividade() { return nivelAgressividade; }
    public void setNivelAgressividade(String nivelAgressividade) { this.nivelAgressividade = nivelAgressividade; }
    public String getNivelInteligencia() { return nivelInteligencia; }
    public void setNivelInteligencia(String nivelInteligencia) { this.nivelInteligencia = nivelInteligencia; }
} 