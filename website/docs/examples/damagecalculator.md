# Damage Calculator Example Tutorial

This tutorial shows how to create a new JMiniApp example from scratch.

# 1. Create the Module Folder

Inside the repository:
```bash
cd examples
mkdir damage-calculator
```
then create the Java package
```bash
mkdir -p damage-calculator/src/main/java/com/jminiapp/examples/damagecalculator
```

# 2. Implement the Source Files

## 2.1. DamageState.java

[text](../../../examples/damage-calc/src/main/java/com/jminiapp/examples/damagecalculator/DamageState.java)

## 2.2. DamageCalculatorApp.java

[text](../../../examples/damage-calc/src/main/java/com/jminiapp/examples/damagecalculator/DamageCalculatorApp.java)

## 2.3. DamageCalculatorRunner.java

[text](../../../examples/damage-calc/src/main/java/com/jminiapp/examples/damagecalculator/DamageCalculatorRunner.java)

# 3. Add the pom.xml File

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
          http://maven.apache.org/POM/4.0.0 
          http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
    <groupId>com.jminiapp</groupId>
    <artifactId>jminiapp-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../..</relativePath>
  </parent>


    <artifactId>damagecalulator</artifactId>
    <name>Damage Calculator Example</name>

     <dependencies>
    <dependency>
      <groupId>com.jminiapp</groupId>
      <artifactId>jminiapp-core</artifactId>
      <version>${project.version}</version>
    </dependency>
  </dependencies>

</project>
```

# 4. Register your example in the Parent pom.xml
Add inside the modules section:
```xml
<module>examples/damage-calculator</module>
```
# 5. Run the example
```bash
mvn clean install
cd examples/damage-calculator
mvn exec:java
```
# 6. Done
You have successfully created a new JMiniApp example.

# Author
Waldo Cámara Villacís







