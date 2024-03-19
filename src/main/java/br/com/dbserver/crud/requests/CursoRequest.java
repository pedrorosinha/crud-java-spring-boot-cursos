package br.com.dbserver.crud.requests;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoRequest {

    @NotNull(message = "O nome do curso é obrigatório")
    private String nome;

    @NotNull(message = "O ID do professor é obrigatório")
    private Long idProfessor;

    private List<@NotNull(message = "O ID do aluno não pode ser nulo") Long> idsAlunos;
}
