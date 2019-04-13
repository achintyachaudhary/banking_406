/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
@WebServlet(urlPatterns = {"/payment"})
public class payment extends HttpServlet {

    String username = "";
    String password = "";
    String type1 = "";
    Connection con = null;
    Statement st = null, st1 = null;
    ResultSet rs = null, rs1 = null;

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
            out.println("<title>Servlet payment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet payment at " + request.getContextPath() + "</h1>");
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

        String cfor = req.getParameter("ccpayment");
        String cam = req.getParameter("ccamount");
        String cname = req.getParameter("ccname");

        String cnum = req.getParameter("ccnumber");
        String cexp = req.getParameter("ccexp");
        String code = req.getParameter("code");
        String loc = req.getParameter("ccloc");

        HttpSession sn = req.getSession(true);
        // sn.setAttribute("username",username);
        String username1 = sn.getAttribute("username").toString();
        String mail_id = sn.getAttribute("mailid").toString();

        System.out.println("username : " + username);
        System.out.println("otp : " + password);
        System.out.println("type1 : " + type1);
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
//            rs.close();
            if (a < 1) {

                rs = st.executeQuery("select * from card where uname='" + cname + "' and card_='" + cnum + "' and valid='" + cexp + "' and pin='" + code + "' ");
                if (rs.next()) {

                    sn.setAttribute("cfor", cfor);
                    sn.setAttribute("cam", cam);
                    sn.setAttribute("cname", cname);
                    sn.setAttribute("cnum", cnum);
                    sn.setAttribute("cexp", cexp);
                    sn.setAttribute("code", code);
                    sn.setAttribute("mac", getMacAddress());
                    sn.setAttribute("ccloc", loc);
                    Random rnd = new Random();
                    int n = 1; // TODO: Change it back to Random.NextInt()
                    //int n1 = 100000 + rnd.nextInt(900000);
                    String n3 = String.valueOf(n);
                    sn.setAttribute("otp", n3);
                    MobileOtp.sendSMS(n3);

                    rd = req.getRequestDispatcher("otp.jsp");

                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Invalid Card Details');");
                    out.println("location='payment.jsp';");
                    out.println("</script>");
                    rd = req.getRequestDispatcher("payment.jsp");
                }
//                rs.close();
            } else {
                rs1 = st.executeQuery("select * from transaction where uname='" + cname + "' and mac1='" + getMacAddress() + "' and place1='" + loc + "' ");
                if (rs1.next()) {
                    rs = st.executeQuery("select * from card where uname='" + cname + "' and card_='" + cnum + "' and valid='" + cexp + "' and pin='" + code + "' ");
                    if (rs.next()) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        Random rnd = new Random();
                        int n = 100000 + rnd.nextInt(900000);
                        st1 = con.createStatement();
                        int add = st1.executeUpdate("insert into transaction  values('" + username1 + "','" + String.valueOf(n) + "','" + cfor + "','" + dtf.format(now) + "','" + cam + "','" + getMacAddress() + "','" + loc + "')");
                        rd = req.getRequestDispatcher("error.jsp");
                    } else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Invalid Card Details');");
                        out.println("location='payment.jsp';");
                        out.println("</script>");
                        rd = req.getRequestDispatcher("payment.jsp");
                    }
//                    rs.close();
                } else {
                    System.out.println("Variables: " + cfor + ", " + cam + ", " + cname + ", " + cexp + ", " + code + ", " + loc);

                    // <editor-fold desc="User Data Validation">
                    int validationCount = 0;

                    // <editor-fold desc="Amount Validation">
                    ResultSet amountResult = st.executeQuery("select * from transaction where uname='" + username1 + "' order by date1 desc limit 10");
                    float totalAmount = 0;
                    int counter = 0;
                    while (amountResult.next()) {
                        totalAmount += Float.parseFloat(amountResult.getString("amount1"));
                        counter += 1;
                    }
//                    amountResult.close();
                    float parsedAmount = Float.parseFloat(cam);

                    float average = totalAmount / counter;
                    if (parsedAmount > average * 1.25f) {
                        System.out.println("Transaction is too large. OTP required");
                    } else {
                        validationCount += 2;
                    }
                    // <editor-fold>

                    // <editor-fold desc="Site Validation">
                    ResultSet siteResult = st.executeQuery("select * from transaction where uname='" + username1 + "' order by date1 desc");
                    String currentSite = cfor;
                    boolean siteFound = false;
                    while (siteResult.next()) {
                        String oldSite = siteResult.getString("foe_");
                        if (oldSite.equals(currentSite)) {
                            siteFound = true;
                            break;
                        }
                    }
//                    siteResult.close();

                    if (!siteFound) {
                        System.out.println("Invlaid Site. Otp required");
                    } else {
                        validationCount += 1;
                    }

                    // <editor-fold>
                    // <editor-fold desc="Location Validation">
                    ResultSet locationResult = st.executeQuery("select * from transaction where uname='" + username1 + "' order by date1 desc");
                    String currentLocation = loc;
                    boolean locationExists = false;

                    while (locationResult.next()) {
                        String prevLocation = locationResult.getString("place1");
                        if (prevLocation.equals(currentLocation)) {
                            locationExists = true;
                            break;
                        }
                    }
//                    locationResult.close();

                    if (!locationExists) {
                        System.out.println("Invlaid Location. Otp required");
                    } else {
                        validationCount += 1;
                    }

                    // <editor-fold>
                    // <editor-fold desc="MAC Validation">
                    ResultSet macResult = st.executeQuery("select * from transaction where uname='" + username1 + "' order by date1 desc");
                    String currentMac = getMacAddress();
                    boolean macExists = false;

                    while (macResult.next()) {
                        String prevMac = locationResult.getString("mac1");
                        if (prevMac.equals(currentMac)) {
                            macExists = true;
                            break;
                        }
                    }
//                    macResult.close();

                    if (!macExists) {
                        System.out.println("Invlaid Location. Otp required");
                    } else {
                        validationCount += 2;
                    }

                    // <editor-fold>
                    float probality = validationCount / 6.0f;
                    if (probality < 0.5f) {
                        System.out.println("Probality is < 0.5f. OTP required");

                        Random rnd = new Random();
                        int n = 1000 + rnd.nextInt(9000);
                        String n3 = String.valueOf(n);

                        sn.setAttribute("cfor", cfor);
                        sn.setAttribute("cam", cam);
                        sn.setAttribute("cname", cname);
                        sn.setAttribute("cnum", cnum);
                        sn.setAttribute("cexp", cexp);
                        sn.setAttribute("code", code);
                        sn.setAttribute("mac", getMacAddress());
                        sn.setAttribute("ccloc", loc);
                        sn.setAttribute("otp", n3);

                        MobileOtp.sendSMS(n3);
                        rd = req.getRequestDispatcher("otp.jsp");
                        rd.forward(req, res);
                        return;
                    } else if (probality == 0.5f) {
                        System.out.println("Probality is 0.5. OTP?");

                        if (!locationExists && !macExists) {
                            System.out.println("Probality is 0.5. OTP required");
                            Random rnd = new Random();
                            int n = 1000 + rnd.nextInt(9000);
                            String n3 = String.valueOf(n);

                            sn.setAttribute("cfor", cfor);
                            sn.setAttribute("cam", cam);
                            sn.setAttribute("cname", cname);
                            sn.setAttribute("cnum", cnum);
                            sn.setAttribute("cexp", cexp);
                            sn.setAttribute("code", code);
                            sn.setAttribute("mac", getMacAddress());
                            sn.setAttribute("ccloc", loc);
                            sn.setAttribute("otp", n3);

                            MobileOtp.sendSMS(n3);
                            rd = req.getRequestDispatcher("otp.jsp");
                            rd.forward(req, res);
                            return;
                        }
                    }

                    // <editor-fold>
                    rs = st.executeQuery("select * from card where uname='" + cname + "' and card_='" + cnum + "' and valid='" + cexp + "' and pin='" + code + "' ");
                    if (rs.next()) {
                        sn.setAttribute("cfor", cfor);
                        sn.setAttribute("cam", cam);
                        sn.setAttribute("cname", cname);
                        sn.setAttribute("cnum", cnum);
                        sn.setAttribute("cexp", cexp);
                        sn.setAttribute("code", code);
                        sn.setAttribute("mac", getMacAddress());
                        sn.setAttribute("ccloc", loc);
                        Random rnd = new Random();
                        int n = 1000 + rnd.nextInt(9000);
                        //int n1 = 100000 + rnd.nextInt(900000);
                        String n3 = String.valueOf(n);
                        sn.setAttribute("otp", n3);
                        MobileOtp.sendSMS(n3);
                        rd = req.getRequestDispatcher("otp.jsp");
                    } else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Invalid Card Details');");
                        out.println("location='payment.jsp';");
                        out.println("</script>");
                        rd = req.getRequestDispatcher("payment.jsp");
                    }
//                    rs.close();
                }
            }
//            rs1.close();
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

    public static String getMacAddress() throws UnknownHostException,
            SocketException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface
                .getByInetAddress(ipAddress);
        byte[] macAddressBytes = networkInterface.getHardwareAddress();
        StringBuilder macAddressBuilder = new StringBuilder();

        for (int macAddressByteIndex = 0; macAddressByteIndex < macAddressBytes.length; macAddressByteIndex++) {
            String macAddressHexByte = String.format("%02X",
                    macAddressBytes[macAddressByteIndex]);
            macAddressBuilder.append(macAddressHexByte);

            if (macAddressByteIndex != macAddressBytes.length - 1) {
                macAddressBuilder.append(":");
            }
        }

        return macAddressBuilder.toString();
    }
}
