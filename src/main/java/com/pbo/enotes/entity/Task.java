package com.pbo.enotes.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String excerpt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dueDate;

    private boolean completed;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateStr(){
        return dueDate.toString();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setExcerpt(String description){
        this.excerpt = truncateDescription(description, 10);
    }

    public String getExcerpt(){
        return this.excerpt;
    }



    private String truncateDescription(String description, int wordLimit) {
        String[] words = description.split("\\s+");
        if (words.length > wordLimit) {
            StringBuilder truncated = new StringBuilder();
            for (int i = 0; i < wordLimit; i++) {
                truncated.append(words[i]).append(" ");
            }
            return truncated.toString().trim() + "...";
        } else {
            return description;
        }
    }
}
