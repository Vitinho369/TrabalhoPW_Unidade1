package eaj.ufrn.br.trabalhopw.dominio;

import eaj.ufrn.br.trabalhopw.persistencia.ClienteDAO;

import java.sql.SQLIntegrityConstraintViolationException;

public class  Cliente extends Usuario {

    public Cliente(){
        super();
    }

    public Cliente(String nome, String email, String senha){
        super(nome,email,senha);
    }

    public static Cliente clienteLogin(String email, String senha){
        return ClienteDAO.clienteLogado(email,senha);
    }
}