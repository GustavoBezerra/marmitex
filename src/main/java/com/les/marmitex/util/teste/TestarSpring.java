package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.IDAO;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.EntidadeDominio;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 21/08/2016
 */
public class TestarSpring {
    
    private AnnotationConfigApplicationContext daos = null;
    

    public TestarSpring() {
        daos = new AnnotationConfigApplicationContext("com.les.marmitex.core.dao");        
    }    

    public IDAO retornaDAO(EntidadeDominio e){
        return (IDAO) daos.getBean(e.getClass().getName());
    }
}
