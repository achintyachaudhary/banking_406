/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.random;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
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
@WebServlet(urlPatterns = {"/reg1"})
public class reg1 extends HttpServlet {
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
            out.println("<title>Servlet reg1</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet reg1 at " + request.getContextPath() + "</h1>");
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
         String pan= req.getParameter("pan");
            String aadhar= req.getParameter("aadhar");
          String mobile= req.getParameter("mobile");
           
        	String type= req.getParameter("ac_type");
        	
		
               // String type1= "user";
        	RequestDispatcher rd;
                HttpSession sn = req.getSession(true);
                
    String username1= sn.getAttribute("username").toString();
   String email=   sn.getAttribute("email").toString();	
     String pwd= sn.getAttribute("pwd").toString();
    String name=  sn.getAttribute("name").toString();	
     String address= sn.getAttribute("address").toString();
     String gender= sn.getAttribute("gender").toString();
     sn.setAttribute("balance","0");
            try
            {
		Random rnd = new Random();
                int n = 100000 + rnd.nextInt(900000);
                int n1 = 100000 + rnd.nextInt(900000);
                String n3=String.valueOf(n)+String.valueOf(n1);
                String card=String.format("%04d", rnd.nextInt(10000))+String.format("%04d", rnd.nextInt(10000))+String.format("%04d", rnd.nextInt(10000))+String.format("%04d", rnd.nextInt(10000));
           String pin = String.format("%04d", rnd.nextInt(10000));
                Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
            st = con.createStatement();
            int add=st.executeUpdate("insert into account values('"+n3+"','"+username1+"','"+email+"','"+pwd+"','"+name+"','"+address+"','"+gender+"','"+pan+"','"+aadhar+"','"+mobile+"','"+type+"','0')");
          add=st.executeUpdate("insert into card values('"+username1+"','"+card+"','02/25','"+pin+"','"+getSaltString()+"')");
          
// st1 = con.createStatement();
           // int add1=st1.executeUpdate("insert into usersec values('"+username+"','"+sb.toString()+"','"+dec1.mod(bi)+"')");
          
            
            rd=req.getRequestDispatcher("balance.jsp");
            rd.forward(req,res);
        } catch(Exception e2) {
             rd=req.getRequestDispatcher("failure.jsp");
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
public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
