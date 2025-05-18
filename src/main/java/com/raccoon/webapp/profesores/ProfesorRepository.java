package com.raccoon.webapp.profesores;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfesorRepository extends CrudRepository<Profesor, Integer> {

    public Long countById(Integer id);

    List<Profesor> findByEnabledTrue(); //metodo que devuelve solo los usuarios con enabled true


    // Busca por nombre, apellido o correo electrónico
    @Query(
            ("SELECT e FROM Profesor e WHERE " +
                    "LOWER(e.nombres) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
                    "LOWER(e.apellidos) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
                    "LOWER(e.email) LIKE LOWER(CONCAT('%', :criterio, '%'))"))
    List<Profesor> findByNombreOrApellidosOrEmail(@Param("criterio") String search);

}
