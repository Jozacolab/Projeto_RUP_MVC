# BiblioTech — Sistema de Gerenciamento de Biblioteca
### Projeto Acadêmico: Desenvolvimento de Sistemas com RUP e MVC em Java

**Curso:** Gestão de Tecnologia da Informação  
**Disciplina:** Desenvolvimento Back End  
**Discente:** Josias Soares de Souza  
**Versão:** 1.0 (Novembro/2026)  

---

## 1. Visão Geral do Projeto
O **BiblioTech** é um sistema orientado a objetos desenvolvido em **Java 17 LTS** que tem como finalidade gerenciar o acervo de livros e as operações de empréstimo de uma biblioteca acadêmica.

O projeto foi concebido, modelado e construído seguindo rigorosamente:
1. **O Processo Unificado Rational (RUP):** Condução do ciclo de vida em 4 fases (*Concepção, Elaboração, Construção e Transição*) e disciplinas de Engenharia de Software.
2. **O Padrão Arquitetural MVC (Model-View-Controller):** Separação estrita de responsabilidades entre as camadas de apresentação (View), orquestração de negócios (Controller) e entidades de domínio/repositório (Model).
3. **Engenharia de Prompt e Metodologia CERTA:** Aplicação dos conceitos de *Contexto, Essência, Referências, Tom/Tipo e Alvo* para desenvolvimento orientado com IA.

---

## 2. Estrutura do Repositório

```text
Projeto_RUP_MVC/
│
├── .gitignore                      # Configuração de arquivos ignorados no controle de versão
├── README.md                       # Documento principal de apresentação do projeto
├── LICENSE
│
├── planejamento/                   # Artefatos da Fase de Concepção e Elaboração
│   ├── escopo.md                   # Documento de visão e delimitação de escopo
│   ├── requisitos.md               # Especificação de Requisitos Funcionais, Não Funcionais e RNs
│   ├── casos_de_uso.md             # Detalhamento de casos de uso (UC01 a UC05)
│   └── arquitetura_mvc.md          # Justificativas técnicas e modelo de camadas
│
├── diagramas/                      # Modelagem UML em formato textual versionável (Mermaid)
│   ├── diagrama_classes.mmd        # Diagrama de Classes completo
│   ├── sequencia_cadastro_livro.mmd# Diagrama de Sequência: Cadastro de Livro
│   └── sequencia_registro_emprestimo.mmd # Diagrama de Sequência: Registro de Empréstimo
│
├── src/                            # Código-fonte Java puro (Sem frameworks pesados)
│   └── br/com/bibliotech/
│       ├── Main.java               # Ponto de entrada (Entry Point) e injeção de dependências
│       ├── model/
│       │   ├── dominio/
│       │   │   ├── Livro.java      # Entidade do acervo com validações de invariantes
│       │   │   └── Emprestimo.java # Entidade de transação de locação temporal
│       │   ├── enums/
│       │   │   ├── StatusLivro.java# Enum: DISPONIVEL, EMPRESTADO
│       │   │   └── StatusEmprestimo.java # Enum: ATIVO, FINALIZADO
│       │   └── repositorio/
│       │       └── BibliotecaRepository.java # Repositório em memória com dados prévios
│       ├── controller/
│       │   ├── BibliotecaController.java    # Coordenação e validações de regras de negócio
│       │   └── ResultadoOperacao.java       # DTO para transporte seguro de respostas
│       ├── view/
│       │   └── BibliotecaConsoleView.java   # Interface CLI em console
│       └── testes/
│           └── TestesAutomatizados.java     # Bateria de testes automatizados
│
├── testes/                         # Documentação e matriz de testes
│   ├── plano_testes.md             # Matriz de casos de teste planejados (CT01 a CT08)
│   └── matriz_resultados.md        # Evidências e resultados reais de execução (100% Aprovados)
│
└── entrega/                        # Artefatos finais para submissão
    ├── relatorio_academico_rup_mvc.docx # Relatório formatado no modelo institucional
    └── relatorio_academico_rup_mvc.pdf  # Versão final em PDF para entrega
```

---

## 3. Instruções de Compilação e Execução

### Pré-requisitos
- **Java Development Kit (JDK) 17** ou superior instalado e configurado no `PATH`.
- Terminal (PowerShell, Bash ou Prompt de Comando).

### 3.1 Compilando o Projeto
Abra o terminal na pasta raiz do projeto e execute:
```powershell
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse src/*.java | Select-Object -ExpandProperty FullName)
```

*(No Linux ou macOS: `javac -encoding UTF-8 -d bin $(find src -name "*.java")`)*

### 3.2 Executando a Aplicação (Interface do Usuário)
Para iniciar o sistema em modo interativo via console:
```powershell
java -cp bin br.com.bibliotech.Main
```

### 3.3 Executando a Bateria de Testes Automatizados
Para disparar os 8 casos de teste de regras de negócio:
```powershell
java "-Dfile.encoding=UTF-8" -cp bin br.com.bibliotech.testes.TestesAutomatizados
```

---

## 4. O Sistema em Funcionamento: As Quatro Fases do RUP

1. **Concepção (Inception):**
   - Entendimento do problema de desorganização de acervos bibliográficos;
   - Delimitação estrita do escopo em 2 entidades: `Livro` e `Emprestimo`;
   - Levantamento dos 6 Requisitos Funcionais e 6 Requisitos Não Funcionais.
2. **Elaboração (Elaboration):**
   - Modelagem do Diagrama de Classes e Diagramas de Sequência;
   - Desenho da arquitetura em 3 camadas (Model, View, Controller) com comunicação via DTOs.
3. **Construção (Construction):**
   - Codificação em Java 17 LTS com respeito aos princípios SOLID e encapsulamento;
   - Implementação de repositório em memória com carga inicial de demonstração;
   - Criação da bateria de testes funcionais.
4. **Transição (Transition):**
   - Execução e homologação de 100% dos testes planejados (8 aprovados, 0 reprovados);
   - Geração do relatório acadêmico integrado no modelo da instituição em formato PDF.

---

## 5. Resumo da Matriz de Testes

| ID | Cenário | Resultado Obtido | Status |
| :---: | :--- | :--- | :---: |
| **CT01** | Cadastro de livro válido | Livro cadastrado com ID #4 e status `DISPONIVEL` | **APROVADO** |
| **CT02** | Cadastro com campos vazios | Rejeição por validação de campos obrigatórios | **APROVADO** |
| **CT03** | Cadastro com ISBN duplicado | Bloqueio pela regra de unicidade (RN01) | **APROVADO** |
| **CT04** | Consulta de livro existente/inexistente | Localização precisa do exemplar e retorno seguro | **APROVADO** |
| **CT05** | Empréstimo de livro DISPONIVEL | Empréstimo #1 criado e status do livro atualizado para `EMPRESTADO` | **APROVADO** |
| **CT06** | Empréstimo de livro EMPRESTADO | Bloqueio pela regra de disponibilidade (RN02) | **APROVADO** |
| **CT07** | Devolução de empréstimo ativo | Encerramento com sucesso e livro restaurado para `DISPONIVEL` (RN03) | **APROVADO** |
| **CT08** | Devolução de empréstimo finalizado | Bloqueio de duplicidade de encerramento | **APROVADO** |
