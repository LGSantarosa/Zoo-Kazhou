package zoo.animais.comportamento;

public class Herbivoro implements ComportamentoAlimentar {
    @Override
    public void alimentar() {
        System.out.println("Alimentando com plantas e vegetais");
    }
} 