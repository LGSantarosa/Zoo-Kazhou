package src.zoo;

import src.resources.EscritorCSV;

import java.io.IOException;
import java.util.List;

public class Animal extends SerVivo {
    private String especie;
    private double peso;
    private String habitat;
    private boolean estaSaudavel;

    public Animal(String nome, int idade, String especie, double peso, String habitat) {
        super(nome, idade);
        this.especie = especie;
        this.peso = peso;
        this.habitat = habitat;
        this.estaSaudavel = true;
    }

    public String getEspecie() {
        return especie;
    }

    public double getPeso() {
        return peso;
    }

    public String getHabitat() {
        return habitat;
    }

    public void transferirHabitat(String novoHabitat) {
        // Validação rigorosa do novo habitat
        if (novoHabitat == null || novoHabitat.trim().isEmpty() || novoHabitat.contains(",")) {
            throw new IllegalArgumentException("Habitat inválido: " + novoHabitat);
        }

        // Atualização segura do habitat
        this.habitat = novoHabitat.trim();

        // Salvamento otimizado
        try {
            EscritorCSV.salvarAnimais("src/resources/animais.csv",
                    StatusZoo.getInstance().getAnimais());
        } catch (IOException | ZooException e) {
            System.err.println("ERRO CRÍTICO: " + e.getMessage());
            throw new RuntimeException("Falha ao salvar alterações", e);
        }
    }

    public boolean examinarSaude() {
        return estaSaudavel;
    }

    @Override
    public String emitirSom() {
        return "Som genérico de animal";
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " está dormindo em " + habitat);
    }

    public void comer(String alimento) {
        System.out.println(getNome() + " está comendo " + alimento);
        peso += 0.1;
        salvarDados();
    }

    // Método modificado para evitar salvamento automático durante carregamento
    public void setEstaSaudavel(boolean estaSaudavel) {
        this.estaSaudavel = estaSaudavel;
    }

    // Novo método para quando precisamos forçar o salvamento
    public void setEstaSaudavelESalvar(boolean estaSaudavel) {
        this.estaSaudavel = estaSaudavel;
        salvarDados();
    }

    public void fazerExercicio() {
        System.out.println(getNome() + " está se exercitando");
        peso -= 0.05;
        salvarDados();
    }

    private void salvarDados() {
        try {
            // Pega a lista atualizada de animais
            List<Animal> animaisAtualizados = StatusZoo.getInstance().getAnimais();

            // Sobrescreve o arquivo completamente
            EscritorCSV.salvarAnimais("src/resources/animais.csv", animaisAtualizados);
        } catch (Exception e) {
            System.err.println("ERRO CRÍTICO ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}