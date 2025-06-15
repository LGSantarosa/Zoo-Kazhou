package zoo;

public abstract class SerVivo {
    private String nome;
    private int idade;

    public SerVivo(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public abstract String emitirSom();

    public abstract void dormir();
}
