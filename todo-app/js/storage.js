/**
 * Storage Manager - Handles all Local Storage operations
 */
class StorageManager {
    constructor() {
        this.storageKey = 'brokerSkipTodos';
        this.settingsKey = 'brokerSkipSettings';
        this.initializeStorage();
    }

    // Initialize storage with default data if empty
    initializeStorage() {
        if (!this.getAllTodos()) {
            this.saveTodos([]);
        }
        if (!this.getSettings()) {
            this.saveSettings({
                darkMode: false,
                sortBy: 'dueDate',
                filterBy: 'all'
            });
        }
    }

    // Get all todos from storage
    getAllTodos() {
        const data = localStorage.getItem(this.storageKey);
        return data ? JSON.parse(data) : null;
    }

    // Save todos to storage
    saveTodos(todos) {
        localStorage.setItem(this.storageKey, JSON.stringify(todos));
    }

    // Add new todo
    addTodo(todoData) {
        const todos = this.getAllTodos() || [];
        const newTodo = {
            id: this.generateId(),
            title: todoData.title,
            category: todoData.category || 'personal',
            priority: todoData.priority || 'medium',
            dueDate: todoData.dueDate || null,
            notes: todoData.notes || '',
            completed: false,
            createdAt: new Date().toISOString(),
            completedAt: null
        };
        todos.push(newTodo);
        this.saveTodos(todos);
        return newTodo;
    }

    // Update existing todo
    updateTodo(id, updates) {
        const todos = this.getAllTodos() || [];
        const index = todos.findIndex(todo => todo.id === id);
        
        if (index !== -1) {
            todos[index] = { ...todos[index], ...updates };
            this.saveTodos(todos);
            return todos[index];
        }
        return null;
    }

    // Delete todo by id
    deleteTodo(id) {
        const todos = this.getAllTodos() || [];
        const filteredTodos = todos.filter(todo => todo.id !== id);
        this.saveTodos(filteredTodos);
    }

    // Toggle todo completion
    toggleTodo(id) {
        const todos = this.getAllTodos() || [];
        const todo = todos.find(t => t.id === id);
        
        if (todo) {
            todo.completed = !todo.completed;
            todo.completedAt = todo.completed ? new Date().toISOString() : null;
            this.saveTodos(todos);
            return todo;
        }
        return null;
    }

    // Get todo by id
    getTodoById(id) {
        const todos = this.getAllTodos() || [];
        return todos.find(todo => todo.id === id);
    }

    // Get todos by category
    getTodosByCategory(category) {
        const todos = this.getAllTodos() || [];
        if (category === 'all') return todos;
        return todos.filter(todo => todo.category === category);
    }

    // Get completed todos
    getCompletedTodos() {
        const todos = this.getAllTodos() || [];
        return todos.filter(todo => todo.completed);
    }

    // Get pending todos
    getPendingTodos() {
        const todos = this.getAllTodos() || [];
        return todos.filter(todo => !todo.completed);
    }

    // Get overdue todos
    getOverdueTodos() {
        const todos = this.getAllTodos() || [];
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        
        return todos.filter(todo => {
            if (!todo.dueDate || todo.completed) return false;
            const dueDate = new Date(todo.dueDate);
            dueDate.setHours(0, 0, 0, 0);
            return dueDate < today;
        });
    }

    // Get todos due today
    getTodosDueToday() {
        const todos = this.getAllTodos() || [];
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        
        return todos.filter(todo => {
            if (!todo.dueDate) return false;
            const dueDate = new Date(todo.dueDate);
            dueDate.setHours(0, 0, 0, 0);
            return dueDate.getTime() === today.getTime();
        });
    }

    // Search todos
    searchTodos(query) {
        const todos = this.getAllTodos() || [];
        const lowerQuery = query.toLowerCase();
        
        return todos.filter(todo => 
            todo.title.toLowerCase().includes(lowerQuery) ||
            todo.notes.toLowerCase().includes(lowerQuery) ||
            todo.category.toLowerCase().includes(lowerQuery) ||
            todo.priority.toLowerCase().includes(lowerQuery)
        );
    }

    // Get statistics
    getStatistics() {
        const todos = this.getAllTodos() || [];
        const completed = this.getCompletedTodos();
        
        const stats = {
            total: todos.length,
            completed: completed.length,
            pending: todos.length - completed.length,
            completionPercent: todos.length > 0 ? Math.round((completed.length / todos.length) * 100) : 0,
            byCategory: {},
            byPriority: {},
            overdue: this.getOverdueTodos().length,
            dueToday: this.getTodosDueToday().length
        };

        // Count by category
        todos.forEach(todo => {
            stats.byCategory[todo.category] = (stats.byCategory[todo.category] || 0) + 1;
        });

        // Count by priority
        todos.forEach(todo => {
            stats.byPriority[todo.priority] = (stats.byPriority[todo.priority] || 0) + 1;
        });

        return stats;
    }

    // Clear all todos
    clearAll() {
        this.saveTodos([]);
    }

    // Export todos as JSON
    exportTodos() {
        const todos = this.getAllTodos() || [];
        return JSON.stringify(todos, null, 2);
    }

    // Import todos from JSON
    importTodos(jsonData) {
        try {
            const todos = JSON.parse(jsonData);
            if (Array.isArray(todos)) {
                this.saveTodos(todos);
                return true;
            }
            return false;
        } catch (error) {
            console.error('Import error:', error);
            return false;
        }
    }

    // Get settings
    getSettings() {
        const data = localStorage.getItem(this.settingsKey);
        return data ? JSON.parse(data) : null;
    }

    // Save settings
    saveSettings(settings) {
        localStorage.setItem(this.settingsKey, JSON.stringify(settings));
    }

    // Update specific setting
    updateSetting(key, value) {
        const settings = this.getSettings() || {};
        settings[key] = value;
        this.saveSettings(settings);
    }

    // Generate unique ID
    generateId() {
        return Date.now().toString(36) + Math.random().toString(36).substr(2);
    }

    // Get storage size info
    getStorageInfo() {
        let size = 0;
        const data = {
            todos: this.getAllTodos(),
            settings: this.getSettings()
        };
        
        size = JSON.stringify(data).length;
        return {
            size: size,
            sizeKB: (size / 1024).toFixed(2),
            available: 'Unlimited (Local Storage)'
        };
    }
}

// Initialize Storage Manager
const storage = new StorageManager();
