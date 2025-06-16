package src.zoo;

import src.resources.EscritorCSV;

public class Funcionario extends SerVivo {
    private String cargo;
    private double salario;
    private boolean estaDeFerias;
    private String tarefaAtual;

    public Funcionario(String nome, int idade, String cargo, double salario) {
        super(nome, idade);
        this.cargo = cargo;
        this.salario = salario;
        this.estaDeFerias = false;
        this.tarefaAtual = "Nenhuma";
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    public boolean isEstaDeFerias() {
        return estaDeFerias;
    }

    public String getTarefaAtual() {
        return tarefaAtual;
    }

    // sem salvamento automático
    public void setTarefaAtual(String tarefa) {
        this.tarefaAtual = tarefa;
    }

    // com salvamento explícito
    public void setTarefaAtualESalvar(String tarefa) {
        this.tarefaAtual = tarefa;
        salvarDados();
    }

    public boolean podeReceberTarefa() {
        return !estaDeFerias && tarefaAtual.equals("Nenhuma");
    }

    @Override
    public String emitirSom() {
        return "Olá!";
    }

    // sem salvamento automático
    public void tirarFerias() {
        this.estaDeFerias = true;
        this.tarefaAtual = "Nenhuma";
        System.out.println(getNome() + " entrou em férias");
    }

    // com salvamento
    public void tirarFeriasESalvar() {
        tirarFerias();
        salvarDados();
    }

    // sem salvamento automático
    public void voltarAoTrabalho() {
        this.estaDeFerias = false;
        System.out.println(getNome() + " retornou ao trabalho");
    }

    // com salvamento explícito
    public void voltarAoTrabalhoESalvar() {
        voltarAoTrabalho();
        salvarDados();
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " não deve dormir durante o trabalho!");
    }

    public void alimentarAnimal(Animal animal, String alimento) {
        if (podeReceberTarefa()) {
            System.out.println(getNome() + " está alimentando " + animal.getNome());
            setTarefaAtualESalvar("Alimentando " + animal.getNome());
            animal.comer(alimento);
        }
    }

    public void limparHabitat(Animal animal) {
        if (podeReceberTarefa()) {
            System.out.println(getNome() + " está limpando o habitat de " + animal.getNome());
            setTarefaAtualESalvar("Limpando habitat de " + animal.getNome());
        }
    }

    public void concluirTarefa() {
        setTarefaAtualESalvar("Nenhuma");
    }

    private void salvarDados() {
        try {
            EscritorCSV.salvarFuncionarios(
                    "src/resources/funcionarios.csv",
                    StatusZoo.getInstance().getFuncionarios(),
                    true
            );
        } catch (Exception e) {
            System.err.println("Erro ao salvar funcionários: " + e.getMessage());
        }
    }
}