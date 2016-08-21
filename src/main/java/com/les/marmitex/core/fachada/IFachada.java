package com.les.marmitex.core.fachada;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;

/**
 *
 * @author gustavo
 */
public interface IFachada {
    
    public Resultado salvar(EntidadeDominio entidade);
    public Resultado alterar(EntidadeDominio entidade);
    public Resultado excluir(EntidadeDominio entidade);
    public Resultado consultar(EntidadeDominio entidade);
//    public Resultado visualizar(EntidadeDominio entidade);
    
}
