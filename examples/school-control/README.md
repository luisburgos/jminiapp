# School Control Example

A simple school control application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a mini-app using JMiniApp core that manages a list of students. Users can add students, list them, update their grades, and persist the data through JSON import/export.

## Features

- **Add Student**: Register a new student with name and grade
- **List Students**: Display all registered students
- **Update Grade**: Modify the grade of an existing student
- **Export to JSON**: Save the student list to a JSON file
- **Import from JSON**: Load student data from a JSON file
- **Persistent State**: Student records are maintained in the app state

## Project Structure
```
school-control/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/schoolcontrol/
    ├── SchoolControlApp.java          # Main application class
    ├── SchoolControlAppRunner.java    # Bootstrap configuration
    ├── Student.java        # Student model
    └── SchoolControlJSONAdapter.java  # JSON format adapter
```

## Key Components

### Student
A model class representing a student with:
-`id`: Student's id
- `name`: Student's name
- `grade`: Student's grade

Includes methods to:
- `updateGrade(int newGrade)`: Update the student's grade

### SchoolControlJSONAdapter
A format adapter that enables JSON import/export for `Student`:
- Extends `JSONAdapter<Student>` from the framework
- Registers with the framework during app bootstrap
- Handles serialization/deserialization of student data

### SchoolControlApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Load existing student data
- `run()`: Display menu and handle user input
- `shutdown()`: Save student data before exiting
- Uses framework's `context.importData()` and `context.exportData()` for file operations

### SchoolControlAppRunner
Bootstrap configuration that:
- Registers `SchoolControlJSONAdapter` with `.withAdapters()`
- Configures the app name and model class
- Launches the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/school-control directory):
```bash
mvn clean install
```
### Run the application

Option 1: Using Maven exec plugin (from the examples/school-control directory)
```bash
cd examples/school-control
mvn exec:java
```

Option 2: Using the packaged JAR (from the examples/school-control directory)
```bash
cd examples/school-control
java -jar target/school-control-app.jar
```

Option 3: From the project root
```bash
cd examples/school-control && mvn exec:java
```
## Usage Example

### Basic Operations
```
=== School Control App ===
1. Add student
2. List students
3. Update grade
4. Export to JSON file
5. Import from JSON file
6. Exit

Choose an option: 1
Enter student ID: 2020
Enter student name: Damian
Enter grade: 99
Student added successfully!

Choose an option: 2
2020 - Damian (Grade: 99)

Choose an option: 3
Enter student ID to update: 2020
Enter new grade: 100
Grade updated successfully!

```

### Export to JSON

```
Choose an option: 4
Student list exported successfully to: SchoolControl.json
```
The exported JSON file will look like:
```json
[
  {
    "id": "2020",
    "name": "Damian",
    "grade": 100
  }
]

```
### Import from JSON
```
Choose an option: 5
Student list imported successfully from SchoolControl.json!

```
## Author
- Name: Israel Damian Villares Matos
- Group: 2