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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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
@WebServlet(urlPatterns = {"/otp"})
public class otp extends HttpServlet {

    String username = "";
    String password = "";
    String type1 = "";
    Connection con = null;
    Statement st, st1 = null;
    ResultSet rs = null;

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
            out.println("<title>Servlet otp</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet otp at " + request.getContextPath() + "</h1>");
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
        int a = 0;
        //username = req.getParameter("uname");
        // password = req.getParameter("pwd");

        HttpSession sn = req.getSession(true);
        String cfor = sn.getAttribute("cfor").toString();
        String cam = sn.getAttribute("cam").toString();
        String cname = sn.getAttribute("cname").toString();
        String cnum = sn.getAttribute("cnum").toString();
        String cexp = sn.getAttribute("cexp").toString();
        String code = sn.getAttribute("code").toString();
        String mac = sn.getAttribute("mac").toString();
        String loc = sn.getAttribute("ccloc").toString();
        // sn.setAttribute("username",username);
        String username1 = sn.getAttribute("username").toString();
        String pt = req.getParameter("otp");
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        RequestDispatcher rd = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
            st = con.createStatement();
            rs = st.executeQuery("select count(*) from transaction where uname='" + username1 + "'");
            if (rs.next()) {
                a = Integer.parseInt(rs.getString(1));
               // sn.setAttribute("balance",rs.getString(12));
                // sn.setAttribute("type",rs.getString(11));
                //  rd=req.getRequestDispatcher("balance.jsp");

                //sn.setAttribute("dpm",department);
            }
            if (a < 1) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                rs = st.executeQuery("select * from card where uname='" + cname + "' and card_='" + cnum + "' and valid='" + cexp + "' and pin='" + code + "' ");
                if (rs.next()) {

                    String pt1 = rs.getString(5).toString();
                    if (pt.contains(pt1)) {
                        System.out.println("Updating Amount in user database");
                        st1 = con.createStatement();
                        int add = st1.executeUpdate("insert into transaction  values('" + username1 + "','" + String.valueOf(n) + "','" + cfor + "','" + dtf.format(now) + "','" + cam + "','" + mac + "','" + loc + "')");
                        ResultSet queryResult = st1.executeQuery("select amount from account where uname='" + username1 + "'");
                        queryResult.next();
                        float amount = Float.parseFloat(queryResult.getString("amount"));
                        System.out.println(amount);
                        amount -= Float.parseFloat(cam);
                        System.out.println(amount);
                        st1.executeUpdate("update account set amount='" + String.valueOf(amount) + "' where uname='" + username1 + "'");
                        rd = req.getRequestDispatcher("error.jsp");
                    } else {
                        rd = req.getRequestDispatcher("error1.jsp");
                    }

                }
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));
                System.out.println("Updating User amount");

                rs = st.executeQuery("select * from card where uname='" + cname + "' and card_='" + cnum + "' and valid='" + cexp + "' and pin='" + code + "' ");
                if (rs.next()) {

                    String pt1 = rs.getString(5).toString();
                    if (pt.contains(pt1)) {
                        System.out.println("Updating Amount in user database");
                        st1 = con.createStatement();
                        int add = st1.executeUpdate("insert into transaction  values('" + username1 + "','" + String.valueOf(n) + "','" + cfor + "','" + dtf.format(now) + "','" + cam + "','" + mac + "','" + loc + "')");
                        ResultSet queryResult = st1.executeQuery("select amount from account where uname='" + username1 + "'");
                        queryResult.next();
                        float amount = Float.parseFloat(queryResult.getString("amount"));
                        System.out.println(amount);
                        amount -= Float.parseFloat(cam);
                        System.out.println(amount);
                        st1.executeUpdate("update account set amount='" + String.valueOf(amount) + "' where uname='" + username1 + "'");
                        rd = req.getRequestDispatcher("error.jsp");
                    } else {
                        rd = req.getRequestDispatcher("error1.jsp");
                    }

                }
            }

            rd.forward(req, res);
        } catch (Exception e2) {
            System.out.println("Exception : " + e2.toString());
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
