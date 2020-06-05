import java.sql.*;

public class DatabaseConnector {
    private static String url="jdbc:mysql://localhost:3306/?user=root";
    private static String user="root";
    private static String password="Leomessi";

    public static Connection getConnection(){

        Connection myCon=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myCon = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return myCon;
    }
}
