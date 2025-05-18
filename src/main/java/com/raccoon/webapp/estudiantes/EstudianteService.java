package com.raccoon.webapp.estudiantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Esta clase maneja la lógica de negocio relacionada con los estudiantes en la aplicación.
 * Proporciona métodos para gestionar estudiantes, incluyendo la creación, actualización,
 * eliminación y recuperación de estudiantes.
 */
@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository repo;

    /**
     * Obtiene una lista de estudiantes habilitados.
     *
     * @return Lista de estudiantes habilitados.
     */
    public List<Estudiantes> findEnabledUsers() {
        return repo.findByEnabledTrue();
    }

    /**
     * Recupera todos los estudiantes de la base de datos.
     *
     * @return Lista de todos los estudiantes.
     */
    public List<Estudiantes> findAll() {
        return (List<Estudiantes>) repo.findAll();
    }

    /**
     * Guarda o actualiza un estudiante en la base de datos.
     *
     * @param estudiantes El objeto estudiante a guardar o actualizar.
     */
    public void save(Estudiantes estudiantes) {
        repo.save(estudiantes);
    }

    /**
     * Recupera un estudiante por su ID.
     * Si el estudiante no existe, lanza una excepción {@link EstudianteNotFoundException}.
     *
     * @param id El ID del estudiante a recuperar.
     * @return El estudiante con el ID proporcionado.
     * @throws EstudianteNotFoundException Si no se encuentra un estudiante con el ID dado.
     */
    public Estudiantes getById(Integer id) throws EstudianteNotFoundException {
        Optional<Estudiantes> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new EstudianteNotFoundException("No se pudo encontrar el usuario con el id dado: " + id);
    }

    /**
     * Elimina un estudiante por su ID.
     * Si el estudiante no existe, lanza una excepción {@link EstudianteNotFoundException}.
     *
     * @param id El ID del estudiante a eliminar.
     * @throws EstudianteNotFoundException Si no se encuentra un estudiante con el ID dado.
     */
    public void delete(Integer id) throws EstudianteNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new EstudianteNotFoundException("No se encontró ningún usuario con el ID: " + id);
        }
        repo.deleteById(id);
    }

    /**
     * Busca estudiantes por nombre, apellidos o correo electrónico.
     *
     * @param search El criterio de búsqueda.
     * @return Lista de estudiantes que coinciden con el criterio de búsqueda.
     */
    public List<Estudiantes> buscarPorCriterio(String search) {
        return repo.findByNombreOrApellidosOrEmail(search);
    }
}
