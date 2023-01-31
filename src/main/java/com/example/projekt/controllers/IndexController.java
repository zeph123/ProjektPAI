package com.example.projekt.controllers;

import com.example.projekt.config.JwtTokenUtil;
import com.example.projekt.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class IndexController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/")
    public String homePage() {
        // przekierowanie do adresu url: /loginPage
        return "redirect:/loginPage";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        // zwrócenie widoku - loginPage.html
        return "loginPage";
    }

    @GetMapping("/registrationPage")
    public String registrationPage(Model m) {
        // dodanie do modelu użytkownika, adresu
        m.addAttribute("user", new UserDto());
        // zwrócenie widoku - registrationPage.html
        return "registrationPage";
    }

    @GetMapping("/tasksPage")
    public String tasksPage() {

        // zwrócenie widoku - tasksPage.html
        return "tasksPage";
    }

    @GetMapping("/profilePage")
    public String profilePage() {

        // zwrócenie widoku - loggedUserProfilePage.html
        return "loggedUserProfilePage";
    }



}
