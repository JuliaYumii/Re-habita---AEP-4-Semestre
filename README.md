# REHABITA

Sistema de gestão de doações e acompanhamento de famílias em situação de vulnerabilidade social.

Atividade de Estudo Programada (AEP) do 4º semestre (2026) – Unicesumar, Maringá/PR.

**Equipe:** Amanda Rodrigues Neves, Julia Oliveira dos Santos e Julia Yumi Hashimoto

Documento da 1ª entrega: [`docs/AEP_1Entrega_REHABITA.pdf`](docs/AEP_1Entrega_REHABITA.pdf)

## Sobre o projeto

Paróquias, igrejas, a APAE e projetos sociais de Maringá recebem doações (cesta básica, produtos de higiene, fraldas, roupas, cobertores) e repassam para famílias da comunidade. Na maioria dos casos o controle fica em caderno, planilha ou WhatsApp, e fica difícil saber o que tem em estoque, quem já recebeu no mês e quais famílias estão em situação mais urgente.

O REHABITA organiza esse ciclo dentro da instituição: a doação chega e entra no estoque, a família é cadastrada e visitada, e o que foi entregue fica no histórico dela.

Quem usa o sistema é a equipe da instituição (coordenação e assistentes sociais/voluntários). Doadores e famílias não acessam a aplicação.

## ODS relacionadas

- **ODS 1 – Erradicação da Pobreza (meta 1.3):** cada família tem status, histórico de visitas e data de retorno.
- **ODS 2 – Fome Zero e Agricultura Sustentável (meta 2.1):** controle de estoque e de entrega de alimentos.
- **ODS 10 – Redução das Desigualdades (meta 10.2):** ajuda distribuída por critério (urgência e tempo sem receber).

## Requisitos

A entidade principal do sistema (CRUD completo) é a **Família** (R1).

| Nº | Requisito |
|----|-----------|
| R1 | O sistema deve permitir o cadastro de famílias assistidas, com os dados do responsável (nome completo, CPF, data de nascimento, telefone, escolaridade e situação de trabalho), o endereço (CEP, rua, número, complemento e bairro), o tipo de moradia e a faixa de renda, além de consultar, editar e excluir esse cadastro. |
| R2 | O sistema deve permitir o cadastro dos membros de cada família, com nome completo, data de nascimento, grau de parentesco com o responsável, telefone e CPF (esses dois opcionais), calculando automaticamente quantas pessoas moram na casa. |
| R3 | O sistema deve permitir o registro dos atendimentos e visitas feitos a cada família, com data da visita, nível de urgência (baixa, média ou alta), usuário que atendeu, observações e data prevista para o retorno. |
| R4 | O sistema deve permitir o cadastro dos itens distribuídos pela instituição (ex.: cesta básica, kit de higiene, fralda, cobertor), com nome, categoria (alimento, higiene, vestuário ou outros) e unidade de medida, mostrando o saldo atual em estoque de cada item. |
| R5 | O sistema deve permitir o registro das doações recebidas, com data, doador (pessoa física, empresa, instituição ou doação anônima) e os itens com suas quantidades, somando essas quantidades ao estoque. |
| R6 | O sistema deve permitir o registro dos itens entregues à família em cada atendimento, com a quantidade, descontando do estoque e mantendo o histórico de tudo o que a família já recebeu. |
| R7 | O sistema deve permitir a alteração do status de acompanhamento da família (Novo, Em acompanhamento ou Inativo), guardando a data, o usuário e o motivo de cada mudança. |
| R8 | O sistema deve permitir o cadastro de usuários (nome, CPF, telefone, e-mail, senha e perfil) e o login com e-mail e senha, com dois perfis: coordenador, que acessa tudo e é o único que cadastra usuários, itens e bairros, e assistente social, que trabalha com famílias, atendimentos, entregas, doações e consultas. |
| R9 | O sistema deve permitir consultar as famílias por bairro, status e nível de urgência, gerar a lista de prioridade de atendimento e o relatório de saldo dos itens em estoque. |

## Regras de negócio

| Código | Regra | Requisito |
|--------|-------|-----------|
| RN1 | Não pode existir duas famílias com o mesmo CPF de responsável. Se o CPF já estiver cadastrado, o sistema mostra a família que já existe em vez de criar outra. | R1 |
| RN2 | A quantidade de pessoas da família não é digitada: é o responsável mais os membros cadastrados. | R2 |
| RN3 | O saldo de um item só muda por doação recebida (entrada) ou por entrega (saída), nunca é editado à mão. A entrega só é gravada se houver saldo suficiente, e o registro da entrega e a baixa no estoque são salvos juntos: se um falhar, nenhum dos dois é salvo. | R4, R5, R6 |
| RN4 | Se a família já recebeu o mesmo item nos últimos 30 dias (a cesta básica costuma ser mensal), o sistema avisa. A entrega só continua se o atendimento estiver marcado com urgência alta. | R6 |
| RN5 | Toda família começa com status Novo e passa para Em acompanhamento no primeiro atendimento. Qualquer mudança manual de status exige um motivo e fica gravada no histórico. | R3, R7 |
| RN6 | Família que já tem atendimento registrado não pode ser excluída, só inativada, para não apagar o histórico de entregas. | R1, R7 |
| RN7 | A lista de prioridade mostra as famílias que não estão inativas, ordenadas pela urgência do último atendimento e, em caso de empate, por quem está há mais tempo sem receber entrega. Família com status Novo entra como urgência média até receber a primeira visita. | R9 |

**Fora do escopo desta versão:** acesso de doadores ou famílias ao sistema, versão web ou app de celular, várias instituições no mesmo banco, integração com o CadÚnico e controle de validade dos alimentos.

## Perfis de uso

| Perfil | O que faz |
|---|---|
| Assistente social / coordenador | Cadastra, edita e acompanha famílias e atendimentos |
| Comunidade / público | Visualiza os pontos de atendimento no mapa |

## Cronograma (2º bimestre)

| Semana | Período | Atividade | Requisito | Responsável |
|:------:|:-------:|-----------|:---------:|:-----------:|
| 1 | 14/09 a 20/09 | Criar o projeto Java com Maven, os pacotes (model, view, controller, service, dao, util) e a classe ConexaoBanco lendo o arquivo db.properties | – | Julia O. |
| 1 | 14/09 a 20/09 | Instalar o SQL Server, habilitar TCP/IP na porta 1433, criar o login rehabita_app e testar os scripts da pasta /database | – | Amanda |
| 2 | 21/09 a 27/09 | Implementar as classes Pessoa, Responsavel, Membro e Usuario e os enums do pacote model | R1, R2, R8 | Julia Y. |
| 2 | 21/09 a 27/09 | Criar a interface `Dao<T>` e o BairroDao, testando leitura e gravação no banco | R1 | Amanda |
| 3 | 28/09 a 04/10 | Implementar Familia e FamiliaDao com o CRUD completo (inserir, buscar, listar, atualizar e excluir) e a regra do CPF único | R1 | Amanda |
| 3 | 28/09 a 04/10 | UsuarioDao, cadastro de usuários e tela de login com senha em hash e controle de perfil | R8 | Julia O. |
| 4 | 05/10 a 11/10 | Telas de listagem e cadastro de famílias ligadas ao FamiliaController | R1 | Julia Y. |
| 4 | 05/10 a 11/10 | Cadastro de membros (MembroDao e aba na tela da família) com cálculo da quantidade de pessoas | R2 | Julia O. |
| 5 | 12/10 a 18/10 | Atendimento, AtendimentoDao e tela de registro de visita | R3 | Julia Y. |
| 5 | 12/10 a 18/10 | Status da família com histórico e regra de excluir ou inativar | R7 | Amanda |
| 6 | 19/10 a 25/10 | ItemDoacao, MovimentacaoItem e ItemRecebido, com cadastro de itens e saldo | R4 | Amanda |
| 6 | 19/10 a 25/10 | Doador, Doacao e tela de doações recebidas somando no estoque | R5 | Julia O. |
| 7 | 26/10 a 01/11 | Entregas no atendimento (ItemEntregue e EntregaService) com baixa de estoque em transação e aviso de entrega repetida | R6 | Julia Y. |
| 7 | 26/10 a 01/11 | Testar as regras de estoque (saldo insuficiente, entrega repetida, urgência alta) | R6 | Amanda |
| 8 | 02/11 a 08/11 | Consultas de famílias por bairro, status e urgência e lista de prioridade | R9 | Julia O. |
| 8 | 02/11 a 08/11 | Relatório de saldo em estoque e menu principal de acordo com o perfil | R8, R9 | Julia Y. |
| 9 | 09/11 a 15/11 | Teste geral do sistema com dados de exemplo e correção de erros | Todos | Amanda, Julia O. e Julia Y. |
| 9 | 09/11 a 15/11 | Rodar o projeto do zero em outra máquina (Windows e Linux com Docker) seguindo o README | – | Julia Y. |
| 10 | 16/11 a 22/11 | Completar o README com o passo a passo de execução e atualizar os diagramas se algo mudou | – | Julia O. |
| 10 | 16/11 a 22/11 | Revisão final do código (nomes em CamelCase, uso de @Override, código sem uso) e versão v1.0 no GitHub | – | Amanda |

Julia O. = Julia Oliveira dos Santos · Julia Y. = Julia Yumi Hashimoto

## Tecnologias

| Item | Escolha |
|------|---------|
| Linguagem | Java 21 (JDK LTS) |
| Banco de dados | SQL Server (edição Developer ou Express), porta 1433 |
| Acesso ao banco | JDBC + driver oficial `mssql-jdbc` (13.4.0.jre11) |
| Interface | Swing |
| Build | Maven |
| Arquitetura | MVC em camadas com DAO |
| Diagramas | PlantUML |

## Estrutura do repositório

```
rehabita-aep-2026/
├── README.md
├── .gitignore
├── database/
│   ├── 01_criar_banco.sql        # banco REHABITA + login rehabita_app
│   ├── 02_criar_tabelas.sql      # tabelas, FKs, CHECKs e índices
│   └── 03_dados_iniciais.sql     # coordenador inicial, bairros e itens
├── docs/
│   ├── AEP_1Entrega_REHABITA.pdf
│   └── diagramas/                # classes, arquitetura e DER (.png e .puml)
└── src/main/
    ├── java/br/com/rehabita/
    │   ├── model/                # entidades e enums
    │   ├── view/                 # telas Swing
    │   ├── controller/           # liga telas às services
    │   ├── service/              # regras de negócio (RN1 a RN7)
    │   ├── dao/                  # interface Dao<T> e acesso via JDBC
    │   └── util/                 # ConexaoBanco, ValidadorCpf, SenhaUtil
    └── resources/
        └── db.properties.example
```

As pastas de código estão com `.gitkeep` porque o Git não guarda pasta vazia. O código entra na 2ª entrega.

## Como preparar o banco de dados

### 1. Subir o SQL Server

**Windows**

1. Instale o SQL Server (Developer ou Express) marcando autenticação **mista** (SQL Server e Windows).
2. Abra o **SQL Server Configuration Manager** → Configuração de Rede do SQL Server → Protocolos → habilite **TCP/IP**.
3. Em TCP/IP → Endereços → IPAll, deixe **TCP Port = 1433** e reinicie o serviço do SQL Server.
4. Libere a porta 1433 (TCP) no Firewall do Windows.

**Linux ou macOS (Docker)**

```bash
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=SuaSenha@Forte1" \
  -p 1433:1433 --name rehabita-sql -d mcr.microsoft.com/mssql/server:2022-latest
```

> Em Mac com processador Apple (M1/M2/M3), ative no Docker Desktop a opção de usar Rosetta para emulação x86/amd64.

### 2. Rodar os scripts

Conecte como `sa` no **SSMS** (Windows) ou na extensão **SQL Server (mssql)** do VS Code e execute, **nesta ordem**:

1. `database/01_criar_banco.sql`
2. `database/02_criar_tabelas.sql`
3. `database/03_dados_iniciais.sql`

> **Atenção:** o `02_criar_tabelas.sql` apaga e recria as tabelas. Serve para montar o ambiente do zero.

### 3. Configurar a conexão

Copie `src/main/resources/db.properties.example` para `src/main/resources/db.properties` e ajuste servidor, usuário e senha se necessário. Esse arquivo não sobe para o GitHub.

## Como executar o sistema

Nesta 1ª entrega o repositório contém a estrutura do projeto, os diagramas e os scripts do banco. O passo a passo para compilar e rodar a aplicação (JDK 21 + Maven) será incluído na 2ª entrega, conforme a semana 10 do cronograma.

## Organização do trabalho no Git

- A branch `main` só recebe código que compila.
- Cada integrante trabalha em uma branch por tarefa (ex.: `feature/cadastro-familia`) e abre pull request, revisado por outra integrante antes do merge.
- Commits pequenos, com mensagem dizendo o que foi feito.
- Versões marcadas no GitHub: `v0.1` (1ª entrega) e `v1.0` (2ª entrega).

## Documentação

- Diagrama de classes: 
- Diagrama entidade-relacionamento (DER): a adicionar em `docs/`.
- Documento da AEP (PDF da 1ª entrega): a adicionar em `docs/`.
