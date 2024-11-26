package com.raccoon.webapp.estudiantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class EstudiantesController {
    @Autowired private EstudianteService estudianteService;

    @GetMapping("/estudiantes/all_users")
    public String enabledUsers(Model model) {
        List<Estudiantes> listaEstudiantes = estudianteService.findAll();
        model.addAttribute("listaEstudiantes", listaEstudiantes);

        return "all_estudiantes";
    }

    @GetMapping("/estudiantes")
    public String mostrarListaUsuarios(Model model) {
        List<Estudiantes> listaEstudiantes = estudianteService.findEnabledUsers();
        model.addAttribute("listaEstudiantes", listaEstudiantes);
        System.out.println(listaEstudiantes);
        return "estudiantes";
    }

    @GetMapping("/estudiantes/new")
    public String crearNuevoUsuario(Model model) {
        model.addAttribute("estudiantes", new Estudiantes());
        model.addAttribute("pageTitle", "Nuevo usuario");

        return "estudiantes_form";
    }

    @PostMapping("/estudiantes/save")
    public String guardarNuevoUsuario(@ModelAttribute("estudiantes") Estudiantes estudiantes, RedirectAttributes redirectAttributes) {
        try{
            // verificamos si existe el usuario
            if (estudiantes.getId() != null) {
                //recuperamos el usuario
                Estudiantes existingEstudiantes = estudianteService.getById(estudiantes.getId());

                //actualizamos los datos
                existingEstudiantes.setFirstName(estudiantes.getFirstName());
                existingEstudiantes.setLastName(estudiantes.getLastName());
                existingEstudiantes.setEmail(estudiantes.getEmail());

                //solo actualizaremos si se ingresa una nueva contrasena

                if (estudiantes.getPassword() != null && !estudiantes.getPassword().isEmpty()) {
                    existingEstudiantes.setPassword((estudiantes.getPassword()));
                }

                existingEstudiantes.setEnabled(estudiantes.isEnabled());

                estudianteService.save(existingEstudiantes);
                redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamente");
            }

            else {
                estudianteService.save(estudiantes);
                redirectAttributes.addFlashAttribute("message", "Usuario guardado exitosamente");
            }

            //guardamos el usuario actualizado
            estudianteService.save(estudiantes);
        } catch (EstudianteNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/estudiantes/all_users";


        /*userService.save(estudiantes);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado exitosamente");
        return "redirect:/users";*/
    }

    @GetMapping("/estudiantes/edit/{id}")
    public String mostrarEditarUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Estudiantes estudiantes = estudianteService.getById(id);
            model.addAttribute("user", estudiantes);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "estudiantes_form";
        }
        catch (EstudianteNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/estudiantes";
    }

    @GetMapping("/estudiantes/all_users/edit/{id}")
    public String mostrarEditarAllUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Estudiantes estudiantes = estudianteService.getById(id);
            model.addAttribute("user", estudiantes);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "estudiantes_form";
        }
        catch (EstudianteNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/estudiantes";
    }

    @GetMapping("/estudiantes/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try{
            estudianteService.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado " +id + " exitosamente");
        }catch (EstudianteNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());

        }
        return "redirect:/estudiantes";
    }
}

