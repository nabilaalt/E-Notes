package com.pbo.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pbo.enotes.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
