package br.com.dbserver.crud.controllers;

import br.com.dbserver.crud.modelos.Aluno;
import br.com.dbserver.crud.modelos.Curso;
import br.com.dbserver.crud.services.AlunoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    private Curso curso;
    private Aluno aluno1;
    private Aluno aluno2;

    @BeforeEach
    public void setUp() {
        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Curso de Teste");

        aluno1 = new Aluno(1L, "João", curso);
        aluno2 = new Aluno(2L, "Maria", curso);
    }

    @Test
    public void testGetAllAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno1);
        alunos.add(aluno2);
        when(alunoService.listarAlunos()).thenReturn(alunos);

        ResponseEntity<List<Aluno>> responseEntity = alunoController.getAllAlunos();

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().size() == 2;

    }

    @Test
    public void testCriarAluno() {
        when(alunoService.criarAluno(any(Aluno.class))).thenReturn(aluno1);

        ResponseEntity<Aluno> responseEntity = alunoController.criarAluno(aluno1);

        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert responseEntity.getBody().equals(aluno1);

    }

    @Test
    public void testGetAlunoByIdNotFound() {
        when(alunoService.buscarAlunoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Aluno> responseEntity = alunoController.getAlunoById(1L);

        assert responseEntity.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testAtualizarAluno() {
        when(alunoService.atualizarAluno(any(Aluno.class))).thenReturn(aluno1);

        ResponseEntity<Aluno> responseEntity = alunoController.atualizarAluno(1L, aluno1);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().equals(aluno1);
    }

    @Test
    public void testApagarAluno() {
        ResponseEntity<Void> responseEntity = alunoController.apagarAluno(1L);

        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        verify(alunoService, times(1)).apagarAluno(1L);
    }

}
