package zoo.animais;

public class Pinguim extends Ave {
    public Pinguim(String nome, int idade, double peso) {
        super(nome, idade, peso, "Polo Sul", 0.5);
    }

    @Override
    public void emitirSom() {
        System.out.println("Pinguim fazendo barulho: CUAAAAA");
    }

    @Override
    public void voar() {
        System.out.println("Pinguim não voa, gordo demais");
    }
}