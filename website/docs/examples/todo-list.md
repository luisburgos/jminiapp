---
sidebar_position: 2
---

# Ejemplo de Aplicación de Lista de Tareas

Una aplicación completa e interactiva de lista de tareas que demuestra la gestión del ciclo de vida, estado persistente e interacción del usuario utilizando el framework JMiniApp.

**Características:**
- Agregar nuevas tareas
- Listar todas las tareas con numeración
- Marcar tareas como completadas (eliminándolas)
- Persistencia completa del estado entre ejecuciones
- Almacenamiento basado en JSON usando un adaptador personalizado
- Menú interactivo limpio en la consola

**Código Fuente:** [examples/todo-list](https://github.com/Tony-Pereira05/jminiapp/tree/feat/todo-list/examples/todo-list)

### Conceptos Clave Demostrados

- Ciclo de vida de la aplicación (`initialize`, `run`, `shutdown`)
- Clase de estado personalizada con `Serializable`
- Implementación de un `PersistenceAdapter` en JSON
- Separación de responsabilidades usando un runner dedicado
- Estructura Maven de múltiples módulos

### Quick Start

```bash
cd examples/todo-list
mvn clean install
mvn exec:java