package zoo.service;

import zoo.animais.*;
import zoo.model.*;
import zoo.exceptions.AnimalNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZooService {

    private final Map<String, Animal> animais = new ConcurrentHashMap<>();
    private final Map<String, Tarefa> tarefas = new ConcurrentHashMap<>();
    private final Map<String, Funcionario> funcionarios = new ConcurrentHashMap<>();

    public ZooService() {
        // Dados de exemplo
        funcionarios.put("admin", new Funcionario("admin", "admin"));
        
        Animal leao = new Leao("Alex", 5, 190, 0.8);
        animais.put(leao.getId(), leao);

        Animal pinguim = new Pinguim("Capitão", 3, 15);
        animais.put(pinguim.getId(), pinguim);

        Tarefa t1 = new Tarefa("Alimentar os leões");
        tarefas.put(t1.getId(), t1);

        Tarefa t2 = new Tarefa("Limpar o recinto dos pinguins");
        tarefas.put(t2.getId(), t2);
    }

    // Métodos para Animais
    public List<Animal> getAnimais() {
        return new ArrayList<>(animais.values());
    }

    public Animal getAnimalById(String id) throws AnimalNaoEncontradoException {
        Animal animal = animais.get(id);
        if (animal == null) {
            throw new AnimalNaoEncontradoException("ID: " + id);
        }
        return animal;
    }

    public Animal addAnimal(Animal animal) {
        animais.put(animal.getId(), animal);
        return animal;
    }

    public void removeAnimal(String id) throws AnimalNaoEncontradoException {
        if (animais.remove(id) == null) {
            throw new AnimalNaoEncontradoException("ID: " + id);
        }
    }

    public Ocorrencia registrarOcorrencia(String animalId, String descricao) throws AnimalNaoEncontradoException {
        Animal animal = getAnimalById(animalId);
        Ocorrencia ocorrencia = new Ocorrencia(descricao);
        animal.adicionarOcorrencia(ocorrencia);
        return ocorrencia;
    }

    // Métodos para Tarefas
    public List<Tarefa> getTarefas() {
        return new ArrayList<>(tarefas.values());
    }

    public Tarefa addTarefa(Tarefa tarefa) {
        tarefas.put(tarefa.getId(), tarefa);
        return tarefa;
    }

    public void concluirTarefa(String id) {
        Tarefa tarefa = tarefas.get(id);
        if (tarefa != null) {
            tarefa.setConcluida(true);
        }
    }

    // Métodos para Funcionários
    public Funcionario autenticarFuncionario(String login, String senha) {
        Funcionario funcionario = funcionarios.get(login);
        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            return funcionario;
        }
        return null;
    }

    public Funcionario registrarFuncionario(String login, String senha) {
        if (funcionarios.containsKey(login)) {
            throw new IllegalArgumentException("Funcionário com este login já existe.");
        }
        Funcionario novoFuncionario = new Funcionario(login, senha);
        funcionarios.put(login, novoFuncionario);
        return novoFuncionario;
    }
} 