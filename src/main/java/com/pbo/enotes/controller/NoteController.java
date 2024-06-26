package com.pbo.enotes.controller;

import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.service.NoteService;
import com.pbo.enotes.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;



    @PostMapping
    public Note createNote(@RequestParam Long userId, @RequestBody Note note) {
        return userService.getUserById(userId).map(user -> {
            user.getNotes().add(note);
            userService.saveUser(user);
            return note;
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/{id}")
    public Optional<Note> getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
    }
}
