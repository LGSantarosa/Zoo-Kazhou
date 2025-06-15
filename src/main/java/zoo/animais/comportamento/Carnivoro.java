package zoo.animais.comportamento;

public class Carnivoro implements ComportamentoAlimentar {
    @Override
    public void alimentar() {
        System.out.println("Alimentando com carne e proteínas");
    }
} 