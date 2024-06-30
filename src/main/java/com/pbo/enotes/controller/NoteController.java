package com.pbo.enotes.controller;

import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.Task;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.NoteService;
import com.pbo.enotes.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            note.setExcerpt(note.getContent());
            user.getNotes().add(note);
            userService.saveUser(user);
            return "redirect:/notes";
        }).orElse("error/404");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable(value = "id") Long taskId) {
        Optional<Note> note = noteService.getTaskById(taskId);
        if (note.isPresent()) {
            return ResponseEntity.ok(note.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
            List<Note> notes = noteService.getAllNoteByUserId(loggedUser.getId());
            int outerSize = (int) Math.ceil(notes.size() / 2.0);
            Note[][] noteArray2D = new Note[outerSize][2];

            // Mengisi array 2D dengan task
            for (int i = 0; i < notes.size(); i++) {
                noteArray2D[i / 2][i % 2] = notes.get(i);
            }

            model.addAttribute("user", loggedUser);
            model.addAttribute("jumlah", notes.size());
            model.addAttribute("notes", noteArray2D);
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

    @PostMapping("/{id}/edit")
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
