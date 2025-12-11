package dev.jminiapp.examples.todolist;

import com.jminiapp.core.JMiniApp;
import com.jminiapp.core.JMiniAppConfig;
import com.jminiapp.core.JMiniAppRunner;
import java.util.ArrayList;
import java.util.List;

public class ToDoListApp extends JMiniApp {
    private List<TodoItem> items;

    public ToDoListApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        List<TodoItem> data = context.getData(TodoItem.class);
        items = (data == null || data.isEmpty()) ? new ArrayList<>() : new ArrayList<>(data);
        if (items.isEmpty()) {
            items.add(new TodoItem("1", "Ejemplo: Comprar leche", false));
        }
    }

    @Override
    protected void run() {
        System.out.println("ToDoList inicializada. Items:");
        items.forEach(i -> System.out.println(i.getId() + " - " + i.getText() + " [" + (i.isDone() ? "x" : " ") + "]"));
    }

    @Override
    protected void shutdown() {
        context.setData(items);
    }

    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(ToDoListApp.class)
            .withState(TodoItem.class)
            .run(args);
    }
}
