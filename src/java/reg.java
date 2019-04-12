/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Satheeshkumar S
 */
@WebServlet(urlPatterns = {"/reg"})
public class reg extends HttpServlet {
Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    Statement st1=null;
    RequestDispatcher rd=null;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet reg</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet reg at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
          
            String username= req.getParameter("username");
          String password= req.getParameter("password");
           
        	String name= req.getParameter("name1");
        	
		
		String emailid1= req.getParameter("email");
                String address= req.getParameter("address");
                     String gender= req.getParameter("gender");
                     RequestDispatcher rd;
             
                     if(gender.equals("0"))
                     {
                         rd=req.getRequestDispatcher("register.jsp");
            rd.forward(req,res);
                     }
               else
                     {
               // String type1= "user";
        	

	try {
		HttpSession sn = req.getSession(true);
     sn.setAttribute("username",username);
      sn.setAttribute("email",emailid1);	
      sn.setAttribute("pwd",password);
      sn.setAttribute("name",name);	
      sn.setAttribute("address",address);
      sn.setAttribute("gender",gender
      
      
      );	
            rd=req.getRequestDispatcher("nextpage.jsp");
            rd.forward(req,res);
        } catch(Exception e2) {
            // rd=req.getRequestDispatcher("failure.jsp");
            rd=req.getRequestDispatcher("register.jsp");
            rd.forward(req,res);
        }
                     }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
