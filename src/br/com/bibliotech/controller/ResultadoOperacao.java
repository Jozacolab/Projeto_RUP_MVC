package br.com.bibliotech.controller;

/**
 * Objeto de Transferência de Dados (DTO) que encapsula a resposta de operações do Controller para a View.
 * Permite que a View conheça o status da operação sem manipular exceções técnicas ou acoplar-se às regras de negócio.
 *
 * @param <T> Tipo do objeto de domínio retornado em caso de sucesso.
 */
public class ResultadoOperacao<T> {

    private final boolean sucesso;
    private final String mensagem;
    private final T dados;

    private ResultadoOperacao(boolean sucesso, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public static <T> ResultadoOperacao<T> sucesso(String mensagem, T dados) {
        return new ResultadoOperacao<>(true, mensagem, dados);
    }

    public static <T> ResultadoOperacao<T> sucesso(String mensagem) {
        return new ResultadoOperacao<>(true, mensagem, null);
    }

    public static <T> ResultadoOperacao<T> erro(String mensagem) {
        return new ResultadoOperacao<>(false, mensagem, null);
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public T getDados() {
        return dados;
    }

    @Override
    public String toString() {
        return (sucesso ? "[SUCESSO] " : "[ERRO] ") + mensagem;
    }
}
