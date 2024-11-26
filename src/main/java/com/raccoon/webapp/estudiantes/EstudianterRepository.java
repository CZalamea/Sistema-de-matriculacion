package com.raccoon.webapp.estudiantes;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EstudianterRepository extends CrudRepository<Estudiantes, Integer> {

    public Long countById(Integer id);

    List<Estudiantes> findByEnabledTrue(); //metodo que devuelve solo los usuarios con enabled true
}
