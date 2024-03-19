package br.com.dbserver.crud.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorRequest {

    @NotBlank(message = "O nome do professor é obrigatório")
    private String nome;
}
