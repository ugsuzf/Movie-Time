import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AyarlarController implements Initializable {

    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setText(LoginController.getName());
        surname.setText(LoginController.getSurname());
        username.setText(LoginController.getUsernam());
        password.setText(LoginController.getPass());
    }
    @FXML
    public void news(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
    }

    @FXML
    public void öneriler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Oneriler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();

    }
    @FXML
    public void izlenenler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Izlenenler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();

    }

    @FXML
    public void listem(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Listem.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();

    }



    @FXML
    public void favoriler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Favoriler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();

    }

    @FXML
    public void ayarlar(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ayarlar.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        System.out.println("ooooo");
    }
    @FXML
    public void save(ActionEvent e) throws SQLException {
        String newName=name.getText();
        String newSurname=surname.getText();
        String newUsername=username.getText();
        String newPassword=password.getText();
        Connection connection;
        PreparedStatement psmt = OnerilerController.connection.prepareStatement("select username from users where username =?");
        psmt.setString(1,newUsername);
        ResultSet rs= psmt.executeQuery();
        int count=0;
        while (rs.next()){
            count++;
        }
        if (count>1){
            Alert alert=new Alert(AlertType.WARNING);
            alert.setContentText("Please try another username");
            alert.setHeaderText("Username already taken");
            alert.showAndWait();
        }
        else{
            PreparedStatement preparedStatement= OnerilerController.connection.prepareStatement("update users set name=?,surname=?,username=?,password=? where id=?");
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,newSurname);
            preparedStatement.setString(3,newUsername);
            preparedStatement.setString(4,newPassword);
            preparedStatement.setInt(5, LoginController.getId());
            preparedStatement.executeUpdate();
            System.out.println(LoginController.getId());
            System.out.println(newPassword);
            Alert.AlertType alertAlertType;
            Alert alert=new Alert(AlertType.INFORMATION);
            alert.setTitle("Settings");
            alert.setContentText("Changes saved");
            alert.showAndWait();
        }






    }
}
