package br.com.dbserver.crud.services;

import br.com.dbserver.crud.modelos.Curso;
import br.com.dbserver.crud.modelos.Professor;
import br.com.dbserver.crud.repositories.CursoRepository;
import br.com.dbserver.crud.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso associarProfessor(Long cursoId, Long professorId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        curso.setProfessor(professor);
        return cursoRepository.save(curso);
    }

    public Curso criarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> buscarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso atualizarCurso(Curso cursoAtualizado) {
        return cursoRepository.save(cursoAtualizado);
    }

    public void apagarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}
