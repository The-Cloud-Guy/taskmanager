package com.techguy.task.controllers;

import com.techguy.task.domain.dto.TaskDto;
import com.techguy.task.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @PathVariable Long task_list_id, @Valid @RequestBody TaskDto taskDto) {

        TaskDto createdTask = taskService.createTask(task_list_id, taskDto);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listTask(
            @PathVariable Long task_list_id) {

        return new ResponseEntity<>(taskService.listTask(task_list_id), HttpStatus.OK);
    }

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable Long task_list_id, @PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTask(task_list_id, taskId), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long task_list_id, @PathVariable Long taskId
    ) {
        taskService.deleteTask(task_list_id, taskId);
        return ResponseEntity.noContent().build();
    }


}
