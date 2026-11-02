package br.com.bibliotech.model.enums;

/**
 * Enumeração que define a situação do registro de Empréstimo de um livro.
 */
public enum StatusEmprestimo {
    ATIVO("Em andamento"),
    FINALIZADO("Devolvido e encerrado");

    private final String descricao;

    StatusEmprestimo(String descricao) {
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
