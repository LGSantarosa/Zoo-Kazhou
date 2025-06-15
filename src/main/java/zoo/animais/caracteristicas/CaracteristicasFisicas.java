package zoo.animais.caracteristicas;

public class CaracteristicasFisicas {
    private double peso;
    private double altura;
    private String cor;
    private String tipoPele; // pele, penas, escamas, etc.
    private String habitatNatural;

    public CaracteristicasFisicas(double peso, double altura, String cor, String tipoPele, String habitatNatural) {
        this.peso = peso;
        this.altura = altura;
        this.cor = cor;
        this.tipoPele = tipoPele;
        this.habitatNatural = habitatNatural;
    }

    // Getters e Setters
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public String getTipoPele() { return tipoPele; }
    public void setTipoPele(String tipoPele) { this.tipoPele = tipoPele; }
    public String getHabitatNatural() { return habitatNatural; }
    public void setHabitatNatural(String habitatNatural) { this.habitatNatural = habitatNatural; }
} 