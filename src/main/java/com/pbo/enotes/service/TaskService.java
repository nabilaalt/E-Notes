package com.pbo.enotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbo.enotes.ResourceNotFoundException;
import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.Task;
import com.pbo.enotes.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + id));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setCompleted(taskDetails.isCompleted());


        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + id));
        taskRepository.delete(task);
    }

    public List<Task> getAllTaskByUserId(Long id) {
        return taskRepository.findByUserId(id);
    }

    public Task completeTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + taskId));
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public List<Task> getCompletedTasks(Long userId){
        return taskRepository.findByUserIdAndCompleted(userId, true);
    }

    public List<Task> getUncompletedTask(Long userId){
        return taskRepository.findByUserIdAndCompleted(userId,false);
    }

    public List<Task> getTodayTask(Long userId){
        Date now = new Date();
        return taskRepository.findByUserIdAndDueDateToday(userId);
    }

    public List<Task> getUpcomingTask(Long userId){
        Date now = new Date();
        return taskRepository.findByUserIdAndDueDateAfter(userId, now);
    }




}

