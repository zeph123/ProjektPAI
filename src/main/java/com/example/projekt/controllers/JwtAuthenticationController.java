package com.example.projekt.controllers;

import com.example.projekt.config.JwtTokenUtil;
import com.example.projekt.models.JwtRequest;
import com.example.projekt.models.JwtResponse;
import com.example.projekt.models.UserDao;
import com.example.projekt.models.UserDto;
import com.example.projekt.repositories.UserRepository;
import com.example.projekt.services.JwtUserDetailsService;
import com.example.projekt.services.UserService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> createAuthenticationToken(
        @RequestBody JwtRequest authenticationRequest
    ) {

        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception exception) {
            switch (exception.getMessage()) {
                case "USER_DISABLED": {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", "Konto o podanej nazwie użytkownika zostało permanentnie wyłączone. Skontaktuj się z administratorem w celu uzyskania pomocy.");
                    return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.UNAUTHORIZED);
                }
                case "INVALID_CREDENTIALS": {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", "Niepoprawne dane logowania, sprawdź czy wprowadzona nazwa użytkownika, bądź hasło są poprawne.");
                    return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.UNAUTHORIZED);
                }
            }
        }

        UserDao userFromDb = userRepository.findByUsername(authenticationRequest.getUsername());
        if (userFromDb.getIsArchived()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Konto o podanej nazwie użytkownika zostało zarchiwizowane. Skontaktuj się z administratorem w celu uzyskania pomocy.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final JwtResponse tokenJWT = new JwtResponse(token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", tokenJWT.getToken());
        jsonObject.put("message", "Pomyślnie zalogowano.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(
            @Valid @RequestBody UserDto user, BindingResult result
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
            jsonObject.put("message", "Nie udało się zarejestrować użytkownika, błędy walidacji.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.BAD_REQUEST);
        }

        if (userDetailsService.save(user)) {
            UserDetails registeredUser = userDetailsService.loadUserByUsername(user.getUsername());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", registeredUser.getUsername());
            jsonObject.put("password", registeredUser.getPassword());
            jsonObject.put("message", "Pomyślnie zarejestrowano użytkownika.");
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Nie udało się zarejestrować użytkownika, użytkownik o podanej nazwie: " + user.getUsername() + " już istnieje w bazie.");
        return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.BAD_REQUEST);
    }

    private void authenticate(
        String username,
        String password)
            throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
