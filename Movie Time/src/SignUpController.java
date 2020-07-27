import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button login;

    @FXML
    private TextField tname;

    @FXML
    private TextField tsurname;

    @FXML
    private TextField tusername;

    @FXML
    private PasswordField tpassword;

    @FXML
    private Button signup;

    @FXML
    private ImageView progress;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void signUp(ActionEvent e1) throws IOException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String name =tname.getText();
        String surname=tsurname.getText();
        String username= tusername.getText();
        String password= tpassword.getText();

        insert(name,surname,username,password);
        System.out.println("signed up");

//            Statement statement= connection.createStatement();
//            int status= statement.executeUpdate("INSERT INTO users (username,name,surname,password) VALUES (" + username + "," + name + "," + surname + "," + password +");");
//            int status= statement.executeUpdate("insert into users(name,surname,username,password)"+" values(' "+name+","+surname+","+username+","+password+"')");
//
//        if (status>0){
//                System.out.println("user registered");
//            }
        Parent root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();



    }
    @FXML
    public void loginAction(ActionEvent e1) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        /*
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene root1=new Scene(root);
        Stage window =(Stage)((Node)e1.getSource()).getScene().getWindow();
        window.setScene(root1);
        window.show();
         */
    }
    public void insert(String username, String name, String surname,String password) {
        String sql = "INSERT INTO users( name,surname,username,password) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3,surname);
            pstmt.setString(4,password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        conn = DBConnection.getInstance().getConnection();
        return conn;
    }
}
