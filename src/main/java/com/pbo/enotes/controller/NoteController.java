package com.pbo.enotes.controller;

import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.NoteService;
import com.pbo.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String createNote(@RequestParam Long userId, @ModelAttribute Note note) {
        return userService.getUserById(userId).map(user -> {
            user.getNotes().add(note);
            userService.saveUser(user);
            return "redirect:/notes";
        }).orElse("error/404");
    }

    @GetMapping("/{id}")
    public String getNoteById(@PathVariable Long id, Model model) {
        Optional<Note> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            model.addAttribute("note", note.get());
            return "notes/show";
        } else {
            return "error/404";
        }
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Note> notes = noteService.getAllNoteByUserId(loggedUser.getId());
            model.addAttribute("user", loggedUser);
            model.addAttribute("jumlah", notes.size());
            model.addAttribute("notes", notes);
            return "notes/index";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "notes/new";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Note> note = noteService.getNoteById(id);
        if (note.isPresent()) {
            model.addAttribute("note", note.get());
            return "notes/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/{id}")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note noteDetails) {
        noteService.updateNoteById(id, noteDetails);
        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes";
    }
}
