# Plano de Testes de Software
**Projeto:** BiblioTech — Sistema de Gerenciamento de Biblioteca  
**Metodologia:** RUP — Disciplina de Testes (Fase de Construção)  
**Arquitetura:** MVC (Model-View-Controller)  

---

## 1. Visão Geral e Estratégia
O objetivo deste plano de testes é validar de forma sistemática a conformidade das funcionalidades implementadas em relação aos Requisitos Funcionais (RF) e Regras de Negócio (RN) estabelecidos na Fase de Concepção.

Os testes cobrem:
- **Testes Unitários de Domínio:** Validação do comportamento das classes `Livro` e `Emprestimo` (proteção de estado).
- **Testes de Integração MVC:** Validação do fluxo entre `BibliotecaController`, `BibliotecaRepository` e `Model`.
- **Testes de Casos Limítrofes e Negativos:** Verificação de tratamento de entradas inválidas, duplicações e violações de regras.

---

## 2. Matriz de Casos de Teste Planejados

| ID | Requisito / Caso de Uso | Cenário | Dados de Entrada | Resultado Esperado | Status Planejado |
| :---: | :---: | :--- | :--- | :--- | :---: |
| **CT01** | RF01 / UC01 | Cadastrar livro com todos os dados válidos | Título: "Refactoring", Autor: "Martin Fowler", ISBN: "978-0134757599", Ano: 2018 | Livro cadastrado com sucesso; ID gerado; status `DISPONIVEL`. | Planejado |
| **CT02** | RF01 / UC01 | Tentativa de cadastro com campos obrigatórios vazios | Título: "", Autor: "Autor Teste", ISBN: "", Ano: 2020 | Operação rejeitada com mensagem amigável de erro informando os campos obrigatórios. | Planejado |
| **CT03** | RF01 / UC01 | Tentativa de cadastro com ISBN duplicado | Título: "Livro Duplicado", Autor: "Outro", ISBN de obra já cadastrada ("978-8576082675"), Ano: 2021 | Operação bloqueada; aviso de violação da regra de unicidade de ISBN (RN01). | Planejado |
| **CT04** | RF02, RF03 / UC02 | Consulta de livro existente e livro inexistente | ID existente (1) e ID inexistente (999) | Para ID 1: retorna o livro com seus dados. Para ID 999: retorna Optional vazio sem lançar exceção não tratada. | Planejado |
| **CT05** | RF04 / UC03 | Registro de empréstimo de livro com status DISPONIVEL | Livro ID: 2, Locatário: "Maria Souza" | Empréstimo gerado com sucesso com status `ATIVO`; status do livro alterado para `EMPRESTADO`. | Planejado |
| **CT06** | RF04 / UC03 | Tentativa de empréstimo de livro já EMPRESTADO | Livro ID: 2 (após CT05), Locatário: "João Pedro" | Operação rejeitada; alerta de que o livro já se encontra indisponível (RN02). | Planejado |
| **CT07** | RF05 / UC04 | Registrar devolução de empréstimo ativo | ID do empréstimo gerado no CT05 | Empréstimo finalizado com data efetiva registrada; status do livro restaurado para `DISPONIVEL` (RN03). | Planejado |
| **CT08** | RF05 / UC04 | Tentativa de devolução de empréstimo já finalizado | ID do mesmo empréstimo finalizado no CT07 | Operação rejeitada com mensagem informando que a devolução já foi realizada anteriormente. | Planejado |
