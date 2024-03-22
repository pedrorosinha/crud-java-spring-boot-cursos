package br.com.dbserver.crud.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursoDetalhesDTO {

    private Long id;
    private String nome;
    private ProfessorDTO professor;
    private List<AlunoDTO> alunos;
}
