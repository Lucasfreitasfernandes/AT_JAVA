package org.example;

import org.example.Model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTarefas {
    private static final List<Tarefa> tarefas = new ArrayList<>();
    private static int idAtual = 1;

    public static List<Tarefa> getTarefas() {
        return tarefas;
    }

    public static void adicionarTarefa(Tarefa tarefa) {
        tarefa.setId(idAtual++);
        tarefas.add(tarefa);
    }

    public static Tarefa buscarPorId(int id) {
        return tarefas.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public static boolean removerPorId(int id) {
        return tarefas.removeIf(t -> t.getId() == id);
    }

    public static void atualizarTarefa(Tarefa tarefaAtualizada) {
        Tarefa existente = buscarPorId(tarefaAtualizada.getId());
        if (existente != null) {
            existente.setTitulo(tarefaAtualizada.getTitulo());
            existente.setDescricao(tarefaAtualizada.getDescricao());
            existente.setConcluida(tarefaAtualizada.isConcluida());
        }
    }

    public static void resetar() {
        tarefas.clear();
        idAtual = 1;
    }
}
