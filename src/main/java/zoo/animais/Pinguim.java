package zoo.animais;

//Classe 5: pingunim
public class Pinguim extends Animal {
    public Pinguim(String nome, int idade, double peso) {
        super(nome, idade, "Pinguim");
    }

    @Override
    public void emitirSom() {
        System.out.println("Pinguim fazendo barulho: CUAAAAA");
    }
}