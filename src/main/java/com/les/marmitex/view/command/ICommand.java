package com.les.marmitex.view.command;

import com.les.marmitex.model.dominio.IEntidade;
import com.les.marmitex.model.dominio.Resultado;

/**
 *
 * @author gustavo
 */
public interface ICommand {
    public Resultado execute(IEntidade entidade);
}
