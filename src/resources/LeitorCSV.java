package src.resources;

import src.zoo.Animal;
import src.zoo.Funcionario;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

                    animal.setEstaSaudavel(Boolean.parseBoolean(dados[dados.length-1].trim()));
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

        if (!Files.exists(path)) {
            System.err.println("Arquivo de funcionários não encontrado: " + caminhoArquivo);
            return funcionarios;
        }

        try (BufferedReader br = Files.newBufferedReader(path)) {
            // Pular cabeçalho
            br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(",", -1);
                if (dados.length >= 4) {
                    try {
                        Funcionario funcionario = new Funcionario(
                                dados[0].trim(),
                                Integer.parseInt(dados[1].trim()),
                                dados[2].trim(),
                                Double.parseDouble(dados[3].trim())
                        );

                        // Carregar status de férias (sem disparar salvamento)
                        if (dados.length > 4 && !dados[4].trim().isEmpty()) {
                            boolean ferias = Boolean.parseBoolean(dados[4].trim());
                            if (ferias) {
                                funcionario.tirarFerias(); // Versão sem salvamento
                            }
                        }

                        // Carregar tarefa atual (sem disparar salvamento)
                        if (dados.length > 5) {
                            funcionario.setTarefaAtual(dados[5].trim()); // Versão sem salvamento
                        }

                        funcionarios.add(funcionario);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao processar linha: " + linha);
                        throw new IOException("Formato inválido nos dados do funcionário: " + linha, e);
                    }
                }
            }
        }

        return funcionarios;
    }
}