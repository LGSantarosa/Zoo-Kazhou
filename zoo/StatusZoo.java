package zoo;

import resources.LeitorCSV;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatusZoo {
    private boolean estaAberto;
    private List<Animal> animais;
    private List<Funcionario> funcionarios;
    private int numeroVisitantes;

    public StatusZoo() throws ZooException, IOException {
        this.estaAberto = false;
        this.animais = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.numeroVisitantes = 0;

        carregarDados();
    }

    private void carregarDados() throws IOException, ZooException {
        try {
            this.animais = LeitorCSV.carregarAnimais("src/resources/animais.csv");
            System.out.println("Animais carregados: " + animais.size());
        } catch (IOException e) {
            throw new ZooException("Falha ao carregar animais: " + e.getMessage(), e);
        }

        try {
            this.funcionarios = LeitorCSV.carregarFuncionarios("src/resources/funcionarios.csv");
            System.out.println("Funcionários carregados: " + funcionarios.size());
        } catch (IOException e) {
            this.animais.clear(); // Limpa dados parciais
            throw new ZooException("Falha ao carregar funcionários: " + e.getMessage(), e);
        }
    }


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
                status.append("- ").append(func.getNome()).append(" iniciou suas atividades\n");
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
                status.append("- ").append(func.getNome()).append(" encerrou suas atividades\n");
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
        return animais;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

}
