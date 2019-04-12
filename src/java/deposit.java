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


@WebServlet(urlPatterns = {"/deposit"})
public class deposit extends HttpServlet {
    String amount_added="";
    String password="";
    String type1="";
    Connection con=null;
    Statement st=null,st1=null;
    ResultSet rs=null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
         amount_added = req.getParameter("cc-payment");
	HttpSession sn = req.getSession(true);

      
      String username1= sn.getAttribute("username").toString();
      System.out.println("username : " +amount_added);
      System.out.println("otp : " +password);
      System.out.println("type1 : " +type1);
		RequestDispatcher rd = null;
        try {
             Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
            st1 = con.createStatement();
            rs = st1.executeQuery("select amount from account where uname='"+username1+"'");
            if(rs.next()) {
                Float aa=Float.parseFloat(rs.getString(1));
                String bal=String.valueOf(aa + Float.parseFloat(amount_added));
               int  add=st1.executeUpdate("update account set amount='"+bal+"' where uname='"+username1+"'");
          
                sn.setAttribute("balance",bal);
                amount_added = "";
                res.sendRedirect("balance.jsp");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
