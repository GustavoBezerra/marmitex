/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.les.marmitex.view.command.impl;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import org.springframework.stereotype.Component;

/**
 *
 * @author gustavo
 */
@Component("CONSULTAR")
public class ConsultarCommand extends AbstractCommand {

    @Override
    public Resultado execute(EntidadeDominio entidade) {
        return fachada.consultar(entidade);
    }
    
}
