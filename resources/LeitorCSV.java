package resources;
import zoo.Animal;
import zoo.Funcionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {
    public static List<Animal> carregarAnimais(String caminhoArquivo) throws IOException {
        List<Animal> animais = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))){
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                try {
                    String[] dados = linha.split(",");
                    if (dados.length == 5) {
                        Animal animal = new Animal(
                                dados[0].trim(),                    // nome
                                Integer.parseInt(dados[1].trim()),  // idade
                                dados[2].trim(),                    // especie
                                Double.parseDouble(dados[3].trim()),// peso
                                dados[4].trim()                     // habitat
                        );
                        animais.add(animal);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Erro ao processar linha de animal: " + linha, e);
                }
            }
        }

        return animais;
    }

    public static List<Funcionario> carregarFuncionarios(String caminhoArquivo) throws IOException {
        List<Funcionario> funcionarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                try {
                    String[] dados = linha.split(",");
                    if (dados.length == 4) {
                        Funcionario funcionario = new Funcionario(
                                dados[0].trim(),                    // nome
                                Integer.parseInt(dados[1].trim()),  // idade
                                dados[2].trim(),                    // cargo
                                Double.parseDouble(dados[3].trim()) // salario
                        );
                        funcionarios.add(funcionario);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Erro ao processar linha de funcionário: " + linha, e);
                }
            }
        }

        return funcionarios;
    }
}
