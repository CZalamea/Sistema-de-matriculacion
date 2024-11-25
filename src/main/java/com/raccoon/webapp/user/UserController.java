package com.raccoon.webapp.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/users/all_users")
    public String enabledUsers(Model model) {
        List<User> listaUsers = userService.findAll();
        model.addAttribute("listaUsers", listaUsers);

        return "all_users";
    }

    @GetMapping("/users")
    public String mostrarListaUsuarios(Model model) {
        List<User> listaUsers = userService.findEnabledUsers();
        model.addAttribute("listaUsers", listaUsers);
        System.out.println(listaUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String crearNuevoUsuario(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Nuevo usuario");

        return "user_form";
    }

    @PostMapping("/users/save")
    public String guardarNuevoUsuario(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try{
            // verificamos si existe el usuario
            if (user.getId() != null) {
                //recuperamos el usuario
                User existingUser = userService.getById(user.getId());

                //actualizamos los datos
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());

                //solo actualizaremos si se ingresa una nueva contrasena

                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    existingUser.setPassword((user.getPassword()));
                }

                existingUser.setEnabled(user.isEnabled());

                userService.save(existingUser);
                redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamente");
            }

            else {
                userService.save(user);
                redirectAttributes.addFlashAttribute("message", "Usuario guardado exitosamente");
            }

            //guardamos el usuario actualizado
            userService.save(user);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/users/all_users";


        /*userService.save(user);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado exitosamente");
        return "redirect:/users";*/
    }

    @GetMapping("/users/edit/{id}")
    public String mostrarEditarUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            User user = userService.getById(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "user_form";
        }
        catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/all_users/edit/{id}")
    public String mostrarEditarAllUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            User user = userService.getById(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "user_form";
        }
        catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try{
            userService.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado " +id + " exitosamente");
        }catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());

        }
        return "redirect:/users";
    }
}

