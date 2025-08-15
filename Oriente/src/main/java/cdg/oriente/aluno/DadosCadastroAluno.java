package cdg.oriente.aluno;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroAluno(@Id
                                 Long matricula,
                                 @NotBlank
                                 String nome,
                                 @NotBlank
                                 String curso,
                                 Long projetoId) {
}


