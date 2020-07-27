import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FavorilerController implements Initializable {
    ObservableList<OnerilerController.Movies> list= FXCollections.observableArrayList();

    @FXML
    TableView<OnerilerController.Movies> tableview;
    @FXML
    TableColumn<OnerilerController.Movies,String>title;
    @FXML
    TableColumn<OnerilerController.Movies,String>myRate;
    @FXML
    TableColumn<OnerilerController.Movies,String> myComment;
    @FXML
    TableColumn<OnerilerController.Movies,Double>userRate;
    @FXML
    TableColumn<OnerilerController.Movies,String> info;
    @FXML
    private MenuItem remove;


    static private int movieId=0;
    static private int userId=0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        remove.setOnAction(event -> {
            OnerilerController.Movies item= tableview.getSelectionModel().getSelectedItem();
            try {
                movieId=(getMovieId(item.title));
                userId=(getID(LoginController.getUsernam()));
                String quer="delete from favorites where IdMovie=? and IdUser=?";
                PreparedStatement preparedStatement=OnerilerController.connection.prepareStatement(quer);
                preparedStatement.setInt(1,movieId);
                preparedStatement.setInt(2,userId);
                preparedStatement.executeUpdate();

                list.clear();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        initCol();

        try {

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCol() {
        this.title.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.myRate.setCellValueFactory(new PropertyValueFactory<>("myRate"));
        this.myComment.setCellValueFactory(new PropertyValueFactory<>("myComment"));
        this.userRate.setCellValueFactory(new PropertyValueFactory<>("userRates"));
        this.info.setCellValueFactory(new PropertyValueFactory<>("info"));
    }
    int getID(String username) throws SQLException {
        PreparedStatement pst =OnerilerController.connection.prepareStatement("select * from users where username = ?");
        pst.setString(1,username);
        ResultSet resultSet=pst.executeQuery();
        if (resultSet.next())
            return resultSet.getInt("id");
        return 0;

    }
    int getMovieId(String movieName) throws SQLException {
        PreparedStatement pst =OnerilerController.connection.prepareStatement("select * from Movies where MName = ?");
        pst.setString(1,movieName);
        ResultSet resultSet=pst.executeQuery();
        if (resultSet.next())
            return resultSet.getInt("Id_Movie");
        return 0;
    }




    private void loadData() throws SQLException {
        int movieId;
        Double average = null;
        PreparedStatement pst = OnerilerController.connection.prepareStatement("select DISTINCT IdMovie,IdUser,MyRate,Comment from favorites where IdUser = ?");
        pst.setInt(1, LoginController.getId());
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            movieId = resultSet.getInt("IdMovie");
            PreparedStatement psmt = OnerilerController.connection.prepareStatement("select * from Movies where  Id_Movie= ?");
            psmt.setInt(1, movieId);
            System.out.println(movieId);

            ResultSet resultSet1 = psmt.executeQuery();
            if (resultSet1.next()) {

                String titlex = resultSet1.getString("MName");
                Double rate = resultSet1.getDouble("MRate");
                String date = resultSet1.getString("MPublishDAte");
                String gender = resultSet1.getString("MGender");
                String comme = resultSet.getString("Comment");
                String rat = resultSet.getString("MyRate");
                String dir= resultSet1.getString("MDirector");
                String act= resultSet1.getString("MActors");
                String info= resultSet1.getString("MInfo");
                PreparedStatement preparedStatement= OnerilerController.connection.prepareStatement("select AVG(MyRate) from watched_list where IdMovie = ?");
                preparedStatement.setInt(1,movieId);
                ResultSet avg=preparedStatement.executeQuery();
                if (avg.next()) {
                    average=(avg.getDouble("AVG(MyRate)"));
                    if (average==0)
                        average=rate;

                }
                list.add(new OnerilerController.Movies(titlex,rate,date,gender,rat,comme, average, info, dir, act));

            }


        }
        tableview.getItems().setAll(list);
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
        System.out.println("ooooo");
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

    }
}
