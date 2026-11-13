-- banco do rehabita roda na master!
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'BancoREHABITA')
BEGIN
    CREATE DATABASE BancoREHABITA;
END
GO

USE BancoREHABITA;
GO

--tabelas
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'bairro')
BEGIN
    CREATE TABLE bairro (
        id_bairro INT IDENTITY(1,1) PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        cidade VARCHAR(100) NOT NULL
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'familia')
BEGIN
    CREATE TABLE familia (
        id_familia INT IDENTITY(1,1) PRIMARY KEY,
        id_bairro INT NULL,
        nome_responsavel VARCHAR(150) NOT NULL,
        cpf_responsavel VARCHAR(11) NULL,
        data_nascimento DATE NULL,
        telefone_responsavel VARCHAR(20) NULL,
        escolaridade_responsavel VARCHAR(50) NULL,
        situacao_trabalho VARCHAR(50) NULL,
        cep VARCHAR(8) NULL,
        rua VARCHAR(200) NULL,
        numero VARCHAR(20) NULL,
        complemento VARCHAR(100) NULL,
        tipo_moradia VARCHAR(30) NULL,
        faixa_renda VARCHAR(30) NULL,
        status VARCHAR(20) NOT NULL DEFAULT 'NOVO',
        data_cadastro DATE NOT NULL DEFAULT GETDATE(),
        CONSTRAINT FK_familia_bairro FOREIGN KEY (id_bairro)
            REFERENCES bairro(id_bairro)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'membro_familia')
BEGIN
    CREATE TABLE membro_familia (
        id_membro INT IDENTITY(1,1) PRIMARY KEY,
        id_familia INT NOT NULL,
        nome VARCHAR(150) NOT NULL,
        cpf VARCHAR(11) NULL,
        data_nascimento DATE NULL,
        telefone VARCHAR(20) NULL,
        parentesco VARCHAR(50) NOT NULL,
        CONSTRAINT FK_membro_familia FOREIGN KEY (id_familia)
            REFERENCES familia(id_familia)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'usuario')
BEGIN
    CREATE TABLE usuario (
        id_usuario INT IDENTITY(1,1) PRIMARY KEY,
        nome VARCHAR(150) NOT NULL,
        cpf VARCHAR(11) NULL,
        data_nascimento DATE NULL,
        telefone VARCHAR(20) NULL,
        email VARCHAR(150) NOT NULL UNIQUE,
        senha_hash VARCHAR(255) NOT NULL,
        perfil VARCHAR(30) NOT NULL,
        data_cadastro DATE NOT NULL DEFAULT GETDATE(),
        ativo BIT NOT NULL DEFAULT 1
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'historico_status')
BEGIN
    CREATE TABLE historico_status (
        id_historico INT IDENTITY(1,1) PRIMARY KEY,
        id_familia INT NOT NULL,
        id_usuario INT NULL,
        status_anterior VARCHAR(20) NULL,
        status_novo VARCHAR(20) NOT NULL,
        motivo VARCHAR(500) NULL,
        data_alteracao DATE NOT NULL DEFAULT GETDATE(),
        CONSTRAINT FK_historico_familia FOREIGN KEY (id_familia)
            REFERENCES familia(id_familia),
        CONSTRAINT FK_historico_usuario FOREIGN KEY (id_usuario)
            REFERENCES usuario(id_usuario)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'atendimento')
BEGIN
    CREATE TABLE atendimento (
        id_atendimento INT IDENTITY(1,1) PRIMARY KEY,
        id_familia INT NOT NULL,
        id_usuario INT NOT NULL,
        data_visita DATE NOT NULL DEFAULT GETDATE(),
        nivel_urgencia VARCHAR(10) NOT NULL,
        observacoes VARCHAR(1000) NULL,
        data_retorno DATE NULL,
        CONSTRAINT FK_atendimento_familia FOREIGN KEY (id_familia)
            REFERENCES familia(id_familia),
        CONSTRAINT FK_atendimento_usuario FOREIGN KEY (id_usuario)
            REFERENCES usuario(id_usuario)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'item_doacao')
BEGIN
    CREATE TABLE item_doacao (
        id_item INT IDENTITY(1,1) PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        categoria VARCHAR(30) NOT NULL,
        unidade_medida VARCHAR(20) NOT NULL,
        saldo_estoque INT NOT NULL DEFAULT 0,
        ativo BIT NOT NULL DEFAULT 1
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'atendimento_item')
BEGIN
    CREATE TABLE atendimento_item (
        id_atendimento_item INT IDENTITY(1,1) PRIMARY KEY,
        id_atendimento INT NOT NULL,
        id_item INT NOT NULL,
        quantidade INT NOT NULL,
        CONSTRAINT FK_atitem_atendimento FOREIGN KEY (id_atendimento)
            REFERENCES atendimento(id_atendimento),
        CONSTRAINT FK_atitem_item FOREIGN KEY (id_item)
            REFERENCES item_doacao(id_item)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'doador')
BEGIN
    CREATE TABLE doador (
        id_doador INT IDENTITY(1,1) PRIMARY KEY,
        tipo VARCHAR(30) NOT NULL,
        nome VARCHAR(150) NOT NULL,
        cpf_cnpj VARCHAR(14) NULL,
        telefone VARCHAR(20) NULL,
        email VARCHAR(150) NULL
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'doacao')
BEGIN
    CREATE TABLE doacao (
        id_doacao INT IDENTITY(1,1) PRIMARY KEY,
        id_doador INT NULL,
        id_usuario INT NOT NULL,
        data_recebimento DATE NOT NULL DEFAULT GETDATE(),
        observacoes VARCHAR(500) NULL,
        CONSTRAINT FK_doacao_doador FOREIGN KEY (id_doador)
            REFERENCES doador(id_doador),
        CONSTRAINT FK_doacao_usuario FOREIGN KEY (id_usuario)
            REFERENCES usuario(id_usuario)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'doacao_item')
BEGIN
    CREATE TABLE doacao_item (
        id_doacao_item INT IDENTITY(1,1) PRIMARY KEY,
        id_doacao INT NOT NULL,
        id_item INT NOT NULL,
        quantidade INT NOT NULL,
        CONSTRAINT FK_doacaoitem_doacao FOREIGN KEY (id_doacao)
            REFERENCES doacao(id_doacao),
        CONSTRAINT FK_doacaoitem_item FOREIGN KEY (id_item)
            REFERENCES item_doacao(id_item)
    );
END
GO

IF NOT EXISTS (SELECT 1 FROM bairro WHERE nome = 'Centro')
BEGIN
    INSERT INTO bairro (nome, cidade) VALUES
    ('Centro', 'Maringa'),
    ('Zona 7', 'Maringa'),
    ('Jardim Alvorada', 'Maringa'),
    ('Vila Esperanca', 'Maringa'),
    ('Conjunto Requiao', 'Maringa'),
    ('Parque das Laranjeiras', 'Maringa'),
    ('Jardim Inga', 'Maringa'),
    ('Vila Morangueira', 'Maringa'),
    ('Jardim Olimpico', 'Maringa'),
    ('Zona 5', 'Maringa');
END
GO

IF NOT EXISTS (SELECT 1 FROM usuario WHERE email = 'admin@rehabita.com')
BEGIN
    INSERT INTO usuario (nome, cpf, data_nascimento, telefone, email, senha_hash, perfil) VALUES
    ('Administrador', '11528776332', '1980-03-15', '44999000001', 'admin@rehabita.com', 'admin123', 'COORDENADOR'),
    ('Ana Paula Souza', '22233344455', '1990-07-22', '44999000002', 'ana.souza@rehabita.com', 'senha123', 'ASSISTENTE'),
    ('Carlos Eduardo Lima', '33344455566', '1985-11-10', '44999000003', 'carlos.lima@rehabita.com', 'senha123', 'ASSISTENTE'),
    ('Mariana Costa Silva', '44455566677', '1992-01-05', '44999000004', 'mariana.silva@rehabita.com', 'senha123', 'VOLUNTARIO'),
    ('Roberto Almeida', '55566677788', '1988-09-18', '44999000005', 'roberto.almeida@rehabita.com', 'senha123', 'COORDENADOR'),
    ('Fernanda Oliveira', '66677788899', '1995-04-30', '44999000006', 'fernanda.oliveira@rehabita.com', 'senha123', 'ASSISTENTE'),
    ('Lucas Pereira Santos', '77788899900', '1991-06-12', '44999000007', 'lucas.santos@rehabita.com', 'senha123', 'VOLUNTARIO'),
    ('Patricia Rodrigues', '88899900011', '1987-12-25', '44999000008', 'patricia.rodrigues@rehabita.com', 'senha123', 'ASSISTENTE'),
    ('Thiago Nascimento', '99900011122', '1993-08-08', '44999000009', 'thiago.nascimento@rehabita.com', 'senha123', 'VOLUNTARIO'),
    ('Juliana Ferreira', '10011122233', '1994-02-14', '44999000010', 'juliana.ferreira@rehabita.com', 'senha123', 'ASSISTENTE');
END
GO

IF NOT EXISTS (SELECT 1 FROM item_doacao WHERE nome = 'Arroz 5kg')
BEGIN
    INSERT INTO item_doacao (nome, categoria, unidade_medida, saldo_estoque) VALUES
    ('Arroz 5kg', 'ALIMENTO', 'pacote', 47),
    ('Feijao 1kg', 'ALIMENTO', 'pacote', 38),
    ('Sabonete', 'HIGIENE', 'unidade', 112),
    ('Cobertor', 'VESTUARIO', 'unidade', 19),
    ('Oleo de Soja', 'ALIMENTO', 'unidade', 26),
    ('Leite em Po', 'ALIMENTO', 'lata', 34),
    ('Pasta de Dente', 'HIGIENE', 'unidade', 65),
    ('Cesta Basica', 'ALIMENTO', 'cesta', 15),
    ('Fralda Descartavel', 'HIGIENE', 'pacote', 41),
    ('Toalha de Banho', 'VESTUARIO', 'unidade', 22);
END
GO

IF NOT EXISTS (SELECT 1 FROM familia WHERE cpf_responsavel = '11122233344')
BEGIN
    INSERT INTO familia (id_bairro, nome_responsavel, cpf_responsavel, data_nascimento, telefone_responsavel, escolaridade_responsavel, situacao_trabalho, cep, rua, numero, tipo_moradia, faixa_renda, status) VALUES
    (1, 'Maria da Silva Oliveira', '11122233344', '1985-03-10', '44988001122', 'Ensino Medio', 'Desempregada', '87010001', 'Rua Santos Dumont', '150', 'Aluguel', 'Ate 1 salario', 'EM_ATENDIMENTO'),
    (2, 'Joana Pereira dos Santos', '22233344455', '1990-08-20', '44988002233', 'Ensino Fundamental', 'Informal', '87020002', 'Rua Pioneiro Zulmiro', '320', 'Cedida', 'Ate 1 salario', 'EM_ATENDIMENTO'),
    (3, 'Lucia Fernandes Costa', '33344455566', '1978-12-01', '44988003344', 'Nao Alfabetizada', 'Desempregada', '87030003', 'Av Brasil', '45', 'Propria', 'Sem renda', 'NOVO'),
    (4, 'Rosa Maria Souza', '44455566677', '1982-05-15', '44988004455', 'Ensino Medio', 'CLT', '87040004', 'Rua Neo Alves Martins', '780', 'Aluguel', '1 a 2 salarios', 'EM_ATENDIMENTO'),
    (5, 'Ana Carolina Lima', '55566677788', '1995-01-30', '44988005566', 'Superior Incompleto', 'Desempregada', '87050005', 'Rua Joubert de Carvalho', '210', 'Aluguel', 'Ate 1 salario', 'NOVO'),
    (6, 'Tereza Cristina Almeida', '66677788899', '1970-09-08', '44988006677', 'Ensino Fundamental', 'Aposentada', '87060006', 'Rua Paranagua', '92', 'Propria', 'Ate 1 salario', 'ATENDIDA'),
    (7, 'Sandra Regina Martins', '77788899900', '1988-04-22', '44988007788', 'Ensino Medio', 'Informal', '87070007', 'Rua Maraba', '430', 'Cedida', 'Sem renda', 'EM_ATENDIMENTO'),
    (1, 'Francisca Aparecida Rocha', '88899900011', '1975-11-17', '44988008899', 'Nao Alfabetizada', 'Desempregada', '87010008', 'Rua Piratininga', '55', 'Aluguel', 'Sem renda', 'NOVO'),
    (3, 'Claudia Batista Ferreira', '99900011122', '1993-07-04', '44988009900', 'Ensino Medio', 'CLT', '87030009', 'Rua Cabo Frio', '118', 'Aluguel', '1 a 2 salarios', 'ATENDIDA'),
    (8, 'Vera Lucia Goncalves', '10011122233', '1968-02-28', '44988010011', 'Ensino Fundamental', 'Aposentada', '87080010', 'Rua das Primaveras', '67', 'Propria', 'Ate 1 salario', 'EM_ATENDIMENTO');
END
GO

IF NOT EXISTS (SELECT 1 FROM membro_familia WHERE nome = 'Pedro Silva Oliveira')
BEGIN
    INSERT INTO membro_familia (id_familia, nome, cpf, data_nascimento, telefone, parentesco) VALUES
    (1, 'Pedro Silva Oliveira', NULL, '2010-06-15', NULL, 'Filho'),
    (1, 'Ana Julia Oliveira', NULL, '2013-09-22', NULL, 'Filha'),
    (2, 'Lucas Pereira Santos', '12312312312', '2005-03-10', '44988112233', 'Filho'),
    (2, 'Enzo Gabriel Santos', NULL, '2015-11-05', NULL, 'Filho'),
    (3, 'Jose Fernandes Costa', '23423423423', '1975-07-20', '44988223344', 'Esposo'),
    (3, 'Carla Fernandes Costa', NULL, '2008-01-18', NULL, 'Filha'),
    (4, 'Miguel Souza', NULL, '2018-04-30', NULL, 'Filho'),
    (5, 'Helena Lima', NULL, '2020-12-10', NULL, 'Filha'),
    (7, 'Davi Martins', NULL, '2012-08-25', NULL, 'Filho'),
    (7, 'Laura Martins', NULL, '2016-02-14', NULL, 'Filha'),
    (8, 'Antonio Rocha', '34534534534', '1972-10-03', '44988334455', 'Esposo'),
    (10, 'Sebastiao Goncalves', '45645645645', '1965-05-20', '44988445566', 'Esposo');
END
GO

IF NOT EXISTS (SELECT 1 FROM doador WHERE nome = 'Supermercado Cidade Cancao')
BEGIN
    INSERT INTO doador (tipo, nome, cpf_cnpj, telefone, email) VALUES
    ('PESSOA_JURIDICA', 'Supermercado Cidade Cancao', '12345678000190', '4430301010', 'contato@cidadecancao.com.br'),
    ('PESSOA_JURIDICA', 'Cocamar Cooperativa', '23456789000180', '4430302020', 'social@cocamar.com.br'),
    ('PESSOA_FISICA', 'Dr. Marcos Ribeiro', '11111111111', '44999111111', 'marcos.ribeiro@email.com'),
    ('PESSOA_JURIDICA', 'Igreja Matriz de Maringa', '34567890000170', '4430303030', 'paroquia@igrejamga.com.br'),
    ('PESSOA_FISICA', 'Dona Helena Tanaka', '22222222222', '44999222222', 'helena.tanaka@email.com'),
    ('PESSOA_JURIDICA', 'Lions Clube Maringa', '45678901000160', '4430304040', 'lions@maringa.com.br'),
    ('PESSOA_FISICA', 'Prof. Ricardo Mendes', '33333333333', '44999333333', 'ricardo.mendes@email.com'),
    ('PESSOA_JURIDICA', 'Rotary Club Maringa Sul', '56789012000150', '4430305050', 'rotary.sul@maringa.com.br'),
    ('PESSOA_FISICA', 'Sra. Beatriz Campos', '44444444444', '44999444444', 'beatriz.campos@email.com'),
    ('PESSOA_JURIDICA', 'Unicesumar', '67890123000140', '4430306060', 'extensao@unicesumar.edu.br');
END
GO

IF NOT EXISTS (SELECT 1 FROM historico_status WHERE id_familia = 1 AND status_novo = 'EM_ATENDIMENTO')
BEGIN
    INSERT INTO historico_status (id_familia, id_usuario, status_anterior, status_novo, motivo, data_alteracao) VALUES
    (1, 1, 'NOVO', 'EM_ATENDIMENTO', 'Familia aprovada para receber doacoes apos visita inicial', '2026-01-15'),
    (2, 2, 'NOVO', 'EM_ATENDIMENTO', 'Situacao de vulnerabilidade confirmada em visita', '2026-01-20'),
    (3, 1, NULL, 'NOVO', 'Cadastro inicial da familia', '2026-02-05'),
    (4, 3, 'NOVO', 'EM_ATENDIMENTO', 'Familia com filhos pequenos, prioridade alta', '2026-02-10'),
    (5, 2, NULL, 'NOVO', 'Cadastro realizado por indicacao do CRAS', '2026-03-01'),
    (6, 1, 'EM_ATENDIMENTO', 'ATENDIDA', 'Familia recebeu doacoes por 6 meses, situacao estabilizada', '2026-03-15'),
    (7, 6, 'NOVO', 'EM_ATENDIMENTO', 'Urgencia alta, mae desempregada com 2 filhos', '2026-04-01'),
    (8, 2, NULL, 'NOVO', 'Encaminhamento via assistencia social do municipio', '2026-04-20'),
    (9, 3, 'EM_ATENDIMENTO', 'ATENDIDA', 'Responsavel conseguiu emprego CLT', '2026-05-10'),
    (10, 6, 'NOVO', 'EM_ATENDIMENTO', 'Casal de idosos com renda insuficiente', '2026-05-25');
END
GO

IF NOT EXISTS (SELECT 1 FROM atendimento WHERE id_familia = 1 AND data_visita = '2026-01-15')
BEGIN
    INSERT INTO atendimento (id_familia, id_usuario, data_visita, nivel_urgencia, observacoes, data_retorno) VALUES
    (1, 2, '2026-01-15', 'ALTA', 'Familia sem alimentos, geladeira vazia. Criancas em idade escolar.', '2026-02-15'),
    (2, 3, '2026-01-20', 'MEDIA', 'Mae solo com dois filhos, renda informal insuficiente.', '2026-02-20'),
    (4, 2, '2026-02-10', 'ALTA', 'Bebe de 2 anos precisa de fraldas e leite. Situacao critica.', '2026-03-10'),
    (1, 6, '2026-02-15', 'MEDIA', 'Retorno: familia recebeu cesta basica, situacao melhorou.', '2026-03-15'),
    (7, 3, '2026-04-01', 'ALTA', 'Mae desempregada, aluguel atrasado, sem alimentos em casa.', '2026-05-01'),
    (2, 2, '2026-04-20', 'BAIXA', 'Retorno: filhos na escola, mae conseguiu bicos.', '2026-06-20'),
    (10, 6, '2026-05-25', 'MEDIA', 'Idosos com dificuldade de locomocao, precisam de itens de higiene.', '2026-06-25'),
    (1, 2, '2026-06-15', 'BAIXA', 'Terceira visita: familia estabilizando, mae fez curso profissionalizante.', '2026-08-15'),
    (4, 3, '2026-07-10', 'MEDIA', 'Crianca cresceu, precisa de roupas e cobertores para o inverno.', '2026-08-10'),
    (7, 6, '2026-08-01', 'ALTA', 'Retorno: mae ainda desempregada, situacao permanece critica.', '2026-09-01');
END
GO

IF NOT EXISTS (SELECT 1 FROM atendimento_item WHERE id_atendimento = 1 AND id_item = 1)
BEGIN
    INSERT INTO atendimento_item (id_atendimento, id_item, quantidade) VALUES
    (1, 1, 2),   
    (1, 2, 3),   
    (1, 5, 2),   
    (2, 1, 1),   
    (2, 3, 5),   
    (3, 6, 4),   
    (3, 9, 3),   
    (5, 8, 1),   
    (5, 4, 2),   
    (7, 3, 3),   
    (7, 7, 2),   
    (9, 4, 3);   
END
GO

IF NOT EXISTS (SELECT 1 FROM doacao WHERE id_doador = 1 AND data_recebimento = '2026-01-10')
BEGIN
    INSERT INTO doacao (id_doador, id_usuario, data_recebimento, observacoes) VALUES
    (1, 1, '2026-01-10', 'Doacao mensal do supermercado - alimentos proximos da validade'),
    (3, 2, '2026-01-25', 'Doacao espontanea do Dr. Marcos - produtos de higiene'),
    (4, 1, '2026-02-05', 'Campanha de inverno da igreja - cobertores e roupas'),
    (6, 3, '2026-02-20', 'Arrecadacao do Lions Clube - cestas basicas'),
    (2, 1, '2026-03-10', 'Doacao da Cocamar - alimentos nao pereciveis'),
    (10, 5, '2026-04-01', 'Projeto de extensao Unicesumar - kits de higiene'),
    (5, 2, '2026-04-15', 'Doacao mensal da Dona Helena - fraldas e leite'),
    (8, 6, '2026-05-10', 'Campanha do Rotary - alimentos e vestuario'),
    (7, 3, '2026-06-01', 'Doacao do Prof. Ricardo - material escolar e alimentos'),
    (9, 2, '2026-07-01', 'Doacao da Sra. Beatriz - toalhas e cobertores');
END
GO

IF NOT EXISTS (SELECT 1 FROM doacao_item WHERE id_doacao = 1 AND id_item = 1)
BEGIN
    INSERT INTO doacao_item (id_doacao, id_item, quantidade) VALUES
    (1, 1, 20), 
    (1, 2, 15), 
    (1, 5, 10), 
    (2, 3, 50), 
    (2, 7, 30), 
    (3, 4, 15), 
    (4, 8, 10), 
    (5, 1, 25), 
    (5, 2, 20), 
    (6, 3, 40), 
    (7, 6, 20), 
    (7, 9, 30); 
END
GO

PRINT 'Banco criado com sucesso - todas as tabelas populadas.';
GO
