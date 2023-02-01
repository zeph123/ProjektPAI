package com.example.projekt.services;

import com.example.projekt.daos.TaskDao;
import com.example.projekt.daos.TaskStatusDao;
import com.example.projekt.daos.UserDao;
import com.example.projekt.enums.TaskStatus;
import com.example.projekt.models.TaskDto;
import com.example.projekt.repositories.TaskRepository;
import com.example.projekt.repositories.TaskStatusRepository;
import com.example.projekt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskDto> getAllTasks() {
        return (List<TaskDto>) taskRepository.findAllTasks();
    }

    public List<TaskDto> getAllTasksByUser(UserDao user) {
        return (List<TaskDto>) taskRepository.findAllTasksByUser(user.getId());
    }

    public TaskDao getTaskById(String id) {
        Long taskId = Long.parseLong(id);
        if (taskRepository.existsById(taskId)) {
            return taskRepository.findTaskDaoById(taskId);
        }
        return null;
    }

    public TaskDto getTaskDtoById(String id) {
        Long taskId = Long.parseLong(id);
        if (taskRepository.existsById(taskId)) {
            return taskRepository.findTaskDtoById(taskId);
        }
        return null;
    }

    public TaskStatusDao getTaskStatusById(String id) {
        Long statusId = Long.parseLong(id);
        if (taskStatusRepository.existsById(statusId)) {
            return taskStatusRepository.findTaskStatusDaoById(statusId);
        }
        return null;
    }

    public void addTask(TaskDto task) {
        TaskDao newTask = new TaskDao();
        newTask.setName(task.getName());
        Long statusId = (long) TaskStatus.STATUS_TODO.value();
        TaskStatusDao taskStatus = taskStatusRepository.findTaskStatusDaoById(statusId);
        newTask.setStatus(taskStatus);
        newTask.setCreationDate(Instant.now().atZone(ZoneId.of("Europe/Warsaw")));
        newTask.setUser(null);
        taskRepository.save(newTask);
    }

    public boolean updateTaskById(TaskDto task, String task_id){
        TaskDao taskFromDb = getTaskById(task_id);
        if (taskFromDb != null) {
            taskFromDb.setName(task.getName());
            TaskStatusDao taskStatus = taskStatusRepository.findTaskStatusDaoById(task.getStatusId());
            taskFromDb.setStatus(taskStatus);
            if (checkIfTaskIsCompleted(taskStatus)) {
                taskFromDb.setCompletionDate(Instant.now().atZone(ZoneId.of("Europe/Warsaw")));
            } else {
                taskFromDb.setCompletionDate(null);
            }
            UserDao userFromDb = userRepository.findUserDaoById(task.getUserId());
            taskFromDb.setUser(userFromDb);
            taskRepository.save(taskFromDb);
            return true;
        }
        return false;
    }

    public boolean assignUserToTask(String task_id, UserDao user) {
        TaskDao taskFromDb = getTaskById(task_id);
        if (taskFromDb != null) {
            taskFromDb.setUser(user);
            taskRepository.save(taskFromDb);
            return true;
        }
        return false;
    }

    public boolean checkIfTaskIsCompleted(TaskStatusDao taskStatus) {
        Long statusDoneId = (long) TaskStatus.STATUS_DONE.value();
        return taskStatus.getId().equals(statusDoneId);
    }

    public boolean changeTaskStatus(String task_id, TaskStatusDao taskStatus){
        TaskDao taskFromDb = getTaskById(task_id);
        if (taskFromDb != null) {
            taskFromDb.setStatus(taskStatus);
            if (checkIfTaskIsCompleted(taskStatus)) {
                taskFromDb.setCompletionDate(Instant.now().atZone(ZoneId.of("Europe/Warsaw")));
            } else {
                taskFromDb.setCompletionDate(null);
            }
            taskRepository.save(taskFromDb);
            return true;
        }
        return false;
    }

    public boolean deleteTask(String id){
        TaskDao taskFromDb = getTaskById(id);
        if (taskFromDb != null) {
            taskRepository.delete(taskFromDb);
            return true;
        }
        return false;
    }

}
