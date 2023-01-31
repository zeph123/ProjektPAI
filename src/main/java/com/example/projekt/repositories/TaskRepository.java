package com.example.projekt.repositories;

import com.example.projekt.models.TaskDao;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskDao, Long> {

}
