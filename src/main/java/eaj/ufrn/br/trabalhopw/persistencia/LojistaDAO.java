package eaj.ufrn.br.trabalhopw.persistencia;

import eaj.ufrn.br.trabalhopw.dominio.Cliente;
import eaj.ufrn.br.trabalhopw.dominio.Lojista;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LojistaDAO {


    public static Lojista lojistaLogado(String email, String senha){
        Lojista lBusca = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement("select * from lojista where email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);

            rs = stmt.executeQuery();

            if (rs.next()) {
                lBusca = new Lojista(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
            }

            connection.close();
        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println(ex.toString());
        }

        return lBusca;
    }
}
