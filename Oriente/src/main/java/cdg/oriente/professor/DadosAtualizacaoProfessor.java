package cdg.oriente.professor;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProfessor(@NotNull
                                        Long registro,
                                        String nome,
                                        String permissao){
}
