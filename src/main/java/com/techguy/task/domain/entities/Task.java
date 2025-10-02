package com.techguy.task.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private TaskPriority priority;

    @Column(nullable = false)
    private TaskStatus   status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name= "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "task_list_id")
    @JsonBackReference
    private TaskList      taskList;

    @PrePersist
    public void onCreate(){
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());

    }

    @PreUpdate
    public void onUpdate(){
        this.setUpdatedAt(LocalDateTime.now());
    }


}
