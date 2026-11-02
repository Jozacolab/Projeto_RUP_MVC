package br.com.bibliotech.controller;

import br.com.bibliotech.model.dominio.Emprestimo;
import br.com.bibliotech.model.dominio.Livro;
import br.com.bibliotech.model.repositorio.BibliotecaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador central da aplicação.
 * Responsável por intermediar as interações da View com as entidades do Model e o Repositório,
 * aplicando regras de coordenação, validação e orquestração.
 */
public class BibliotecaController {

    private static final int PRAZO_PADRAO_DIAS = 14;
    private final BibliotecaRepository repository;

    public BibliotecaController(BibliotecaRepository repository) {
        this.repository = repository;
    }

    /**
     * Realiza o cadastro de uma nova obra no acervo.
     * Validações: campos obrigatórios, ano plausível e unicidade do ISBN.
     */
    public ResultadoOperacao<Livro> cadastrarLivro(String titulo, String autor, String isbn, int ano) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return ResultadoOperacao.erro("O título do livro é obrigatório.");
        }
        if (autor == null || autor.trim().isEmpty()) {
            return ResultadoOperacao.erro("O autor do livro é obrigatório.");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            return ResultadoOperacao.erro("O código ISBN é obrigatório.");
        }
        int anoAtual = LocalDate.now().getYear();
        if (ano < 1450 || ano > anoAtual + 1) {
            return ResultadoOperacao.erro("Ano de publicação inválido (" + ano + "). Deve estar entre 1450 e " + (anoAtual + 1) + ".");
        }

        // RN01: Unicidade do ISBN
        Optional<Livro> livroExistente = repository.buscarLivroPorIsbn(isbn);
        if (livroExistente.isPresent()) {
            return ResultadoOperacao.erro("Já existe um livro cadastrado com o ISBN: " + isbn + " (\"" + livroExistente.get().getTitulo() + "\").");
        }

        Livro novoLivro = new Livro(null, titulo, autor, isbn, ano);
        Livro salvo = repository.salvarLivro(novoLivro);
        return ResultadoOperacao.sucesso("Livro cadastrado com sucesso com ID #" + salvo.getId() + "!", salvo);
    }

    /**
     * Retorna a listagem completa dos livros cadastrados.
     */
    public List<Livro> listarLivros() {
        return repository.listarLivros();
    }

    /**
     * Busca um livro pelo identificador numérico.
     */
    public Optional<Livro> buscarLivroPorId(Integer id) {
        return repository.buscarLivroPorId(id);
    }

    /**
     * Registra o empréstimo de um exemplar para um locatário.
     * Validações: locatário informado, livro existente e livro disponível.
     */
    public ResultadoOperacao<Emprestimo> registrarEmprestimo(Integer livroId, String locatario) {
        if (livroId == null || livroId <= 0) {
            return ResultadoOperacao.erro("ID do livro inválido.");
        }
        if (locatario == null || locatario.trim().isEmpty()) {
            return ResultadoOperacao.erro("O nome do locatário é obrigatório.");
        }

        Optional<Livro> livroOpt = repository.buscarLivroPorId(livroId);
        if (livroOpt.isEmpty()) {
            return ResultadoOperacao.erro("Nenhum exemplar foi localizado com o ID #" + livroId + ".");
        }

        Livro livro = livroOpt.get();

        // RN02: Disponibilidade do livro
        if (!livro.isDisponivel()) {
            return ResultadoOperacao.erro("O livro \"" + livro.getTitulo() + "\" não está disponível (Status atual: " + livro.getStatus() + ").");
        }

        // Transição de estado no Model
        livro.emprestar();
        repository.salvarLivro(livro);

        Emprestimo novoEmprestimo = new Emprestimo(null, livro, locatario, LocalDate.now(), PRAZO_PADRAO_DIAS);
        Emprestimo salvo = repository.salvarEmprestimo(novoEmprestimo);

        return ResultadoOperacao.sucesso("Empréstimo registrado com sucesso! ID #" + 
        salvo.getId() + " (Prazo para devolução: " + salvo.getDataDevolucaoPrevista() + ").", salvo);
    }

    /**
     * Finaliza uma locação ativa, restaurando o livro para DISPONIVEL.
     */
    public ResultadoOperacao<Emprestimo> registrarDevolucao(Integer emprestimoId) {
        if (emprestimoId == null || emprestimoId <= 0) {
            return ResultadoOperacao.erro("ID do empréstimo inválido.");
        }

        Optional<Emprestimo> empOpt = repository.buscarEmprestimoPorId(emprestimoId);
        if (empOpt.isEmpty()) {
            return ResultadoOperacao.erro("Empréstimo com ID #" + emprestimoId + " não encontrado.");
        }

        Emprestimo emprestimo = empOpt.get();

        if (!emprestimo.isAtivo()) {
            return ResultadoOperacao.erro("O empréstimo #" + emprestimoId + " já foi finalizado anteriormente.");
        }

        // RN03: Encerramento do empréstimo e liberação do exemplar
        emprestimo.finalizar(LocalDate.now());
        repository.salvarEmprestimo(emprestimo);

        Livro livro = emprestimo.getLivro();
        if (livro != null) {
            livro.devolver();
            repository.salvarLivro(livro);
        }

        return ResultadoOperacao.sucesso("Devolução concluída com sucesso! O livro \"" + (livro != null ? livro.getTitulo() : "") + "\" está novamente disponível.", emprestimo);
    }

    /**
     * Retorna a relação de todos os empréstimos registrados.
     */
    public List<Emprestimo> listarEmprestimos() {
        return repository.listarEmprestimos();
    }
}
