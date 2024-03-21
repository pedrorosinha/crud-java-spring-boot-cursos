package br.com.dbserver.crud.services;

import br.com.dbserver.crud.modelos.Professor;
import br.com.dbserver.crud.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    public Professor criarProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    public Optional<Professor> buscarProfessorPorId(Long id) {
        return professorRepository.findById(id);
    }

    public Professor atualizarProfessor(Professor professorAtualizado) {
        return professorRepository.save(professorAtualizado);
    }

    public void apagarProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
