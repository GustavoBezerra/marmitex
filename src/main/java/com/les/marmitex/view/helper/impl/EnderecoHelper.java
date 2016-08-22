package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por entender a página do CRUD de endereços
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 21/08/2016
 */
@Component("/marmitex/endereco")
public class EnderecoHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        Endereco e = null;
        
        String cep;
        String cidade;
        String endereco;
        String numero;
        String complemento;
        String bairro;        

        if (("SALVAR").equals(operacao)) {
            cep = request.getParameter("cep");
            cidade = request.getParameter("cidade");
            endereco = request.getParameter("endereco");
            numero = request.getParameter("numero");
            complemento = request.getParameter("complemento");
            bairro = request.getParameter("bairro");            

            e = new Endereco();
            e.setId_cliente(1);
            e.setCep(cep);
            e.setCidade(cidade);
            e.setRua(endereco);
            e.setNumero(numero);
            e.setComplemento(complemento);
            e.setBairro(bairro);

        } else if (("CONSULTAR").equals(operacao)) {
            e = new Endereco();
            e.setId_cliente(1);
        } else if (("EXCLUIR").equals(operacao)) {
            e = new Endereco();
            e.setId(Integer.valueOf(request.getParameter("id")));
        } else if(("ALTERAR").equals(operacao)){
            cep = request.getParameter("cep");
            cidade = request.getParameter("cidade");
            endereco = request.getParameter("endereco");
            numero = request.getParameter("numero");
            complemento = request.getParameter("complemento");
            bairro = request.getParameter("bairro");
            String id = request.getParameter("id");

            e = new Endereco();
            e.setId(Integer.valueOf(id));
            e.setCep(cep);
            e.setCidade(cidade);
            e.setRua(endereco);
            e.setNumero(numero);
            e.setComplemento(complemento);
            e.setBairro(bairro);
        }

        return e;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        Gson gson = new Gson();

        if (("SALVAR").equals(operacao)) {

        }
        else if (("CONSULTAR").equals(operacao)) {
            retorno = gson.toJson(resultado.getEntidades());
            try {
                response.getWriter().write(retorno);
            } catch (IOException ex) {
                System.out.println("ERRO!");
            }
        }
        else if(("EXCLUIR").equals(operacao)){
            
        }
        else if(("ALTERAR").equals(operacao)){
            
        }
    }

}
