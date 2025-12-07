# Study Planner (Example Tutorial)

This tutorial shows how this Study Planner example was built using the JMiniApp framework.  
The goal is simple: build a small console app where you can add study tasks, change their status, list them, and export/import everything as JSON.

Nothing complicated — this is just a friendly walkthrough so anyone can recreate the example from scratch.

---

## 1. Create the example folder

Inside the project:
examples/
study-planner/


Inside that folder, create:
src/main/java/com/jminiapp/examples/studyplanner/

This is where all the Java files will go.

---

## 2. Add the `pom.xml` for this module

Inside `examples/study-planner/`, create a file named `pom.xml` and paste this:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.jminiapp</groupId>
        <artifactId>jminiapp-parent</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../..</relativePath>
    </parent>

    <artifactId>study-planner-example</artifactId>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.jminiapp.examples.studyplanner.StudyPlannerAppRunner</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>com.jminiapp</groupId>
            <artifactId>jminiapp-core</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>

</project>

#3. Create the task model: StudyTask.java

Path: src/main/java/com/jminiapp/examples/studyplanner/

create the file: 

package com.jminiapp.examples.studyplanner;

public class StudyTask {

    private int id;
    private String subject;
    private String topic;
    private String deadline;
    private int estimatedMinutes;
    private String status;

    public StudyTask() {}

    public StudyTask(int id, String subject, String topic,
                     String deadline, int estimatedMinutes) {
        this.id = id;
        this.subject = subject;
        this.topic = topic;
        this.deadline = deadline;
        this.estimatedMinutes = estimatedMinutes;
        this.status = "pending";
    }

    // Getters, setters + toString()
}

#4. Create the main app: StudyPlannerApp.java

This class handles:

1. menu display
2. adding tasks
3. changing status
4. listing by subject
5. exporting/importing JSON
6.saving state on exit

Code:

package com.jminiapp.examples.studyplanner;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;

import java.io.IOException;
import java.util.*;

public class StudyPlannerApp extends JMiniApp {

    private Scanner scanner;
    private List<StudyTask> tasks;
    private boolean running;
    private int nextId;

    public StudyPlannerApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        scanner = new Scanner(System.in);
        tasks = new ArrayList<>();
        nextId = 1;
    }

    @Override
    protected void run() {
        running = true;

        while (running) {
            printMenu();
            handleInput();
        }
    }

    @Override
    protected void shutdown() {
        context.setData(tasks);
        System.out.println("Saving tasks...");
    }

    private void printMenu() {
        System.out.println("\n--- Study Planner ---");
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
        } else {
            for (StudyTask t : tasks)
                System.out.println("- " + t);
        }

        System.out.println("""
                Options:
                1. Add task
                2. Change status
                3. List by subject
                4. Total study time
                5. Export JSON
                6. Import JSON
                7. Exit
                """);

        System.out.print("Choose an option: ");
    }

    private void handleInput() {
        String input = scanner.nextLine().trim();

        switch (input) {
            case "1" -> addTask();
            case "2" -> changeStatus();
            case "3" -> listBySubject();
            case "4" -> totalTime();
            case "5" -> exportJSON();
            case "6" -> importJSON();
            case "7" -> running = false;
            default -> System.out.println("Invalid option.");
        }
    }

    private void addTask() {
        System.out.print("Subject: ");
        String subject = scanner.nextLine();

        System.out.print("Topic: ");
        String topic = scanner.nextLine();

        System.out.print("Deadline (YYYY-MM-DD): ");
        String deadline = scanner.nextLine();

        System.out.print("Estimated minutes: ");
        int minutes = Integer.parseInt(scanner.nextLine());

        StudyTask task = new StudyTask(nextId++, subject, topic, deadline, minutes);
        tasks.add(task);

        System.out.println("Task added!");
    }

    private void changeStatus() {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        StudyTask task = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (task == null) {
            System.out.println("Not found.");
            return;
        }

        System.out.print("New status (pending/in-progress/done): ");
        task.setStatus(scanner.nextLine());

        System.out.println("Status updated!");
    }

    private void listBySubject() {
        System.out.print("Subject: ");
        String subject = scanner.nextLine();

        tasks.stream()
                .filter(t -> t.getSubject().equalsIgnoreCase(subject))
                .forEach(System.out::println);
    }

    private void totalTime() {
        int total = tasks.stream().mapToInt(StudyTask::getEstimatedMinutes).sum();
        System.out.println("Total time: " + total + " minutes");
    }

    private void exportJSON() {
        try {
            context.setData(tasks);
            context.exportData("json");
            System.out.println("Exported!");
        } catch (IOException e) {
            System.out.println("Error exporting.");
        }
    }

    private void importJSON() {
        try {
            context.importData("json");
            tasks = context.getData();
            System.out.println("Imported!");
        } catch (IOException e) {
            System.out.println("Error importing.");
        }
    }
}

#5. JSON Adapter:

package com.jminiapp.examples.studyplanner;

import com.jminiapp.core.adapters.JSONAdapter;

public class StudyPlannerJSONAdapter implements JSONAdapter {
    @Override
    public Class getstateClass() {
        return StudyTask.class;
    }
}

package com.jminiapp.examples.studyplanner;

import com.jminiapp.core.adapters.JSONAdapter;

public class StudyPlannerJSONAdapter implements JSONAdapter {
    @Override
    public Class getstateClass() {
        return StudyTask.class;
    }
}
 
#6. App runner:

package com.jminiapp.examples.studyplanner;

import com.jminiapp.core.engine.JMiniAppRunner;

public class StudyPlannerAppRunner {

    public static void main(String[] args) {
        JMiniAppRunner
                .forApp(StudyPlannerApp.class)
                .withState(StudyTask.class)
                .withAdapters(new StudyPlannerJSONAdapter())
                .named("StudyPlanner")
                .run(args);
    }
}

#7. Build and run:

From the root:

mvn clean install

Then run:

cd examples/study-planner
mvn exec:java

You should now see the menu and be able to use the Study Planner.

End of tutorial :)
