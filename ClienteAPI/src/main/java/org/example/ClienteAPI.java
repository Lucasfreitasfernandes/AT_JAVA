package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteAPI {

    public static void main(String[] args) throws Exception {
        postTarefa();
        getTarefas();
        getTarefaPorId(1);
        getStatus();
    }

    public static void postTarefa() throws IOException {
        URL url = new URL("http://localhost:7000/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = """
            {
              "titulo": "Teste Tarefa",
              "descricao": "Está tarefa está em teste pelo lucas",
              "concluida": false
            }
        """;

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        System.out.println("Código de resposta: " + conn.getResponseCode());
        conn.getInputStream().transferTo(System.out);
    }

    public static void getTarefas() throws IOException {
        URL url = new URL("http://localhost:7000/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        System.out.println("Código de resposta: " + conn.getResponseCode());
        try (InputStream is = conn.getInputStream()) {
            is.transferTo(System.out);
        }
    }

    public static void getTarefaPorId(int id) throws IOException {
        URL url = new URL("http://localhost:7000/tarefas/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        System.out.println("Código de resposta: " + conn.getResponseCode());
        try (InputStream is = conn.getInputStream()) {
            is.transferTo(System.out);
        }
    }

    public static void getStatus() throws IOException {
        URL url = new URL("http://localhost:7000/status");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        System.out.println("Código de resposta: " + conn.getResponseCode());
        try (InputStream is = conn.getInputStream()) {
            is.transferTo(System.out);
        }
    }
}
