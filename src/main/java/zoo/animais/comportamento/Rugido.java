package zoo.animais.comportamento;

public class Rugido implements ComportamentoSonoro {
    @Override
    public void emitirSom() {
        System.out.println("Rugindo alto!");
    }
} 