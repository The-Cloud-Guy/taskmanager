package com.techguy.task.services;

import com.techguy.task.domain.dto.TaskDto;
import com.techguy.task.domain.dto.TaskListDto;
import com.techguy.task.domain.entities.Task;

import java.util.List;

public interface TaskService {
    TaskDto createTask(Long taskListId, TaskDto taskDto);
    List<TaskDto> listTask(Long taskListId);
    TaskDto getTask(Long taskListId, Long taskId);
    void deleteTask(Long taskListId, Long taskId);

}
