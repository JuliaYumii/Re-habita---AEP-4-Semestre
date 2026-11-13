package br.com.rehabita.dao;

import br.com.rehabita.model.PerfilUsuario;
import br.com.rehabita.model.Usuario;
import br.com.rehabita.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements Dao<Usuario> {

    @Override
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, cpf, data_nascimento, telefone, "
                + "email, senha_hash, perfil, data_cadastro, ativo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setDate(3, usuario.getDataNascimento() != null ? Date.valueOf(usuario.getDataNascimento()) : null);
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getSenhaHash());
            ps.setString(7, usuario.getPerfil() != null ? usuario.getPerfil().name() : null);
            ps.setDate(8, Date.valueOf(java.time.LocalDate.now()));
            ps.setBoolean(9, usuario.isAtivo());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarUsuario(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(montarUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuarios: " + e.getMessage(), e);
        }
        return usuarios;
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, data_nascimento = ?, "
                + "telefone = ?, email = ?, senha_hash = ?, perfil = ?, ativo = ? "
                + "WHERE id_usuario = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setDate(3, usuario.getDataNascimento() != null ? Date.valueOf(usuario.getDataNascimento()) : null);
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getSenhaHash());
            ps.setString(7, usuario.getPerfil() != null ? usuario.getPerfil().name() : null);
            ps.setBoolean(8, usuario.isAtivo());
            ps.setInt(9, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Busca usuario pelo email (para login).
     */
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND ativo = 1";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarUsuario(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por email: " + e.getMessage(), e);
        }
    }

    private Usuario montarUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setCpf(rs.getString("cpf"));

        Date dataNasc = rs.getDate("data_nascimento");
        if (dataNasc != null) {
            u.setDataNascimento(dataNasc.toLocalDate());
        }

        u.setTelefone(rs.getString("telefone"));
        u.setEmail(rs.getString("email"));
        u.setSenhaHash(rs.getString("senha_hash"));

        String perfilStr = rs.getString("perfil");
        if (perfilStr != null) {
            u.setPerfil(PerfilUsuario.valueOf(perfilStr));
        }

        Date dataCad = rs.getDate("data_cadastro");
        if (dataCad != null) {
            u.setDataCadastro(dataCad.toLocalDate());
        }

        u.setAtivo(rs.getBoolean("ativo"));
        return u;
    }
}
