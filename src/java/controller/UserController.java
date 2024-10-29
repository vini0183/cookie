package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Usuarios;
import model.dao.UsuariosDAO;

@WebServlet(name = "UserController", urlPatterns = {"/UserController","/home","/login","/logar"})
public class UserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String paginaAtual = request.getServletPath();
        
        String nome = "";
        String sobrenome = "";
        String status = "";
        
        Cookie[] cookies = request.getCookies();
        
        for(Cookie c: cookies) {
            if(c.getName().equals("nome")) {
                nome = c.getValue();
            }
            if(c.getName().equals("sobrenome")) {
                sobrenome = c.getValue();
            }
            if(c.getName().equals("status")) {
                status = c.getValue();
            }
        }
        
        if(paginaAtual.equals("/home")) {
            
            request.setAttribute("nome", nome);
            
            request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
            
        } else if(paginaAtual.equals("/login")) {
            
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
                String paginaAtual = request.getServletPath();
                
                if(paginaAtual.equals("/logar")) {
                    String email = request.getParameter("email");
                    String senha = request.getParameter("senha");
                    
                    Usuarios user = new UsuariosDAO().logar(email, senha);
                    
                    if(user.getId() > 0) {
                        Cookie cNome = new Cookie("nome", user.getNome());
                        Cookie cSobrenome = new Cookie("sobrenome", user.getSobrenome());
                        Cookie cStatus = new Cookie("status", user.getStatus());
                        
                        response.addCookie(cNome);
                        response.addCookie(cSobrenome);
                        response.addCookie(cStatus);
                        
                        response.sendRedirect("./home");
                        
                    } else {
                        response.sendRedirect("./login");
                    }
                    
                }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
