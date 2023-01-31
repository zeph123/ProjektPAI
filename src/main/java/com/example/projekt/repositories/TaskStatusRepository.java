package com.example.projekt.repositories;

import com.example.projekt.models.TaskStatusDao;
import org.springframework.data.repository.CrudRepository;

public interface TaskStatusRepository extends CrudRepository<TaskStatusDao, Long> {


}
