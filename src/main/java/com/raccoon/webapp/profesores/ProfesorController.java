package com.raccoon.webapp.profesores;

import com.raccoon.webapp.estudiantes.EstudianteNotFoundException;
import com.raccoon.webapp.estudiantes.EstudianteService;
import com.raccoon.webapp.estudiantes.Estudiantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProfesorController {
    @Autowired private ProfesorService profesorService;


    // Métodh que muestra todos los estudiantes (con filtro de búsqueda opcional)
    @GetMapping("/profesores/all_users")
    public String enabledUsers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Profesor> listaProfesores;

        if (search != null && !search.isEmpty()) {
            listaProfesores = profesorService.buscarPorCriterio(search); // Metodh de búsqueda en el servicio
        } else {
            listaProfesores = profesorService.findAll(); // Lista completa
        }

        model.addAttribute("listaProfesores", listaProfesores);
        model.addAttribute("search", search); // Para mantener el valor del campo de búsqueda en la vista
        return "all_profesores";
    }

    //metodo que muestra los estudiantes activos
    @GetMapping("/profesores")
    public String mostrarListaUsuarios(Model model) {
        List<Profesor> listaProfesores = profesorService.findEnabledUsers();
        model.addAttribute("listaProfesores", listaProfesores);
        System.out.println(listaProfesores);
        return "profesores";
    }

    @GetMapping("/profesores/new")
    public String crearNuevoUsuario(Model model) {
        model.addAttribute("profesores", new Profesor());
        model.addAttribute("pageTitle", "Nuevo usuario");

        return "profesores_form";
    }

    @PostMapping("/profesores/save")
    public String guardarNuevoUsuario(@ModelAttribute("estudiantes") Profesor profesor, RedirectAttributes redirectAttributes) {
        try{
            // verificamos si existe el usuario
            if (profesor.getId() != null) {
                //recuperamos el usuario
                Profesor existingProfesor = profesorService.getById(profesor.getId());

                //actualizamos los datos
                existingProfesor.setNombres(profesor.getNombres());
                existingProfesor.setApellidos(profesor.getApellidos());
                existingProfesor.setEmail(profesor.getEmail());

                //solo actualizaremos si se ingresa una nueva contrasena

                if (profesor.getPassword() != null && !profesor.getPassword().isEmpty()) {
                    existingProfesor.setPassword((profesor.getPassword()));
                }

                existingProfesor.setEnabled(profesor.isEnabled());

                profesorService.save(existingProfesor);
                redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamente");
            }

            else {
                profesorService.save(profesor);
                redirectAttributes.addFlashAttribute("message", "Usuario guardado exitosamente");
            }

            //guardamos el usuario actualizado
            profesorService.save(profesor);
        } catch (EstudianteNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/profesores";


        /*userService.save(estudiantes);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado exitosamente");
        return "redirect:/users";*/
    }

    @GetMapping("/profesores/edit/{id}")
    public String mostrarEditarUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Profesor profesor = profesorService.getById(id);
            model.addAttribute("profesores", profesor);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "profesores_form";
        }
        catch (EstudianteNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/profesores";
    }

    @GetMapping("/profesores/all_users/edit/{id}")
    public String mostrarEditarAllUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Profesor profesor = profesorService.getById(id);
            model.addAttribute("estudiantes", profesor);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "profesores_form";
        }
        catch (EstudianteNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/profesores";
    }

    @GetMapping("/profesores/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try{
            profesorService.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado " +id + " exitosamente");
        }catch (EstudianteNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());

        }
        return "redirect:/profesores";
    }
}

