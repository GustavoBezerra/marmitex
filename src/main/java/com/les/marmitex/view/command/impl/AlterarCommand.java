/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.les.marmitex.view.command.impl;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.command.ICommand;

/**
 *
 * @author gustavo
 */
public class AlterarCommand extends AbstractCommand {

    @Override
    public Resultado execute(EntidadeDominio entidade) {
        return fachada.alterar(entidade);
    }
    
}
