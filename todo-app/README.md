# To-Do List Application

A modern, feature-rich To-Do List application with local storage functionality.

## Features

✅ **Add Tasks** - Create new to-do items  
✅ **Complete Tasks** - Mark tasks as done  
✅ **Delete Tasks** - Remove tasks from list  
✅ **Edit Tasks** - Modify existing tasks  
✅ **Local Storage** - Auto-save all data to browser  
✅ **Task Categories** - Organize by Work, Personal, Shopping  
✅ **Priority Levels** - High, Medium, Low priority  
✅ **Due Dates** - Set deadlines for tasks  
✅ **Search & Filter** - Find tasks easily  
✅ **Statistics** - View completion stats  
✅ **Dark Mode** - Eye-friendly dark theme  
✅ **Responsive Design** - Works on mobile & desktop  

## Project Structure

```
todo-app/
├── index.html          # Main HTML file
├── css/
│   └── style.css       # All styling
├── js/
│   ├── app.js          # Main application
│   ├── storage.js      # Local storage management
│   └── utils.js        # Utility functions
├── assets/
│   └── icons.svg       # SVG icons
└── README.md           # Documentation
```

## Getting Started

### 1. Open in Browser
```bash
# Simply open index.html in your browser
open index.html

# Or use a local server
python -m http.server 8000
# Then visit http://localhost:8000
```

### 2. How to Use

**Add a Task:**
- Type task name in input field
- Select category, priority, and due date (optional)
- Press Enter or click Add button

**Complete a Task:**
- Click checkbox next to task
- Task will be marked as completed

**Edit a Task:**
- Click edit icon
- Modify task details
- Save changes

**Delete a Task:**
- Click delete/trash icon
- Task will be removed

**Search Tasks:**
- Use search box to find tasks
- Results update in real-time

**Filter by Category:**
- Select category from dropdown
- View only tasks in that category

**Toggle Dark Mode:**
- Click moon icon in header
- Theme will switch

## Local Storage Structure

```javascript
{
  "todos": [
    {
      "id": "1234567890",
      "title": "Buy groceries",
      "category": "shopping",
      "priority": "high",
      "dueDate": "2026-05-15",
      "completed": false,
      "createdAt": "2026-05-09T10:30:00Z",
      "completedAt": null
    }
  ],
  "settings": {
    "darkMode": false,
    "sortBy": "dueDate",
    "filterBy": "all"
  }
}
```

## API Reference

### Storage Methods

```javascript
// Get all tasks
const tasks = StorageManager.getAllTodos();

// Add new task
StorageManager.addTodo({
  title: "Task name",
  category: "work",
  priority: "high",
  dueDate: "2026-05-15"
});

// Update task
StorageManager.updateTodo(taskId, updatedData);

// Delete task
StorageManager.deleteTodo(taskId);

// Toggle task completion
StorageManager.toggleTodo(taskId);

// Clear all tasks
StorageManager.clearAll();
```

## Features Explained

### Local Storage
- **Persistent**: Data saved even after browser closes
- **No Server**: Everything stored in browser
- **Auto-save**: Changes saved automatically
- **Backup**: Export data as JSON

### Categories
- **Work**: Professional tasks
- **Personal**: Personal activities
- **Shopping**: Shopping items
- **Custom**: Add your own categories

### Priority Levels
- **High**: Urgent tasks (Red)
- **Medium**: Regular tasks (Orange)
- **Low**: Less urgent tasks (Green)

### Statistics
- Total tasks created
- Tasks completed
- Completion percentage
- Tasks due today
- Overdue tasks

## Browser Compatibility

✅ Chrome 90+  
✅ Firefox 88+  
✅ Safari 14+  
✅ Edge 90+  
✅ Mobile browsers  

## Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| Enter | Add new task |
| Ctrl+D | Toggle dark mode |
| Ctrl+K | Focus search |
| Escape | Close modal |

## Tips & Tricks

1. **Bulk Actions**: Select multiple tasks and delete at once
2. **Smart Search**: Search by category, priority, or text
3. **Due Today**: Quick filter for today's tasks
4. **Export Data**: Download tasks as JSON backup
5. **Recurring Tasks**: Create daily/weekly recurring tasks

## Troubleshooting

### Tasks not saving?
- Check if localStorage is enabled
- Clear browser cache and try again
- Check browser privacy settings

### Data lost?
- Check if you cleared browsing data
- LocalStorage has ~5-10MB limit
- Use export feature to backup

## Future Enhancements

- [ ] Cloud sync with Firebase
- [ ] Push notifications
- [ ] Multiple profiles/accounts
- [ ] Recurring tasks
- [ ] Subtasks
- [ ] Tags instead of categories
- [ ] Calendar view
- [ ] Pomodoro timer integration
- [ ] Voice input
- [ ] Export to PDF/Excel

## License

MIT License - Free to use and modify

## Support

For issues or questions, create an issue in the repository.
