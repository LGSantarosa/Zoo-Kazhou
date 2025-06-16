package src.resources;

import src.zoo.Animal;
import src.zoo.Funcionario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {
    public static List<Animal> carregarAnimais(String caminhoArquivo) throws IOException {
        List<Animal> animais = new ArrayList<>();
        Path path = Paths.get(caminhoArquivo);


        try (BufferedReader br = Files.newBufferedReader(path)) {
            // Pular cabeçalho
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",", -1);

                // Corrige a posição do habitat quando há um zero
                String habitat;
                if (dados.length > 5 && dados[4].equals("0")) {
                    habitat = dados[5]; // Pega o valor após o zero
                } else {
                    habitat = dados[4]; // Pega o valor normal
                }

                try {
                    Animal animal = new Animal(
                            dados[0].trim(),
                            Integer.parseInt(dados[1].trim()),
                            dados[2].trim(),
                            Double.parseDouble(dados[3].trim()),
                            habitat.trim()
                    );

                    animal.setEstaSaudavel(Boolean.parseBoolean(dados[dados.length - 1].trim()));
                    animais.add(animal);
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha: " + linha);
                }
            }
        }
        return animais;
    }

    public static List<Funcionario> carregarFuncionarios(String caminhoArquivo) throws IOException {
        List<Funcionario> funcionarios = new ArrayList<>();
        Path path = Paths.get(caminhoArquivo);

        // Se o arquivo não existe, cria um novo
        if (!Files.exists(path)) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
                writer.write("nome,idade,cargo,salario,ferias,tarefa\n");
            }
            return funcionarios;
        }

        try (BufferedReader br = Files.newBufferedReader(path)) {
            // Pular cabeçalho
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",", -1);

                // Corrige a posição quando há campos extras (como "00")
                boolean temCampoExtra = dados.length > 6 && dados[4].equals("00");
                int indiceFerias = temCampoExtra ? 5 : 4;
                int indiceTarefa = temCampoExtra ? 6 : 5;

                try {
                    Funcionario func = new Funcionario(
                            dados[0].trim(),
                            Integer.parseInt(dados[1].trim()),
                            dados[2].trim(),
                            Double.parseDouble(dados[3].trim())
                    );

                    // Configura férias
                    if (indiceFerias < dados.length) {
                        boolean ferias = Boolean.parseBoolean(dados[indiceFerias].trim());
                        if (ferias) func.tirarFerias();
                    }

                    // Configura tarefa
                    if (indiceTarefa < dados.length && !dados[indiceTarefa].equalsIgnoreCase("null")) {
                        func.setTarefaAtual(dados[indiceTarefa].trim());
                    }

                    funcionarios.add(func);
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha: " + linha);
                }
            }
        }
        return funcionarios;
    }
}