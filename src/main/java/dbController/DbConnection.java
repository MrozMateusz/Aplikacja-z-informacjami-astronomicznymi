package dbController;

import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {
    private static final String USERNAME = "c##HR";
    private static final String PASSWORD = "hr";
    private static final String SQCONN = "jdbc:oracle:thin:@localhost:1521:xe";


    public static java.sql.Connection getConnection() {

        try{
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(SQCONN, USERNAME, PASSWORD);


        }catch (SQLException ex) {
            System.out.println("Cos poszlo nie tak: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
