/**
 * Utility Functions
 */
const Utils = {
    // Format date to readable format
    formatDate(dateString) {
        if (!dateString) return 'No date';
        const date = new Date(dateString + 'T00:00:00');
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const tomorrow = new Date(today);
        tomorrow.setDate(tomorrow.getDate() + 1);

        if (date.getTime() === today.getTime()) {
            return 'Today';
        } else if (date.getTime() === tomorrow.getTime()) {
            return 'Tomorrow';
        } else {
            return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
        }
    },

    // Format date for input
    formatDateForInput(dateString) {
        if (!dateString) return '';
        return dateString;
    },

    // Get relative time
    getRelativeTime(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        const now = new Date();
        const diffMs = now - date;
        const diffMins = Math.floor(diffMs / 60000);
        const diffHours = Math.floor(diffMs / 3600000);
        const diffDays = Math.floor(diffMs / 86400000);

        if (diffMins < 1) return 'just now';
        if (diffMins < 60) return `${diffMins}m ago`;
        if (diffHours < 24) return `${diffHours}h ago`;
        if (diffDays < 7) return `${diffDays}d ago`;
        return date.toLocaleDateString();
    },

    // Check if date is overdue
    isOverdue(dateString) {
        if (!dateString) return false;
        const dueDate = new Date(dateString + 'T00:00:00');
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return dueDate < today;
    },

    // Check if date is today
    isToday(dateString) {
        if (!dateString) return false;
        const date = new Date(dateString + 'T00:00:00');
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return date.getTime() === today.getTime();
    },

    // Debounce function
    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },

    // Sort array of todos
    sortTodos(todos, sortBy) {
        const sorted = [...todos];
        
        switch (sortBy) {
            case 'dueDate':
                sorted.sort((a, b) => {
                    if (!a.dueDate) return 1;
                    if (!b.dueDate) return -1;
                    return new Date(a.dueDate) - new Date(b.dueDate);
                });
                break;
            
            case 'priority':
                const priorityOrder = { high: 1, medium: 2, low: 3 };
                sorted.sort((a, b) => priorityOrder[a.priority] - priorityOrder[b.priority]);
                break;
            
            case 'created':
                sorted.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                break;
            
            default:
                break;
        }
        
        return sorted;
    },

    // Get category icon
    getCategoryIcon(category) {
        const icons = {
            work: '💼',
            personal: '👤',
            shopping: '🛒'
        };
        return icons[category] || '📌';
    },

    // Get priority color
    getPriorityColor(priority) {
        const colors = {
            high: '#f56565',
            medium: '#ed8936',
            low: '#48bb78'
        };
        return colors[priority] || '#667eea';
    },

    // Get category color
    getCategoryColor(category) {
        const colors = {
            work: '#667eea',
            personal: '#48bb78',
            shopping: '#f56565'
        };
        return colors[category] || '#667eea';
    },

    // Copy to clipboard
    copyToClipboard(text) {
        navigator.clipboard.writeText(text).then(() => {
            showToast('Copied to clipboard!', 'success');
        }).catch(() => {
            showToast('Failed to copy', 'error');
        });
    },

    // Download file
    downloadFile(content, filename) {
        const element = document.createElement('a');
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content));
        element.setAttribute('download', filename);
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    },

    // Show/hide element
    show(element) {
        if (element) element.style.display = '';
    },

    hide(element) {
        if (element) element.style.display = 'none';
    },

    // Check if empty
    isEmpty(value) {
        return value === null || value === undefined || value === '';
    }
};

/**
 * Global Toast Notification Function
 */
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    if (!toast) return;
    
    toast.textContent = message;
    toast.className = `toast ${type}`;
    toast.style.display = 'block';
    
    setTimeout(() => {
        toast.style.display = 'none';
    }, 3000);
}
