package org.example;

import io.javalin.Javalin;
import org.example.Model.Tarefa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static Javalin startApp() {
        Javalin app = Javalin.create();

        app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));

        app.get("/status", ctx -> {
            Map<String, String> status = new HashMap<>();
            status.put("status", "ok");
            status.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            ctx.json(status);
        });

        app.post("/echo", ctx -> {
            Map<String, String> mensagem = ctx.bodyAsClass(Map.class);
            ctx.json(mensagem);
        });

        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            ctx.json(Map.of("mensagem", "Olá, " + nome + "!"));
        });

        app.post("/tarefas", ctx -> {
            Tarefa novaTarefa = ctx.bodyAsClass(Tarefa.class);
            novaTarefa.setDataCriacao(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            RepositorioTarefas.adicionarTarefa(novaTarefa);
            ctx.status(201).json(novaTarefa);
        });

        app.get("/tarefas", ctx -> {
            List<Tarefa> tarefas = RepositorioTarefas.getTarefas();
            ctx.json(tarefas);
        });

        app.get("/tarefas/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Tarefa encontrada = RepositorioTarefas.buscarPorId(id);
            if (encontrada == null) {
                ctx.status(404).result("Tarefa não encontrada.");
            } else {
                ctx.json(encontrada);
            }
        });

        app.start(7000);
        return app;
    }

    public static void main(String[] args) {
        startApp();
    }
}
