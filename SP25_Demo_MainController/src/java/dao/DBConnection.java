package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dbURL =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=Sp25_DemoPRJ;"
          + "encrypt=true;"
          + "trustServerCertificate=true;";   
    public static String userDB = "sa";      
    public static String passDB = "Matkhau123@"; 

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        return DriverManager.getConnection(dbURL, userDB, passDB);
    }

    // Test kết nối
    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            if (con != null) {
                System.out.println(" Connect to Sp25_DemoPRJ Success");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
