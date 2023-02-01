package com.example.projekt.controllers;

import com.example.projekt.config.JwtTokenUtil;
import com.example.projekt.daos.TaskStatusDao;
import com.example.projekt.daos.UserDao;
import com.example.projekt.models.TaskDto;
import com.example.projekt.services.TaskService;
import com.example.projekt.services.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class TaskController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public ResponseEntity<JSONObject> getAllTasks(
            @RequestHeader("Authorization") String bearerToken
    ) {

        List<TaskDto> tasks = taskService.getAllTasks();
        if (tasks.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie pobrano wszystkie zadania z bazy.");
            jsonObject.put("data", tasks);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie znaleziono żadnych zadań w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tasksByLoggedUser")
    public ResponseEntity<JSONObject> getAllTasksByLoggedUser(
            @RequestHeader("Authorization") String bearerToken
    ) {

        String token = bearerToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDao userFromDb = userService.getUserByUsername(username);

        List<TaskDto> tasks = taskService.getAllTasksByUser(userFromDb);
        if (tasks.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie pobrano wszystkie zadania przypisane do zalogowanego użytkownika.");
            jsonObject.put("data", taskService.getAllTasksByUser(userFromDb));
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie znaleziono żadnych zadań przypisanych do zalogowanego użytkownika.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tasksByUserId/{id}")
    public ResponseEntity<JSONObject> getAllTasksByUserId(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id
    ) {

        UserDao userFromDb = userService.getUserById(id);
        if (userFromDb != null) {
            List<TaskDto> tasks = taskService.getAllTasksByUser(userFromDb);
            if (tasks.size() > 0) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Pomyślnie pobrano wszystkie zadania przypisane do użytkownika o ID: " + id + ".");
                jsonObject.put("data", tasks);
                return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Nie znaleziono żadnych zadań przypisanych do użytkownika o podanym ID: " + id + ".");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się pobrać zadań, użytkownik o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/taskById/{id}")
    public ResponseEntity<JSONObject> getTaskById(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id
    ) {

        TaskDto taskFromDb = taskService.getTaskDtoById(id);
        if (taskFromDb != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie pobrano zadanie o podanym ID: " + id + " z bazy.");
            jsonObject.put("data", taskFromDb);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się pobrać zadania, zadanie o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addTask")
    public ResponseEntity<JSONObject> addTask(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody TaskDto task,
            BindingResult result
    ) {

        if (result.hasErrors()) {

            // Utworzenie listy pól, które zwracaja bledy walidacji
            List<String> fields = new ArrayList<String>();
            for (FieldError fieldError: result.getFieldErrors()) {
                fields.add(fieldError.getField());
            }

            // Usuniecie duplikatów z listy pól
            List<String> fieldsNoDuplicates = fields.stream().distinct().collect(Collectors.toList());
//            System.out.println(fieldsNoDuplicates);

            JSONObject allErrors = new JSONObject();
            for (String fieldName : fieldsNoDuplicates) {
                // Utworzenie listy bledów walidacji dla okreslonego pola
                JSONArray errorsOfField = new JSONArray();
                for (FieldError fieldError: result.getFieldErrors()) {
                    if (fieldError.getField().equals(fieldName)) {
                        errorsOfField.add(fieldError.getDefaultMessage());
                    }
                }
                allErrors.put(fieldName, errorsOfField);
            }
//            System.out.println(allErrors);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errors", allErrors);
            jsonObject.put("message", "Nie udało się dodać nowego zadania, błędy walidacji.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        String token = bearerToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        taskService.addTask(task);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Pomyślnie dodano zadanie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }

    @PutMapping("/updateSelectedTask/{id}")
    public ResponseEntity<JSONObject> updateSelectedTask(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody TaskDto task,
            @PathVariable String id,
            BindingResult result
    ) {

        if (result.hasErrors()) {

            // Utworzenie listy pól, które zwracaja bledy walidacji
            List<String> fields = new ArrayList<String>();
            for (FieldError fieldError: result.getFieldErrors()) {
                fields.add(fieldError.getField());
            }

            // Usuniecie duplikatów z listy pól
            List<String> fieldsNoDuplicates = fields.stream().distinct().collect(Collectors.toList());
//            System.out.println(fieldsNoDuplicates);

            JSONObject allErrors = new JSONObject();
            for (String fieldName : fieldsNoDuplicates) {
                // Utworzenie listy bledów walidacji dla okreslonego pola
                JSONArray errorsOfField = new JSONArray();
                for (FieldError fieldError: result.getFieldErrors()) {
                    if (fieldError.getField().equals(fieldName)) {
                        errorsOfField.add(fieldError.getDefaultMessage());
                    }
                }
                allErrors.put(fieldName, errorsOfField);
            }
//            System.out.println(allErrors);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errors", allErrors);
            jsonObject.put("message", "Nie udało się zaktualizować zadania, błędy walidacji.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        if (taskService.updateTaskById(task, id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie zaktualizowano zadanie o podanym ID: " + id);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zaktualizować zadania, zadanie o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/assignUserToSelectedTask/{task_id}/user/{user_id}")
    public ResponseEntity<JSONObject> assignUserToSelectedTask(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String task_id,
            @PathVariable String user_id
    ) {

        UserDao userFromDb = userService.getUserById(user_id);
        if (userFromDb != null) {
            if (taskService.assignUserToTask(task_id, userFromDb)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Pomyślnie przypisano użytkownika do zadania o podanym ID: " + task_id);
                return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Nie udało się przypisać zadania, zadanie o podanym ID: " + task_id + " nie istnieje w bazie.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się przypisać zadania, użytkownik o podanym ID: " + user_id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/changeStatusOfSelectedTask/{task_id}/status/{status_id}")
    public ResponseEntity<JSONObject> changeStatusOfSelectedTask(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String task_id,
            @PathVariable String status_id
    ) {

        TaskStatusDao taskStatusFromDb = taskService.getTaskStatusById(status_id);
        if (taskStatusFromDb != null) {
            if (taskService.changeTaskStatus(task_id, taskStatusFromDb)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Pomyślnie zmieniono status zadania o podanym ID: " + task_id + " na status: " + taskStatusFromDb.getName() + ".");
                return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Nie udało się zmienić statusu zadania, zadanie o podanym ID: " + task_id + " nie istnieje w bazie.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zmienić statusu zadania, status o podanym ID: " + status_id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteSelectedTask/{id}")
    public ResponseEntity<JSONObject> deleteSelectedTask(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id
    ) {

        if (taskService.deleteTask(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie usunięto zadanie o podanym ID: " + id);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się usunąć zadania, zadanie o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

}
