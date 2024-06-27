package com.pbo.enotes.service;

import com.pbo.enotes.entity.Note;
import com.pbo.enotes.entity.Task;
import com.pbo.enotes.entity.User;
import com.pbo.enotes.repository.NoteRepository;
import com.pbo.enotes.repository.TaskRepository;
import com.pbo.enotes.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
