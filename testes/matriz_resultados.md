# Matriz de Resultados dos Testes Executados
**Projeto:** BiblioTech — Sistema de Gerenciamento de Biblioteca  
**Metodologia:** RUP — Disciplina de Testes (Fase de Construção e Transição)  
**Ambiente de Execução:** Java 17 LTS (build 17.0.12+8-LTS-286) no Windows  
**Data da Execução:** 02/11/2026  

---

## 1. Evidências Reais de Execução da Bateria de Testes

Os testes automatizados foram compilados e disparados através do comando:
```bash
java "-Dfile.encoding=UTF-8" -cp bin br.com.bibliotech.testes.TestesAutomatizados
```

### Log de Saída do Terminal
```text
======================================================================
        BIBLIOTECH — EXECUÇÃO DA SUÍTE DE TESTES AUTOMATIZADOS        
======================================================================
[APROVADO]  CT01   - Cadastro de livro com dados válidos
            -> Detalhe: Livro cadastrado com sucesso com ID #4!
[APROVADO]  CT02   - Tentativa de cadastro com campos obrigatórios vazios
            -> Detalhe: O título do livro é obrigatório.
[APROVADO]  CT03   - Tentativa de cadastro com ISBN duplicado (RN01)
            -> Detalhe: Já existe um livro cadastrado com o ISBN: 978-8576082675 ("Código Limpo: Habilidades Práticas do Agile Software").
[APROVADO]  CT04   - Consulta de livro existente e inexistente
            -> Detalhe: Livro #1 localizado ("Código Limpo: Habilidades Práticas do Agile Software") e ID #9999 retornou vazio com segurança.
[APROVADO]  CT05   - Registro de empréstimo de livro com status DISPONIVEL
            -> Detalhe: Empréstimo registrado com sucesso! ID #1 (Prazo para devolução: 2026-11-16).
[APROVADO]  CT06   - Tentativa de empréstimo de livro já EMPRESTADO (RN02)
            -> Detalhe: O livro "Design Patterns: Padrões de Projetos Orientados a Objetos" não está disponível (Status atual: Emprestado).
[APROVADO]  CT07   - Registrar devolução de empréstimo ativo (RN03)
            -> Detalhe: Devolução concluída com sucesso! O livro "Design Patterns: Padrões de Projetos Orientados a Objetos" está novamente disponível.
[APROVADO]  CT08   - Tentativa de devolução de empréstimo já finalizado
            -> Detalhe: O empréstimo #1 já foi finalizado anteriormente.

----------------------------------------------------------------------
RESUMO FINAL DOS TESTES: Total: 8 | Aprovados: 8 | Reprovados: 0
----------------------------------------------------------------------
RESULTADO GLOBAL: TODOS OS TESTES FORAM EXECUTADOS E APROVADOS COM SUCESSO!
```

---

## 2. Matriz Comparativa (Planejado vs. Obtido)

| ID | Cenário | Dados de Entrada | Resultado Esperado | Resultado Obtido na Execução | Situação Final |
| :---: | :--- | :--- | :--- | :--- | :---: |
| **CT01** | Cadastro de livro com dados válidos | "Refactoring", Martin Fowler, ISBN 978-0134757599, 2018 | Livro cadastrado, ID gerado, status `DISPONIVEL`. | Objeto instanciado com ID #4, salvo no repositório e retornado com status `DISPONIVEL`. | **APROVADO** |
| **CT02** | Cadastro com campos vazios | Título "", Autor "Autor", ISBN "" | Bloqueio e mensagem de erro amigável. | `isSucesso() = false`, mensagem: "O título do livro é obrigatório." | **APROVADO** |
| **CT03** | Cadastro com ISBN duplicado | ISBN já existente "978-8576082675" | Bloqueio da duplicação (RN01). | Operação rejeitada alertando livro duplicado com título da obra original. | **APROVADO** |
| **CT04** | Consulta de livro existente e inexistente | ID 1 e ID 9999 | Localização do ID 1 e Optional vazio para 9999 sem exceção fatal. | Livro #1 retornado com sucesso; ID 9999 retornou `Optional.empty()`. | **APROVADO** |
| **CT05** | Empréstimo de livro DISPONIVEL | Livro ID 2, Locatário "Maria Souza" | Empréstimo `ATIVO`, livro alterado para `EMPRESTADO`. | Empréstimo #1 criado com prazo de 14 dias; status do livro atualizado para `EMPRESTADO`. | **APROVADO** |
| **CT06** | Empréstimo de livro já EMPRESTADO | Livro ID 2, Locatário "João Pedro" | Rejeição por indisponibilidade (RN02). | Operação bloqueada com mensagem informando que o exemplar já está emprestado. | **APROVADO** |
| **CT07** | Devolução de empréstimo ativo | ID do empréstimo 1 | Empréstimo `FINALIZADO`, livro restaurado para `DISPONIVEL` (RN03). | Empréstimo encerrado com data de devolução preenchida; livro voltou a ser `DISPONIVEL`. | **APROVADO** |
| **CT08** | Devolução de empréstimo já finalizado | ID do empréstimo 1 | Rejeição com aviso de que já foi encerrado. | Bloqueio com mensagem: "O empréstimo #1 já foi finalizado anteriormente." | **APROVADO** |
