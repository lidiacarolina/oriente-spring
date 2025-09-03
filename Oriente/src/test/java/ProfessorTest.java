import cdg.oriente.aluno.Aluno;
import cdg.oriente.aluno.DadosCadastroAluno;
import cdg.oriente.professor.DadosAtualizacaoProfessor;
import cdg.oriente.professor.DadosCadastroProfessor;
import cdg.oriente.professor.Professor;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProfessorTest {
        @Test
        void testProfessorCreation() {
            DadosCadastroProfessor dadosCadastroProfessor = new DadosCadastroProfessor(123L, "Chris", "orientador");
            Professor professor = new Professor(dadosCadastroProfessor);

            assertEquals(123, professor.getRegistro());
            assertEquals("Chris", professor.getNome());
            assertEquals("orientador", professor.getPermissao());
        }

        @Test
        void testProfessorUpdate(){
            DadosCadastroProfessor dadosCadastroProfessor = new DadosCadastroProfessor(123L, "Chris", "orientador");
            Professor professor = new Professor(dadosCadastroProfessor);

            DadosAtualizacaoProfessor dadosAtualizacaoProfessor = new DadosAtualizacaoProfessor(123L, "Chris Lima", "orientador");
            professor.update(dadosAtualizacaoProfessor);

            assertEquals(123L, professor.getRegistro());
            assertEquals("Chris Lima",  professor.getNome());
            assertEquals("orientador",  professor.getPermissao());
        }

        @Test
        void testProfessorCredentials(){
            DadosCadastroProfessor dadosCadastroProfessor = new DadosCadastroProfessor(123L, "Chris", "orientador");
            Professor professor = new Professor(dadosCadastroProfessor);

            assertNotNull(professor.getEmail());
            assertNotNull(professor.getSenha());
            assertTrue(professor.getEmail().endsWith("@prof.inatel.br"));
            assertEquals("123", professor.getSenha());
        }

        @Test
        void testProfessorCreationWithNullData() {
            assertThrows(NullPointerException.class, () -> new Professor(null));
        }

        @Test
        void testProfessorUpdateWithNullData() {
            DadosCadastroProfessor dadosCadastroProfessor = new DadosCadastroProfessor(123L, "Chris", "orientador");
            Professor professor = new Professor(dadosCadastroProfessor);
            assertThrows(NullPointerException.class, () -> professor.update(null));
        }

        @Test
        void testProfessorEmailGenerationWithSpecialCharacters() {
            DadosCadastroProfessor dados = new DadosCadastroProfessor(456L, "João São", "coordenador");
            Professor professor = new Professor(dados);

            assertEquals("joaosao@prof.inatel.br", professor.getEmail());
        }

}

