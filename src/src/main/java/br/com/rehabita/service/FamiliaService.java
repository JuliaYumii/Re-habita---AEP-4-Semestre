package br.com.rehabita.service;

import br.com.rehabita.dao.FamiliaDao;
import br.com.rehabita.model.Familia;
import br.com.rehabita.model.StatusFamilia;

import java.util.List;

public class FamiliaService {

    private final FamiliaDao familiaDao;

    public FamiliaService() {
        this.familiaDao = new FamiliaDao();
    }

    public void salvar(Familia familia) {
        if (familia.getId() > 0) {
            familiaDao.atualizar(familia);
        } else {
            familiaDao.inserir(familia);
        }
    }

    public Familia buscarPorId(int id) {
        return familiaDao.buscarPorId(id);
    }

    public List<Familia> listarTodas() {
        return familiaDao.listarTodos();
    }

    public List<Familia> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return listarTodas();
        }
        return familiaDao.buscarPorNomeResponsavel(nome);
    }

     public String excluirOuInativar(int id) {
        Familia familia = familiaDao.buscarPorId(id);
        if (familia == null) {
            return "Familia nao encontrada.";
        }

        if (familia.podeSerExcluida()) {
            familiaDao.excluir(id);
            return "Familia excluida com sucesso.";
        } else {
            familia.setStatus(StatusFamilia.INATIVO);
            familiaDao.atualizar(familia);
            return "Familia inativada (possui atendimentos vinculados).";
        }
    }

    public List<Familia> listarPorPrioridade() {
        List<Familia> familias = familiaDao.listarTodos();

        familias.sort((f1, f2) -> {
            int urg1 = pontuacaoUrgencia(f1);
            int urg2 = pontuacaoUrgencia(f2);
            if (urg1 != urg2) {
                return urg2 - urg1;
            }
            return Long.compare(f2.diasSemEntrega(), f1.diasSemEntrega());
        });

        return familias;
    }

    private int pontuacaoUrgencia(Familia familia) {
        if (familia.getUltimaUrgencia() == null) {
            return 0;
        }
        switch (familia.getUltimaUrgencia()) {
            case ALTA: return 3;
            case MEDIA: return 2;
            case BAIXA: return 1;
            default: return 0;
        }
    }
}
