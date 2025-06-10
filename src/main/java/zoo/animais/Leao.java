package zoo.animais;


//Classe 4: Leão
public class Leao extends Animal {
    private double tamanhoJuba;

    public Leao(String nome, int idade, double peso, double tamanhoJuba) {
        super(nome, idade, "Leão");
        this.tamanhoJuba = tamanhoJuba;
    }

    @Override
    public void emitirSom() {
        System.out.println("Rugido do leão: RAAAAAA");
    }

    public double getTamanhoJuba() { return tamanhoJuba; }
    public void setTamanhoJuba(double tamanhoJuba) { this.tamanhoJuba = tamanhoJuba; }
}
