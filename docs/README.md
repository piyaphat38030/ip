# Tem User Guide

Tem is a personal assistant chatbot that helps you keep track of tasks.

## Features

### Adding a todo

Adds a task without a date or time.

Example: `todo read book`

```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
```

### Adding a deadline

Adds a task that must be done by a date in `yyyy-MM-dd` format.

Example: `deadline return book /by 2019-10-15`

```
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019)
Now you have 1 tasks in the list.
```

### Adding an event

Adds a task with a start and end time.

Example: `event project meeting /from Mon 2pm /to 4pm`

### Listing tasks

Shows all tasks with their numbers.

Example: `list`

### Marking and unmarking tasks

Example: `mark 1`

Example: `unmark 1`

### Deleting a task

Example: `delete 1`

### Finding tasks

Finds tasks whose descriptions contain a keyword (case-insensitive).

Example: `find book`

### Sorting tasks

Reorders the list so deadline tasks come first, sorted by due date from
earliest to latest. Todo and event tasks keep their previous relative
order after the deadlines. The new order is saved.

Example: `sort`

```
Got it. I've sorted the deadlines chronologically:
Here are the tasks in your list:
1.[D][ ] return book (by: Oct 15 2019)
2.[D][ ] submit report (by: Dec 2 2019)
3.[T][ ] read book
```

### Exiting

Example: `bye`
