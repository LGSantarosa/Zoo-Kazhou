package zoo;

import zoo.animais.*;

public class Zoo {
    public static void main(String[] args) {
        Animal[] animais = {
                new Leao("Abmis", 5, 190, 25.5),
                new Pinguim("Ugnip", 2, 12)
        };

        for (Animal animal : animais) {
            animal.emitirSom(); // Chamada polimórfica
        }
    }
}
