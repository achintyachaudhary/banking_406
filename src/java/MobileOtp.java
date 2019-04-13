/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SACHIN KUMAR
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.mysql.jdbc.Driver;

class MobileOtp {

    public static String sendSMS(String otp, String username) {
        try {
            // Construct data
            //String apiKey = "apikey=" + "ZabeAFyoAtQ-KNvPAMeyQ7Ww5Jbii73ISlKvQcNypm\t";
            String apiKey = "apikey=" + "r6mTQHKuVmw-J5AflsM3MYASHLFPXuiSAwayDevqle";
            String message = "&message=" + "This is your otp " + otp;
            String sender = "&sender=" + "TXTLCL";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select mobile1 from account where uname='" + username + "'");
            rs.next();
            String mobileNumber = rs.getString("mobile1");

            String numbers = "&numbers=" + "91" + mobileNumber;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }
}
