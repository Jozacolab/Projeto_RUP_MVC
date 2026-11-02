package br.com.bibliotech.model.dominio;

import br.com.bibliotech.model.enums.StatusEmprestimo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Entidade que modela a transação de cessão temporária de um Livro a um usuário.
 * Demonstra a relação 1..1 entre o Empréstimo e o Livro referenciado.
 */
public class Emprestimo {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Integer id;
    private Livro livro;
    private String nomeLocatario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;
    private StatusEmprestimo status;

    public Emprestimo() {
        this.status = StatusEmprestimo.ATIVO;
    }

    public Emprestimo(Integer id, Livro livro, String nomeLocatario, LocalDate dataEmprestimo, int diasPrazo) {
        this.id = id;
        this.livro = Objects.requireNonNull(livro, "O livro associado não pode ser nulo.");
        this.nomeLocatario = Objects.requireNonNull(nomeLocatario, "O nome do locatário não pode ser nulo.").trim();
        this.dataEmprestimo = Objects.requireNonNull(dataEmprestimo, "A data de empréstimo é obrigatória.");
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(diasPrazo);
        this.status = StatusEmprestimo.ATIVO;
        this.dataDevolucaoEfetiva = null;
    }

    /**
     * Verifica se o empréstimo ainda está em aberto.
     * @return true se status for ATIVO, false caso contrário.
     */
    public boolean isAtivo() {
        return this.status == StatusEmprestimo.ATIVO;
    }

    /**
     * Encerra o empréstimo, atribuindo a data de devolução efetiva e mudando o status.
     * @param dataDevolucao data em que a obra foi devolvida.
     */
    public void finalizar(LocalDate dataDevolucao) {
        if (!isAtivo()) {
            throw new IllegalStateException("Este empréstimo já se encontra finalizado.");
        }
        this.dataDevolucaoEfetiva = Objects.requireNonNull(dataDevolucao, "A data de devolução não pode ser nula.");
        this.status = StatusEmprestimo.FINALIZADO;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getNomeLocatario() {
        return nomeLocatario;
    }

    public void setNomeLocatario(String nomeLocatario) {
        this.nomeLocatario = nomeLocatario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoEfetiva() {
        return dataDevolucaoEfetiva;
    }

    public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) {
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String dataPrevistaStr = dataDevolucaoPrevista != null ? dataDevolucaoPrevista.format(FORMATTER) : "N/D";
        String dataEfetivaStr = dataDevolucaoEfetiva != null ? dataDevolucaoEfetiva.format(FORMATTER) : "Pendente";
        return String.format("[%d] Livro: \"%s\" (ID: %d) | Locatário: %s | Retirada: %s | Prevista: %s | Efetiva: %s | Status: %s",
                id,
                livro != null ? livro.getTitulo() : "Sem Livro",
                livro != null ? livro.getId() : 0,
                nomeLocatario,
                dataEmprestimo != null ? dataEmprestimo.format(FORMATTER) : "N/D",
                dataPrevistaStr,
                dataEfetivaStr,
                status);
    }
}
