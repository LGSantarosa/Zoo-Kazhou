package zoo.utils;

import java.io.*;
import java.util.*;

public class LeitorCSV {
    public static List<Animal> lerAnimais(String arquivo) throws IOException {
        List<Animal> animais = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;
        while ((linha = br.readLine()) != null) {
            // Lógica de leitura do CSV
        }
        return animais;
    }
} 