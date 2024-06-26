document.addEventListener('DOMContentLoaded', () => {
    const taskForm = document.getElementById('task-form');
    const newTaskInput = document.getElementById('new-task');
    const taskList = document.getElementById('task-list');

    taskForm.addEventListener('submit', (e) => {
        e.preventDefault();
        addTask(newTaskInput.value);
        newTaskInput.value = '';
    });

    taskList.addEventListener('click', (e) => {
        if (e.target.classList.contains('delete')) {
            deleteTask(e.target.parentElement);
        } else if (e.target.classList.contains('edit')) {
            editTask(e.target.parentElement);
        } else if (e.target.tagName === 'INPUT' && e.target.type === 'checkbox') {
            toggleTask(e.target.parentElement);
        }
    });

    taskList.addEventListener('keydown', (e) => {
        if (e.target.tagName === 'INPUT' && e.target.type === 'text' && e.key === 'Enter') {
            saveTask(e.target);
        }
    });

    function addTask(taskText) {
        const li = document.createElement('li');
        li.innerHTML = `
            <input type="checkbox">
            <label>${taskText}</label>
            <input type="text" value="${taskText}">
            <button class="edit">Edit</button>
            <button class="delete">Delete</button>
        `;
        taskList.appendChild(li);
    }

    function deleteTask(task) {
        taskList.removeChild(task);
    }

    function editTask(task) {
        const label = task.querySelector('label');
        const input = task.querySelector('input[type="text"]');
        const editButton = task.querySelector('button.edit');

        if (input.style.display === 'none') {
            input.style.display = 'block';
            label.style.display = 'none';
            editButton.textContent = 'Save';
            input.focus();
        } else {
            saveTask(input);
        }
    }

    function saveTask(input) {
        const task = input.parentElement;
        const label = task.querySelector('label');
        const editButton = task.querySelector('button.edit');

        label.textContent = input.value;
        input.style.display = 'none';
        label.style.display = 'block';
        editButton.textContent = 'Edit';
    }

    function toggleTask(task) {
        task.classList.toggle('completed');
    }
});
