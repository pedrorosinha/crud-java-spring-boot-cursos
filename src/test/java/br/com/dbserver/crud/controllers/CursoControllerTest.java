package br.com.dbserver.crud.controllers;

import br.com.dbserver.crud.dto.CursoDTO;
import br.com.dbserver.crud.dto.CursoDetalhesDTO;
import br.com.dbserver.crud.modelos.Aluno;
import br.com.dbserver.crud.modelos.Curso;
import br.com.dbserver.crud.modelos.Professor;
import br.com.dbserver.crud.services.AlunoService;
import br.com.dbserver.crud.services.CursoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CursoControllerTest {

    @Mock
    private CursoService cursoService;

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private CursoController cursoController;

    private Curso curso;
    private Professor professor;
    private List<Aluno> alunos;

    @BeforeEach
    public void setUp() {
        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Curso de Teste");

        professor = new Professor();
        professor.setId(1L);
        professor.setNome("Professor José");

        alunos = new ArrayList<>();
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("João");
        alunos.add(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Maria");
        alunos.add(aluno2);

        curso.setProfessor(professor);
        curso.setAlunos(alunos);
    }

    @Test
    public void testCriarCurso() {
        when(cursoService.criarCurso(any(Curso.class))).thenReturn(curso);
        Curso cursoTeste = new Curso();
        cursoTeste.setNome("Novo Curso");

        ResponseEntity<Curso> responseEntity = cursoController.criarCurso(cursoTeste);

        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert responseEntity.getBody().getId() == 1L;
        assert responseEntity.getBody().getNome().equals("Curso de Teste");
    }

    @Test
    public void testGetCursoDetalhes() {
        when(cursoService.buscarCursoPorId(1L)).thenReturn(Optional.of(curso));

        ResponseEntity<CursoDetalhesDTO> responseEntity = cursoController.getCursoDetalhes(1L);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().getId() == 1L;
        assert responseEntity.getBody().getNome().equals("Curso de Teste");
        assert responseEntity.getBody().getProfessor().getId() == 1L;
        assert responseEntity.getBody().getProfessor().getNome().equals("Professor José");
        assert responseEntity.getBody().getAlunos().size() == 2;
    }

    @Test
    public void testAdicionarAlunoAoCurso() {
        when(cursoService.buscarCursoPorId(1L)).thenReturn(Optional.of(curso));
        when(alunoService.criarAluno(any(Aluno.class))).thenReturn(alunos.get(0));

        ResponseEntity<Curso> responseEntity = cursoController.adicionarAlunoAoCurso(1L, alunos.get(0));

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().getAlunos().size() == 3;
    }

    @Test
    public void testGetCursoById() {
        when(cursoService.buscarCursoPorId(1L)).thenReturn(Optional.of(curso));

        ResponseEntity<Curso> responseEntity = cursoController.getCursoById(1L);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().getId() == 1L;
        assert responseEntity.getBody().getNome().equals("Curso de Teste");

    }

    @Test
    public void testAtualizarCurso() {
        Curso cursoAtualizado = new Curso();
        cursoAtualizado.setNome("Novo nome do curso");
        when(cursoService.atualizarCurso(any(Curso.class))).thenReturn(cursoAtualizado);

        ResponseEntity<Curso> responseEntity = cursoController.atualizarCurso(1L, cursoAtualizado);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().getNome().equals("Novo nome do curso");
    }

    @Test
    public void testAssociarProfessor() {
        when(cursoService.associarProfessor(1L, 1L)).thenReturn(curso);

        ResponseEntity<CursoDTO> responseEntity = cursoController.associarProfessor(1L, 1L);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().getId() == 1L;
        assert responseEntity.getBody().getNome().equals("Curso de Teste");
        assert responseEntity.getBody().getProfessor().getId() == 1L;
        assert responseEntity.getBody().getProfessor().getNome().equals("Professor José");
    }

    @Test
    public void testApagarCurso() {
        ResponseEntity<Void> responseEntity = cursoController.apagarCurso(1L);

        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
