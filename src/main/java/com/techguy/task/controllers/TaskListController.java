package com.techguy.task.controllers;

import com.techguy.task.domain.dto.TaskListDto;
import com.techguy.task.services.TaskListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/task-lists")
public class TaskListController {

    private final TaskListService taskListService;

    @GetMapping
    public ResponseEntity<List<TaskListDto>> listTaskList() {
        return new ResponseEntity<>(taskListService.listTaskList(), HttpStatus.OK);
    }

    @GetMapping(path = "/{task_list_id}")
    public ResponseEntity<TaskListDto> getTaskList(
            @PathVariable Long task_list_id) {
        return new ResponseEntity<>(taskListService.getTasklist(task_list_id), HttpStatus.OK);
    }

    @PutMapping(path = "/{task_list_id}")
    public ResponseEntity<TaskListDto> updateTaskList(
            @Valid @PathVariable Long task_list_id, @RequestBody TaskListDto taskListDto) {

        return new ResponseEntity<>(taskListService.updateTaskList(task_list_id, taskListDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskListDto> createTaskList(
            @RequestBody TaskListDto taskListDto) {

        return new ResponseEntity<>(taskListService.createTaskList(taskListDto), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{task_list_id}")
    public ResponseEntity<Void> deleteTaskList(
            @PathVariable Long task_list_id) {
        taskListService.deleteTaskList(task_list_id);

        return ResponseEntity.noContent().build();

    }


}
