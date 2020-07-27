import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static String usernam;
    private static String name;
    private static String surname;
    private static String pass;
    private static Integer id;
    @FXML
    private Button signup;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField username;
    @FXML
    private Button login;
    @FXML
    private PasswordField password;
    @FXML
    private ImageView progress;

    private DBConnection handler;
    private Connection connection;
    private PreparedStatement pst;

    public static Integer getId() {
        return id;
    }

    public static void setId(Integer id) {
        LoginController.id = id;
    }


    @FXML
    public void loginAction(ActionEvent e) throws IOException {


//        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
//        Scene scene = new Scene(root);
//        Stage login =(Stage)((Node)e.getSource()).getScene().getWindow();
//        login.setScene(scene);
//        login.show();
        connection=handler.getConnection();
        String q1="SELECT * FROM users where username=? and password=?";
        try {
            pst= connection.prepareStatement(q1);
            pst.setString(1,username.getText());
            pst.setString(2,password.getText());
            ResultSet rs= pst.executeQuery();




            int count=0;

            while (rs.next()){
                count++;
                this.name=rs.getString("name");
                this.surname=rs.getString("surname");
                this.pass=rs.getString("password");
                this.usernam=rs.getString("username");
                this.id=rs.getInt("id");
            }
            if (count==1){
                Stage home= new Stage();
                try {
                    /*Parent root= FXMLLoader.load(getClass().getResource("MainPage.fxml"));//yeni pencerede acılmasını istersen
                    Scene scene= new Scene(root);
                    home.setScene(scene);
                    home.show();
                    */

                    Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                    Scene scene = new Scene(root);
                    Stage login =(Stage)((Node)e.getSource()).getScene().getWindow();
                    login.setScene(scene);
                    login.centerOnScreen();
                    login.show();

                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }else {
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong");
                alert.setHeaderText(null);
                alert.setContentText("Username and password is not correct");
                alert.show();



            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        finally {
            try {
                connection.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    public void signUp(ActionEvent e1) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene root1=new Scene(root);
        Stage window =(Stage)((Node)e1.getSource()).getScene().getWindow();
        window.setScene(root1);
        window.show();



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler= new DBConnection();
        File file=new File("C:\\Users\\Padawan\\Downloads\\camera.png");
        InputStream is;
        Image image =new Image(file.toURI().toString());
        progress.setImage(image);
    }

    public static String getUsernam() {
        return usernam;
    }

    public static void setUsernam(String usernam) {
        LoginController.usernam = usernam;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LoginController.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        LoginController.surname = surname;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        LoginController.pass = pass;
    }
}
