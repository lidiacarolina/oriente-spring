package cdg.oriente.projeto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DadosCadastroProjeto(@Id
                                   Long id,
                                   @NotNull
                                   String titulo,
                                   @NotNull
                                   String descricao,
                                   Float nota,
                                   boolean aprovado,
                                   List<Long> matriculas,
                                   Map<String, Long> professores) {
}
