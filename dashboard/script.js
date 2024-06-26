const body = document.querySelector('body'),
      sidebar = body.querySelector('nav'),
      toggle = body.querySelector(".toggle");

toggle.addEventListener("click", () => {
    sidebar.classList.toggle("close");
});

document.addEventListener('DOMContentLoaded', () => {
    const taskForm = document.getElementById('task-form');
    const taskInput = document.getElementById('new-task');
    const taskList = document.getElementById('task-list');
    const allTasksLink = document.getElementById('all-tasks-link');
    const todayTasksLink = document.getElementById('today-tasks-link');
    const upcomingTasksLink = document.getElementById('upcoming-tasks-link');
    const completedTasksLink = document.getElementById('completed-tasks-link');

    let tasks = [];

    function addTask(taskText) {
        const task = {
            text: taskText,
            completed: false,
            date: new Date()
        };
        tasks.push(task);
        renderTasks();
    }

    function renderTasks(filter = 'all') {
        taskList.innerHTML = '';
        tasks.forEach(task => {
            if (filter === 'all' ||
                (filter === 'today' && isToday(task.date)) ||
                (filter === 'upcoming' && isUpcoming(task.date)) ||
                (filter === 'completed' && task.completed)) {
                const li = document.createElement('li');
                li.className = 'task-item';
    
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.className = 'task-checkbox';
                checkbox.checked = task.completed;
    
                const span = document.createElement('span');
                span.className = 'task-text';
                span.textContent = task.text;
    
                li.appendChild(checkbox);
                li.appendChild(span);
    
                if (!task.completed) {
                    const editButton = document.createElement('button');
                    editButton.className = 'edit';
                    editButton.textContent = 'Edit';
                    li.appendChild(editButton);
                    editButton.addEventListener('click', () => editTask(li, task));
    
                    const deleteButton = document.createElement('button');
                    deleteButton.className = 'delete';
                    deleteButton.textContent = 'Delete';
                    li.appendChild(deleteButton);
                    deleteButton.addEventListener('click', () => deleteTask(li, task));
                }
    
                taskList.appendChild(li);
    
                checkbox.addEventListener('change', () => toggleTaskCompletion(task, checkbox));
            }
        });
    }
    

    function toggleTaskCompletion(task, checkbox) {
        task.completed = checkbox.checked;
        renderTasks();
    }

    function editTask(taskItem, task) {
        const newTaskText = prompt('Edit your task:', task.text);
        if (newTaskText !== null && newTaskText.trim() !== '') {
            task.text = newTaskText;
            renderTasks();
        }
    }

    function deleteTask(taskItem, task) {
        tasks = tasks.filter(t => t !== task);
        renderTasks();
    }

    taskForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const newTaskText = taskInput.value.trim();
        if (newTaskText !== '') {
            addTask(newTaskText);
            taskInput.value = '';
        }
    });

    allTasksLink.addEventListener('click', () => renderTasks('all'));
    todayTasksLink.addEventListener('click', () => renderTasks('today'));
    upcomingTasksLink.addEventListener('click', () => renderTasks('upcoming'));
    completedTasksLink.addEventListener('click', () => renderTasks('completed'));

    function isToday(date) {
        const today = new Date();
        return date.getDate() === today.getDate() &&
               date.getMonth() === today.getMonth() &&
               date.getFullYear() === today.getFullYear();
    }

    function isUpcoming(date) {
        const today = new Date();
        return date > today;
    }
});
