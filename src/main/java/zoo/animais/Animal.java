package zoo.animais; //Organização Lógica do Código

//Classe 1: Animal
public abstract class Animal {
    private String nome;
    private int idade;
    private double peso;
    private String habitat;

    public Animal(String nome, int idade, double peso, String habitat) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.habitat = habitat;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public String getHabitat() { return habitat; }
    public void setHabitat(String habitat) { this.habitat = habitat; }

    public abstract void emitirSom();
}