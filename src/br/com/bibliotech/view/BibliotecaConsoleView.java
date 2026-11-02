package br.com.bibliotech.view;

import br.com.bibliotech.controller.BibliotecaController;
import br.com.bibliotech.controller.ResultadoOperacao;
import br.com.bibliotech.model.dominio.Emprestimo;
import br.com.bibliotech.model.dominio.Livro;
import java.util.List;
import java.util.Scanner;

/**
 * Camada View responsável pela interface de linha de comando (CLI).
 * Não contém regras de negócio; apenas coleta dados do operador e exibe os resultados formatados.
 */
public class BibliotecaConsoleView {

    private final BibliotecaController controller;
    private final Scanner scanner;

    public BibliotecaConsoleView(BibliotecaController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean executando = true;
        while (executando) {
            exibirCabecalho();
            System.out.println(" 1. Cadastrar Novo Livro");
            System.out.println(" 2. Listar Acervo de Livros");
            System.out.println(" 3. Registrar Empréstimo");
            System.out.println(" 4. Registrar Devolução");
            System.out.println(" 5. Listar Todos os Empréstimos");
            System.out.println(" 0. Encerrar Sistema");
            System.out.print("\n Escolha uma opção: ");

            String entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1" -> cadastrarLivro();
                case "2" -> listarLivros();
                case "3" -> registrarEmprestimo();
                case "4" -> registrarDevolucao();
                case "5" -> listarEmprestimos();
                case "0" -> {
                    System.out.println("\n[BiblioTech] Encerrando a aplicação... Até logo!");
                    executando = false;
                }
                default -> {
                    System.out.println("\n[!] Opção inválida. Digite um número de 0 a 5.");
                    pausar();
                }
            }
        }
    }

    private void exibirCabecalho() {
        System.out.println("\n=======================================================");
        System.out.println("     BIBLIOTECH — SISTEMA DE GESTÃO DE BIBLIOTECA      ");
        System.out.println("             Arquitetura MVC + Metodologia RUP         ");
        System.out.println("=======================================================");
    }

    private void cadastrarLivro() {
        System.out.println("\n--- [CADASTRO DE LIVRO] ---");
        System.out.print("Título da obra: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Ano de publicação: ");
        String anoStr = scanner.nextLine();

        int ano;
        try {
            ano = Integer.parseInt(anoStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] O ano de publicação deve ser um número inteiro válido.");
            pausar();
            return;
        }

        // Delegação para o Controller
        ResultadoOperacao<Livro> resultado = controller.cadastrarLivro(titulo, autor, isbn, ano);
        exibirResultado(resultado);
        pausar();
    }

    private void listarLivros() {
        System.out.println("\n--- [ACERVO DE LIVROS CADASTRADOS] ---");
        List<Livro> livros = controller.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no momento.");
        } else {
            System.out.printf("%-4s | %-38s | %-22s | %-15s | %-6s | %-12s%n",
                    "ID", "TITULO", "AUTOR", "ISBN", "ANO", "STATUS");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            for (Livro l : livros) {
                String tituloTruncado = truncar(l.getTitulo(), 38);
                String autorTruncado = truncar(l.getAutor(), 22);
                System.out.printf("%-4d | %-38s | %-22s | %-15s | %-6d | %-12s%n",
                        l.getId(), tituloTruncado, autorTruncado, l.getIsbn(), l.getAnoPublicacao(), l.getStatus().name());
            }
        }
        pausar();
    }

    private void registrarEmprestimo() {
        System.out.println("\n--- [REGISTRO DE EMPRÉSTIMO] ---");
        System.out.print("Informe o ID do Livro: ");
        String idStr = scanner.nextLine();

        int livroId;
        try {
            livroId = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] O ID do livro deve ser numérico.");
            pausar();
            return;
        }

        System.out.print("Nome do Locatário (Leitor): ");
        String locatario = scanner.nextLine();

        ResultadoOperacao<Emprestimo> resultado = controller.registrarEmprestimo(livroId, locatario);
        exibirResultado(resultado);
        pausar();
    }

    private void registrarDevolucao() {
        System.out.println("\n--- [REGISTRO DE DEVOLUÇÃO] ---");
        System.out.print("Informe o ID do Empréstimo a ser finalizado: ");
        String idStr = scanner.nextLine();

        int emprestimoId;
        try {
            emprestimoId = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] O ID do empréstimo deve ser numérico.");
            pausar();
            return;
        }

        ResultadoOperacao<Emprestimo> resultado = controller.registrarDevolucao(emprestimoId);
        exibirResultado(resultado);
        pausar();
    }

    private void listarEmprestimos() {
        System.out.println("\n--- [HISTÓRICO GERAL DE EMPRÉSTIMOS] ---");
        List<Emprestimo> emprestimos = controller.listarEmprestimos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado no sistema.");
        } else {
            System.out.printf("%-4s | %-4s | %-32s | %-18s | %-10s | %-10s | %-10s%n",
                    "ID", "LIV", "TITULO DA OBRA", "LOCATARIO", "RETIRADA", "PREVISAO", "STATUS");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            for (Emprestimo e : emprestimos) {
                String tit = e.getLivro() != null ? truncar(e.getLivro().getTitulo(), 32) : "N/D";
                int idLiv = e.getLivro() != null ? e.getLivro().getId() : 0;
                System.out.printf("%-4d | %-4d | %-32s | %-18s | %-10s | %-10s | %-10s%n",
                        e.getId(), idLiv, tit, truncar(e.getNomeLocatario(), 18),
                        e.getDataEmprestimo(), e.getDataDevolucaoPrevista(), e.getStatus().name());
            }
        }
        pausar();
    }

    private void exibirResultado(ResultadoOperacao<?> resultado) {
        if (resultado.isSucesso()) {
            System.out.println("\n[+] SUCESSO: " + resultado.getMensagem());
        } else {
            System.out.println("\n[!] ERRO: " + resultado.getMensagem());
        }
    }

    private void pausar() {
        System.out.print("\nPressione [ENTER] para continuar...");
        scanner.nextLine();
    }

    private String truncar(String texto, int tamanhoMax) {
        if (texto == null) return "";
        if (texto.length() <= tamanhoMax) return texto;
        return texto.substring(0, tamanhoMax - 3) + "...";
    }
}
