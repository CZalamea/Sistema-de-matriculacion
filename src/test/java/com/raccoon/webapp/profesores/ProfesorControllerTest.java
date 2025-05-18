package com.raccoon.webapp.profesores;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfesorController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ProfesorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesorService profesorService;

    @Test
    void testMostrarListaUsuarios() throws Exception {
        // Crear profesores usando el constructor por defecto y setters
        Profesor profesor1 = new Profesor();
        profesor1.setId(1);
        profesor1.setNombres("Juan");
        profesor1.setApellidos("Pérez");
        profesor1.setEmail("juan@example.com");
        profesor1.setPassword("password123");
        profesor1.setCurso("Matemáticas");
        profesor1.setMatricula("12345");
        profesor1.setEnabled(true);

        Profesor profesor2 = new Profesor();
        profesor2.setId(2);
        profesor2.setNombres("Ana");
        profesor2.setApellidos("Gómez");
        profesor2.setEmail("ana@example.com");
        profesor2.setPassword("password456");
        profesor2.setCurso("Ciencias");
        profesor2.setMatricula("67890");
        profesor2.setEnabled(true);

        // Lista de profesores simulada
        List<Profesor> listaProfesores = Arrays.asList(profesor1, profesor2);

        // Simulando el comportamiento del servicio
        Mockito.when(profesorService.findEnabledUsers()).thenReturn(listaProfesores);

        // Realiza la solicitud GET y verifica que se devuelve la vista correcta
        mockMvc.perform(MockMvcRequestBuilders.get("/profesores"))
                .andExpect(status().isOk())
                .andExpect(view().name("profesores"))
                .andExpect(model().attribute("listaProfesores", hasSize(3))); //aqui falla el test debido a que solo se ingresaron 2 objetos de la clase profesor
    }

    @Test
    void testCrearNuevoUsuario() throws Exception {
        // Realiza la solicitud GET a "/profesores/new"
        mockMvc.perform(MockMvcRequestBuilders.get("/profesores/new"))
                .andExpect(status().isOk()) // Asegura que la respuesta sea 200 OK
                .andExpect(view().name("profesores_form")) // Verifica que se devuelva la vista "profesores_form"
                .andExpect(model().attributeExists("profesores")) // Asegura que el modelo contenga el atributo "profesores"
                .andExpect(model().attribute("pageTitle", "Nuevo usuario")); // Verifica que el atributo "pageTitle" tenga el valor esperado
    }

}