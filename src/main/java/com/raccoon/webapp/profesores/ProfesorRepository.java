package com.raccoon.webapp.profesores;

import com.raccoon.webapp.estudiantes.Estudiantes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfesorRepository extends CrudRepository<Profesor, Integer> {

    public Long countById(Integer id);

    List<Profesor> findByEnabledTrue();

}
