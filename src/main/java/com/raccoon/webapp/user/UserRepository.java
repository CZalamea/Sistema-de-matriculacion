package com.raccoon.webapp.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public Long countById(Integer id);

    List<User> findByEnabledTrue(); //metodo que devuelve solo los usuarios con enabled true
}
