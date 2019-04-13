
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

@WebServlet(urlPatterns = {"/reg"})
public class reg extends HttpServlet {
Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    Statement st1=null;
    RequestDispatcher rd=null;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
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
            rd=req.getRequestDispatcher("register.jsp");
            rd.forward(req,res);
        }
                     }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
