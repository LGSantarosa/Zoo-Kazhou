package zoo.animais;

import zoo.model.Ocorrencia;
import zoo.animais.comportamento.ComportamentoAlimentar;
import zoo.animais.comportamento.ComportamentoSonoro;
import zoo.animais.comportamento.ComportamentoAmbiente;
import zoo.animais.caracteristicas.CaracteristicasFisicas;
import zoo.animais.caracteristicas.CaracteristicasComportamentais;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Classe 1: Animal
public class Animal {
    private String id;
    private String nome;
    private int idade;
    private String especie;
    private List<Ocorrencia> ocorrencias;
    
    // Novos atributos
    private LocalDateTime dataEntrada;
    private String procedencia;
    private String statusSaude; // "SAUDÁVEL", "EM_TRATAMENTO", "CRÍTICO"
    private List<String> vacinas;
    private String dietaEspecial;
    private boolean emQuarentena;
    private String localizacaoAtual;
    private List<String> cuidadosEspeciais;
    private String responsavelVeterinario;
    private double ultimoPesoRegistrado;
    
    // Composição de comportamentos
    private ComportamentoAlimentar comportamentoAlimentar;
    private ComportamentoSonoro comportamentoSonoro;
    private ComportamentoAmbiente comportamentoAmbiente;
    
    // Características do animal
    private CaracteristicasFisicas caracteristicasFisicas;
    private CaracteristicasComportamentais caracteristicasComportamentais;

    // Construtor padrão para uso do Gson
    public Animal() {
        this.ocorrencias = new ArrayList<>();
        this.vacinas = new ArrayList<>();
        this.cuidadosEspeciais = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.dataEntrada = LocalDateTime.now();
        this.statusSaude = "SAUDÁVEL";
        this.emQuarentena = false;
    }

    public Animal(String nome, int idade, String especie) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.ocorrencias = new ArrayList<>();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public List<Ocorrencia> getOcorrencias() { return ocorrencias; }
    
    // Getters e Setters de comportamentos
    public ComportamentoAlimentar getComportamentoAlimentar() { return comportamentoAlimentar; }
    public void setComportamentoAlimentar(ComportamentoAlimentar comportamentoAlimentar) { 
        this.comportamentoAlimentar = comportamentoAlimentar; 
    }
    
    public ComportamentoSonoro getComportamentoSonoro() { return comportamentoSonoro; }
    public void setComportamentoSonoro(ComportamentoSonoro comportamentoSonoro) { 
        this.comportamentoSonoro = comportamentoSonoro; 
    }
    
    public ComportamentoAmbiente getComportamentoAmbiente() { return comportamentoAmbiente; }
    public void setComportamentoAmbiente(ComportamentoAmbiente comportamentoAmbiente) { 
        this.comportamentoAmbiente = comportamentoAmbiente; 
    }
    
    // Getters e Setters de características
    public CaracteristicasFisicas getCaracteristicasFisicas() { return caracteristicasFisicas; }
    public void setCaracteristicasFisicas(CaracteristicasFisicas caracteristicasFisicas) { 
        this.caracteristicasFisicas = caracteristicasFisicas; 
    }
    
    public CaracteristicasComportamentais getCaracteristicasComportamentais() { return caracteristicasComportamentais; }
    public void setCaracteristicasComportamentais(CaracteristicasComportamentais caracteristicasComportamentais) { 
        this.caracteristicasComportamentais = caracteristicasComportamentais; 
    }

    // Novos métodos específicos
    public void registrarVacina(String vacina) {
        if (!vacinas.contains(vacina)) {
            vacinas.add(vacina);
            adicionarOcorrencia(new Ocorrencia("Vacinação", "Aplicada vacina: " + vacina));
        }
    }

    public void atualizarStatusSaude(String novoStatus) {
        this.statusSaude = novoStatus;
        adicionarOcorrencia(new Ocorrencia("Atualização de Saúde", 
            "Status alterado para: " + novoStatus));
    }

    public void iniciarQuarentena(String motivo) {
        this.emQuarentena = true;
        adicionarOcorrencia(new Ocorrencia("Quarentena", 
            "Iniciada quarentena. Motivo: " + motivo));
    }

    public void finalizarQuarentena() {
        this.emQuarentena = false;
        adicionarOcorrencia(new Ocorrencia("Quarentena", 
            "Quarentena finalizada com sucesso"));
    }

    public void registrarPeso(double peso) {
        this.ultimoPesoRegistrado = peso;
        adicionarOcorrencia(new Ocorrencia("Peso", 
            "Peso registrado: " + peso + "kg"));
    }

    public void adicionarCuidadoEspecial(String cuidado) {
        if (!cuidadosEspeciais.contains(cuidado)) {
            cuidadosEspeciais.add(cuidado);
            adicionarOcorrencia(new Ocorrencia("Cuidado Especial", 
                "Adicionado cuidado: " + cuidado));
        }
    }

    public void transferirLocalizacao(String novaLocalizacao) {
        String localizacaoAnterior = this.localizacaoAtual;
        this.localizacaoAtual = novaLocalizacao;
        adicionarOcorrencia(new Ocorrencia("Transferência", 
            "Transferido de " + localizacaoAnterior + " para " + novaLocalizacao));
    }

    public void designarVeterinario(String veterinario) {
        this.responsavelVeterinario = veterinario;
        adicionarOcorrencia(new Ocorrencia("Veterinário", 
            "Designado veterinário: " + veterinario));
    }

    public void definirDietaEspecial(String dieta) {
        this.dietaEspecial = dieta;
        adicionarOcorrencia(new Ocorrencia("Dieta", 
            "Definida dieta especial: " + dieta));
    }

    public boolean precisaAtencaoImediata() {
        return statusSaude.equals("CRÍTICO") || 
               emQuarentena || 
               cuidadosEspeciais.size() > 3;
    }

    // Métodos de comportamento
    public void alimentar() {
        if (comportamentoAlimentar != null) {
            comportamentoAlimentar.alimentar();
        }
    }

    public void emitirSom() {
        if (comportamentoSonoro != null) {
            comportamentoSonoro.emitirSom();
        }
    }

    public void adaptarAmbiente() {
        if (comportamentoAmbiente != null) {
            comportamentoAmbiente.adaptarAmbiente();
        }
    }

    public void adicionarOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
    }
}