package com.raccoon.webapp.estudiantes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Esta interfaz extiende {@link CrudRepository} para proporcionar operaciones CRUD
 * para la entidad {@link Estudiantes}. Contiene métodos personalizados para consultar
 * estudiantes habilitados y buscar por nombre, apellido o correo electrónico.
 */
public interface EstudianteRepository extends CrudRepository<Estudiantes, Integer> {

    /**
     * Cuenta el número de estudiantes con un ID específico.
     *
     * @param id El ID del estudiante.
     * @return El número de estudiantes con el ID dado.
     */
    public Long countById(Integer id);

    /**
     * Recupera una lista de estudiantes que tienen el campo 'enabled' establecido en true.
     *
     * @return Lista de estudiantes habilitados.
     */
    List<Estudiantes> findByEnabledTrue();

    /**
     * Busca estudiantes por nombre, apellido o correo electrónico utilizando un criterio de búsqueda.
     * La búsqueda es insensible a mayúsculas/minúsculas.
     *
     * @param search El criterio de búsqueda que puede coincidir con nombre, apellido o correo electrónico.
     * @return Lista de estudiantes que coinciden con el criterio de búsqueda.
     */
    @Query(
            "SELECT e FROM Estudiantes e WHERE " +
                    "LOWER(e.nombres) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
                    "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
                    "LOWER(e.email) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Estudiantes> findByNombreOrApellidosOrEmail(@Param("criterio") String search);
}
