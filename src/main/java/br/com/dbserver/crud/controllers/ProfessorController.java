package br.com.dbserver.crud.controllers;

import br.com.dbserver.crud.modelos.Professor;
import br.com.dbserver.crud.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessores() {
        List<Professor> professores = professorRepository.findAll();
        return ResponseEntity.ok(professores);
    }

    @PostMapping
    public ResponseEntity<Professor> criarProfessor(@RequestBody Professor professor) {
        Professor novoProfessor = professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        Optional<Professor> professorOptional = professorRepository.findById(id);
        return professorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable Long id, @RequestBody Professor professorAtualizado) {
        if (!professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        professorAtualizado.setId(id);
        Professor professor = professorRepository.save(professorAtualizado);
        return ResponseEntity.ok(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarProfessor(@PathVariable Long id) {
        if (!professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        professorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
