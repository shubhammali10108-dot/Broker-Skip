/**
 * To-Do List Application
 * Main Application Logic
 */

class TodoApp {
    constructor() {
        this.currentFilter = 'all';
        this.currentSort = 'dueDate';
        this.searchQuery = '';
        this.editingTodoId = null;
        this.init();
    }

    init() {
        this.cacheElements();
        this.attachEventListeners();
        this.loadSettings();
        this.renderTodos();
        this.updateStatistics();
    }

    cacheElements() {
        // Input elements
        this.taskInput = document.getElementById('taskInput');
        this.categorySelect = document.getElementById('categorySelect');
        this.prioritySelect = document.getElementById('prioritySelect');
        this.dueDateInput = document.getElementById('dueDateInput');
        this.searchInput = document.getElementById('searchInput');
        
        // Buttons
        this.addTaskBtn = document.getElementById('addTaskBtn');
        this.darkModeBtn = document.getElementById('darkModeBtn');
        this.statsBtn = document.getElementById('statsBtn');
        this.exportBtn = document.getElementById('exportBtn');
        this.clearSearchBtn = document.getElementById('clearSearchBtn');
        
        // Container elements
        this.tasksList = document.getElementById('tasksList');
        this.emptyState = document.getElementById('emptyState');
        
        // Modal elements
        this.editModal = document.getElementById('editModal');
        this.statsModal = document.getElementById('statsModal');
        this.closeModalBtn = document.getElementById('closeModalBtn');
        this.closeStatsBtn = document.getElementById('closeStatsBtn');
        this.cancelEditBtn = document.getElementById('cancelEditBtn');
        this.saveEditBtn = document.getElementById('saveEditBtn');
        
        // Edit form elements
        this.editTaskTitle = document.getElementById('editTaskTitle');
        this.editCategory = document.getElementById('editCategory');
        this.editPriority = document.getElementById('editPriority');
        this.editDueDate = document.getElementById('editDueDate');
        this.editNotes = document.getElementById('editNotes');
    }

    attachEventListeners() {
        // Add task
        this.addTaskBtn.addEventListener('click', () => this.addTask());
        this.taskInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') this.addTask();
        });

        // Search
        this.searchInput.addEventListener('input', Utils.debounce(() => this.handleSearch(), 300));
        this.clearSearchBtn.addEventListener('click', () => this.clearSearch());

        // Dark mode
        this.darkModeBtn.addEventListener('click', () => this.toggleDarkMode());

        // Statistics
        this.statsBtn.addEventListener('click', () => this.showStatistics());

        // Export
        this.exportBtn.addEventListener('click', () => this.exportData());

        // Categories
        document.querySelectorAll('.category-btn').forEach(btn => {
            btn.addEventListener('click', (e) => this.handleCategoryFilter(e));
        });

        // Filters
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.addEventListener('click', (e) => this.handleFilter(e));
        });

        // Sort
        document.querySelectorAll('.sort-btn').forEach(btn => {
            btn.addEventListener('click', (e) => this.handleSort(e));
        });

        // Modal
        this.closeModalBtn.addEventListener('click', () => this.closeModal());
        this.closeStatsBtn.addEventListener('click', () => this.closeStatsModal());
        this.cancelEditBtn.addEventListener('click', () => this.closeModal());
        this.saveEditBtn.addEventListener('click', () => this.saveEdit());

        // Close modal on outside click
        this.editModal.addEventListener('click', (e) => {
            if (e.target === this.editModal) this.closeModal();
        });

        this.statsModal.addEventListener('click', (e) => {
            if (e.target === this.statsModal) this.closeStatsModal();
        });
    }

    addTask() {
        const title = this.taskInput.value.trim();
        if (!title) {
            showToast('Please enter a task', 'warning');
            return;
        }

        const todo = storage.addTodo({
            title: title,
            category: this.categorySelect.value,
            priority: this.prioritySelect.value,
            dueDate: this.dueDateInput.value
        });

        this.taskInput.value = '';
        this.dueDateInput.value = '';
        this.prioritySelect.value = 'medium';
        this.categorySelect.value = 'personal';
        
        showToast('Task added successfully!', 'success');
        this.renderTodos();
        this.updateStatistics();
    }

    renderTodos() {
        let todos = storage.getAllTodos() || [];

        // Apply filters
        todos = this.applyFilters(todos);

        // Apply search
        if (this.searchQuery) {
            todos = todos.filter(todo => 
                todo.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
                todo.notes.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        }

        // Apply sort
        todos = Utils.sortTodos(todos, this.currentSort);

        // Clear container
        this.tasksList.innerHTML = '';

        if (todos.length === 0) {
            Utils.show(this.emptyState);
            return;
        }

        Utils.hide(this.emptyState);

        todos.forEach(todo => {
            const taskEl = this.createTaskElement(todo);
            this.tasksList.appendChild(taskEl);
        });
    }

    createTaskElement(todo) {
        const div = document.createElement('div');
        div.className = `task-item ${todo.completed ? 'completed' : ''}`;
        div.innerHTML = `
            <input type="checkbox" class="task-checkbox" ${todo.completed ? 'checked' : ''} data-id="${todo.id}">
            <div class="task-content">
                <div class="task-header">
                    <span class="task-title">${this.escapeHtml(todo.title)}</span>
                </div>
                <div class="task-meta">
                    <span class="task-badge ${todo.category}">
                        ${Utils.getCategoryIcon(todo.category)} ${todo.category}
                    </span>
                    <span class="task-badge ${todo.priority}">
                        ${todo.priority}
                    </span>
                    ${todo.dueDate ? `<span class="task-badge">📅 ${Utils.formatDate(todo.dueDate)}</span>` : ''}
                </div>
                ${todo.notes ? `<div class="task-note">${this.escapeHtml(todo.notes)}</div>` : ''}
            </div>
            <div class="task-actions">
                <button class="task-btn" data-action="edit" data-id="${todo.id}" title="Edit">✏️</button>
                <button class="task-btn" data-action="delete" data-id="${todo.id}" title="Delete">🗑️</button>
            </div>
        `;

        // Checkbox event
        const checkbox = div.querySelector('.task-checkbox');
        checkbox.addEventListener('change', () => this.toggleTodo(todo.id));

        // Action buttons
        const editBtn = div.querySelector('[data-action="edit"]');
        const deleteBtn = div.querySelector('[data-action="delete"]');
        
        editBtn.addEventListener('click', () => this.openEditModal(todo.id));
        deleteBtn.addEventListener('click', () => this.deleteTodo(todo.id));

        return div;
    }

    toggleTodo(id) {
        storage.toggleTodo(id);
        showToast('Task updated', 'success');
        this.renderTodos();
        this.updateStatistics();
    }

    deleteTodo(id) {
        if (confirm('Are you sure you want to delete this task?')) {
            storage.deleteTodo(id);
            showToast('Task deleted', 'success');
            this.renderTodos();
            this.updateStatistics();
        }
    }

    openEditModal(id) {
        this.editingTodoId = id;
        const todo = storage.getTodoById(id);
        if (!todo) return;

        this.editTaskTitle.value = todo.title;
        this.editCategory.value = todo.category;
        this.editPriority.value = todo.priority;
        this.editDueDate.value = todo.dueDate || '';
        this.editNotes.value = todo.notes || '';

        Utils.show(this.editModal);
    }

    saveEdit() {
        const updates = {
            title: this.editTaskTitle.value.trim(),
            category: this.editCategory.value,
            priority: this.editPriority.value,
            dueDate: this.editDueDate.value,
            notes: this.editNotes.value.trim()
        };

        if (!updates.title) {
            showToast('Task title cannot be empty', 'warning');
            return;
        }

        storage.updateTodo(this.editingTodoId, updates);
        showToast('Task updated successfully', 'success');
        this.closeModal();
        this.renderTodos();
        this.updateStatistics();
    }

    closeModal() {
        Utils.hide(this.editModal);
        this.editingTodoId = null;
    }

    handleSearch() {
        this.searchQuery = this.searchInput.value.trim();
        this.renderTodos();
    }

    clearSearch() {
        this.searchInput.value = '';
        this.searchQuery = '';
        this.renderTodos();
    }

    handleCategoryFilter(e) {
        document.querySelectorAll('.category-btn').forEach(btn => btn.classList.remove('active'));
        e.target.classList.add('active');
        this.currentFilter = e.target.dataset.category;
        this.renderTodos();
    }

    handleFilter(e) {
        const filter = e.target.dataset.filter;
        document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
        e.target.classList.add('active');
        
        let todos = storage.getAllTodos() || [];
        
        switch (filter) {
            case 'today':
                todos = todos.filter(t => Utils.isToday(t.dueDate) && !t.completed);
                break;
            case 'overdue':
                todos = storage.getOverdueTodos();
                break;
            case 'completed':
                todos = storage.getCompletedTodos();
                break;
            case 'pending':
                todos = storage.getPendingTodos();
                break;
        }
        
        this.tasksList.innerHTML = '';
        if (todos.length === 0) {
            Utils.show(this.emptyState);
            return;
        }
        
        Utils.hide(this.emptyState);
        todos.forEach(todo => {
            this.tasksList.appendChild(this.createTaskElement(todo));
        });
    }

    handleSort(e) {
        document.querySelectorAll('.sort-btn').forEach(btn => btn.classList.remove('active'));
        e.target.classList.add('active');
        this.currentSort = e.target.dataset.sort;
        this.renderTodos();
    }

    applyFilters(todos) {
        if (this.currentFilter === 'all') return todos;
        return todos.filter(todo => todo.category === this.currentFilter);
    }

    updateStatistics() {
        const stats = storage.getStatistics();
        
        // Sidebar stats
        document.getElementById('totalTasks').textContent = stats.total;
        document.getElementById('completedTasks').textContent = stats.completed;
        document.getElementById('completionPercent').textContent = stats.completionPercent + '%';
    }

    showStatistics() {
        const stats = storage.getStatistics();
        
        document.getElementById('statTotal').textContent = stats.total;
        document.getElementById('statCompleted').textContent = stats.completed;
        document.getElementById('statPending').textContent = stats.pending;
        document.getElementById('statPercent').textContent = stats.completionPercent + '%';
        
        // Category stats
        const categoryStats = document.getElementById('categoryStats');
        categoryStats.innerHTML = '';
        Object.entries(stats.byCategory).forEach(([category, count]) => {
            const percentage = stats.total > 0 ? (count / stats.total * 100).toFixed(0) : 0;
            categoryStats.innerHTML += `
                <div class="stat-bar">
                    <div class="stat-bar-label">${Utils.getCategoryIcon(category)} ${category}</div>
                    <div class="stat-bar-container">
                        <div class="stat-bar-fill" style="width: ${percentage}%">${percentage}%</div>
                    </div>
                    <div class="stat-bar-value">${count}</div>
                </div>
            `;
        });
        
        // Priority stats
        const priorityStats = document.getElementById('priorityStats');
        priorityStats.innerHTML = '';
        Object.entries(stats.byPriority).forEach(([priority, count]) => {
            const percentage = stats.total > 0 ? (count / stats.total * 100).toFixed(0) : 0;
            priorityStats.innerHTML += `
                <div class="stat-bar">
                    <div class="stat-bar-label">${priority.charAt(0).toUpperCase() + priority.slice(1)}</div>
                    <div class="stat-bar-container">
                        <div class="stat-bar-fill" style="width: ${percentage}%; background: linear-gradient(90deg, ${Utils.getPriorityColor(priority)}, ${Utils.getPriorityColor(priority)}cc)">${percentage}%</div>
                    </div>
                    <div class="stat-bar-value">${count}</div>
                </div>
            `;
        });
        
        Utils.show(this.statsModal);
    }

    closeStatsModal() {
        Utils.hide(this.statsModal);
    }

    toggleDarkMode() {
        document.body.classList.toggle('dark-mode');
        const isDark = document.body.classList.contains('dark-mode');
        storage.updateSetting('darkMode', isDark);
        showToast(isDark ? 'Dark mode ON' : 'Dark mode OFF', 'info');
    }

    loadSettings() {
        const settings = storage.getSettings();
        if (settings && settings.darkMode) {
            document.body.classList.add('dark-mode');
        }
    }

    exportData() {
        const data = storage.exportTodos();
        const filename = `todos-backup-${new Date().getTime()}.json`;
        Utils.downloadFile(data, filename);
        showToast('Data exported successfully!', 'success');
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
}

// Initialize app when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
        new TodoApp();
    });
} else {
    new TodoApp();
}
