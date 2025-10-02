package com.techguy.task.mappers;

import com.techguy.task.domain.dto.TaskDto;
import com.techguy.task.domain.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    @Mapping(source = "taskPriority", target = "priority")
    @Mapping(source = "taskStatus", target = "status")
    Task toEntity(TaskDto taskDto);

    @Mapping(target = "taskPriority", source = "priority")
    @Mapping(target = "taskStatus", source = "status")
    TaskDto toDto(Task task);
}
