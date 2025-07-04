package src.resources;

import src.zoo.Animal;
import src.zoo.Funcionario;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.List;

public class EscritorCSV {
    public static void salvarAnimais(String caminhoArquivo, List<Animal> animais) throws IOException {

        //BufferWriter para escrever no csv caminhoArquivo, vulgo animais.csv (declaração disso direto em Animal)
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(caminhoArquivo))) {
            writer.write("nome,idade,especie,peso,habitat,saudavel\n");

            //Divide com base em como está a formatação de animais.csv, usando ,
            for (Animal animal : animais) {
                writer.write(String.format(
                        "%s,%d,%s,%.1f,%s,%b\n",
                        animal.getNome(),
                        animal.getIdade(),
                        animal.getEspecie(),
                        animal.getPeso(),
                        animal.getHabitat(),
                        animal.examinarSaude()
                ));
            }
        }
    }

    private static String escapeCsv(String value) {
        return value.replace(",", "").replace("\n", "").replace("\r", "");
    }

    public static void salvarFuncionarios(String caminhoArquivo,
                                          List<Funcionario> funcionarios,
                                          boolean evitarRecursao) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(caminhoArquivo))) {
            writer.write("nome,idade,cargo,salario,ferias,tarefa\n");

            //Basicamente a mesma coisa de animais, mas agora para funcionarios
            for (Funcionario func : funcionarios) {
                writer.write(String.format(
                        "%s,%d,%s,%.2f,%b,%s\n",
                        func.getNome(),
                        func.getIdade(),
                        func.getCargo(),
                        func.getSalario(),
                        func.isEstaDeFerias(),
                        func.getTarefaAtual() != null ? func.getTarefaAtual() : "Nenhuma"
                ));

                if (!evitarRecursao) {
                    writer.flush();
                }
            }
        }
    }
}