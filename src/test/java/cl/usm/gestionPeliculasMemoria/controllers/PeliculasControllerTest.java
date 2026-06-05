package cl.usm.gestionPeliculasMemoria.controllers;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.services.PeliculasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeliculasControllerTest {

    @Mock
    private PeliculasService peliculasService;

    @InjectMocks
    private PeliculasController peliculasController;


    @Test
    void getAllOk() {
        Pelicula p = new Pelicula();
        when(peliculasService.getAll()).thenReturn(Collections.singletonList(p));

        ResponseEntity<List<Pelicula>> response = peliculasController.getAll(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllWithQueryOk() {
        Pelicula p = new Pelicula();
        when(peliculasService.filter("test")).thenReturn(Collections.singletonList(p));

        ResponseEntity<List<Pelicula>> response = peliculasController.getAll("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllNok() {
        when(peliculasService.getAll()).thenThrow(new RuntimeException("Error base de datos"));

        ResponseEntity<List<Pelicula>> response = peliculasController.getAll(null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void createPeliculaOk() {
        Pelicula p = new Pelicula();
        when(peliculasService.createPelicula(any(Pelicula.class))).thenReturn(p);

        ResponseEntity<?> response = peliculasController.createPelicula(p);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(p, response.getBody());
    }

    @Test
    void createPeliculaNok() {
        Pelicula p = new Pelicula();
        when(peliculasService.createPelicula(any(Pelicula.class))).thenReturn(null);

        ResponseEntity<?> response = peliculasController.createPelicula(p);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void findByIdOk() {
        Pelicula p = new Pelicula();
        when(peliculasService.findById("P1")).thenReturn(p);

        ResponseEntity<Pelicula> response = peliculasController.findById("P1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(p, response.getBody());
    }

    @Test
    void findByIdNotFoundNok() {
        when(peliculasService.findById("P999")).thenReturn(null);

        ResponseEntity<Pelicula> response = peliculasController.findById("P999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByIdExceptionNok() {
        when(peliculasService.findById(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Pelicula> response = peliculasController.findById("P1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void getComentariosOk() {
        Pelicula p = new Pelicula();
        //tuve que agregarlo pal 100% este nomás fallaba xD
        p.setComentarios(new Comentario[0]);
        when(peliculasService.findById("P1")).thenReturn(p);

        ResponseEntity<?> response = peliculasController.getComentarios("P1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getComentariosNotFoundNok() {
        when(peliculasService.findById("P999")).thenReturn(null);

        ResponseEntity<?> response = peliculasController.getComentarios("P999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getComentariosExceptionNok() {
        when(peliculasService.findById(anyString())).thenThrow(new RuntimeException("Error simulado"));

        ResponseEntity<?> response = peliculasController.getComentarios("P1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}