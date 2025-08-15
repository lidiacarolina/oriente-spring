package cdg.oriente.controller;

import cdg.oriente.aluno.*;
import cdg.oriente.projeto.Projeto;
import cdg.oriente.projeto.ProjetoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoRepository repository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid DadosCadastroAluno dados) {
        try {
            Aluno aluno = new Aluno(dados);
            if (dados.projetoId() != null) {
                Projeto projeto = projetoRepository.findById(dados.projetoId())
                        .orElseThrow(() -> new RuntimeException("Projeto não encontrado com ID: " + dados.projetoId()));
                aluno.setProjeto(projeto);
            }
            repository.save(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Ocorreu um erro inesperado: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> get() {
        try {
            List<Aluno> alunos = repository.findAll();
            if (alunos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
            }
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((List<Aluno>) Map.of("erro", "Erro ao buscar alunos", "detalhes", e.getMessage()));
        }
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Aluno> getById(@PathVariable Long matricula) {
        try {
            Aluno aluno = repository.findById(matricula)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com matrícula: " + matricula));
            return ResponseEntity.ok(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Aluno) Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Aluno) Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        try {
            Aluno aluno = repository.findById(dados.matricula())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            aluno.update(dados);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{matricula}")
    @Transactional
    public ResponseEntity delete(@PathVariable("matricula") Long matricula) {
        try {
            repository.deleteById(matricula);
            return ResponseEntity.ok("Deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{matricula}/projeto")
    public ResponseEntity<Object> getProjeto(@PathVariable Long matricula) {
        try {
            Aluno aluno = repository.findById(matricula)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com matrícula: " + matricula));
            Projeto projeto = aluno.getProjeto();
            if (projeto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não possui projeto");
            }
            return ResponseEntity.ok(projeto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", e.getMessage()));
        }
    }

}
