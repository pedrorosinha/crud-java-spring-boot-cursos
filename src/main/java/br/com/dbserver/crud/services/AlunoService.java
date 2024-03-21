package br.com.dbserver.crud.services;

import br.com.dbserver.crud.modelos.Aluno;
import br.com.dbserver.crud.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno criarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public Aluno atualizarAluno(Aluno alunoAtualizado) {
        return alunoRepository.save(alunoAtualizado);
    }

    public void apagarAluno(Long id) {
        alunoRepository.deleteById(id);
    }
}
