/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.les.marmitex.view.helper.impl;

import com.les.marmitex.view.command.ICommand;
import com.les.marmitex.view.helper.IViewHelper;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void tratarRequisicaoFrontController(HttpServletRequest request, HttpServletResponse response, ICommand comando) {
        // TODO: Fazer aqui o inicio da validação do login
        try {
            response.getWriter().append("Served at: ").append(request.getContextPath());
        } catch (IOException ex) {
            Logger.getLogger(LoginHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
