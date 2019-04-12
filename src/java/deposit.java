/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author Satheeshkumar S
 */
@WebServlet(urlPatterns = {"/deposit"})
public class deposit extends HttpServlet {
String username="";
    String password="";
    String type1="";
    Connection con=null;
    Statement st=null,st1=null;
    ResultSet rs=null;
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
            out.println("<title>Servlet deposit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deposit at " + request.getContextPath() + "</h1>");
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
         username = req.getParameter("cc-payment");
      //  password = req.getParameter("pwd");
	//type1 = req.getParameter("type1");
	HttpSession sn = req.getSession(true);
     //sn.setAttribute("username",username);
      
      String username1= sn.getAttribute("username").toString();
      System.out.println("username : " +username);
      System.out.println("otp : " +password);
      System.out.println("type1 : " +type1);
		RequestDispatcher rd = null;
        try {
             Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
            st1 = con.createStatement();
            rs = st1.executeQuery("select amount from account where uname='"+username1+"'");
            if(rs.next()) {
                String aa=rs.getString(1);
                String bal=String.valueOf(Integer.parseInt(aa)+Integer.parseInt(username));
               int  add=st1.executeUpdate("update account set amount='"+bal+"' where uname='"+username1+"'");
          
                sn.setAttribute("balance",bal);
                rd=req.getRequestDispatcher("balance.jsp");
                
				//sn.setAttribute("dpm",department);
            } else {

               rd=req.getRequestDispatcher("failure.jsp");
	        }
	     rd.forward(req,res);
        }
        catch(Exception e2)
         {
            System.out.println("Exception : "+e2.toString());
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
