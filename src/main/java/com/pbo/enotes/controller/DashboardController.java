package com.pbo.enotes.controller;

import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.Task;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.NoteService;
import com.pbo.enotes.service.TaskService;
import com.pbo.enotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/dashboard/notes")
    public String home(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Note> notes = noteService.getAllNoteByUserId(loggedUser.getId());


            model.addAttribute("username", loggedUser.getUsername());
            model.addAttribute("jumlah", notes.size());
            model.addAttribute("notes", notes);
        }
        return "home/index";
    }

    @GetMapping("/dashboard/task")
    public String getMethodName(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        return "home/index";
    }
    
}
