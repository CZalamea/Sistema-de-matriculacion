package com.raccoon.webapp.profesores;

import com.raccoon.webapp.estudiantes.EstudianteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
esta clase meneja la logica del proyecto
 */

@Service
public class ProfesorService {
    @Autowired private ProfesorRepository profesorRepository;


    public List<Profesor> findEnabledUsers() {
        return  profesorRepository.findByEnabledTrue();
    }
    public List<Profesor> findAll() {
        return (List<Profesor>) profesorRepository.findAll();
    }

    public void save(Profesor profesor) {
        profesorRepository.save(profesor);
    }

    public Profesor getById(Integer id) throws EstudianteNotFoundException {
        Optional<Profesor> result = profesorRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new EstudianteNotFoundException("No se pudo encontrar el usuario con el id dado" +id);
    }

    public void delete(Integer id) throws EstudianteNotFoundException {
        Long count = profesorRepository.countById(id);
        if (count  == null || count == 0) {
            throw new EstudianteNotFoundException("No se encontro ningn usuario con el ID" +id);
        }
        profesorRepository.deleteById(id);
    }

    public List<Profesor> buscarPorCriterio(String search) {
        return profesorRepository.findByNombreOrApellidosOrEmail(search);
    }
}
