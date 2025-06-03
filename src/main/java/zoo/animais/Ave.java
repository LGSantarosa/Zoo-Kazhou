package zoo.animais;

//Classe 3: Ave
public abstract class Ave extends Animal {
    private double envergaduraAsa;

    public Ave(String nome, int idade, double peso, String habitat, double envergaduraAsa) {
        super(nome, idade, peso, habitat);
        this.envergaduraAsa = envergaduraAsa;
    }

    public double getEnvergaduraAsa() { return envergaduraAsa; }
    public void setEnvergaduraAsa(double envergaduraAsa) { this.envergaduraAsa = envergaduraAsa; }

    public abstract void voar();
}