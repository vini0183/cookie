package model.dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.bean.Usuarios;

public class UsuariosDAO {
    
    public Usuarios logar (String email, String senha) {
        Usuarios user = new Usuarios();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement("select * from usuarios where email = ? and senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setSobrenome(rs.getString("sobrenome"));
                user.setStatus(rs.getString("status"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
}
