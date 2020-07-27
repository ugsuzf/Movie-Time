import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private WebView viewweb;

    @FXML
    private Button oneriler;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final WebEngine web = viewweb.getEngine();
        String urlweb="https://www.imdb.com/news/top";
        web.load(urlweb);
    }
    @FXML
    public void öneriler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Oneriler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.centerOnScreen();
        login.show();

    }
    @FXML
    public void izlenenler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Izlenenler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();

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
    public void listem(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Listem.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
    }

    @FXML
    public void beklenenler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Beklenenler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
    }

    @FXML
    public void favoriler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Favoriler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
    }

    @FXML
    public void ayarlar(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ayarlar.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
    }
}
