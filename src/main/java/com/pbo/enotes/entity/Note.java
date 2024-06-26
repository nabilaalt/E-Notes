package com.pbo.enotes.entity;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Note implements Serializable {
    @Id
    private String id;
    private String title;
    private String content;
    
}
