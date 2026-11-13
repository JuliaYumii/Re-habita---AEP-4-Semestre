package br.com.rehabita.dao;

import br.com.rehabita.model.*;
import br.com.rehabita.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamiliaDao implements Dao<Familia> {

    @Override
    public void inserir(Familia familia) {
        String sql = "INSERT INTO familia (id_bairro, nome_responsavel, cpf_responsavel, "
                + "data_nascimento, telefone_responsavel, escolaridade_responsavel, "
                + "situacao_trabalho, cep, rua, numero, complemento, tipo_moradia, "
                + "faixa_renda, status, data_cadastro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherStatement(ps, familia);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    familia.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir familia: " + e.getMessage(), e);
        }
    }

    @Override
    public Familia buscarPorId(int id) {
        String sql = "SELECT f.*, b.nome AS bairro_nome, b.cidade AS bairro_cidade "
                + "FROM familia f "
                + "LEFT JOIN bairro b ON f.id_bairro = b.id_bairro "
                + "WHERE f.id_familia = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarFamilia(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar familia: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Familia> listarTodos() {
        String sql = "SELECT f.*, b.nome AS bairro_nome, b.cidade AS bairro_cidade "
                + "FROM familia f "
                + "LEFT JOIN bairro b ON f.id_bairro = b.id_bairro "
                + "ORDER BY f.data_cadastro DESC";

        List<Familia> familias = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                familias.add(montarFamilia(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar familias: " + e.getMessage(), e);
        }
        return familias;
    }

    @Override
    public void atualizar(Familia familia) {
        String sql = "UPDATE familia SET id_bairro = ?, nome_responsavel = ?, "
                + "cpf_responsavel = ?, data_nascimento = ?, telefone_responsavel = ?, "
                + "escolaridade_responsavel = ?, situacao_trabalho = ?, cep = ?, "
                + "rua = ?, numero = ?, complemento = ?, tipo_moradia = ?, "
                + "faixa_renda = ?, status = ?, data_cadastro = ? "
                + "WHERE id_familia = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            preencherStatement(ps, familia);
            ps.setInt(16, familia.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar familia: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM familia WHERE id_familia = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir familia: " + e.getMessage(), e);
        }
    }

    /**
     * Busca familias pelo nome do responsavel (para pesquisa).
     */
    public List<Familia> buscarPorNomeResponsavel(String nome) {
        String sql = "SELECT f.*, b.nome AS bairro_nome, b.cidade AS bairro_cidade "
                + "FROM familia f "
                + "LEFT JOIN bairro b ON f.id_bairro = b.id_bairro "
                + "WHERE f.nome_responsavel LIKE ? "
                + "ORDER BY f.nome_responsavel";

        List<Familia> familias = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    familias.add(montarFamilia(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar familia por nome: " + e.getMessage(), e);
        }
        return familias;
    }

    // --- Metodos auxiliares ---

    private void preencherStatement(PreparedStatement ps, Familia familia) throws SQLException {
        Responsavel resp = familia.getResponsavel();
        Bairro bairro = familia.getBairro();

        if (bairro != null && bairro.getId() > 0) {
            ps.setInt(1, bairro.getId());
        } else {
            ps.setNull(1, Types.INTEGER);
        }

        ps.setString(2, resp != null ? resp.getNome() : null);
        ps.setString(3, resp != null ? resp.getCpf() : null);

        if (resp != null && resp.getDataNascimento() != null) {
            ps.setDate(4, Date.valueOf(resp.getDataNascimento()));
        } else {
            ps.setNull(4, Types.DATE);
        }

        ps.setString(5, resp != null ? resp.getTelefone() : null);
        ps.setString(6, resp != null ? resp.getEscolaridade() : null);
        ps.setString(7, resp != null ? resp.getSituacaoTrabalho() : null);
        ps.setString(8, familia.getCep());
        ps.setString(9, familia.getRua());
        ps.setString(10, familia.getNumero());
        ps.setString(11, familia.getComplemento());
        ps.setString(12, familia.getTipoMoradia());
        ps.setString(13, familia.getFaixaRenda());
        ps.setString(14, familia.getStatus() != null ? familia.getStatus().name() : StatusFamilia.NOVO.name());
        ps.setDate(15, Date.valueOf(familia.getDataCadastro() != null ? familia.getDataCadastro() : java.time.LocalDate.now()));
    }

    private Familia montarFamilia(ResultSet rs) throws SQLException {
        Familia familia = new Familia();
        familia.setId(rs.getInt("id_familia"));
        familia.setCep(rs.getString("cep"));
        familia.setRua(rs.getString("rua"));
        familia.setNumero(rs.getString("numero"));
        familia.setComplemento(rs.getString("complemento"));
        familia.setTipoMoradia(rs.getString("tipo_moradia"));
        familia.setFaixaRenda(rs.getString("faixa_renda"));

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            familia.setStatus(StatusFamilia.valueOf(statusStr));
        }

        Date dataCad = rs.getDate("data_cadastro");
        if (dataCad != null) {
            familia.setDataCadastro(dataCad.toLocalDate());
        }

        Responsavel resp = new Responsavel();
        resp.setNome(rs.getString("nome_responsavel"));
        resp.setCpf(rs.getString("cpf_responsavel"));

        Date dataNasc = rs.getDate("data_nascimento");
        if (dataNasc != null) {
            resp.setDataNascimento(dataNasc.toLocalDate());
        }

        resp.setTelefone(rs.getString("telefone_responsavel"));
        resp.setEscolaridade(rs.getString("escolaridade_responsavel"));
        resp.setSituacaoTrabalho(rs.getString("situacao_trabalho"));
        familia.setResponsavel(resp);

        int idBairro = rs.getInt("id_bairro");
        if (!rs.wasNull()) {
            Bairro bairro = new Bairro();
            bairro.setId(idBairro);
            bairro.setNome(rs.getString("bairro_nome"));
            bairro.setCidade(rs.getString("bairro_cidade"));
            familia.setBairro(bairro);
        }

        return familia;
    }
}
