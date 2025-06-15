package zoo;

public class Funcionario extends SerVivo {
    private String cargo;
    private double salario;
    private boolean estaDeFerias;

    public Funcionario(String nome, int idade, String cargo, double salario) {
        super(nome, idade);
        this.cargo = cargo;
        this.salario = salario;
        this.estaDeFerias = false;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String emitirSom() {
        return "Olá!";
    }

    public void tirarFerias() {
        this.estaDeFerias = true;
        System.out.println(getNome() + " entrou em férias");
    }

    public void voltarAoTrabalho() {
        this.estaDeFerias = false;
        System.out.println(getNome() + " retornou ao trabalho");
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " não deve dormir durante o trabalho!");
    }

    public void alimentarAnimal(Animal animal, String alimento) {
        System.out.println(getNome() + " está alimentando " + animal.getNome());
        animal.comer(alimento);
    }

    public void limparHabitat(Animal animal) {
        System.out.println(getNome() + " está limpando o habitat de " + animal.getNome());
    }
}
