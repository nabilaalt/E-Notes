package com.pbo.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pbo.enotes.entity.Task;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.TaskService;
import com.pbo.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Task> tasks = loggedUser.getTasks();
            model.addAttribute("tasks", tasks);
            return "home/index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable(value = "id") Long taskId, Model model) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "tasks/show";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }

    @PostMapping
    public String createTask(@RequestParam Long userId, @ModelAttribute Task task) {
        return userService.getUserById(userId).map(user -> {
            user.getTasks().add(task);
            userService.saveUser(user);
            return "redirect:/tasks";
        }).orElse("error/404");
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable(value = "id") Long taskId, Model model) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "tasks/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable(value = "id") Long taskId, @ModelAttribute Task taskDetails) {
        taskService.updateTask(taskId, taskDetails);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable(value = "id") Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/tasks";
    }
}
