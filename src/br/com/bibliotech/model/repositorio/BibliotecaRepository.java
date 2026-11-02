package br.com.bibliotech.model.repositorio;

import br.com.bibliotech.model.dominio.Emprestimo;
import br.com.bibliotech.model.dominio.Livro;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Repositório em memória responsável pelo gerenciamento de dados das entidades Livro e Emprestimo.
 * Isola o acesso a dados e simula um mecanismo de persistência com controle transacional e IDs autoincrementados.
 */
public class BibliotecaRepository {

    private final List<Livro> livros = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private int proximoIdLivro = 1;
    private int proximoIdEmprestimo = 1;

    public BibliotecaRepository() {
        carregarDadosIniciais();
    }

    /**
     * Persiste ou atualiza um livro no acervo.
     */
    public synchronized Livro salvarLivro(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(proximoIdLivro++);
            livros.add(livro);
        } else {
            for (int i = 0; i < livros.size(); i++) {
                if (livros.get(i).getId().equals(livro.getId())) {
                    livros.set(i, livro);
                    return livro;
                }
            }
            livros.add(livro);
        }
        return livro;
    }

    /**
     * Busca um exemplar de livro pelo seu identificador primário.
     */
    public Optional<Livro> buscarLivroPorId(Integer id) {
        if (id == null) return Optional.empty();
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    /**
     * Busca um exemplar pelo código internacional normalizado (ISBN).
     */
    public Optional<Livro> buscarLivroPorIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) return Optional.empty();
        String isbnLimpo = isbn.trim().replace("-", "").toUpperCase();
        return livros.stream()
                .filter(l -> l.getIsbn().replace("-", "").equalsIgnoreCase(isbnLimpo))
                .findFirst();
    }

    /**
     * Retorna uma lista imutável de todos os livros cadastrados.
     */
    public List<Livro> listarLivros() {
        return Collections.unmodifiableList(new ArrayList<>(livros));
    }

    /**
     * Persiste ou atualiza um empréstimo no histórico.
     */
    public synchronized Emprestimo salvarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo.getId() == null) {
            emprestimo.setId(proximoIdEmprestimo++);
            emprestimos.add(emprestimo);
        } else {
            for (int i = 0; i < emprestimos.size(); i++) {
                if (emprestimos.get(i).getId().equals(emprestimo.getId())) {
                    emprestimos.set(i, emprestimo);
                    return emprestimo;
                }
            }
            emprestimos.add(emprestimo);
        }
        return emprestimo;
    }

    /**
     * Localiza um empréstimo pelo seu ID.
     */
    public Optional<Emprestimo> buscarEmprestimoPorId(Integer id) {
        if (id == null) return Optional.empty();
        return emprestimos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    /**
     * Retorna uma lista imutável de todos os empréstimos registrados.
     */
    public List<Emprestimo> listarEmprestimos() {
        return Collections.unmodifiableList(new ArrayList<>(emprestimos));
    }

    /**
     * Popula o acervo com dados iniciais para viabilizar demonstração e testes imediatos.
     */
    private void carregarDadosIniciais() {
        salvarLivro(new Livro(null, "Código Limpo: Habilidades Práticas do Agile Software", "Robert C. Martin", "978-8576082675", 2009));
        salvarLivro(new Livro(null, "Design Patterns: Padrões de Projetos Orientados a Objetos", "Erich Gamma et al.", "978-8573076103", 2000));
        salvarLivro(new Livro(null, "Engenharia de Software: Uma Abordagem Profissional", "Roger S. Pressman", "978-8580555332", 2016));
    }
}
