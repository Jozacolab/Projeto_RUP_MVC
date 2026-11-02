package br.com.bibliotech;

import br.com.bibliotech.controller.BibliotecaController;
import br.com.bibliotech.model.repositorio.BibliotecaRepository;
import br.com.bibliotech.view.BibliotecaConsoleView;

/**
 * Ponto de entrada (Entry Point) da aplicação BiblioTech.
 * Responsável por instanciar os componentes das três camadas MVC e realizar a injeção de dependências:
 * Model (Repository) -> Controller -> View.
 */
public class Main {

    public static void main(String[] args) {
        // 1. Inicializa a camada Model (Repositório com dados prévios)
        BibliotecaRepository repository = new BibliotecaRepository();

        // 2. Inicializa a camada Controller injetando o Model
        BibliotecaController controller = new BibliotecaController(repository);

        // 3. Inicializa a camada View injetando o Controller
        BibliotecaConsoleView view = new BibliotecaConsoleView(controller);

        // 4. Inicia o loop de interação com o usuário
        view.iniciar();
    }
}
