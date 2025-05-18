package com.raccoon.webapp.estudiantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los estudiantes.
 * Este controlador maneja la visualización, creación, edición, actualización y eliminación de estudiantes.
 */
@Controller
public class EstudiantesController {

    // Inyección del servicio para gestionar operaciones con los estudiantes
    @Autowired
    private EstudianteService estudianteService;

    /**
     * Muestra todos los estudiantes con un filtro de búsqueda opcional.
     * Si el parámetro de búsqueda es proporcionado, se filtran los estudiantes según el criterio de búsqueda.
     *
     * @param search Criterio de búsqueda opcional.
     * @param model El modelo que se pasa a la vista.
     * @return Nombre de la vista a renderizar (all_estudiantes).
     */
    @GetMapping("/estudiantes/all_users")
    public String enabledUsers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Estudiantes> listaEstudiantes;

        if (search != null && !search.isEmpty()) {
            // Si hay un criterio de búsqueda, se busca por ese criterio
            listaEstudiantes = estudianteService.buscarPorCriterio(search);
        } else {
            // Si no hay criterio de búsqueda, se muestran todos los estudiantes
            listaEstudiantes = estudianteService.findAll();
        }

        // Se añaden los estudiantes y el valor de búsqueda al modelo para ser utilizados en la vista
        model.addAttribute("listaEstudiantes", listaEstudiantes);
        model.addAttribute("search", search); // Para mantener el valor del campo de búsqueda en la vista
        return "all_estudiantes";
    }

    /**
     * Muestra la lista de estudiantes activos.
     *
     * @param model El modelo que se pasa a la vista.
     * @return Nombre de la vista a renderizar (estudiantes).
     */
    @GetMapping("/estudiantes")
    public String mostrarListaUsuarios(Model model) {
        List<Estudiantes> listaEstudiantes = estudianteService.findEnabledUsers();
        model.addAttribute("listaEstudiantes", listaEstudiantes);
        System.out.println(listaEstudiantes);
        return "estudiantes";
    }

    /**
     * Muestra el formulario para crear un nuevo estudiante.
     *
     * @param model El modelo que se pasa a la vista.
     * @return Nombre de la vista a renderizar (estudiantes_form).
     */
    @GetMapping("/estudiantes/new")
    public String crearNuevoUsuario(Model model) {
        model.addAttribute("estudiantes", new Estudiantes());
        model.addAttribute("pageTitle", "Nuevo usuario");

        return "estudiantes_form";
    }

    /**
     * Guarda un nuevo estudiante o actualiza uno existente.
     * Si el estudiante tiene un ID, se considera una actualización; si no, se crea un nuevo estudiante.
     *
     * @param estudiantes El objeto Estudiantes que se va a guardar o actualizar.
     * @param redirectAttributes Atributos para redirigir con un mensaje de éxito.
     * @return Redirección a la lista de estudiantes.
     */
    @PostMapping("/estudiantes/save")
    public String guardarNuevoUsuario(@ModelAttribute("estudiantes") Estudiantes estudiantes, RedirectAttributes redirectAttributes) {
        try {
            // Verificamos si el estudiante tiene un ID (actualización)
            if (estudiantes.getId() != null) {
                // Recuperamos el estudiante existente
                Estudiantes existingEstudiantes = estudianteService.getById(estudiantes.getId());

                // Actualizamos los datos del estudiante
                existingEstudiantes.setNombres(estudiantes.getNombres());
                existingEstudiantes.setApellidos(estudiantes.getApellidos());
                existingEstudiantes.setEmail(estudiantes.getEmail());

                // Solo actualizamos la contraseña si se ha ingresado una nueva
                if (estudiantes.getPassword() != null && !estudiantes.getPassword().isEmpty()) {
                    existingEstudiantes.setPassword(estudiantes.getPassword());
                }

                existingEstudiantes.setEnabled(estudiantes.isEnabled());

                // Guardamos el estudiante actualizado
                estudianteService.save(existingEstudiantes);
                redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamente");
            } else {
                // Si no tiene ID, creamos un nuevo estudiante
                estudianteService.save(estudiantes);
                redirectAttributes.addFlashAttribute("message", "Usuario guardado exitosamente");
            }

        } catch (EstudianteNotFoundException e) {
            // Excepción en caso de que no se encuentre el estudiante
            throw new RuntimeException(e);
        }
        return "redirect:/estudiantes/all_users";
    }

    @PostMapping("/estudiantes/api/save")
    public ResponseEntity<String> guardarEstudianteDesdeJson(@RequestBody Estudiantes estudiantes) {
        try {
            if (estudiantes.getId() != null) {
                // Actualización
                Estudiantes existingEstudiantes = estudianteService.getById(estudiantes.getId());
                existingEstudiantes.setNombres(estudiantes.getNombres());
                existingEstudiantes.setApellidos(estudiantes.getApellidos());
                existingEstudiantes.setEmail(estudiantes.getEmail());
                if (estudiantes.getPassword() != null && !estudiantes.getPassword().isEmpty()) {
                    existingEstudiantes.setPassword(estudiantes.getPassword());
                }
                existingEstudiantes.setEnabled(estudiantes.isEnabled());
                estudianteService.save(existingEstudiantes);
                return ResponseEntity.ok("Usuario actualizado correctamente");
            } else {
                // Creación
                estudianteService.save(estudiantes);
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario guardado exitosamente");
            }
        } catch (EstudianteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
        }
    }

    /**
     * Muestra el formulario de edición de un estudiante específico.
     *
     * @param id El ID del estudiante a editar.
     * @param model El modelo que se pasa a la vista.
     * @param redirectAttributes Atributos para redirigir con un mensaje de error en caso de no encontrar el estudiante.
     * @return Nombre de la vista a renderizar (estudiantes_form).
     */
    @GetMapping("/estudiantes/edit/{id}")
    public String mostrarEditarUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Estudiantes estudiantes = estudianteService.getById(id);
            model.addAttribute("estudiantes", estudiantes);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "estudiantes_form";
        } catch (EstudianteNotFoundException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/estudiantes";
    }

    /**
     * Muestra el formulario de edición de un estudiante desde la lista completa de estudiantes.
     *
     * @param id El ID del estudiante a editar.
     * @param model El modelo que se pasa a la vista.
     * @param redirectAttributes Atributos para redirigir con un mensaje de error en caso de no encontrar el estudiante.
     * @return Nombre de la vista a renderizar (estudiantes_form).
     */
    @GetMapping("/estudiantes/all_users/edit/{id}")
    public String mostrarEditarAllUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Estudiantes estudiantes = estudianteService.getById(id);
            model.addAttribute("estudiantes", estudiantes);
            model.addAttribute("pageTitle", "Editar usuario (ID: " + id + ")");
            return "estudiantes_form";
        } catch (EstudianteNotFoundException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/estudiantes";
    }

    /**
     * Elimina un estudiante basado en su ID.
     *
     * @param id El ID del estudiante a eliminar.
     * @param redirectAttributes Atributos para redirigir con un mensaje de éxito o error.
     * @return Redirección a la lista de estudiantes.
     */
    @GetMapping("/estudiantes/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            estudianteService.delete(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado " + id + " exitosamente");
        } catch (EstudianteNotFoundException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/estudiantes";
    }
}

