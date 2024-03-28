package eaj.ufrn.br.trabalhopw.persistencia;

import eaj.ufrn.br.trabalhopw.dominio.Cliente;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public static void cadastrar(Cliente c) {

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement(
                    "insert into cliente (nome, email, senha) values (?,?,?)");

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getSenha());

            stmt.executeUpdate();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println("Connection Failed! Check output console\n" + ex.toString());
        }
    }

    public static Cliente clienteLogado(String email, String senha){
        Cliente cBusca = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = Conexao.getConnection();

            stmt = connection.prepareStatement("select * from cliente where email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);

            rs = stmt.executeQuery();

            if (rs.next()) {
                cBusca = new Cliente(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
            }

            connection.close();
        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
            System.out.println(ex.toString());
        }

        return cBusca;
    }

    public void deletar(Cliente c){

    }
    public void atualizar(Cliente c){

    }

}