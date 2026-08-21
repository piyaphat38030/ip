# Tem UI test plan

The `test-ui` skill reads each test case below, compiles the application with
the active Java version, and compares Tem's full console transcript exactly.

### Add and manage every Level 4 task type

Aim: Verify task creation, type-specific display, and inherited mark/unmark behavior.

Input:
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
mark 2
unmark 2
list
bye
```

Expected output:
```text
____________________________________________________________
 _____                 
|_   _|__ _ __ ___     
  | |/ _ \ '_ ` _ \    
  | |  __/ | | | | |   
  |_|\___|_| |_| |_|   

Hello! I'm Tem.
What can I do for you?
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: Sunday)
____________________________________________________________
OK, I've marked this task as not done yet:
  [D][ ] return book (by: Sunday)
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
