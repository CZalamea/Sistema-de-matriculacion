package com.raccoon.webapp;


import com.raccoon.webapp.user.User;
import com.raccoon.webapp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired private UserRepository repo;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setEmail("cesar.z@gmail.com");
        user.setPassword("123456");
        user.setFirstName("Cesar");
        user.setLastName("Zalamea");

        User saverUser = repo.save(user);

        Assertions.assertNotNull(saverUser); //acierta que el objeto no sea nulo
        Assertions.assertTrue(saverUser.getId() > 0); //acierta que el id del objeto sea mayor a 0


    }

    @Test
    public void testListAllUsers() {
        List<User> users = (List<User>) repo.findAll();

        Assertions.assertTrue(users.size()>0);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("hello2000");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        Assertions.assertEquals("hello2000", updatedUser.getPassword());

    }

    @Test
    public void testGet() {
        int userId = 2;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertTrue(optionalUser.isPresent());
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        int userId = 2;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertFalse(optionalUser.isPresent());
    }

}
