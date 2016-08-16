package com.les.marmitex.view.controller;

import com.les.marmitex.view.command.ICommand;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author gustavo
 */
public class FrontControllerServlet extends HttpServlet {
    
    private final AnnotationConfigApplicationContext context;
    
    public FrontControllerServlet(){
        // recupera as anotations
        context = new AnnotationConfigApplicationContext("com.les.marmitex");
    }

    
    /**
     * Redireciona as requições para as viewmodels 
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // para aceitar acentuacao
        request.setCharacterEncoding("UTF-8");
        
        //Obtém a uri que invocou esta servlet 
        String uri = request.getRequestURI();
        
        //Obtém a operação executada
        String operacao = request.getParameter("operacao");
        
        // Recupera o command correspondente com a operacao
        ICommand command = (ICommand) context.getBean(operacao);
                
        //Recebe a viewmodel mapeada com o endereço da uri
        IViewHelper vh = (IViewHelper)  context.getBean(uri);
        
        //Repassa a requisicao para a ViewModel responsavel        
        vh.tratarRequisicaoFrontController(request, response, command);
                
    }
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
