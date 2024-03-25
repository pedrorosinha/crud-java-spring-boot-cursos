package br.com.dbserver.crud.controllers;

import br.com.dbserver.crud.modelos.Curso;
import br.com.dbserver.crud.modelos.Professor;
import br.com.dbserver.crud.services.ProfessorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ProfessorControllerTest {

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController professorController;

    private Professor professor1;
    private Professor professor2;

    private Curso curso1;
    private Curso curso2;

    @BeforeEach
    public void setUp() {
        curso1 = new Curso();
        curso1.setId(1L);
        curso1.setNome("Curso 1");

        curso2 = new Curso();
        curso2.setId(2L);
        curso2.setNome("Curso 2");

        professor1 = new Professor();
        professor1.setId(1L);
        professor1.setNome("Professor A");

        professor2 = new Professor();
        professor2.setId(2L);
        professor2.setNome("Professor B");
    }

    @Test
    public void testGetAllProfessores() {
        List<Professor> professores = new ArrayList<>();
        professores.add(professor1);
        professores.add(professor2);

        when(professorService.listarProfessores()).thenReturn(professores);

        ResponseEntity<List<Professor>> responseEntity = professorController.getAllProfessores();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(professores.size(), responseEntity.getBody().size());
    }

    @Test
    public void testCriarProfessor() {
        when(professorService.criarProfessor(any(Professor.class))).thenReturn(professor1);

        ResponseEntity<Professor> responseEntity = professorController.criarProfessor(professor1);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(professor1.getId(), responseEntity.getBody().getId());
    }

    @Test
    public void testGetProfessorById() {
        when(professorService.buscarProfessorPorId(1L)).thenReturn(Optional.of(professor1));

        ResponseEntity<Professor> responseEntity = professorController.getProfessorById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(professor1.getId(), responseEntity.getBody().getId());
    }

    @Test
    public void testAtualizarProfessor() {
        when(professorService.atualizarProfessor(any(Professor.class))).thenReturn(professor1);

        ResponseEntity<Professor> responseEntity = professorController.atualizarProfessor(1L, professor1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(professor1.getId(), responseEntity.getBody().getId());
    }

    @Test
    public void testApagarProfessor() {
        when(professorService.buscarProfessorPorId(1L)).thenReturn(Optional.of(professor1));
        doNothing().when(professorService).apagarProfessor(1L);

        ResponseEntity<Void> responseEntity = professorController.apagarProfessor(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(professorService).apagarProfessor(1L);

        when(professorService.buscarProfessorPorId(2L)).thenReturn(Optional.empty());
        responseEntity = professorController.apagarProfessor(2L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
