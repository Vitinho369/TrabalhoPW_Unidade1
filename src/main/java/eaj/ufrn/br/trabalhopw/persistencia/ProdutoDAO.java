package eaj.ufrn.br.trabalhopw.persistencia;

import eaj.ufrn.br.trabalhopw.dominio.Lojista;
import eaj.ufrn.br.trabalhopw.dominio.Produto;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {

    public  static void Cadastrar_Produto(Produto p){
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement(
                    "insert into produto (nome,preco, estoque,descricao) values (?,?,?,?)");

            stmt.setString(1, p.getNome());
            stmt.setFloat(2, p.getPreco());
            stmt.setInt(3, p.getEstoque());
            stmt.setString(4,p.getDescricao());

            stmt.execute();
            stmt.close();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println("Connection Failed! Check output console\n" + ex.toString());
        }
    }
    public static ArrayList<Produto> listarProdutos(){
        ArrayList<Produto> produtos = new ArrayList<Produto>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement("select * from produto");
            rs = stmt.executeQuery();

            while (rs.next()) {
                produtos.add(new Produto(rs.getInt("id"), rs.getFloat("preco"), rs.getString("nome"),rs.getString("descricao"), rs.getInt(("estoque"))));
            }

            connection.close();
        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println(ex.toString());
        }

        return produtos;
    }

    public static Produto buscarProduto(int id){
        Produto produto = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement("select * from produto where id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(rs.getInt("id"), rs.getFloat("preco"), rs.getString("nome"),rs.getString("descricao"), rs.getInt(("estoque")));
            }

            connection.close();
        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println(ex.toString());
        }

        return produto;
    }
    public static void atualizarEstoque(int id,int estoque){
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement("update produto set estoque = ? where id = ?");
            stmt.setInt(1, estoque);
            stmt.setInt(2,id);
            stmt.execute();
            connection.close();
        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println(ex.toString());
        }
    }

}
