package com.techguy.task.mappers;

import com.techguy.task.domain.dto.TaskListDto;
import com.techguy.task.domain.entities.Task;
import com.techguy.task.domain.entities.TaskList;
import com.techguy.task.domain.entities.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskListMapper {

    @Mapping(target = "progress", source = "tasks", qualifiedByName = "getProgress")
    @Mapping(target = "count", source = "tasks", qualifiedByName = "getTaskCount")
    TaskListDto toDto(TaskList taskList);

    TaskList toEntity(TaskListDto taskListDto);

    @Named("getTaskCount")
    default int getTaskCount(List<Task> tasks)
    {
        return tasks != null ? tasks.size() : 0;
    }

    @Named("getProgress")
    default Double getProgress(List<Task> tasks)
    {
        if (tasks == null || tasks.size() == 0.0) {
            return 0.0;
        }

        long closedTaskCount = tasks.stream()
                          .filter(task -> TaskStatus.CLOSED.equals(task.getStatus()))
                          .count();

        return (double) (closedTaskCount /  tasks.size());

    }
}
