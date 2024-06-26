package com.pbo.enotes.repository;

import com.pbo.enotes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // Additional query methods if needed
}
