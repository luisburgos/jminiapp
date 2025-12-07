# JMiniApp

A lightweight educational Java framework for building standalone mini applications with seamless and flexible state persistence using multiple file formats.

## What is JMiniApp?

JMiniApp simplifies the development of standalone mini applications by providing:

- **Seamless State Persistence** - Automatic state management with file-based storage
- **Multi-Format Support** - Built-in support for JSON, CSV, and custom formats
- **Clean Lifecycle Management** - Well-defined phases (initialize, run, shutdown)
- **Extensible Architecture** - Easy-to-implement adapters for custom data formats
- **Zero External Dependencies** - Minimal footprint for core functionality

## Quick Example

```java
public class HelloApp extends JMiniApp {
    private Greeting greeting;

    public HelloApp(JMiniAppConfig config) {
        super(config);
    }

    @Override
    protected void initialize() {
        List<Greeting> data = context.getData();
        greeting = data.isEmpty() ? new Greeting() : data.get(0);
    }

    @Override
    protected void run() {
        // Your application logic here
    }

    @Override
    protected void shutdown() {
        context.setData(List.of(greeting));
    }

    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(HelloApp.class)
            .withState(Greeting.class)
            .run(args);
    }
}
```


## Example Application

Check out the **[Counter App](./examples/counter)** - a complete example demonstrating:
- State management with counter increments/decrements
- JSON persistence for saving counter state
- Clean application lifecycle implementation
- Import/export functionality

## Project Structure

```
jminiapp/
├── modules/          # Framework core modules
├── examples/         # Example applications
│   └── counter/      # Counter app example
    └── expense-tracker/  #my app
```

## Building from Source

```bash
# Build the framework
mvn clean install

# Run tests
mvn test

# Build documentation website
cd website
npm install
npm run build
```

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

Autor: Andry Azael Rabanales Andrade