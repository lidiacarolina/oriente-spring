package cdg.oriente.controller;

import cdg.oriente.professor.DadosAtualizacaoProfessor;
import cdg.oriente.professor.DadosCadastroProfessor;
import cdg.oriente.professor.Professor;
import cdg.oriente.professor.ProfessorRepository;
import cdg.oriente.projeto.Projeto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Professor> create(@RequestBody DadosCadastroProfessor dados) {
        try {
            Professor professor = new Professor(dados);
            repository.save(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(professor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Professor) Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Professor>> get() {
        try {
            List<Professor> professores = repository.findAll();
            if (professores.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
            }
            return ResponseEntity.ok(professores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((List<Professor>) Map.of("erro", "Erro ao buscar alunos", "detalhes", e.getMessage()));
        }
    }

    @GetMapping("/{registro}")
    public ResponseEntity<Professor> getById(@PathVariable Long registro) {
        try {
            Professor professor = repository.findById(registro)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com matrícula: " + registro));
            return ResponseEntity.ok(professor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Professor) Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Professor) Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DadosAtualizacaoProfessor dados) {
        try {
            Professor professor = repository.findById(dados.registro())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            professor.update(dados);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{registro}")
    @Transactional
    public ResponseEntity delete(@PathVariable("registro") Long registro) {
        try {
            repository.deleteById(registro);
            return ResponseEntity.ok("Deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{registro}/projetos")
    public ResponseEntity<List<Projeto>> getProjetos(@PathVariable Long registro) {
        try {
            Professor professor = repository.findById(registro)
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado com registro: " + registro));
            Map<String, Projeto> projetos = professor.getProjetos();
            List<Projeto> listaProjetos = new ArrayList<>(projetos.values());
            return ResponseEntity.ok(listaProjetos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((List<Projeto>) Map.of("erro", "Ocorreu um erro inesperado: " + e.getMessage()));
        }
    }

}
