# Damage Calculator Example

This example demonstrates how to implement a stateful console application using the JMiniApp framework.

## Features
- Reads attack, defense and multiplier values.
- Calculates final damage using a simple RPG-style formula.
- Stores a history list using JMiniApp state management.
- Saves and loads state across sessions using a JSON adapter.

## How to Run

Inside the root folder of the project:

```bash
mvn clean install
```

Then go to the example folder:

```bash
cd examples/damagecalculator
mvn exec:java
```

## Author
Waldo Cámara Villacís
