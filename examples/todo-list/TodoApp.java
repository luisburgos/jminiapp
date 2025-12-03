package com.jminiapp.examples.todo;

import org.jminiapp.JMiniApp;

import java.util.Scanner;

public class TodoApp extends JMiniApp<TodoState> {
    private TodoState state;
    private final TodoJSONAdapter adapter;

    public TodoApp(TodoJSONAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void initialize() {
        try {
            state = adapter.load();
            System.out.println("Todo List inicializada. Tareas cargadas: " + state.getTasks().size());
        } catch (Exception e) {
            System.out.println("Error al cargar estado: " + e.getMessage());
            state = new TodoState();
        }
    }

    @Override
    protected void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Mi Lista de Tareas ---");
            System.out.println("1. Añadir tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Completar/Eliminar tarea");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Escribe la tarea: ");
                    String task = scanner.nextLine();
                    state.getTasks().add(task);
                    System.out.println("Tarea añadida: " + task);
                    break;
                case 2:
                    if (state.getTasks().isEmpty()) {
                        System.out.println("No hay tareas.");
                    } else {
                        for (int i = 0; i < state.getTasks().size(); i++) {
                            System.out.println((i + 1) + ". " + state.getTasks().get(i));
                        }
                    }
                    break;
                case 3:
                    System.out.print("Número de tarea a eliminar (completar): ");
                    int index = scanner.nextInt() - 1;
                    if (index >= 0 && index < state.getTasks().size()) {
                        String removed = state.getTasks().remove(index);
                        System.out.println("Tarea completada/eliminada: " + removed);
                    } else {
                        System.out.println("Índice inválido.");
                    }
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    @Override
    protected void shutdown() {
        try {
            adapter.save(state);
            System.out.println("Estado guardado. Tareas restantes: " + state.getTasks().size());
        } catch (Exception e) {
            System.out.println("Error al guardar estado: " + e.getMessage());
        }
    }
}