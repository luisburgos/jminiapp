# School Control App

This tutorial explains how to build the **School Control App** using the JMiniApp framework.

## Step 1: Create the folder
Inside the `examples/` directory, create a new folder called `school-control`.

## Step 2: Define the Student class
Create a file named `Student.java` with the following attributes:
- `id`: Student identifier
- `name`: Student's name
- `grade`: Student's grade

Include methods such as:
- `updateGrade(int newGrade)`: Update the student's grade

## Step 3: Implement the JSON Adapter
Create `SchoolControlJSONAdapter.java` extending `JSONAdapter<Student>`.  
This adapter will handle serialization and deserialization of student data to and from JSON.

## Step 4: Implement the App
Create `SchoolControlApp.java` extending `JMiniApp`.  
Implement the following methods:
- `initialize()`: Load existing student data
- `run()`: Display the interactive menu and handle user input
- `shutdown()`: Save student data before exiting

## Step 5: Bootstrap the App
Create `SchoolControlAppRunner.java` using `JMiniAppRunner`.  
Register the `SchoolControlJSONAdapter` with `.withAdapters()` and configure the app name and model class.

## Step 6: Build the project
From the **project root** (not the examples/school-control directory):
```bash
mvn clean install
```
## Step 7: Run the App

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

## Step 8: Usage Example
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