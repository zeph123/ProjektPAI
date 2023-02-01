package com.example.projekt.repositories;

import com.example.projekt.daos.TaskDao;
import com.example.projekt.models.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskDao, Long>, JpaRepository<TaskDao, Long> {

    TaskDao findTaskDaoById(Long id);

    @Query("select new com.example.projekt.models.TaskDto(t.id, t.name, t.status.id, t.status.name, " +
            "t.creationDate, t.completionDate, t.user.id)" +
            "from TaskDao t")
    List<TaskDto> findAllTasks();

    @Query("select new com.example.projekt.models.TaskDto(t.id, t.name, t.status.id, t.status.name, " +
            "t.creationDate, t.completionDate, t.user.id)" +
            "from TaskDao t WHERE t.user.id = :userId")
    List<TaskDto> findAllTasksByUser(@Param("userId") Long userId);

    @Query("select new com.example.projekt.models.TaskDto(t.id, t.name, t.status.id, t.status.name, " +
            "t.creationDate, t.completionDate, t.user.id)" +
            "from TaskDao t WHERE t.id = :id")
    TaskDto findTaskDtoById(@Param("id") Long id);


//    @Query("select new com.example.projekt.models.TaskDto(t.name, t.status.id, t.status.name, " +
//            "DATE_FORMAT(t.creationDate, '%Y-%m-%d %H:%i:%s'), DATE_FORMAT(t.completionDate, '%Y-%m-%d %H:%i:%s'), t.user.id)" +
//            "from TaskDao t")
//    List<TaskDto> findAllTasks();
}
