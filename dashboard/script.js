const body = document.querySelector('body'),
      sidebar = body.querySelector('nav'),
      toggle = body.querySelector(".toggle"),
      modeSwitch = body.querySelector(".toggle-switch"),
      modeText = body.querySelector(".mode-text");

toggle.addEventListener("click", () => {
    sidebar.classList.toggle("close");
});

document.addEventListener('DOMContentLoaded', () => {
    const taskForm = document.getElementById('task-form');
    const taskInput = document.getElementById('new-task');
    const taskList = document.getElementById('task-list');

    // Function to add a new task
    // function addTask(taskText) {
    //     const li = document.createElement('li');
    //     li.className = 'task-item';

    //     const span = document.createElement('span');
    //     span.className = 'task-text';
    //     span.textContent = taskText;

    //     const editButton = document.createElement('button');
    //     editButton.className = 'edit';
    //     editButton.textContent = 'Edit';

    //     const deleteButton = document.createElement('button');
    //     deleteButton.className = 'delete';
    //     deleteButton.textContent = 'Delete';

    //     li.appendChild(span);
    //     li.appendChild(editButton);
    //     li.appendChild(deleteButton);
    //     taskList.appendChild(li);

    //     // Add event listeners to buttons
    //     editButton.addEventListener('click', () => editTask(li));
    //     deleteButton.addEventListener('click', () => deleteTask(li));
    // }
    function addTask(taskText) {
        const li = document.createElement('li');
        li.className = 'task-item';
    
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.className = 'task-checkbox';
    
        const span = document.createElement('span');
        span.className = 'task-text';
        span.textContent = taskText;
    
        const editButton = document.createElement('button');
        editButton.className = 'edit';
        editButton.textContent = 'Edit';
    
        const deleteButton = document.createElement('button');
        deleteButton.className = 'delete';
        deleteButton.textContent = 'Delete';
    
        li.appendChild(checkbox);
        li.appendChild(span);
        li.appendChild(editButton);
        li.appendChild(deleteButton);
        taskList.appendChild(li);
    
        // Add event listeners to buttons and checkbox
        editButton.addEventListener('click', () => editTask(li));
        deleteButton.addEventListener('click', () => deleteTask(li));
        checkbox.addEventListener('change', () => toggleTaskCompletion(li, checkbox));
    }
    
    function toggleTaskCompletion(li, checkbox) {
        if (checkbox.checked) {
            li.classList.add('completed');
        } else {
            li.classList.remove('completed');
        }
    }
    

    // Function to edit a task
    function editTask(taskItem) {
        const taskText = taskItem.querySelector('.task-text');
        const newTaskText = prompt('Edit your task:', taskText.textContent);
        if (newTaskText !== null && newTaskText.trim() !== '') {
            taskText.textContent = newTaskText;
        }
    }

    // Function to delete a task
    function deleteTask(taskItem) {
        taskList.removeChild(taskItem);
    }

    // Handle form submission
    taskForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const newTaskText = taskInput.value.trim();
        if (newTaskText !== '') {
            addTask(newTaskText);
            taskInput.value = ''; // Clear the input field
        }
    });

    
});

