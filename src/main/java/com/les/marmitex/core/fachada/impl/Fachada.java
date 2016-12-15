package com.les.marmitex.core.fachada.impl;

import com.les.marmitex.core.dao.IDAO;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Pedido;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.core.fachada.IFachada;
import com.les.marmitex.core.strategy.IStrategy;
import com.les.marmitex.core.strategy.impl.BuscarDisponibilidade;
import com.les.marmitex.core.strategy.impl.EfetuarDevolucao;
import com.les.marmitex.core.strategy.impl.ValidarCamposEmBranco;
import com.les.marmitex.core.strategy.impl.ValidarCamposEndereco;
import com.les.marmitex.core.strategy.impl.ValidarEstoque;
import com.les.marmitex.core.strategy.impl.VerificarEmailRepetido;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author gustavo
 */
public class Fachada implements IFachada {

    private final AnnotationConfigApplicationContext daos;
    private Map<String, Map<String, List<IStrategy>>> rns;
    private Resultado resultado;

    public Fachada() {
        // recupera as anotations
        daos = new AnnotationConfigApplicationContext("com.les.marmitex.core.dao");

        rns = new HashMap<String, Map<String, List<IStrategy>>>();

        /* ------- DECLARAÇÃO DE TODOS OS STRATEGIES -------  */
        ValidarCamposEmBranco vCamposEmBranco = new ValidarCamposEmBranco();
        ValidarCamposEndereco vCamposEndereco = new ValidarCamposEndereco();
        ValidarEstoque vEstoque = new ValidarEstoque();
        EfetuarDevolucao efetuarDevolucao = new EfetuarDevolucao();
        VerificarEmailRepetido verificarEmailRepetido = new VerificarEmailRepetido();
        BuscarDisponibilidade buscarDisponibilidade = new BuscarDisponibilidade();


        /* ------- DECLARAÇÃO DAS RNS POR OPERAÇÃO/ENTIDADE -------  */
        List<IStrategy> rnsSalvarEndereco = new ArrayList<IStrategy>();
        List<IStrategy> rnsAlterarEndereco = new ArrayList<IStrategy>();
        List<IStrategy> rnsExcluirEndereco = new ArrayList<IStrategy>();
        List<IStrategy> rnsConsultarEndereco = new ArrayList<IStrategy>();
        List<IStrategy> rnsMontarMarmitex = new ArrayList<>();
        List<IStrategy> rnsAlterarPedido = new ArrayList<>();
        List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
        List<IStrategy> rnsExcluirIngrediente = new ArrayList<IStrategy>();


        /* ------- ADD STRATEGIES EM SUAS RESPECTIVAS OPERAÇÕES -------  */
//        rnsSalvarEndereco.add(vCamposEndereco);
        rnsMontarMarmitex.add(vEstoque);
        rnsAlterarPedido.add(efetuarDevolucao);
        rnsSalvarCliente.add(verificarEmailRepetido);
        
        rnsExcluirIngrediente.add(buscarDisponibilidade);

        /* ------- DECLARAÇÃO DOS RNS POR ENTIDADE -------  */
        Map<String, List<IStrategy>> rnsEndereco = new HashMap<String, List<IStrategy>>();
        Map<String, List<IStrategy>> rnsMarmitex = new HashMap<String, List<IStrategy>>();
        Map<String, List<IStrategy>> rnsPedido = new HashMap<String, List<IStrategy>>();
        Map<String, List<IStrategy>> rnsCliente = new HashMap<String, List<IStrategy>>();
        Map<String, List<IStrategy>> rnsIngrediente = new HashMap<String, List<IStrategy>>();


        /* ------- ADD OS MAPS POR OPERAÇÃO EM SUAS ENTIDADES -------  */
        rnsEndereco.put("SALVAR", rnsSalvarEndereco);
        rnsEndereco.put("CONSULTAR", rnsSalvarEndereco);
        rnsMarmitex.put("ALTERAR", rnsMontarMarmitex);
        rnsPedido.put("ALTERAR", rnsAlterarPedido);
        rnsCliente.put("SALVAR", rnsSalvarCliente);
        rnsIngrediente.put("EXCLUIR", rnsExcluirIngrediente);


        /* ------- ADD OS MAPS POR ENTIDADES NO MAP GERAL -------  */
        rns.put(Endereco.class.getName(), rnsEndereco);
        rns.put(Marmitex.class.getName(), rnsMarmitex);
        rns.put(Pedido.class.getName(), rnsPedido);
        rns.put(Cliente.class.getName(), rnsCliente);
        rns.put(Ingrediente.class.getName(), rnsIngrediente);
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        resultado = new Resultado();
        String nmClasse = entidade.getClass().getName();

        String msg = executarRegras(entidade, "SALVAR");

        if (msg == null) {
            IDAO dao = (IDAO) daos.getBean(nmClasse);
            try {
                dao.salvar(entidade);
                List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
            } catch (SQLException e) {

                e.printStackTrace();
                resultado.setMensagem("Não foi possível realizar o registro!");

            }
        } else {
            resultado.setMensagem(msg);
        }
        return resultado;
    }

    @Override
    public Resultado alterar(EntidadeDominio entidade) {
        resultado = new Resultado();

        String nmClasse = entidade.getClass().getName();

        String msg = executarRegras(entidade, "ALTERAR");

        if (msg == null) {

            IDAO dao = (IDAO) daos.getBean(nmClasse);
            try {
                dao.alterar(entidade);
                List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
            } catch (SQLException e) {

                e.printStackTrace();
                resultado.setMensagem("Não foi possível alterar o registro");
            }
        } else {
            resultado.setMensagem(msg);
        }

        return resultado;
    }

    @Override
    public Resultado excluir(EntidadeDominio entidade) {
        resultado = new Resultado();

        String nmClasse = entidade.getClass().getName();

        String msg = executarRegras(entidade, "EXCLUIR");

        if (msg == null) {

            IDAO dao = (IDAO) daos.getBean(nmClasse);
            try {
                dao.excluir(entidade);
                List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
                entidades.add(entidade);
                resultado.setEntidades(entidades);
            } catch (SQLException e) {
                e.printStackTrace();
                resultado.setMensagem("Não foi possível efetuar a exclusão!");

            }
        } else {
            resultado.setMensagem(msg);
        }

        return resultado;
    }

    @Override
    public Resultado consultar(EntidadeDominio entidade) {
        resultado = new Resultado();

        String nmClasse = entidade.getClass().getName();

        String msg = executarRegras(entidade, "CONSULTAR");

        if (msg == null) {

            IDAO dao = (IDAO) daos.getBean(nmClasse);

            try {
                resultado.setEntidades(dao.consultar(entidade));

            } catch (SQLException e) {
                e.printStackTrace();
                resultado.setMensagem("Não foi possível realizar a consulta!");

            }
        } else {
            resultado.setMensagem(msg);
        }

        return resultado;
    }

    private String executarRegras(EntidadeDominio entidade, String operacao) {
        String nmClasse = entidade.getClass().getName();
        StringBuilder msg = new StringBuilder();

        Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);

        if (regrasOperacao != null) {
            List<IStrategy> regras = regrasOperacao.get(operacao);

            if (regras != null) {
                for (IStrategy s : regras) {
                    String m = s.validar(entidade);

                    if (m != null) {
                        msg.append(m);
                        msg.append("\n");
                    }
                }
            }

        }

        if (msg.length() > 0) {
            return msg.toString();
        } else {
            return null;
        }

    }

}
