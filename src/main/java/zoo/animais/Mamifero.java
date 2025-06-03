package zoo.animais;

//Classe 2: Mamifero
public abstract class Mamifero extends Animal {
    private int periodoGestacao;

    public Mamifero(String nome, int idade, double peso, String habitat, int periodoGestacao) {
        super(nome, idade, peso, habitat);
        this.periodoGestacao = periodoGestacao;
    }

    public int getPeriodoGestacao() { return periodoGestacao; }
    public void setPeriodoGestacao(int periodoGestacao) { this.periodoGestacao = periodoGestacao; }

    public abstract void amamentar();
}