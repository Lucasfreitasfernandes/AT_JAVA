package org.example;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.regex.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteMain {

    private static final String BASE_URL = "http://localhost:7000";
    private static int lastCreatedId;

    @BeforeAll
    static void setUp() throws InterruptedException {
        Main.startApp();
        Thread.sleep(1000);
    }

    @Test
    @Order(1)
    void testHelloEndpoint() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/hello"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("Hello, Javalin!", response.body());
    }

    @Test
    @Order(2)
    void testCreateTarefa() throws IOException, InterruptedException {
        String json = """
            {
              "titulo": "Estudar",
              "descricao": "JUnit",
              "concluida": false
            }
            """;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tarefas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        Pattern pattern = Pattern.compile("\"id\":(\\d+)");
        Matcher matcher = pattern.matcher(response.body());
        assertTrue(matcher.find(), "Resposta não contém ID");

        lastCreatedId = Integer.parseInt(matcher.group(1));
    }

    @Test
    @Order(3)
    void testGetTarefaById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tarefas/" + lastCreatedId))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("JUnit"));
    }

    @Test
    @Order(4)
    void testListTarefas() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tarefas"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().startsWith("["));
        assertTrue(response.body().length() > 2);
    }
}
