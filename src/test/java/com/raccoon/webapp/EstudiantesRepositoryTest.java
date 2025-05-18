package com.raccoon.webapp;


import com.raccoon.webapp.estudiantes.Estudiantes;
import com.raccoon.webapp.estudiantes.EstudianteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EstudiantesRepositoryTest {
    @Autowired private EstudianteRepository repo;

    @Test
    public void testAddUser() {
        Estudiantes estudiantes = new Estudiantes();
        estudiantes.setEmail("cesar.z@gmail.com");
        estudiantes.setPassword("123456");
        estudiantes.setNombres("Cesar");
        estudiantes.setApellidos("Zalamea");

        Estudiantes saverEstudiantes = repo.save(estudiantes);

        Assertions.assertNotNull(saverEstudiantes); //acierta que el objeto no sea nulo
        Assertions.assertTrue(saverEstudiantes.getId() > 0); //acierta que el id del objeto sea mayor a 0


    }

    /*@Test
    public void testListAllUsers() {
        List<Estudiantes> estudiantes = (List<Estudiantes>) repo.findAll();

        Assertions.assertTrue(estudiantes.size()>0);

        for (Estudiantes estudiantes : estudiantes) {
            System.out.println(estudiantes);
        }
    }*/

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<Estudiantes> optionalUser = repo.findById(userId);
        Estudiantes estudiantes = optionalUser.get();
        estudiantes.setPassword("hello2000");
        repo.save(estudiantes);

        Estudiantes updatedEstudiantes = repo.findById(userId).get();
        Assertions.assertEquals("hello2000", updatedEstudiantes.getPassword());

    }

    @Test
    public void testGet() {
        int userId = 2;
        Optional<Estudiantes> optionalUser = repo.findById(userId);
        Assertions.assertTrue(optionalUser.isPresent());
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        int userId = 2;
        repo.deleteById(userId);

        Optional<Estudiantes> optionalUser = repo.findById(userId);
        Assertions.assertFalse(optionalUser.isPresent());
    }

}
