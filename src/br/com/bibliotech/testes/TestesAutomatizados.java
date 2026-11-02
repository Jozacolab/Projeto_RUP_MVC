package br.com.bibliotech.testes;

import br.com.bibliotech.controller.BibliotecaController;
import br.com.bibliotech.controller.ResultadoOperacao;
import br.com.bibliotech.model.dominio.Emprestimo;
import br.com.bibliotech.model.dominio.Livro;
import br.com.bibliotech.model.enums.StatusEmprestimo;
import br.com.bibliotech.model.enums.StatusLivro;
import br.com.bibliotech.model.repositorio.BibliotecaRepository;

import java.util.Optional;

/**
 * Bateria de Testes Automatizados de Regras de Negócio e Integração MVC.
 * Executa os casos de teste especificados no Plano de Testes (CT01 a CT08)
 * e gera evidências reais de execução e validação.
 */
public class TestesAutomatizados {

    private static int totalTestes = 0;
    private static int testesAprovados = 0;
    private static int testesReprovados = 0;

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("        BIBLIOTECH — EXECUÇÃO DA SUÍTE DE TESTES AUTOMATIZADOS        ");
        System.out.println("======================================================================");

        BibliotecaRepository repository = new BibliotecaRepository();
        BibliotecaController controller = new BibliotecaController(repository);

        testarCT01_CadastroValido(controller);
        testarCT02_CadastroCamposVazios(controller);
        testarCT03_CadastroIsbnDuplicado(controller);
        testarCT04_ConsultaLivros(controller);
        testarCT05_EmprestimoLivroDisponivel(controller);
        testarCT06_EmprestimoLivroIndisponivel(controller);
        testarCT07_DevolucaoEmprestimoAtivo(controller);
        testarCT08_DevolucaoEmprestimoJaFinalizado(controller);

        System.out.println("\n----------------------------------------------------------------------");
        System.out.printf("RESUMO FINAL DOS TESTES: Total: %d | Aprovados: %d | Reprovados: %d%n",
                totalTestes, testesAprovados, testesReprovados);
        System.out.println("----------------------------------------------------------------------");

        if (testesReprovados == 0) {
            System.out.println("RESULTADO GLOBAL: TODOS OS TESTES FORAM EXECUTADOS E APROVADOS COM SUCESSO!");
        } else {
            System.err.println("RESULTADO GLOBAL: FORAM IDENTIFICADAS FALHAS NOS TESTES!");
            System.exit(1);
        }
    }

    private static void registrarResultado(String id, String descricao, boolean passou, String detalhe) {
        totalTestes++;
        if (passou) {
            testesAprovados++;
            System.out.printf("[APROVADO]  %-6s - %s%n            -> Detalhe: %s%n", id, descricao, detalhe);
        } else {
            testesReprovados++;
            System.err.printf("[REPROVADO] %-6s - %s%n            -> Falha: %s%n", id, descricao, detalhe);
        }
    }

    private static void testarCT01_CadastroValido(BibliotecaController controller) {
        ResultadoOperacao<Livro> res = controller.cadastrarLivro(
                "Refactoring: Improving the Design of Existing Code",
                "Martin Fowler",
                "978-0134757599",
                2018
        );
        boolean passou = res.isSucesso() && res.getDados() != null && res.getDados().getId() != null
                && res.getDados().getStatus() == StatusLivro.DISPONIVEL;
        registrarResultado("CT01", "Cadastro de livro com dados válidos", passou,
                res.getMensagem());
    }

    private static void testarCT02_CadastroCamposVazios(BibliotecaController controller) {
        ResultadoOperacao<Livro> res = controller.cadastrarLivro(
                "",
                "Autor Qualquer",
                "",
                2020
        );
        boolean passou = !res.isSucesso() && res.getDados() == null;
        registrarResultado("CT02", "Tentativa de cadastro com campos obrigatórios vazios", passou,
                res.getMensagem());
    }

    private static void testarCT03_CadastroIsbnDuplicado(BibliotecaController controller) {
        // Tenta cadastrar obra com ISBN já existente no seed inicial ("978-8576082675")
        ResultadoOperacao<Livro> res = controller.cadastrarLivro(
                "Livro Clone",
                "Outro Autor",
                "978-8576082675",
                2022
        );
        boolean passou = !res.isSucesso() && res.getMensagem().toLowerCase().contains("já existe");
        registrarResultado("CT03", "Tentativa de cadastro com ISBN duplicado (RN01)", passou,
                res.getMensagem());
    }

    private static void testarCT04_ConsultaLivros(BibliotecaController controller) {
        Optional<Livro> livroExistente = controller.buscarLivroPorId(1);
        Optional<Livro> livroInexistente = controller.buscarLivroPorId(9999);
        boolean passou = livroExistente.isPresent() && livroInexistente.isEmpty();
        registrarResultado("CT04", "Consulta de livro existente e inexistente", passou,
                "Livro #1 localizado (\"" + (livroExistente.map(Livro::getTitulo).orElse("")) + "\") e ID #9999 retornou vazio com segurança.");
    }

    private static void testarCT05_EmprestimoLivroDisponivel(BibliotecaController controller) {
        // Livro #2 ("Design Patterns") começa DISPONIVEL
        ResultadoOperacao<Emprestimo> res = controller.registrarEmprestimo(2, "Maria Souza");
        boolean passou = res.isSucesso() && res.getDados() != null
                && res.getDados().getStatus() == StatusEmprestimo.ATIVO
                && res.getDados().getLivro().getStatus() == StatusLivro.EMPRESTADO;
        registrarResultado("CT05", "Registro de empréstimo de livro com status DISPONIVEL", passou,
                res.getMensagem());
    }

    private static void testarCT06_EmprestimoLivroIndisponivel(BibliotecaController controller) {
        // O livro #2 agora está EMPRESTADO por Maria Souza
        ResultadoOperacao<Emprestimo> res = controller.registrarEmprestimo(2, "João Pedro");
        boolean passou = !res.isSucesso() && res.getMensagem().toLowerCase().contains("não está disponível");
        registrarResultado("CT06", "Tentativa de empréstimo de livro já EMPRESTADO (RN02)", passou,
                res.getMensagem());
    }

    private static void testarCT07_DevolucaoEmprestimoAtivo(BibliotecaController controller) {
        // Empréstimo #1 gerado no CT05 para o Livro #2
        ResultadoOperacao<Emprestimo> res = controller.registrarDevolucao(1);
        boolean passou = res.isSucesso() && res.getDados() != null
                && res.getDados().getStatus() == StatusEmprestimo.FINALIZADO
                && res.getDados().getDataDevolucaoEfetiva() != null
                && res.getDados().getLivro().getStatus() == StatusLivro.DISPONIVEL;
        registrarResultado("CT07", "Registrar devolução de empréstimo ativo (RN03)", passou,
                res.getMensagem());
    }

    private static void testarCT08_DevolucaoEmprestimoJaFinalizado(BibliotecaController controller) {
        // Tenta devolver novamente o empréstimo #1 que já foi finalizado no CT07
        ResultadoOperacao<Emprestimo> res = controller.registrarDevolucao(1);
        boolean passou = !res.isSucesso() && res.getMensagem().toLowerCase().contains("já foi finalizado");
        registrarResultado("CT08", "Tentativa de devolução de empréstimo já finalizado", passou,
                res.getMensagem());
    }
}
