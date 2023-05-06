package br.com.unipix.SIPService.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

@Service
public class ArquivoService {
    public static List<String> lerArquivo(String caminho) {
        List<String> linhas = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            // trate a exceção
        }
        return linhas;
    }

    public static void montarArquivoResultado(String resultado) throws IOException {
        FileWriter fileWriter = new FileWriter("resultado" + ".txt", true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(resultado + "\n");
        writer.close();
        fileWriter.close();
    }

    public static void montarArquivoLog(String resultado) throws IOException {
        FileWriter fileWriter = new FileWriter("log" + ".txt", true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(resultado + "\n");
        writer.close();
        fileWriter.close();
    }
}
