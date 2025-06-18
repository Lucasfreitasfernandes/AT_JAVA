package org.example;

import org.example.Model.Tarefa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleAPP {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> criarTarefa();
                case 2 -> listarTarefas();
                case 3 -> buscarTarefaPorId();
                case 4 -> atualizarTarefa();
                case 5 -> removerTarefa();
                case 0 -> System.out.println("Encerrando o programa.");
                default -> System.out.println("Opçao invalida.");
            }

        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Criar Tarefa");
        System.out.println("2. Listar Tarefas");
        System.out.println("3. Buscar Tarefa por ID");
        System.out.println("4. Atualizar Tarefa");
        System.out.println("5. Remover Tarefa");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opçao: ");
    }

    private static void criarTarefa() {
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(titulo);
        tarefa.setDescricao(descricao);
        tarefa.setConcluida(false);
        tarefa.setDataCriacao(LocalDateTime.now().toString());

        RepositorioTarefas.adicionarTarefa(tarefa);
        System.out.println("Tarefa criada com sucesso.");
    }

    private static void listarTarefas() {
        List<Tarefa> tarefas = RepositorioTarefas.getTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            System.out.println("\n--- Lista de Tarefas ---");
            for (Tarefa t : tarefas) {
                System.out.printf("ID: %d | Titulo: %s | Concluida: %s\n",
                        t.getId(), t.getTitulo(), t.isConcluida() ? "Sim" : "Não");
            }
        }
    }

    private static void buscarTarefaPorId() {
        System.out.print("ID da tarefa: ");
        int id = Integer.parseInt(scanner.nextLine());
        Tarefa tarefa = RepositorioTarefas.buscarPorId(id);
        if (tarefa == null) {
            System.out.println("Tarefa não encontrada.");
        } else {
            System.out.println("\n--- Detalhes da Tarefa ---");
            System.out.println("ID: " + tarefa.getId());
            System.out.println("Titulo: " + tarefa.getTitulo());
            System.out.println("Descriçao: " + tarefa.getDescricao());
            System.out.println("Concluida: " + (tarefa.isConcluida() ? "Sim" : "Nao"));
            System.out.println("Data de Criaçao: " + tarefa.getDataCriacao());
        }
    }

    private static void atualizarTarefa() {
        System.out.print("ID da tarefa a atualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Tarefa tarefa = RepositorioTarefas.buscarPorId(id);
        if (tarefa == null) {
            System.out.println("Tarefa nao encontrada.");
            return;
        }

        System.out.print("Novo titulo (deixe em branco para manter): ");
        String novoTitulo = scanner.nextLine();
        if (!novoTitulo.isEmpty()) {
            tarefa.setTitulo(novoTitulo);
        }

        System.out.print("Nova descriçao (deixe em branco para manter): ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.isEmpty()) {
            tarefa.setDescricao(novaDescricao);
        }

        System.out.print("Está concluida? (s/n): ");
        String concluidaStr = scanner.nextLine();
        tarefa.setConcluida(concluidaStr.equalsIgnoreCase("s"));

        RepositorioTarefas.atualizarTarefa(tarefa);
        System.out.println("Tarefa atualizada.");
    }

    private static void removerTarefa() {
        System.out.print("ID da tarefa a remover: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean removida = RepositorioTarefas.removerPorId(id);
        if (removida) {
            System.out.println("Tarefa removida com sucesso.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }
}
