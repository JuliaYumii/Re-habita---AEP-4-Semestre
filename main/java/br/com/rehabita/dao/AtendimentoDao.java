package br.com.rehabita.dao;

import br.com.rehabita.model.Atendimento;
import br.com.rehabita.model.NivelUrgencia;
import br.com.rehabita.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoDao implements Dao<Atendimento> {

    @Override
    public void inserir(Atendimento atendimento) {
        String sql = "INSERT INTO atendimento (id_familia, id_usuario, data_visita, "
                + "nivel_urgencia, observacoes, data_retorno) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, atendimento.getIdFamilia());
            ps.setInt(2, atendimento.getIdUsuario());
            ps.setDate(3, Date.valueOf(atendimento.getDataVisita()));
            ps.setString(4, atendimento.getNivelUrgencia() != null ? atendimento.getNivelUrgencia().name() : null);
            ps.setString(5, atendimento.getObservacoes());

            if (atendimento.getDataRetorno() != null) {
                ps.setDate(6, Date.valueOf(atendimento.getDataRetorno()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    atendimento.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir atendimento: " + e.getMessage(), e);
        }
    }

    @Override
    public Atendimento buscarPorId(int id) {
        String sql = "SELECT * FROM atendimento WHERE id_atendimento = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarAtendimento(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar atendimento: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Atendimento> listarTodos() {
        String sql = "SELECT * FROM atendimento ORDER BY data_visita DESC";
        List<Atendimento> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montarAtendimento(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar atendimentos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void atualizar(Atendimento atendimento) {
        String sql = "UPDATE atendimento SET id_familia = ?, id_usuario = ?, "
                + "data_visita = ?, nivel_urgencia = ?, observacoes = ?, data_retorno = ? "
                + "WHERE id_atendimento = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, atendimento.getIdFamilia());
            ps.setInt(2, atendimento.getIdUsuario());
            ps.setDate(3, Date.valueOf(atendimento.getDataVisita()));
            ps.setString(4, atendimento.getNivelUrgencia() != null ? atendimento.getNivelUrgencia().name() : null);
            ps.setString(5, atendimento.getObservacoes());

            if (atendimento.getDataRetorno() != null) {
                ps.setDate(6, Date.valueOf(atendimento.getDataRetorno()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setInt(7, atendimento.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar atendimento: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM atendimento WHERE id_atendimento = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir atendimento: " + e.getMessage(), e);
        }
    }

    public List<Atendimento> listarPorFamilia(int idFamilia) {
        String sql = "SELECT * FROM atendimento WHERE id_familia = ? ORDER BY data_visita DESC";
        List<Atendimento> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFamilia);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(montarAtendimento(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar atendimentos da familia: " + e.getMessage(), e);
        }
        return lista;
    }

    private Atendimento montarAtendimento(ResultSet rs) throws SQLException {
        Atendimento a = new Atendimento();
        a.setId(rs.getInt("id_atendimento"));
        a.setIdFamilia(rs.getInt("id_familia"));
        a.setIdUsuario(rs.getInt("id_usuario"));

        Date dataVisita = rs.getDate("data_visita");
        if (dataVisita != null) {
            a.setDataVisita(dataVisita.toLocalDate());
        }

        String urgenciaStr = rs.getString("nivel_urgencia");
        if (urgenciaStr != null) {
            a.setNivelUrgencia(NivelUrgencia.valueOf(urgenciaStr));
        }

        a.setObservacoes(rs.getString("observacoes"));

        Date dataRetorno = rs.getDate("data_retorno");
        if (dataRetorno != null) {
            a.setDataRetorno(dataRetorno.toLocalDate());
        }

        return a;
    }
}
