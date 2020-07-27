import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class IzlenenlerController extends LoginController implements Initializable {
    private static DecimalFormat df2= new DecimalFormat("#.#");

    ObservableList<OnerilerController.Movies> list= FXCollections.observableArrayList();
    static private int movieId=0;
    static private int userId=0;
    @FXML
    private TableView<OnerilerController.Movies> tableview;

    @FXML
    private TableColumn<OnerilerController.Movies,String> name;
    @FXML
    private TableColumn<OnerilerController.Movies,Double> rate;
    @FXML
    private TableColumn<OnerilerController.Movies,String> myC;
    @FXML
    private TableColumn<OnerilerController.Movies,String> myR;
    @FXML
    private TableColumn<OnerilerController.Movies,Double> userRate;
    @FXML
    private MenuItem remove;
    @FXML
    private TableColumn<OnerilerController.Movies,String> info;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        remove.setOnAction(event -> {
            OnerilerController.Movies item= tableview.getSelectionModel().getSelectedItem();
            try {
                movieId=(getMovieId(item.title));
                userId=(getID(LoginController.getUsernam()));
                String quer="delete from watched_list where IdMovie=? and IdUsers=?";
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
        this.name.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        this.myR.setCellValueFactory(new PropertyValueFactory<>("myRate"));
        this.myC.setCellValueFactory(new PropertyValueFactory<>("myComment"));
        this.userRate.setCellValueFactory(new PropertyValueFactory<>("userRates"));
        this.info.setCellValueFactory(new PropertyValueFactory<>("info"));

    }


    private void loadData() throws SQLException {
        Double average = null;

        PreparedStatement pst =OnerilerController.connection.prepareStatement("select DISTINCT IdMovie,IdUsers,MyRate,Comment from watched_list where IdUsers = ?" );

        pst.setInt(1,getID(LoginController.getUsernam()));

        ResultSet resultSet=pst.executeQuery();
        int count = 0;
        while (resultSet.next()) {

            movieId = resultSet.getInt("IdMovie");
            PreparedStatement psmt = OnerilerController.connection.prepareStatement("select  * from Movies where  Id_Movie= ?");
            psmt.setInt(1, movieId);


            ResultSet resultSet1 = psmt.executeQuery();
            if (resultSet1.next()){

                String titlex=resultSet1.getString("MName");
                Double rate = resultSet1.getDouble("MRate");
                String date= resultSet1.getString("MPublishDAte");
                String gender =resultSet1.getString("MGender");
                String comme=resultSet.getString("Comment");
                String rat=resultSet.getString("MyRate");
                String dir= resultSet1.getString("MDirector");
                String act= resultSet1.getString("MActors");
                String info= resultSet1.getString("MInfo") ;
                PreparedStatement preparedStatement=OnerilerController.connection.prepareStatement("select AVG(MyRate) from watched_list where IdMovie = ?");
                preparedStatement.setInt(1,movieId);
                ResultSet avg=preparedStatement.executeQuery();
                if (avg.next()) {
                    average=(avg.getDouble("AVG(MyRate)"));
                    System.out.println(""+(++count)+"-"+average);
                    if (average==0)
                        average=rate;
                }


                list.add(new OnerilerController.Movies(titlex,rate,date,gender,rat,comme, average, info, dir, act));


            }

        }

        tableview.getItems().setAll(list);
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


    @FXML
    public void öneriler(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Oneriler.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
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

    @FXML
    public void news(ActionEvent e1) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(root);
        Stage login =(Stage)((Node)e1.getSource()).getScene().getWindow();
        login.setScene(scene);
        login.show();
        login.centerOnScreen();
    }
}
