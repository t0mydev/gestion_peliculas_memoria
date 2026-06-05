package cl.usm.gestionPeliculasMemoria.repositories;

import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculasRepositoryImplTest {

    private PeliculasRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new PeliculasRepositoryImpl();
    }

    @Test
    void insertPeliculaOk() {
        Pelicula p = new Pelicula();
        p.setId("P1");
        Pelicula result = repository.insert(p);

        assertNotNull(result);
        assertEquals("P1", result.getId());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void insertPeliculaNok() {
        Pelicula p = new Pelicula();
        try {
            repository.insert(p);
            fail("expected era una IllegalArgumentException porque el ID es nulo");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("no puede ser nulo"));
        }
    }

    @Test
    void insertPeliculaConIdDuplicado() {
        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        repository.insert(p1);
        Pelicula p2 = new Pelicula();
        p2.setId("P1");
        try {
            repository.insert(p2);
            fail("Expected era IllegalArgumentException por ID duplicado");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("ya existe"));
        }
    }


    @Test
    void findAllRepositorioVacio() {
        List<Pelicula> resultado = repository.findAll();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void findAllConElementos() {
        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        repository.insert(p1);

        List<Pelicula> resultado = repository.findAll();

        assertEquals(1, resultado.size());
    }


    @Test
    void findByIdIdNulo() {
        assertNull(repository.findById(null));
    }

    @Test
    void findByIdPeliculaOk() {
        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        repository.insert(p1);

        Pelicula resultado = repository.findById("P1");

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());
    }

    @Test
    void findByIdPeliculaNok() {
        Pelicula resultado = repository.findById("P1");
        assertNull(resultado);
    }
}