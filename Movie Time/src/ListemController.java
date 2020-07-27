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
import java.util.Optional;
import java.util.ResourceBundle;

public class ListemController extends LoginController implements Initializable {

    ObservableList<OnerilerController.Movies> list= FXCollections.observableArrayList();
    private int movieId=0;
    private int userId=0;
    @FXML
    private TableView<OnerilerController.Movies> tableview;
    @FXML
    private TableColumn<OnerilerController.Movies,String> name;
    @FXML
    private TableColumn<OnerilerController.Movies,Double> rate;
    @FXML
    private TableColumn<OnerilerController.Movies,String> publishdate;
    @FXML
    private TableColumn<OnerilerController.Movies,String> info;
    @FXML
    private MenuItem delete;
    @FXML
    private TableColumn<Double,String> userRate;

    @FXML
    private MenuItem addWatched;
    @FXML
    private MenuItem addFavorite;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delete.setOnAction((event)->{

            OnerilerController.Movies item = tableview.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Todo Item");
            alert.setHeaderText("Delete item: " + item.getTitle());
            alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");
            Optional<ButtonType> result = alert.showAndWait();
            try {
                int movieId=getMovieId(item.getTitle());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                String sql = "DELETE FROM watch_list WHERE IdMovie=? and IdUser=?";
                try {

                    PreparedStatement preparedStatement= OnerilerController.connection.prepareStatement(sql);
                    preparedStatement.setInt(1,movieId);
                    preparedStatement.setInt(2,getID(LoginController.getUsernam()));
                    if(result.isPresent() && (result.get() == ButtonType.OK)) {
                        preparedStatement.executeUpdate();
                        alert.setTitle("Add to watched list");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
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
        this.publishdate.setCellValueFactory(new PropertyValueFactory<>("publishdate"));
        this.info.setCellValueFactory(new PropertyValueFactory<>("info"));

    }
    private void loadData() throws SQLException {


        PreparedStatement pst =OnerilerController.connection.prepareStatement("select DISTINCT IdMovie,IdUser from watch_list where IdUser = ?");
        pst.setInt(1,getID(LoginController.getUsernam()));
        ResultSet resultSet=pst.executeQuery();
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
                String dir= resultSet1.getString("MDirector");
                String act= resultSet1.getString("MActors");
                String inf =resultSet1.getString("MInfo");

                list.add(new OnerilerController.Movies(titlex,rate,date,gender,null,null, null, inf, dir, act));
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


}
