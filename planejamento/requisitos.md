# Especificação de Requisitos e Regras de Negócio
**Projeto:** BiblioTech — Sistema de Gerenciamento de Biblioteca  
**Metodologia:** RUP — Disciplina de Requisitos (Fase de Concepção)  
**Arquitetura:** MVC (Model-View-Controller)  

---

## 1. Requisitos Funcionais (RF)

Os requisitos funcionais descrevem o comportamento e os serviços que o sistema deve fornecer ao usuário.

| ID | Nome do Requisito | Descrição | Prioridade | Regras de Negócio Associadas |
| :--- | :--- | :--- | :---: | :---: |
| **RF01** | Cadastrar Livro | O sistema deve permitir o cadastro de um novo exemplar informando título, autor, ISBN e ano de publicação. | Essencial (Must) | RN01, RN04 |
| **RF02** | Listar Acervo | O sistema deve listar todos os livros cadastrados, exibindo seu identificador, título, autor, ISBN, ano e status atual (`DISPONIVEL` ou `EMPRESTADO`). | Essencial (Must) | — |
| **RF03** | Consultar Livro por ID/ISBN | O sistema deve permitir buscar um livro específico pelo seu ID ou ISBN, apresentando seus dados detalhados. | Importante (Should) | — |
| **RF04** | Registrar Empréstimo | O sistema deve permitir associar um livro disponível a um locatário (nome), gerando um registro com data de empréstimo e data prevista de devolução (padrão de 14 dias). | Essencial (Must) | RN02, RN04, RN05 |
| **RF05** | Registrar Devolução | O sistema deve permitir finalizar um empréstimo ativo, alterando seu status para `FINALIZADO`, registrando a data efetiva de devolução e liberando o livro para `DISPONIVEL`. | Essencial (Must) | RN03 |
| **RF06** | Listar Empréstimos | O sistema deve exibir todos os empréstimos registrados com detalhes do livro emprestado, nome do locatário, datas e status do empréstimo. | Essencial (Must) | — |

---

## 2. Requisitos Não Funcionais (RNF)

Os requisitos não funcionais determinam as qualidades, restrições e padrões arquiteturais do sistema.

| ID | Categoria | Descrição |
| :--- | :--- | :--- |
| **RNF01** | **Arquitetura (MVC)** | O sistema deve implementar estritamente o padrão Model-View-Controller. A camada View não deve conter regras de negócio e o Model não deve depender da interface de usuário. |
| **RNF02** | **Linguagem e Plataforma** | O sistema deve ser desenvolvido em Java Standard Edition (versão 17 LTS ou superior), sem frameworks externos que mascarem a orientação a objetos. |
| **RNF03** | **Portabilidade** | A aplicação deve rodar em qualquer ambiente compatível com a JVM 17 (Windows, Linux, macOS) via linha de comando. |
| **RNF04** | **Tratamento de Exceções** | O sistema deve interceptar entradas inválidas e estados de erro de negócio, exibindo mensagens amigáveis ao usuário sem encerrar abruptamente a aplicação. |
| **RNF05** | **Manutenibilidade e Legibilidade** | O código-fonte deve adotar boas práticas de Orientação a Objetos (SOLID básico, encapsulamento, nomes expressivos e documentação Javadoc onde couber). |
| **RNF06** | **Persistência Volátil Controlada** | O armazenamento deve utilizar coleções em memória gerenciadas pelo padrão Repository, contendo carga prévia de dados para facilitação de testes. |

---

## 3. Regras de Negócio (RN)

As regras de negócio são políticas e restrições fundamentais que governam as operações do domínio da biblioteca.

- **RN01 — Unicidade de ISBN:** Não é permitido cadastrar dois livros com o mesmo número de ISBN. O sistema deve rejeitar tentativas de duplicação.
- **RN02 — Disponibilidade para Empréstimo:** Um livro só pode ser emprestado se seu status atual for rigorosamente `DISPONIVEL`. Se o livro estiver com status `EMPRESTADO`, a operação de novo empréstimo deve ser sumariamente bloqueada.
- **RN03 — Liberação na Devolução:** A devolução de um empréstimo ativo altera automaticamente o status do livro correspondente de volta para `DISPONIVEL` e grava a data do encerramento da locação.
- **RN04 — Campos Obrigatórios:** Nenhum registro de livro pode possuir título, autor ou ISBN em branco/nulo. O registro de empréstimo exige obrigatoriamente o nome do locatário e o vínculo com um livro válido.
- **RN05 — Prazo Padrão de Locação:** Todo empréstimo deve ter uma data de devolução prevista calculada automaticamente para 14 (quatorze) dias corridos após a data de retirada.
