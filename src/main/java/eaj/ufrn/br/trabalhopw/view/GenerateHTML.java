package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GenerateHTML {

    HttpServletResponse response;

    HttpServletRequest request;
    public GenerateHTML(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public void openHTML(String tituloPagina) throws IOException {
        var pagina = this.response.getWriter();
        pagina.println("<html> <head> <title>Lista Produtos</title> " +
                "<link rel='stylesheet' type='text/css' href='./styleTable.css' " +
                "</head> <body>");
    }

    public void closeHTML() throws IOException {
        var pagina = this.response.getWriter();
        pagina.println("</body> </html>");
    }

    public void generateTable(ArrayList<Produto> lista, ArrayList<String> cabecalhos, String caption) throws IOException {
        var pagina = this.response.getWriter();
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        HttpSession sessao = request.getSession(false);

        String tipoSessao = (String) sessao.getAttribute("tipo");

        pagina.println("<table>");
        pagina.println("<caption>"+caption+"</caption>");

        pagina.println("<thead>");
        for(String cabecalho : cabecalhos){
            pagina.println("<th>"+cabecalho+"</th>");
        }

        if(tipoSessao.equals("cliente")){
            pagina.println("<th>Carrinho</th>");
        }

        pagina.println("</thead>");

        pagina.println("<tbody>");
        for(Produto p : lista){
            pagina.println("<tr>");
            pagina.println("<td>" + p.getNome() + "</td>");
            pagina.println("<td>" + p.getDescricao() + "</td>");
            pagina.println("<td>" + p.getPreco() + "</td>");
            pagina.println("<td>" + p.getEstoque() + "</td>");
            pagina.println("</tr>");
        }



        pagina.println("</tbody>");
        pagina.println("</table>");
    }
}
