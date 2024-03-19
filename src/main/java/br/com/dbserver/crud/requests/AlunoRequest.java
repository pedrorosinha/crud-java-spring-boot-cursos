package br.com.dbserver.crud.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoRequest {

    @NotNull(message = "o nome é obrigatório")
    private String nome;
}
