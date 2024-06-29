package com.pbo.enotes.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pbo.enotes.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);  // Berdasarkan userId
    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);  // Berdasarkan userId dan status completed
    List<Task> findByUserIdAndDueDateAfter(Long userId, Date currentDate);

    
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND DATE(t.dueDate) = CURRENT_DATE")
    List<Task> findByUserIdAndDueDateToday(@Param("userId") Long userId);  // Berdasarkan userId dan tanggal dueDate hari ini
}
