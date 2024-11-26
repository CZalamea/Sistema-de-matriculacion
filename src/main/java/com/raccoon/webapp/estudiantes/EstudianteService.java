package com.raccoon.webapp.estudiantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
esta clase meneja la logica del proyecto
 */

@Service
public class EstudianteService {
    @Autowired private EstudianterRepository repo;


    public List<Estudiantes> findEnabledUsers() {
        return  repo.findByEnabledTrue();
    }
    public List<Estudiantes> findAll() {
        return (List<Estudiantes>) repo.findAll();
    }

    public void save(Estudiantes estudiantes) {
        repo.save(estudiantes);
    }

    public Estudiantes getById(Integer id) throws EstudianteNotFoundException {
        Optional<Estudiantes> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new EstudianteNotFoundException("No se pudo encontrar el usuario con el id dado" +id);
    }

    public void delete(Integer id) throws EstudianteNotFoundException {
        Long count = repo.countById(id);
        if (count  == null || count == 0) {
            throw new EstudianteNotFoundException("No se encontro ningn usuario con el ID" +id);
        }
        repo.deleteById(id);
    }
}
