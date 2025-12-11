# ToDoList Example (Java, JMiniApp)

Este ejemplo muestra cómo crear una pequeña ToDo list con JMiniApp.

## Requisitos
- Java 17
- Maven

## Compilar y ejecutar
1. mvn -pl modules/core -am clean install
2. mvn -f examples/todolist-java/pom.xml clean package
3. mvn -f examples/todolist-java exec:java -Dexec.mainClass="dev.jminiapp.examples.todolist.ToDoListApp"
