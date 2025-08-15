package cdg.oriente.projeto;

import java.util.List;
import java.util.Map;

public record DadosAtualizacaoProjeto(String titulo,
                                      String descricao,
                                      Float nota,
                                      boolean aprovado,
                                      List<Long> matriculas,
                                      Map<String, Long> professores
                                      ) {
}
