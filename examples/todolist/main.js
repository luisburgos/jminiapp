// ToDoList example for JMiniApp - minimal implementation
// All names and comments are in English as required.

class TodoItem {
  constructor(id, title, completed = false) {
    this.id = id;
    this.title = title;
    this.completed = completed;
  }
}

class TodoList {
  constructor() {
    this.items = [];
    this.nextId = 1;
  }

  add(title) {
    if (!title || !title.trim()) return null;
    const item = new TodoItem(this.nextId++, title.trim(), false);
    this.items.push(item);
    return item;
  }

  toggle(id) {
    const item = this.items.find(i => i.id === id);
    if (item) item.completed = !item.completed;
    return item;
  }

  remove(id) {
    const index = this.items.findIndex(i => i.id === id);
    if (index >= 0) this.items.splice(index, 1);
  }

  getAll() {
    return [...this.items];
  }

  clear() {
    this.items = [];
    this.nextId = 1;
  }
}

// Expose to global so index.html can use it
window.TodoItem = TodoItem;
window.TodoList = TodoList;
