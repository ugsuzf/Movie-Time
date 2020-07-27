import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    DBConnection(){

    }
    public  static DBConnection getInstance(){
        return  new DBConnection();
    }

    public Connection getConnection(){
        String connect_String = "jdbc:sqlite:data.db";
        Connection connection=null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection= DriverManager.getConnection(connect_String);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }



}
