package br.com.rehabita.dao;

import br.com.rehabita.model.CategoriaItem;
import br.com.rehabita.model.ItemDoacao;
import br.com.rehabita.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDoacaoDao implements Dao<ItemDoacao> {

    @Override
    public void inserir(ItemDoacao item) {
        String sql = "INSERT INTO item_doacao (nome, categoria, unidade_medida, saldo_estoque, ativo) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getNome());
            ps.setString(2, item.getCategoria() != null ? item.getCategoria().name() : null);
            ps.setString(3, item.getUnidadeMedida());
            ps.setInt(4, item.getSaldoEstoque());
            ps.setBoolean(5, item.isAtivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item de doacao: " + e.getMessage(), e);
        }
    }

    @Override
    public ItemDoacao buscarPorId(int id) {
        String sql = "SELECT * FROM item_doacao WHERE id_item = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarItem(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item de doacao: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ItemDoacao> listarTodos() {
        String sql = "SELECT * FROM item_doacao ORDER BY nome";
        List<ItemDoacao> itens = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                itens.add(montarItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens de doacao: " + e.getMessage(), e);
        }
        return itens;
    }

    @Override
    public void atualizar(ItemDoacao item) {
        String sql = "UPDATE item_doacao SET nome = ?, categoria = ?, unidade_medida = ?, "
                + "saldo_estoque = ?, ativo = ? WHERE id_item = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getNome());
            ps.setString(2, item.getCategoria() != null ? item.getCategoria().name() : null);
            ps.setString(3, item.getUnidadeMedida());
            ps.setInt(4, item.getSaldoEstoque());
            ps.setBoolean(5, item.isAtivo());
            ps.setInt(6, item.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item de doacao: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM item_doacao WHERE id_item = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item de doacao: " + e.getMessage(), e);
        }
    }

    public List<ItemDoacao> listarAtivosComSaldo() {
        String sql = "SELECT * FROM item_doacao WHERE ativo = 1 AND saldo_estoque > 0 ORDER BY nome";
        List<ItemDoacao> itens = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                itens.add(montarItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens ativos: " + e.getMessage(), e);
        }
        return itens;
    }

    private ItemDoacao montarItem(ResultSet rs) throws SQLException {
        ItemDoacao item = new ItemDoacao();
        item.setId(rs.getInt("id_item"));
        item.setNome(rs.getString("nome"));

        String catStr = rs.getString("categoria");
        if (catStr != null) {
            item.setCategoria(CategoriaItem.valueOf(catStr));
        }

        item.setUnidadeMedida(rs.getString("unidade_medida"));
        item.setSaldoEstoque(rs.getInt("saldo_estoque"));
        item.setAtivo(rs.getBoolean("ativo"));
        return item;
    }
}
