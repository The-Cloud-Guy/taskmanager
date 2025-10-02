package com.techguy.task.services;

import com.techguy.task.domain.dto.TaskListDto;

import java.util.List;

public interface TaskListService {
    List<TaskListDto> listTaskList();
    TaskListDto getTasklist(Long taskListId);
    TaskListDto updateTaskList(Long id, TaskListDto taskListDto);
    TaskListDto createTaskList(TaskListDto taskListDto);
    void     deleteTaskList(Long id);

}
