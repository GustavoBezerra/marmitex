/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.les.marmitex.view.command.impl;

import com.les.marmitex.model.dominio.IEntidade;
import com.les.marmitex.model.dominio.Resultado;
import com.les.marmitex.view.command.ICommand;
import org.springframework.stereotype.Component;

/**
 *
 * @author gustavo
 */
@Component("CONSULTAR")
public class ConsultarCommand implements ICommand {

    @Override
    public Resultado execute(IEntidade entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
