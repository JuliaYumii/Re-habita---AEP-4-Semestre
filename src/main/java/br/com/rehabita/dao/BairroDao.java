package br.com.rehabita.dao;

import br.com.rehabita.model.Bairro;
import br.com.rehabita.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BairroDao {

    public List<Bairro> listarTodos() {
        String sql = "SELECT * FROM bairro ORDER BY nome";
        List<Bairro> bairros = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bairro b = new Bairro();
                b.setId(rs.getInt("id_bairro"));
                b.setNome(rs.getString("nome"));
                b.setCidade(rs.getString("cidade"));
                bairros.add(b);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar bairros: " + e.getMessage(), e);
        }
        return bairros;
    }

    public Bairro buscarPorId(int id) {
        String sql = "SELECT * FROM bairro WHERE id_bairro = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bairro b = new Bairro();
                    b.setId(rs.getInt("id_bairro"));
                    b.setNome(rs.getString("nome"));
                    b.setCidade(rs.getString("cidade"));
                    return b;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar bairro: " + e.getMessage(), e);
        }
    }
}
