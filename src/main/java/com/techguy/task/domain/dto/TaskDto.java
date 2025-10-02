package com.techguy.task.domain.dto;

import com.techguy.task.domain.entities.TaskList;
import com.techguy.task.domain.entities.TaskPriority;
import com.techguy.task.domain.entities.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TaskDto {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 250, message = "Title muss be between {min} and {max} characters")
    private String title;

    @Size(min = 5, max = 5000, message = "Description muss be between {min} and {max} characters")
    private String description;

    private TaskPriority taskPriority;

    private TaskStatus taskStatus;

    @NotNull(message = "The due date is required")
    private LocalDateTime dueDate;

}
