package com.les.marmitex.core.fachada;

import com.les.marmitex.core.dominio.IEntidade;
import com.les.marmitex.core.dominio.Resultado;

/**
 *
 * @author gustavo
 */
public interface IFachada {
    
    public Resultado salvar(IEntidade entidade);
    public Resultado alterar(IEntidade entidade);
    public Resultado excluir(IEntidade entidade);
    public Resultado consultar(IEntidade entidade);
    public Resultado visualizar(IEntidade entidade);
    
}
