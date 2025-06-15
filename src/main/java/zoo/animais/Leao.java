package zoo.animais;

import zoo.animais.comportamento.Carnivoro;
import zoo.animais.comportamento.Rugido;
import zoo.animais.caracteristicas.CaracteristicasFisicas;
import zoo.animais.caracteristicas.CaracteristicasComportamentais;

//Classe 4: Leão
public class Leao extends Animal {
    private double tamanhoJuba;

    public Leao(String nome, int idade) {
        super();
        setNome(nome);
        setIdade(idade);
        setEspecie("Leão");
        
        // Configurando comportamentos
        setComportamentoAlimentar(new Carnivoro());
        setComportamentoSonoro(new Rugido());
        
        // Configurando características físicas
        CaracteristicasFisicas fisicas = new CaracteristicasFisicas(
            190.0, // peso em kg
            1.2,   // altura em metros
            "Amarelo dourado",
            "Pele com juba",
            "Savana"
        );
        setCaracteristicasFisicas(fisicas);
        
        // Configurando características comportamentais
        CaracteristicasComportamentais comportamentais = new CaracteristicasComportamentais(
            true,  // social
            true,  // noturno
            "alto", // nível de agressividade
            "alto"  // nível de inteligência
        );
        comportamentais.adicionarComportamento("Vive em grupo");
        comportamentais.adicionarComportamento("Caçador");
        setCaracteristicasComportamentais(comportamentais);
    }

    @Override
    public void emitirSom() {
        System.out.println("Rugido do leão: RAAAAAA");
    }

    public double getTamanhoJuba() { return tamanhoJuba; }
    public void setTamanhoJuba(double tamanhoJuba) { this.tamanhoJuba = tamanhoJuba; }
}
