package com.techguy.task.services.impl;

import com.techguy.task.domain.dto.TaskDto;
import com.techguy.task.domain.entities.Task;
import com.techguy.task.domain.entities.TaskList;
import com.techguy.task.domain.entities.TaskPriority;
import com.techguy.task.domain.entities.TaskStatus;
import com.techguy.task.mappers.TaskMapper;
import com.techguy.task.repositories.TaskListRepository;
import com.techguy.task.repositories.TaskRepository;
import com.techguy.task.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl  implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private final TaskMapper     taskMapper;


    @Override
    public TaskDto createTask(Long taskListId, TaskDto taskDto) {
        if (taskListId == null) {
            throw  new IllegalArgumentException("The tasklist Id is required");
        }

        if (taskDto.getId() != null) {
            throw  new IllegalArgumentException("The task already exist");
        }

        TaskPriority taskPriority = Optional.ofNullable(taskDto.getTaskPriority())
                                            .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                                               .orElseThrow(() -> new IllegalArgumentException("Invalid Task List Id provided") );

        taskDto.setTaskPriority(taskPriority);
        taskDto.setTaskStatus(taskStatus);



        Task tempTask = taskMapper.toEntity(taskDto);
        tempTask.setTaskList(taskList);


        Task creatsTask = taskRepository.save(tempTask);

        return taskMapper.toDto(creatsTask);
    }

    @Override
    public List<TaskDto> listTask(Long taskListId) {
        if (taskListId == null) {
            throw  new IllegalArgumentException("The tasklist Id is required");
        }

        return  taskRepository.findByTaskListId(taskListId)
                              .stream()
                              .map(taskMapper::toDto)
                              .toList();
    }

    @Override
    public TaskDto getTask(Long taskListId, Long taskId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("The task List Id is required");
        }
        if (taskId == null) {
            throw new IllegalArgumentException("The task Id is required");
        }

        return taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .map(taskMapper::toDto)
                .orElseThrow( ()-> new EntityNotFoundException(" The task does not exist with id " + taskId));
    }

    @Transactional
    @Override
    public void deleteTask(Long taskListId, Long taskId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("The task List Id is required");
        }
        if (taskId == null) {
            throw new IllegalArgumentException("The task id is required");
        }
        if (!taskRepository.existsByTaskListIdAndId(taskListId, taskId)) {
            throw new EntityNotFoundException("The task was not found");
        }

        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
