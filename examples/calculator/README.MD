# Mini Calculator App

A Mini Calculator App with basics functions

# Overview

This README helps you understand the way to execute the App.

#Features

1.-An add function
2.-A subtract function
3.-A multiply function
4.-A divide function

# Project Structure

calculator/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/calculator/
    ├── MiniCalculatorApp.java          # Main application class
    ├── MiniCalculatorAppRunner.java    # Bootstrap configuration
    └──MiniCalculatorAppModel.java     # Contains calculator logic


# How to execute the proyect

1.- Do a clean package on the command prompt or on any terminal of your preference : "mvn clean package"
2.- After that direct yourself to the Mini Calculator App folders using the command : "cd examples/calculator"
3.- Finally use the maven command to execute a Java program: "mvn exec:java"

# Key Components

### MiniCalculatorApp

 The class that implements the JMiniApp

 1.-Initialize : As the name says initializes all the components of the class
 2.-Shutdown: Closes the scanner and gives a goodbye message
 3.-Run: Displays menu and handles user inputs

### MiniCalculatorModel

Contains calculator logic

### MiniCalculatorAppRunner

Registers MiniCalculatorApp with JMiniAppRunner



#Author information

Osmar Lopez Delgado 
Universidad Autonoma de Yucatan
Arquitectura de Software