package zoo.animais;

import zoo.model.Ocorrencia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Classe 1: Animal
public class Animal {
    private String id;
    private String nome;
    private int idade;
    private String especie;
    private List<Ocorrencia> ocorrencias;

    // Construtor padrão para uso do Gson
    public Animal() {
        this.ocorrencias = new ArrayList<>();
    }

    public Animal(String nome, int idade, String especie) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.ocorrencias = new ArrayList<>();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public List<Ocorrencia> getOcorrencias() { return ocorrencias; }
    public void adicionarOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
    }

    public void emitirSom() {}
}