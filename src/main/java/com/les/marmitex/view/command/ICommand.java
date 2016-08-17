package com.les.marmitex.view.command;

import com.les.marmitex.core.dominio.IEntidade;
import com.les.marmitex.core.dominio.Resultado;

/**
 *
 * @author gustavo
 */
public interface ICommand {
    public Resultado execute(IEntidade entidade);
}
