package com.techguy.task.domain.dto;

import com.techguy.task.domain.entities.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TaskListDto {
    private Long id; // Maybe i don't need it, because on the Update i give it as a path variable which is the best way to do it.

    @NotBlank(message = "Title is required")
    @Size(min = 3, max=250, message = "Title muss be between {min} and {max} characters")
    private String title;

    @Size(min = 5, max=5000, message = "Description muss be between {min} and {max} characters")
    private String description;

    private Integer count;

    private Double progress;

    private List<Task> tasks;
}
