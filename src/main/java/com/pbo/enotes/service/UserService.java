

package com.pbo.enotes.service;

import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.Task;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.repository.NoteRepository;
import com.pbo.enotes.repository.TaskRepository;
import com.pbo.enotes.repository.UserRepository;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private NoteRepository noteRepository;

    @Autowired
    @Lazy
    private TaskRepository taskRepository;

    // Create or update a user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Get a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete a user by ID
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Check if email exists
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<Note> findNotesByUserId(Long userId) {
        
        return noteRepository.findByUser_Id(userId);
    }

    public List<Task> findTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
}
