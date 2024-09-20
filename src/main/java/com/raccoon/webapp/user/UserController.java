package com.raccoon.webapp.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/users")
    public String mostrarListaUsuarios(Model model) {
        List<User> listaUsers = userService.findAll();
        model.addAttribute("listaUsers", listaUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String crearNuevoUsuario(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }
}
