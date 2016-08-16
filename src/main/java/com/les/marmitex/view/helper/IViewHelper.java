package com.les.marmitex.view.helper;

import com.les.marmitex.view.command.ICommand;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gustavo
 */
public interface IViewHelper {
    public void tratarRequisicaoFrontController(HttpServletRequest request, HttpServletResponse response, ICommand comando);
}
