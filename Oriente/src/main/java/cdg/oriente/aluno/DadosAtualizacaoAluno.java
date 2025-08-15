package cdg.oriente.aluno;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(@NotNull
                                    Long matricula,
                                    String nome,
                                    String curso) {
}
