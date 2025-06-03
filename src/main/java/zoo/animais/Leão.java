package zoo.animais;


//Classe 4: Leão
public class Leao extends Mamifero {
    private double tamanhoJuba;

    public Leao(String nome, int idade, double peso, double tamanhoJuba) {
        super(nome, idade, peso, "Savana", 110);
        this.tamanhoJuba = tamanhoJuba;
    }

    @Override
    public void emitirSom() {
        System.out.println("Rugido do leão: RAAAAAA");
    }

    @Override
    public void amamentar() {
        System.out.println("Leão amamentando seus filhotes (lá ele)");
    }

    public double getTamanhoJuba() { return tamanhoJuba; }
    public void setTamanhoJuba(double tamanhoJuba) { this.tamanhoJuba = tamanhoJuba; }
}
