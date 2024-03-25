package br.com.dbserver.crud.controllers;

import br.com.dbserver.crud.modelos.Aluno;
import br.com.dbserver.crud.modelos.Curso;
import br.com.dbserver.crud.services.AlunoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Test
    public void testGetAllAlunos() {
        // Arrange
        List<Aluno> alunos = new ArrayList<>();
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNome("Curso de Teste");
        alunos.add(new Aluno(1L, "João", curso));
        alunos.add(new Aluno(2L, "Maria", curso));
        when(alunoService.listarAlunos()).thenReturn(alunos);

        ResponseEntity<List<Aluno>> responseEntity = alunoController.getAllAlunos();

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().size() == 2;

    }

    @Test
    public void testCriarAluno() {
        Curso curso = new Curso();
        curso.setId(1L);
        Aluno aluno = new Aluno(1L, "João", curso);
        when(alunoService.criarAluno(any(Aluno.class))).thenReturn(aluno);

        ResponseEntity<Aluno> responseEntity = alunoController.criarAluno(aluno);

        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert responseEntity.getBody().equals(aluno);

    }

    @Test
    public void testGetAlunoByIdNotFound() {
        when(alunoService.buscarAlunoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Aluno> responseEntity = alunoController.getAlunoById(1L);

        assert responseEntity.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    public void testAtualizarAluno() {
        Curso curso = new Curso();
        curso.setId(1L);
        Aluno aluno = new Aluno(1L, "João", curso);
        when(alunoService.atualizarAluno(any(Aluno.class))).thenReturn(aluno);

        ResponseEntity<Aluno> responseEntity = alunoController.atualizarAluno(1L, aluno);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().equals(aluno);
    }
    
    @Test
    public void testApagarAluno() {
        ResponseEntity<Void> responseEntity = alunoController.apagarAluno(1L);

        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        verify(alunoService, times(1)).apagarAluno(1L);
    }

}
