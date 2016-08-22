/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.les.marmitex.view.helper.impl;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.command.ICommand;
import com.les.marmitex.view.helper.IViewHelper;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author gustavo
 */
@Component("/marmitex/login")
public class LoginHelper implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
