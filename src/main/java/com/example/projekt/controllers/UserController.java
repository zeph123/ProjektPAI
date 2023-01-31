package com.example.projekt.controllers;

import com.example.projekt.config.JwtTokenUtil;
import com.example.projekt.enums.UserRole;
import com.example.projekt.models.UserDto;
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
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<JSONObject> getAllUser(
            @RequestHeader("Authorization") String bearerToken
    ){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Pomyślnie pobrano wszystkich użytkowników z bazy.");
        jsonObject.put("data", userService.getAllUser());
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }

    @PutMapping("/editLoggedUser")
    public ResponseEntity<JSONObject> editLoggedUser(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody UserDto user,
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
            jsonObject.put("message", "Nie udało się zaktualizować zalogowanego użytkownika, błędy walidacji.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        String token = bearerToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        if (userService.updateLoggedUser(user, username)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie zaktualizowano zalogowanego użytkownika o nazwie użytkownika: " + username + ".");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zaktualizować zalogowanego użytkownika, użytkownik o nazwie użytkownika: " + username + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/archiveLoggedUser")
    public ResponseEntity<JSONObject> archiveLoggedUser(
            @RequestHeader("Authorization") String bearerToken
    ) {

        String token = bearerToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        if (userService.archiveLoggedUser(username)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie zarchiwizowano zalogowanego użytkownika o nazwie użytkownika: " + username + ".");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zarchiwizować zalogowanego użytkownika, użytkownik o nazwie użytkownika: " + username + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/editSelectedUser/{id}")
    public ResponseEntity<JSONObject> editSelectedUser(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody UserDto user,
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
            jsonObject.put("message", "Nie udało się zaktualizować użytkownika, błędy walidacji.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        if (userService.updateSelectedUserById(user, id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie zaktualizowano użytkownika o podanym ID: " + id);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zaktualizować użytkownika, użytkownik o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/archiveSelectedUser/{id}")
    public ResponseEntity<JSONObject> archiveSelectedUser(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id
    ) {

        if (userService.archiveSelectedUserById(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie zarchiwizowano użytkownika o podanym ID: " + id);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zarchiwizować użytkownika, użytkownik o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/unarchiveSelectedUser/{id}")
    public ResponseEntity<JSONObject> unarchiveSelectedUser(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody UserDto user,
            @PathVariable String id
    ) {

        if (userService.unarchiveSelectedUserById(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie odarchiwizowano użytkownika o podanym ID: " + id);
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się odarchiwizować użytkownika, użytkownik o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/changeSelectedUserRole/{id}/role/{role_id}")
    public ResponseEntity<JSONObject> changeSelectedUserRole(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id,
            @PathVariable String role_id
    ) {

        if (userService.changeSelectedUserRoleById(id, role_id)) {
            int roleId = Integer.parseInt(role_id);
            UserRole role = UserRole.valueOf(roleId);
            String roleName = role.getName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Pomyślnie zmieniono rolę użytkownika o podanym ID: " + id + " na rolę: " + roleName + ".");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zmienić roli użytkownika, użytkownik o podanym ID: " + id + " nie istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.NOT_FOUND);
    }

}
