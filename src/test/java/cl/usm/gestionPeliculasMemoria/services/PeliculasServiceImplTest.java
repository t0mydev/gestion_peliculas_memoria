package cl.usm.gestionPeliculasMemoria.services;

import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.repositories.PeliculasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeliculasServiceImplTest {

    @Mock
    private PeliculasRepository peliculasRepository;

    @InjectMocks
    private PeliculasServiceImpl peliculasService;
    

    @Test
    void createPeliculaOk() {
        Pelicula p = new Pelicula();
        when(peliculasRepository.insert(any(Pelicula.class))).thenReturn(p);
        Pelicula resultado = peliculasService.createPelicula(p);

        assertNotNull(resultado);
        assertNotNull(resultado.getTokenDescarga());
        assertEquals(10, resultado.getTokenDescarga().length());
    }

    @Test
    void createPeliculaNok() {
        Pelicula p = new Pelicula();
        when(peliculasRepository.insert(any(Pelicula.class))).thenThrow(new RuntimeException("Error simulado"));
        Pelicula resultado = peliculasService.createPelicula(p);

        assertNull(resultado);
    }
    

    @Test
    void getAllOk() {
        Pelicula p1 = new Pelicula();
        Pelicula p2 = new Pelicula();
        when(peliculasRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        List<Pelicula> resultado = peliculasService.getAll();

        assertEquals(2, resultado.size());
    }
    

    @Test
    void findByIdOk() {
        Pelicula p = new Pelicula();
        p.setId("P1");
        when(peliculasRepository.findById("P1")).thenReturn(p);

        Pelicula resultado = peliculasService.findById("P1");

        assertNotNull(resultado);
        assertEquals("P1", resultado.getId());
    }

    @Test
    void findByIdNok() {
        when(peliculasRepository.findById("PELI-999")).thenReturn(null);

        Pelicula resultado = peliculasService.findById("PELI-999");

        assertNull(resultado);
    }
    

    @Test
    void filterMatchIdOk() {
        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        p1.setTitulo("star wars");
        Pelicula p2 = new Pelicula();
        p2.setId("P2");
        p2.setTitulo("dune");
        
        when(peliculasRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Pelicula> resultado = peliculasService.filter("p1");

        assertEquals(1, resultado.size());
        assertEquals("P1", resultado.get(0).getId());
    }

    @Test
    void filterMatchTituloOk() {
        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        p1.setTitulo("star wars");
        Pelicula p2 = new Pelicula();
        p2.setId("P2");
        p2.setTitulo("dune");

        when(peliculasRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Pelicula> resultado = peliculasService.filter("star");

        assertEquals(1, resultado.size());
        assertEquals("star wars", resultado.get(0).getTitulo());
    }

    @Test
    void filterSinResultadosOk() {
        Pelicula p1 = new Pelicula();
        p1.setId("P1");
        p1.setTitulo("star wars");

        when(peliculasRepository.findAll()).thenReturn(Collections.singletonList(p1));

        List<Pelicula> resultado = peliculasService.filter("spider-man");

        assertTrue(resultado.isEmpty());
    }
}