package br.com.dbserver.crud.controllers;

import br.com.dbserver.crud.dto.CursoDTO;
import br.com.dbserver.crud.dto.ProfessorDTO;
import br.com.dbserver.crud.modelos.Curso;
import br.com.dbserver.crud.modelos.Professor;
import br.com.dbserver.crud.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> getAllCursos() {
        List<Curso> cursos = cursoService.listarCursos();
        return ResponseEntity.ok(cursos);
    }

    private CursoDTO convertToDTO(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setNome(curso.getNome());

        Professor professor = curso.getProfessor();
        if (professor != null) {
            ProfessorDTO professorDTO = new ProfessorDTO();
            professorDTO.setId(professor.getId());
            professorDTO.setNome(professor.getNome());
            cursoDTO.setProfessor(professorDTO);
        }

        return cursoDTO;
    }

    @PostMapping
    public ResponseEntity<Curso> criarCurso(@RequestBody Curso curso) {
        Curso novoCurso = cursoService.criarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        Optional<Curso> cursoOptional = cursoService.buscarCursoPorId(id);
        return cursoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody Curso cursoAtualizado) {
        cursoAtualizado.setId(id);
        Curso curso = cursoService.atualizarCurso(cursoAtualizado);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{cursoId}/associar-professor/{professorId}")
    public ResponseEntity<CursoDTO> associarProfessor(@PathVariable Long cursoId, @PathVariable Long professorId) {
        Curso curso = cursoService.associarProfessor(cursoId, professorId);
        CursoDTO cursoDTO = convertToDTO(curso);
        return ResponseEntity.ok(cursoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarCurso(@PathVariable Long id) {
        cursoService.apagarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
