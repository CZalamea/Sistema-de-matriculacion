package com.raccoon.webapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/*
esta clase meneja la logica del negocio
 */


@Service
public class UserService {
    @Autowired private UserRepository repo;


    public List<User> findEnabledUsers() {
        return  repo.findByEnabledTrue();
    }
    public List<User> findAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User getById(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("No se pudo encontrar el usuario con el id dado" +id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count  == null || count == 0) {
            throw new UserNotFoundException("No se encontro ningn usuario con el ID" +id);
        }
        repo.deleteById(id);
    }
}
