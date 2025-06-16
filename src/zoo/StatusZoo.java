package src.zoo;

import src.resources.LeitorCSV;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatusZoo {
    private static StatusZoo instance;
    private boolean estaAberto;
    private List<Animal> animais;
    private List<Funcionario> funcionarios;
    private int numeroVisitantes;

    private StatusZoo() throws ZooException {
        try {
            this.estaAberto = false;
            this.numeroVisitantes = 0;

            // Carrega dados sem disparar salvamento automático
            this.animais = new ArrayList<>(LeitorCSV.carregarAnimais("src/resources/animais.csv"));
            this.funcionarios = new ArrayList<>(LeitorCSV.carregarFuncionarios("src/resources/funcionarios.csv"));

        } catch (IOException e) {
            throw new ZooException("Falha ao carregar dados iniciais: " + e.getMessage(), e);
        }
    }

    public static synchronized StatusZoo getInstance() throws ZooException {
        if (instance == null) {
            instance = new StatusZoo();
        }
        return instance;
    }

    public static synchronized void resetInstance() throws ZooException {
        instance = new StatusZoo();
    }

    // O restante dos métodos permanece igual
    public boolean isEstaAberto() {
        return estaAberto;
    }

    public int getNumeroVisitantes() {
        return numeroVisitantes;
    }

    public String abrirZoologico() {
        if (!estaAberto) {
            estaAberto = true;
            StringBuilder status = new StringBuilder();
            status.append("=== Zoológico Aberto ===\n");
            status.append("• Animais saindo das jaulas:\n");
            for (Animal animal : animais) {
                status.append("- ").append(animal.getNome()).append(" foi para exposição\n");
            }
            status.append("\n• Funcionários iniciando trabalho:\n");
            for (Funcionario func : funcionarios) {
                if (!func.isEstaDeFerias()) {
                    status.append("- ").append(func.getNome()).append(" (").append(func.getCargo()).append(") iniciou suas atividades\n");
                }
            }
            status.append("\nZoológico pronto para receber visitantes!");
            return status.toString();
        }
        return "O zoológico já está aberto!";
    }

    public String fecharZoologico() {
        if (estaAberto) {
            estaAberto = false;
            StringBuilder status = new StringBuilder();
            status.append("=== Fechando o Zoológico ===\n");
            status.append("• Retornando animais para as jaulas:\n");
            for (Animal animal : animais) {
                status.append("- ").append(animal.getNome()).append(" voltou para sua jaula\n");
            }
            status.append("\n• Funcionários encerrando expediente:\n");
            for (Funcionario func : funcionarios) {
                if (!func.isEstaDeFerias()) {
                    status.append("- ").append(func.getNome()).append(" encerrou suas atividades\n");
                }
            }
            if (numeroVisitantes > 0) {
                status.append("\n• ").append(numeroVisitantes).append(" visitantes foram embora");
                numeroVisitantes = 0;
            }
            return status.toString();
        }
        return "O zoológico já está fechado!";
    }

    public List<Animal> getAnimais() {
        return new ArrayList<>(animais);
    }

    public List<Funcionario> getFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public void adicionarVisitantes(int quantidade) {
        this.numeroVisitantes += quantidade;
    }
}