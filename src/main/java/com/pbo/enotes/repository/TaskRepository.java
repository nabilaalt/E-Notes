package com.pbo.enotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pbo.enotes.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long user_id);
}
