package com.techguy.task.repositories;


import com.techguy.task.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTaskListId(Long taskListId);
    Optional<Task> findByTaskListIdAndId(Long taskListId, Long id);
    void deleteByTaskListIdAndId(Long taskListId, Long taskId);
    boolean existsByTaskListIdAndId(Long taskListId, Long taskId);
}
