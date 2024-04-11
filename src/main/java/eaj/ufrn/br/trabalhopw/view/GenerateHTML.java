package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Produto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
public class GenerateHTML {

    HttpServletResponse response;

    HttpServletRequest request;
    public GenerateHTML(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public void openHTML(String tituloPagina) throws IOException {
        var pagina = this.response.getWriter();
        pagina.println("<html> <head> <title>"+tituloPagina+"</title> </head> <body>");
    }

    public void closeHTML() throws IOException {
        var pagina = this.response.getWriter();
        pagina.println("</body> </html>");
    }

    public void generateForm(String [] labels, String [] ids, String buttonName, String action) throws IOException {
        var pagina = this.response.getWriter();

        pagina.println("<form action = \""+action+"\" method=\"post\">");
        for(int i=0; i < labels.length; i++){
            pagina.println("<label for=\""+labels[i]+"\">");
            pagina.println(labels[i] + ": ");
            pagina.println("<input type=\"text\" name=\""+ids[i]+"\" id=\""+ids[i]+"\">");
            pagina.println("</label>");
            pagina.println("<br>");
        }
        pagina.println("<br>");
        pagina.println("<input type=\"submit\" value=\""+buttonName+"\">");
        pagina.println("</form>");
    }

    public void generateTable(ArrayList<Produto> lista, String [] cabecalhos, String caption) throws IOException {
        var pagina = this.response.getWriter();
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            String tipoSessao = (String) sessao.getAttribute("tipo");

            pagina.println("<table>");
            pagina.println("<caption>" + caption + "</caption>");

            pagina.println("<thead>");
            for (String cabecalho : cabecalhos) {
                pagina.println("<th>" + cabecalho + "</th>");
            }

            if (tipoSessao.equals("cliente")) {
                pagina.println("<th>Carrinho</th>");
            }

            pagina.println("</thead>");

            pagina.println("<tbody>");
            for (Produto p : lista) {
                pagina.println("<tr>");
                pagina.println("<td>" + p.getNome() + "</td>");
                pagina.println("<td>" + p.getDescricao() + "</td>");
                pagina.println("<td>" + p.getPreco() + "</td>");
                pagina.println("<td>" + p.getEstoque() + "</td>");
                if(tipoSessao.equals("cliente")){
                    if(p.getEstoque() > 0){
                        pagina.println("<td><a href=\"/CarrinhoServlet?id="+p.getId()+"&comando=add\">Adicionar</a></td>");
                    }else{
                        pagina.println("<td>Sem Estoque</td>");
                    }
                }
                pagina.println("</tr>");
            }

            pagina.println("</tbody>");
            pagina.println("</table>");


            if(tipoSessao.equals("lojista"))
                pagina.println("<a href='/paginaCadProd'>Cadastrar Produto</a>");
            else
                pagina.println("<a href='/carrinho'>Ver Carrinho</a>");

            pagina.println("<a href='/deslogar'>Sair</a>");
        }
    }
}
