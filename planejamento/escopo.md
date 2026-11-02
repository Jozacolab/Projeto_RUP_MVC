# Documento de Visão e Delimitação de Escopo
**Projeto:** BiblioTech — Sistema de Gerenciamento de Biblioteca  
**Metodologia:** RUP (Rational Unified Process) — Fase de Concepção  
**Arquitetura:** MVC (Model-View-Controller)  
**Disciplina:** Desenvolvimento Back End  
**Discente:** Josias Soares de Souza  

---

## 1. Contexto e Justificativa do Problema
Em muitas instituições e ambientes acadêmicos, a circulação de acervos bibliográficos ainda sofre com registros descentralizados, planilhas avulsas ou anotações manuais. Esse cenário resulta em:
- Extravio ou perda de exemplares por falta de rastreabilidade temporal;
- Ausência de controle de disponibilidade em tempo real;
- Dificuldade em consultar o histórico de leituras e devoluções;
- Inconsistência nos dados cadastrais (como duplicidade de ISBN).

O sistema **BiblioTech** surge como uma solução de software orientada a objetos para gerenciar o acervo de livros e o fluxo de empréstimos, aplicando os padrões rigorosos de engenharia de software preconizados pelo Processo Unificado Rational (RUP).

---

## 2. Objetivos

### 2.1 Objetivo Geral
Projetar e construir um protótipo executável em linguagem Java para o gerenciamento de livros e empréstimos bibliográficos, aplicando a separação estrita de responsabilidades através do padrão arquitetural MVC.

### 2.2 Objetivos Específicos
1. Identificar, analisar e documentar os requisitos funcionais e não funcionais do sistema bibliotecário.
2. Mapear os casos de uso essenciais com fluxos principais e cenários de exceção.
3. Modelar o sistema em UML (diagramas de classes e de sequência em formato textual Mermaid).
4. Implementar a solução em Java 17 LTS, garantindo desacoplamento real entre a interface (View), os controladores (Controller) e o domínio/dados (Model).
5. Planejar e executar uma bateria de testes funcionais, registrando evidências reais de validação.
6. Gerar a documentação acadêmica final estruturada no padrão institucional da instituição de ensino.

---

## 3. Partes Interessadas (Stakeholders) e Atores

| Identificador | Papel / Ator | Descrição e Responsabilidade |
| :--- | :--- | :--- |
| **ST01** | Coordenação Acadêmica | Avalia o cumprimento das competências em RUP, MVC e Orientação a Objetos. |
| **ST02** | Discente / Desenvolvedor | Josias Soares de Souza — responsável pela análise, modelagem, código e testes. |
| **ACT01** | Bibliotecário / Atendente | Ator humano principal que opera o sistema, realiza cadastros e processa empréstimos/devoluções. |
| **ACT02** | Sistema (Temporizador/Interno) | Ator interno responsável pelo cálculo automático de prazos de devolução e atualização de status. |

---

## 4. Delimitação do Escopo

### 4.1 O que ESTÁ no Escopo (In-Scope)
- **Gestão de Livros:** Cadastro completo de obras (título, autor, ISBN, ano) e controle de status (`DISPONIVEL` e `EMPRESTADO`).
- **Gestão de Empréstimos:** Registro de empréstimo vinculado a um livro existente e a um locatário identificado; cálculo da data prevista para devolução; controle de status (`ATIVO` e `FINALIZADO`).
- **Operação de Devolução:** Finalização de empréstimo ativo com registro da data de entrega efetiva e liberação automática do livro para novo empréstimo.
- **Consultas e Relatórios em Tela:** Listagem detalhada do acervo e histórico de empréstimos.
- **Validações de Domínio:** Tratamento de entradas nulas, duplicidade de ISBN e tentativa de empréstimo de exemplar indisponível.
- **Interface Console CLI:** Interface interativa e amigável via terminal para fácil demonstração e execução.

### 4.2 O que NÃO ESTÁ no Escopo (Out-of-Scope)
Para preservar o rigor acadêmico e evitar complexidade desnecessária sem valor pedagógico para esta etapa, ficam explicitamente fora do escopo:
- Sistema de autenticação com login e senhas criptografadas;
- Cobrança financeira de multas ou integração com gateways de pagamento;
- Persistência em banco de dados relacional externo (SQL) ou em nuvem — os dados serão gerenciados em memória com padrão Repository;
- Interfaces gráficas pesadas (Web / Mobile);
- Envio de notificações externas por e-mail ou SMS.

---

## 5. Riscos, Premissas e Restrições

### 5.1 Premissas
- O ambiente de execução possui o Java JDK 17 LTS instalado e funcional.
- O operador do sistema possui conhecimentos básicos de operação de terminal.
- A persistência em memória atende satisfatoriamente a demonstração acadêmica solicitada no enunciado oficial.

### 5.2 Restrições
- O sistema deve respeitar estritamente as duas entidades exigidas (`Livro` e `Emprestimo`).
- Não devem ser adicionadas bibliotecas externas complexas sem necessidade demonstrada.
- As regras de negócio não podem ser codificadas na camada View.

### 5.3 Riscos e Mitigações
| Risco | Probabilidade | Impacto | Mitigação |
| :--- | :---: | :---: | :--- |
| Acoplamento acidental entre View e Model | Média | Alto | Utilização do Controller como intermediário estrito e DTOs/encapsulamento seletivo. |
| Inconsistência entre código Java e Diagramas UML | Média | Alto | Elaboração dos diagramas em código versionável (Mermaid) após validação do modelo conceitual. |
| Perda de dados durante a execução | Baixa | Médio | Criação de carga inicial de demonstração (seed data) para agilizar os testes e validação. |
