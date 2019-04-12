
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


@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {
    String username="";
    String password="";
    String type1="";
    Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
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
        username = req.getParameter("uname");
        password = req.getParameter("pwd");
	//type1 = req.getParameter("type1");
	HttpSession sn = req.getSession(true);
        sn.setAttribute("username",username);
      
      
      System.out.println("username : " +username);
      System.out.println("otp : " +password);
      System.out.println("type1 : " +type1);
		RequestDispatcher rd = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
            st = con.createStatement();
            rs = st.executeQuery("select * from account where uname='"+username+"' && pwd='"+password+"'");
            if(rs.next()) {
                sn.setAttribute("balance",rs.getString(12));
                sn.setAttribute("type",rs.getString(11));
                sn.setAttribute("mailid",rs.getString(3));
                rd=req.getRequestDispatcher("balance.jsp");
                
				
            } else {

               rd=req.getRequestDispatcher("login.jsp");
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
    }
}
