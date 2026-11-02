package br.com.bibliotech.model.dominio;

import br.com.bibliotech.model.enums.StatusLivro;
import java.util.Objects;

/**
 * Entidade central do domínio que representa uma obra literária no acervo da biblioteca.
 * Aplica os conceitos de encapsulamento e proteção de invariantes de estado.
 */
public class Livro {

    private Integer id;
    private String titulo;
    private String autor;
    private String isbn;
    private int anoPublicacao;
    private StatusLivro status;

    public Livro() {
        this.status = StatusLivro.DISPONIVEL;
    }

    public Livro(Integer id, String titulo, String autor, String isbn, int anoPublicacao) {
        this.id = id;
        this.titulo = Objects.requireNonNull(titulo, "O título não pode ser nulo").trim();
        this.autor = Objects.requireNonNull(autor, "O autor não pode ser nulo").trim();
        this.isbn = Objects.requireNonNull(isbn, "O ISBN não pode ser nulo").trim();
        this.anoPublicacao = anoPublicacao;
        this.status = StatusLivro.DISPONIVEL;
    }

    /**
     * Verifica se o exemplar está apto para ser emprestado.
     * @return true se status for DISPONIVEL, false caso contrário.
     */
    public boolean isDisponivel() {
        return this.status == StatusLivro.DISPONIVEL;
    }
    /**
     * Altera o estado do livro para EMPRESTADO.
     * Regra de negócio: Um livro não pode ser emprestado se já não estiver disponível.
     */
    public void emprestar() {
        if (!isDisponivel()) {
            throw new IllegalStateException("O livro '" + titulo + "' já se encontra emprestado.");
        }
        this.status = StatusLivro.EMPRESTADO;
    }
    /**
     * Altera o estado do livro de volta para DISPONIVEL.
     */
    public void devolver() {
        this.status = StatusLivro.DISPONIVEL;
    }
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public StatusLivro getStatus() {
        return status;
    }

    public void setStatus(StatusLivro status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(isbn, livro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s — %s (%d) | ISBN: %s | Status: %s",
                id, titulo, autor, anoPublicacao, isbn, status);
    }
}
