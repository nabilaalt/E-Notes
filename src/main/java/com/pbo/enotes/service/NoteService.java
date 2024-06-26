package com.pbo.enotes.service;

import com.pbo.enotes.ResourceNotFoundException;
import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.Task;
import com.pbo.enotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Create or update a note
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    // Get a note by ID
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    // Get all notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Delete a note by ID
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> getAllNoteByUserId(Long id) {
        return noteRepository.findByUser_Id(id);
    }

    public void updateNoteById(Long id, Note noteDetails) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found for this id :: " + id));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setExcerpt(noteDetails.getContent());

        noteRepository.save(note);
    }

    public Optional<Note> getTaskById(Long taskId) {
        return noteRepository.findById(taskId);
    }
}
