# Study Planner Example

This is a small console app built using the JMiniApp framework.  
The idea is simple: you can keep track of study tasks, set deadlines, add topics, and mark things as done.  
Nothing fancy — just a clean example of how to use the framework.

## What you can do

- Add a study task with subject, topic, deadline, etc.
- Change the status (`pending`, `in-progress`, `done`)
- List tasks by subject
- See the total estimated study time
- Export or import your tasks using JSON

## How to run it

From the root folder of the project:

```bash
mvn clean install

then:
cd examples/study-planner
mvn exec:java

You should see a menu like this:

=== Study Planner App ===
Welcome to the Study Planner!

Files inside this example:
1. StudyTask.java: the structure/model of a study task
2. StudyPlannerApp.java: the main app logic
3. StudyPlannerJSONAdapter.java: handles JSON conversions
4. StudyPlannerAppRunner.java: the class that starts the app

Author: Amauri Sandoval, Software Engineering Student.

