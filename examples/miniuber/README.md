# MiniUber Example

A simple Mini Uber application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a mini-app using JMiniApp core that manages a list of drivers and their earnings. Users can register drivers, import/export driver data to JSON, and view total drivers registered.

## Features

- **Register Driver**: Add a new driver to the system
- **Export to JSON**: Save the current list of drivers to a JSON file
- **Import from JSON**: Load a driver list from a JSON file
- **Persistent State**: Drivers and earnings are maintained in the app state

## Project Structure


## Key Components

### Driver
A simple model class that represents a driver with fields and methods to:
- `getName()`: Return the driver's name
- `getEarnings()`: Return the driver's total earnings
- `addEarnings(amount)`: Add money to the driver's earnings

### DriverJSONAdapter
A format adapter that enables JSON import/export for `Driver`:
- Implements `JSONAdapter<Driver>` from the framework
- Registers with the framework during app bootstrap
- Provides automatic serialization/deserialization

### MiniUberApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Set up the app and load existing driver state
- `run()`: Main loop displaying menu and handling user input
- `shutdown()`: Save driver state before exiting
- Uses framework's `context.importData()` and `context.exportData()` for file operations

### MiniUberAppRunner
Bootstrap configuration that:
- Registers the `DriverJSONAdapter` with `.withAdapters()`
- Configures the app name and model class
- Launches the application

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/miniuber directory):

```bash
mvn clean install
