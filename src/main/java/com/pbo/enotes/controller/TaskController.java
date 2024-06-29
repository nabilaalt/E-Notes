package com.pbo.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pbo.enotes.entity.Task;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.TaskService;
import com.pbo.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date; // Import java.sql.Date untuk tipe data SQL Date
import java.util.Comparator;


@Controller
@RequestMapping("/tasks")
public class TaskController {

    Comparator<Task> statusComparator = new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return Boolean.compare(task1.isCompleted(), task2.isCompleted());
            }
        };

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Task> tasks = taskService.getAllTaskByUserId(loggedUser.getId());
            tasks.sort(statusComparator);

            // Menghitung ukuran dari array 2D yang dibutuhkan
            int outerSize = (tasks.size() + 2) / 3; // Menambah 2 agar pembagian selalu ke atas untuk kasus tidak habis
                                                    // dibagi 3
            Task[][] taskArray2D = new Task[outerSize][3];

            // Mengisi array 2D dengan task
            for (int i = 0; i < tasks.size(); i++) {
                taskArray2D[i / 3][i % 3] = tasks.get(i);
            }

            model.addAttribute("tasks", taskArray2D);
            model.addAttribute("user", loggedUser);
            model.addAttribute("title", "All Tasks");
            model.addAttribute("showButton", true);
            model.addAttribute("jumlah", tasks.isEmpty());

            return "tasks/index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "id") Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }

    @GetMapping("/completed")
    public String getCompletedTask(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Task> tasks = taskService.getCompletedTasks(loggedUser.getId());

            // Menghitung ukuran dari array 2D yang dibutuhkan
            int outerSize = (tasks.size() + 2) / 3; // Menambah 2 agar pembagian selalu ke atas untuk kasus tidak habis
                                                    // dibagi 3
            Task[][] taskArray2D = new Task[outerSize][3];

            // Mengisi array 2D dengan task
            for (int i = 0; i < tasks.size(); i++) {
                taskArray2D[i / 3][i % 3] = tasks.get(i);
            }

            model.addAttribute("tasks", taskArray2D);
            model.addAttribute("user", loggedUser);
            model.addAttribute("title", "Completed Tasks");
            model.addAttribute("jumlah", tasks.isEmpty());

            return "tasks/index";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping("/upcoming")
    public String getUpcomingTask(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Task> tasks = taskService.getUpcomingTask(loggedUser.getId());

            // Menghitung ukuran dari array 2D yang dibutuhkan
            int outerSize = (tasks.size() + 2) / 3; // Menambah 2 agar pembagian selalu ke atas untuk kasus tidak habis
                                                    // dibagi 3
            Task[][] taskArray2D = new Task[outerSize][3];

            // Mengisi array 2D dengan task
            for (int i = 0; i < tasks.size(); i++) {
                taskArray2D[i / 3][i % 3] = tasks.get(i);
            }

            model.addAttribute("tasks", taskArray2D);
            model.addAttribute("user", loggedUser);
            model.addAttribute("title", "Upcoming Tasks");
            model.addAttribute("jumlah", tasks.isEmpty());

            return "tasks/index";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping("/today")
    public String getTodayTask(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Task> tasks = taskService.getTodayTask(loggedUser.getId());

            // Menghitung ukuran dari array 2D yang dibutuhkan
            int outerSize = (tasks.size() + 2) / 3; // Menambah 2 agar pembagian selalu ke atas untuk kasus tidak habis
                                                    // dibagi 3
            Task[][] taskArray2D = new Task[outerSize][3];

            // Mengisi array 2D dengan task
            for (int i = 0; i < tasks.size(); i++) {
                taskArray2D[i / 3][i % 3] = tasks.get(i);
            }

            model.addAttribute("tasks", taskArray2D);
            model.addAttribute("user", loggedUser);
            model.addAttribute("title", "Today Tasks");
            model.addAttribute("jumlah", tasks.isEmpty());

            return "tasks/index";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }

    @PostMapping
    public String createTask(@RequestParam Long userId, @ModelAttribute Task task) {
        // Mengambil user berdasarkan userId
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Tambahkan task ke user
            user.getTasks().add(task);
            userService.saveUser(user); // Simpan user yang sudah diperbarui

            return "redirect:/tasks";
        } else {
            return "error/404"; // Handle jika user tidak ditemukan
        }
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

    @PostMapping("/{id}/edit")
    public String updateTask(@PathVariable(value = "id") Long taskId, @ModelAttribute Task taskDetails) {
        taskService.updateTask(taskId, taskDetails);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable(value = "id") Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/complete")
    public String CompleteTask(@PathVariable(value = "id") Long taskId) {
        taskService.completeTask(taskId);
        
        return "redirect:/tasks";
    }
    
}
