package com.techguy.task.services.impl;

import com.techguy.task.domain.dto.TaskListDto;
import com.techguy.task.domain.entities.TaskList;
import com.techguy.task.mappers.TaskListMapper;
import com.techguy.task.repositories.TaskListRepository;
import com.techguy.task.services.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;
    private final TaskListMapper taskListMapper;


    @Override
    public List<TaskListDto> listTaskList() {
        return taskListRepository.findAll()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @Override
    public TaskListDto getTasklist(Long taskListId) {
        if (taskListId == null) {
            throw new IllegalArgumentException("The task list Id is required");
        }

        return taskListRepository.findById(taskListId)
                .map(taskListMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Task list does not exist with id " + taskListId));
    }

    @Override
    public TaskListDto updateTaskList(Long id, TaskListDto taskListDto) {
        if (id == null) {
            throw new IllegalArgumentException("The task list Id is required");
        }
        if (!taskListDto.getId().equals(id)) { // it's not necessary because the body will not have the ID inside. I will  separate it and put it as Path variable
            throw new IllegalArgumentException("It's not allow to change the Id");
        }

        TaskList taskListToUpdate = taskListRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("The task list doesn't exist")
        );

        taskListToUpdate.setTitle(taskListDto.getTitle());
        taskListToUpdate.setDescription(taskListDto.getDescription());

        return taskListMapper.toDto(taskListRepository.save(taskListToUpdate));
    }

    @Override
    public TaskListDto createTaskList(TaskListDto taskListDto) {
        if (taskListDto.getId() != null) {
            throw new IllegalArgumentException("The task list is existing already");
        }

        TaskList taskListToBeCreated = taskListMapper.toEntity(taskListDto);


        return taskListMapper.toDto(taskListRepository.save(taskListToBeCreated));
    }

    @Transactional
    @Override
    public void deleteTaskList(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The Id of the task list is required.");
        }

        taskListRepository.deleteById(id);
    }
}
