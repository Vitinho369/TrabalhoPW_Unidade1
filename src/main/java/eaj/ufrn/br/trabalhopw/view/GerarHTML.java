package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Carrinho;
import eaj.ufrn.br.trabalhopw.dominio.Produto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
public class GerarHTML {

    private HttpServletResponse response;

    private HttpServletRequest request;

    public GerarHTML(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public void abrirHTML(String tituloPagina) throws IOException {
        var pagina = this.response.getWriter();
        pagina.println("<html> <head> <title>"+tituloPagina+"</title> </head> <body>");
    }

    public void fecharHTML() throws IOException {
        var pagina = this.response.getWriter();
        pagina.println("</body> </html>");
    }

    public void gerarLink(String link, String textoLink) throws IOException{
        var pagina = this.response.getWriter();
        pagina.println("<a href="+link+">" + textoLink+ "</a>");
    }

    public void gerarForm(String [] labels, String [] ids, String buttonName, String action) throws IOException {
        var pagina = this.response.getWriter();

        pagina.println("<form action = \""+action+"\" method=\"post\">");
        for(int i=0; i < labels.length; i++){
            if(ids[i].equals("senha")){
                pagina.println(labels[i] + ": ");
                pagina.println("<input type=\"password\" name=\""+ids[i]+"\" id=\""+ids[i]+"\" required>");
            }else{
                pagina.println("<label for=\"" + ids[i] + "\">" + labels[i] + ": </label>");

                pagina.println("<input type=\"text\" name=\"" + ids[i] + "\" id=\"" + ids[i] + "\">");
            }
            pagina.println("<br>");

        }
        pagina.println("<br>");
        pagina.println("<input type=\"submit\" value=\""+buttonName+"\">");
        pagina.println("</form>");
    }

    public void gerarTabelaCarrinho(Carrinho carrinho, String [] cabecalhos, String caption) throws IOException {
        var pagina = this.response.getWriter();

        if(carrinho.getProdutos() == null || carrinho.getProdutos().isEmpty()){
            response.sendRedirect("/LojaOnline");
        }
        pagina.println("<table border=1>");
        pagina.println("<caption>" + caption + "</caption>");

        pagina.println("<thead>");
        for (String cabecalho : cabecalhos) {
            pagina.println("<th>" + cabecalho + "</th>");
        }
        pagina.println("</thead>");

        pagina.println("<tbody>");
        for (Produto produto : carrinho.getProdutos()) {
            pagina.println("<tr>");
            pagina.println("<td>" + produto.getNome() + "</td>");
            pagina.println("<td>" + produto.getDescricao() + "</td>");
            pagina.println("<td>" + produto.getPreco() + "</td>");
            pagina.println("<td>" + produto.getEstoque() + "</td>");
            pagina.println("<td><a href=\"/LojaOnline/CarrinhoServlet?id="+produto.getId()+"&comando=remove\">Remover</a></td>");
            pagina.println("</tr>");
        }
        pagina.println("</tbody>");
        pagina.println("</table>");

        pagina.println("<a href=\"/LojaOnline\">Ver Produtos</a>");
        pagina.println("<a href=\"/FinalizaCompra\">Finalizar Compra</a>");

    }
    public void gerarTabelaProdutos(ArrayList<Produto> lista, String [] cabecalhos, String caption, Carrinho carrinho) throws IOException {
        var pagina = this.response.getWriter();
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            String tipoSessao = (String) sessao.getAttribute("tipo");

            pagina.println("<table border=1>");
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
                int estoque = p.getEstoque();
                pagina.println("<tr>");
                pagina.println("<td>" + p.getNome() + "</td>");
                pagina.println("<td>" + p.getDescricao() + "</td>");
                pagina.println("<td>" + p.getPreco() + "</td>");

                if(carrinho != null) {
                    for (Produto pCarrinho : carrinho.getProdutos()) {
                        if (pCarrinho.getId() == p.getId())
                            estoque -= pCarrinho.getEstoque();
                    }
                }

                pagina.println("<td>" + estoque + "</td>");

                if(tipoSessao.equals("cliente")){
                    if(estoque > 0){
                        pagina.println("<td><a href=\"/LojaOnline/CarrinhoServlet?id="+p.getId()+"&comando=add\">Adicionar</a></td>");
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
                pagina.println("<a href='/Carrinho'>Ver Carrinho</a>");

            pagina.println("<a href='/deslogar'>Sair</a>");
        }
    }
}
