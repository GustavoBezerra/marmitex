package com.les.marmitex.view.controller;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.command.ICommand;
import com.les.marmitex.view.command.impl.AlterarCommand;
import com.les.marmitex.view.command.impl.ConsultarCommand;
import com.les.marmitex.view.command.impl.ExcluirCommand;
import com.les.marmitex.view.command.impl.SalvarCommand;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    private static Map<String, ICommand> commands;

    private final AnnotationConfigApplicationContext context;

    public FrontControllerServlet() {
        // recupera as anotations
        context = new AnnotationConfigApplicationContext("com.les.marmitex");
        /* Utilizando o command para chamar a fachada e indexando cada command
    	 * pela operação garantimos que esta servelt atenderá qualquer operação */
        commands = new HashMap<String, ICommand>();

        commands.put("SALVAR", new SalvarCommand());
        commands.put("EXCLUIR", new ExcluirCommand());
        commands.put("CONSULTAR", new ConsultarCommand());
        commands.put("ALTERAR", new AlterarCommand());
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

        //Obtêm um viewhelper indexado pela uri que invocou esta servlet
        IViewHelper vh = (IViewHelper) context.getBean(uri);

        //O viewhelper retorna a entidade especifica para a tela que chamou esta servlet
        EntidadeDominio entidade = vh.getEntidade(request);

        //Obtém a operação executada
        String operacao = request.getParameter("operacao");

        // Recupera o command correspondente com a operacao
        ICommand command = commands.get(operacao);

        /*Executa o command que chamará a fachada para executar a operação requisitada
        * o retorno é uma instância da classe resultado que pode conter mensagens derro
        * ou entidades de retorno
        */
        Resultado resultado = command.execute(entidade);

        /*
        * Executa o método setView do view helper específico para definir como deverá ser apresentado
        * o resultado para o usuário
        */
        vh.setView(resultado, request, response);

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
