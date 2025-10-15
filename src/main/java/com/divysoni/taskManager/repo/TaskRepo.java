package com.divysoni.taskManager.repo;

import com.divysoni.taskManager.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {

    // find taskList from its I'd
    List<Task> findByTaskListId(UUID taskListId);

    // looks for single Task where the taskListId and the TaskId matches
    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID Id);

    // deleting the task
    void deleteByTaskListIdAndId(UUID taskListId, UUID taskId);
}