package zoo;

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

    public String getHabitat() {
        return habitat;
    }

    public void transferirHabitat(String novoHabitat) {
        System.out.println(getNome() + " foi transferido para " + novoHabitat);
        this.habitat = novoHabitat;
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
    }

    public void setEstaSaudavel(boolean estaSaudavel) {
        this.estaSaudavel = estaSaudavel;
    }

    public void fazerExercicio() {
        System.out.println(getNome() + " está se exercitando");
        peso -= 0.05;
    }
}
