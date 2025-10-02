package com.techguy.task.repositories;

import com.techguy.task.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
