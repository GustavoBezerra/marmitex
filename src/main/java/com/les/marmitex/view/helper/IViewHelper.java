package com.les.marmitex.view.helper;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gustavo
 */
public interface IViewHelper {

    public EntidadeDominio getEntidade(HttpServletRequest request);

    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
