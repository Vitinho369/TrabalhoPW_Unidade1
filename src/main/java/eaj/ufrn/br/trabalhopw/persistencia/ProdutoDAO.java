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
                produtos.add(new Produto(rs.getInt("id"), rs.getInt("id_lojista"), rs.getFloat("preco"), rs.getString("nome"),rs.getString("descricao"), rs.getInt(("estoque"))));
            }

            connection.close();
        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println(ex.toString());
        }

        return produtos;
    }
}
