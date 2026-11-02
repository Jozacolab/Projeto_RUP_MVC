package br.com.bibliotech.model.enums;

/**
 * Enumeração que define a situação cadastral e de circulação de um Livro no acervo.
 */
public enum StatusLivro {
    DISPONIVEL("Disponível para Empréstimo"),
    EMPRESTADO("Emprestado");

    private final String descricao;

    StatusLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
