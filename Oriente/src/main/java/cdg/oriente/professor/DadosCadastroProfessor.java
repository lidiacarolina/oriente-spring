package cdg.oriente.professor;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProfessor(@Id
                                     Long registro,
                                     @NotNull
                                     String nome,
                                     @NotNull
                                     String permissao){
}

