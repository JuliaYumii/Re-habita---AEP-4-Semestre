package br.com.rehabita;

import br.com.rehabita.dao.*;
import br.com.rehabita.model.*;
import br.com.rehabita.service.FamiliaService;
import br.com.rehabita.util.ConexaoBanco;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== REHABITA - Sistema de Gestao de Doacoes ===");
        System.out.println();

        // ---- Teste de conexao com o banco ----
        testarConexao();

        // ---- Demonstracao de Heranca e Polimorfismo ----
        demonstrarPolimorfismo();

        // ---- Demonstracao de Classe Abstrata (MovimentacaoItem) ----
        demonstrarMovimentacaoEstoque();

        // ---- CRUD de Familia (interface Dao<T> com @Override) ----
        demonstrarCrudFamilia();

        // ---- CRUD de Usuario ----
        demonstrarCrudUsuario();

        // ---- Listagem de Bairros e Itens de Doacao ----
        demonstrarListagens();

        System.out.println();
        System.out.println("=== Fim da execucao ===");
    }

    /**
     * Testa a conexao com o SQL Server.
     * Usa try-with-resources para fechar automaticamente.
     */
    private static void testarConexao() {
        System.out.println("--- Testando conexao com SQL Server ---");

        try (Connection conn = ConexaoBanco.getConexao()) {
            System.out.println("Conexao OK! Banco: " + conn.getCatalog());
        } catch (SQLException e) {
            System.out.println("ERRO de conexao: " + e.getMessage());
            System.out.println("Verifique se o SQL Server esta rodando na porta 1433");
            System.out.println("e se o banco BancoREHABITA foi criado (execute o schema.sql).");
            return;
        }
        System.out.println();
    }

    /**
     * Demonstra HERANCA e POLIMORFISMO com a classe abstrata Pessoa.
     * Responsavel, Membro e Usuario herdam de Pessoa e sobrescrevem
     * getDescricao() e isCpfObrigatorio() com @Override.
     */
    private static void demonstrarPolimorfismo() {
        System.out.println("--- Heranca e Polimorfismo (Pessoa) ---");

        // Criando subclasses de Pessoa
        Responsavel responsavel = new Responsavel(
                "Maria Silva", "12345678901", LocalDate.of(1985, 3, 10),
                "44999001122", "Ensino Medio", "Autonoma"
        );

        Membro filho = new Membro(
                0, "Pedro Silva", null, LocalDate.of(2015, 7, 20),
                null, "Filho"
        );

        Usuario coordenador = new Usuario(
                0, "Ana Souza", "98765432100", LocalDate.of(1990, 1, 15),
                "44988776655", "ana@rehabita.com", "senha123",
                PerfilUsuario.COORDENADOR, true
        );

        // Polimorfismo: mesma chamada, comportamento diferente
        Pessoa[] pessoas = { responsavel, filho, coordenador };

        for (Pessoa p : pessoas) {
            System.out.println("  Descricao: " + p.getDescricao());
            System.out.println("  CPF obrigatorio? " + p.isCpfObrigatorio());
            System.out.println("  CPF valido? " + p.validarCpf());
            System.out.println("  Idade: " + p.calcularIdade() + " anos");
            System.out.println();
        }
    }

    /**
     * Demonstra CLASSE ABSTRATA MovimentacaoItem.
     * ItemRecebido soma no estoque, ItemEntregue subtrai.
     * Polimorfismo via aplicarNoEstoque() com @Override.
     */
    private static void demonstrarMovimentacaoEstoque() {
        System.out.println("--- Classe Abstrata (MovimentacaoItem) ---");

        ItemDoacao arroz = new ItemDoacao(0, "Arroz 5kg", CategoriaItem.ALIMENTO, "pacote", 50);
        System.out.println("  Estoque inicial de " + arroz.getNome() + ": " + arroz.getSaldoEstoque());

        // ItemRecebido: soma no estoque
        ItemRecebido recebimento = new ItemRecebido(0, 10, arroz, 1);
        recebimento.aplicarNoEstoque();
        System.out.println("  Apos receber 10: " + arroz.getSaldoEstoque());

        // ItemEntregue: subtrai do estoque
        ItemEntregue entrega = new ItemEntregue(0, 5, arroz, 1);
        entrega.aplicarNoEstoque();
        System.out.println("  Apos entregar 5: " + arroz.getSaldoEstoque());

        // Polimorfismo via array de MovimentacaoItem
        MovimentacaoItem[] movimentacoes = { recebimento, entrega };
        for (MovimentacaoItem mov : movimentacoes) {
            System.out.println("  Tipo: " + mov.getClass().getSimpleName() + " | Qtd: " + mov.getQuantidade());
        }

        System.out.println();
    }

    /**
     * Demonstra CRUD completo de Familia usando FamiliaDao
     * que implementa a interface generica Dao<T> com @Override.
     */
    private static void demonstrarCrudFamilia() {
        System.out.println("--- CRUD Familia (Dao<T> + @Override) ---");

        FamiliaService service = new FamiliaService();

        // CREATE - Inserir familia
        Familia novaFamilia = new Familia();

        Responsavel resp = new Responsavel();
        resp.setNome("Joana Pereira");
        resp.setCpf("11122233344");
        resp.setDataNascimento(LocalDate.of(1988, 5, 15));
        resp.setEscolaridade("Ensino Medio");
        resp.setSituacaoTrabalho("Desempregada");
        novaFamilia.setResponsavel(resp);

        Bairro bairro = new Bairro();
        bairro.setId(1); // Centro (seed do banco)
        novaFamilia.setBairro(bairro);

        novaFamilia.setRua("Rua das Flores");
        novaFamilia.setNumero("123");
        novaFamilia.setTipoMoradia("Aluguel");
        novaFamilia.setFaixaRenda("Ate 1 salario");

        service.salvar(novaFamilia);
        System.out.println("  INSERT - Familia inserida com ID: " + novaFamilia.getId());

        // READ - Buscar por ID
        Familia buscada = service.buscarPorId(novaFamilia.getId());
        if (buscada != null) {
            System.out.println("  SELECT - Encontrada: " + buscada.getResponsavel().getNome()
                    + " | Status: " + buscada.getStatus());
        }

        // UPDATE - Atualizar
        buscada.setFaixaRenda("1 a 2 salarios");
        buscada.getResponsavel().setSituacaoTrabalho("Informal");
        service.salvar(buscada);
        System.out.println("  UPDATE - Renda atualizada para: " + buscada.getFaixaRenda());

        // LIST - Listar todas
        List<Familia> todas = service.listarTodas();
        System.out.println("  LIST - Total de familias cadastradas: " + todas.size());

        // DELETE - Excluir (ou inativar se tiver atendimentos - RN6)
        String resultado = service.excluirOuInativar(novaFamilia.getId());
        System.out.println("  DELETE - " + resultado);

        System.out.println();
    }

    /**
     * Demonstra CRUD de Usuario (Dao<Usuario> com @Override).
     */
    private static void demonstrarCrudUsuario() {
        System.out.println("--- CRUD Usuario ---");

        UsuarioDao usuarioDao = new UsuarioDao();

        // Listar usuarios existentes (seed)
        List<Usuario> usuarios = usuarioDao.listarTodos();
        System.out.println("  Usuarios no banco: " + usuarios.size());

        for (Usuario u : usuarios) {
            System.out.println("    - " + u.getDescricao() + " | Email: " + u.getEmail());
            System.out.println("      Pode gerenciar cadastros? " + u.podeGerenciarCadastros());
        }

        // Buscar por email
        Usuario admin = usuarioDao.buscarPorEmail("admin@rehabita.com");
        if (admin != null) {
            System.out.println("  Login: " + admin.getEmail()
                    + " | Autenticou? " + admin.autenticar("admin123"));
        }

        System.out.println();
    }

    /**
     * Demonstra listagem de Bairros e Itens de Doacao.
     */
    private static void demonstrarListagens() {
        System.out.println("--- Listagens ---");

        // Bairros
        BairroDao bairroDao = new BairroDao();
        List<Bairro> bairros = bairroDao.listarTodos();
        System.out.println("  Bairros cadastrados: " + bairros.size());
        for (Bairro b : bairros) {
            System.out.println("    - " + b.getNome() + " (" + b.getCidade() + ")");
        }

        System.out.println();

        // Itens de Doacao
        ItemDoacaoDao itemDao = new ItemDoacaoDao();
        List<ItemDoacao> itens = itemDao.listarTodos();
        System.out.println("  Itens de doacao cadastrados: " + itens.size());
        for (ItemDoacao item : itens) {
            System.out.println("    - " + item.getNome()
                    + " | Categoria: " + item.getCategoria()
                    + " | Estoque: " + item.getSaldoEstoque()
                    + " | Tem saldo? " + item.temSaldo());
        }
    }
}
