import cdg.oriente.aluno.Aluno;
import cdg.oriente.aluno.DadosAtualizacaoAluno;
import cdg.oriente.aluno.DadosCadastroAluno;
import cdg.oriente.controller.AlunoController;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {
    @Test
    void testAlunoCreation() {
        DadosCadastroAluno dadosCadastroAluno = new DadosCadastroAluno(641L, "Lídia", "Engenharia de Software", null);
        Aluno aluno = new Aluno(dadosCadastroAluno);

        assertEquals(641, aluno.getMatricula());
        assertEquals("Lídia", aluno.getNome());
        assertEquals("Engenharia de Software", aluno.getCurso());
        assertNull(aluno.getProjeto());
    }

    @Test
    void testAlunoUpdate(){
        DadosCadastroAluno dadosCadastroAluno = new DadosCadastroAluno(641L, "Lídia", "Engenharia de Software", null);
        Aluno aluno = new Aluno(dadosCadastroAluno);

        DadosAtualizacaoAluno dadosAtualizacaoAluno = new DadosAtualizacaoAluno(641L, "Lídia Andrade", "Engenharia de Computação");
        aluno.update(dadosAtualizacaoAluno);

        assertEquals(641L, aluno.getMatricula());
        assertEquals("Lídia Andrade",  aluno.getNome());
        assertEquals("Engenharia de Computação",  aluno.getCurso());
    }

    @Test
    void testAlunoCredentials(){
        DadosCadastroAluno dados = new DadosCadastroAluno(641L, "Lídia", "Engenharia", null);
        Aluno aluno = new Aluno(dados);

        assertNotNull(aluno.getEmail());
        assertNotNull(aluno.getSenha());
        assertTrue(aluno.getEmail().endsWith("@aluno.inatel.br"));
        assertEquals("641", aluno.getSenha());
    }

    @Test
    void testAlunoCreationWithNullData() {
        assertThrows(NullPointerException.class, () -> new Aluno(null));
    }

    @Test
    void testAlunoUpdateWithNullData() {
        DadosCadastroAluno dadosCadastroAluno = new DadosCadastroAluno(641L, "Lídia", "Engenharia de Software", null);
        Aluno aluno = new Aluno(dadosCadastroAluno);
        assertThrows(NullPointerException.class, () -> aluno.update(null));
    }


    @Test
    void testAlunoEmailGenerationWithSpecialCharacters() {
        DadosCadastroAluno dados = new DadosCadastroAluno(123L, "João São", "Engenharia Biomédica", null);
        Aluno aluno = new Aluno(dados);

        assertEquals("joaosao@aluno.inatel.br", aluno.getEmail());
    }

}
