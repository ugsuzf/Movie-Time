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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class OnerilerController extends LoginController implements Initializable  {
    ObservableList<Movies> list= FXCollections.observableArrayList();

    ObservableList<String> directsList = FXCollections.observableArrayList();
    ObservableList<String> starList = FXCollections.observableArrayList();

    @FXML
    private TableView<Movies> tableview;
    @FXML
    private TableColumn<Movies,String> name;
    @FXML
    private TableColumn<Movies,Double> rate;
    @FXML
    private TableColumn<Movies,String> publishdate;
    @FXML
    private TableColumn<Movies,String> genre;
    @FXML
    private TableColumn<Movies,String> director;
    @FXML
    private TableColumn<Movies,String> stars;
    @FXML
    private TableColumn<Movies,String> information;
    @FXML
    private MenuItem delete;
    @FXML
    private MenuItem addWatch;
    @FXML
    private MenuItem addWatchedList;
    @FXML
    private MenuItem addFavorite;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ComboBox dirSearch;
    @FXML
    private ComboBox starSearch;


    private DBConnection handler;
    private static int movieId=0;
    private static int userId=0;
    static Connection connection = DBConnection.getInstance().getConnection();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addFavorite.setOnAction(event -> {
            Movies item = tableview.getSelectionModel().getSelectedItem();
            try {
                movieId=(getMovieId(item.title));
                userId=(getID(LoginController.getUsernam()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddFavorites.fxml"));
            Parent root= null;
            try {

                root = (Parent) fxmlLoader.load();
                Stage stage=new Stage();
                stage.setTitle("Add Favorites");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException  e) {
                e.printStackTrace();
            }
        });
        addWatch.setOnAction((event) -> {
            Movies item = tableview.getSelectionModel().getSelectedItem();
            try {
                movieId=(getMovieId(item.title));
                userId=(getID(LoginController.getUsernam()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql = "INSERT INTO  watch_list( IdMovie,IdUser) VALUES(?,?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, movieId);
                pstmt.setInt(2,userId); ;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setHeaderText("Add this movie: " +item.getTitle());
                alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && (result.get() == ButtonType.OK)) {
                    pstmt.executeUpdate();
                    alert.setTitle("Add to watch list");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }






        });
        addWatchedList.setOnAction((event) -> {
            Movies item = tableview.getSelectionModel().getSelectedItem();
            try {
                movieId=(getMovieId(item.title));
                userId=(getID(LoginController.getUsernam()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddWatchedList.fxml"));
            Parent root= null;
            try {

                root = (Parent) fxmlLoader.load();
                Stage stage=new Stage();
                stage.setTitle("asad");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException  e) {
                e.printStackTrace();
            }
        });


        initCol();

        try {

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        starSearch.getSelectionModel().select("-Select-");
        dirSearch.getSelectionModel().select("-Select-");



    }
    int getID(String username) throws SQLException {
        PreparedStatement pst =connection.prepareStatement("select * from users where username = ?");
        pst.setString(1,username);
        ResultSet resultSet=pst.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;


    }
    int getMovieId(String movieName) throws SQLException {

        PreparedStatement pst =connection.prepareStatement("select * from Movies where MName = ?");
        pst.setString(1,movieName);
        ResultSet resultSet=pst.executeQuery();
        if (resultSet.next())
            return resultSet.getInt("Id_Movie");
        return 0;
    }

    private void loadData() throws SQLException {

        String qu= "Select * from Movies";
        ResultSet resultSet=connection.createStatement().executeQuery(qu);


        try {
            while (resultSet.next()){
                String titlex=resultSet.getString("MName");
                Double rate = resultSet.getDouble("MRate");
                String date= resultSet.getString("MPublishDAte");
                String gender =resultSet.getString("MGender");
                String dir= resultSet.getString("MDirector");
                String act= resultSet.getString("MActors");
                String inf=resultSet.getString("MInfo");

                directsList.add(dir);
                String temp[]=act.split(",");
                for (String e :temp){

                    starList.add(e.replaceAll("\\s+"," "));

                }
                list.add(new Movies(titlex,rate,date,gender,null,null, null, inf, dir ,act ));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        Set<String> set =new HashSet<>(starList);
        starList.clear();
        starList.addAll(set);
        Collections.sort(starList);
        starList.add(0,"-Select-");
        starSearch.setItems(starList);
        tableview.getItems().setAll(list);
        set.clear();
        set.addAll(directsList);
        directsList.clear();
        directsList.addAll(set);
        Collections.sort(directsList);
        directsList.add(0,"-Select-");

        dirSearch.setItems(directsList);
        tableview.getItems().setAll(list);
        starSearch.getSelectionModel().select("-Select-");
        dirSearch.getSelectionModel().select("-Select-");




    }

    private void initCol() {
         this.name.setCellValueFactory(new PropertyValueFactory<>("title"));
         this.rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
         this.publishdate.setCellValueFactory(new PropertyValueFactory<>("publishdate"));
         this.director.setCellValueFactory(new PropertyValueFactory<>("director"));
         this.stars.setCellValueFactory(new PropertyValueFactory<>("stars"));
         this.genre.setCellValueFactory(new PropertyValueFactory<>("gender"));
         this.information.setCellValueFactory(new PropertyValueFactory<>("info"));


    }

    public static  class  Movies{
        public final String title;
        private final Double rate;
        private final String publishdate;
        private final String gender;
        private final String myRate;
        private final  String myComment;
        private  final Double userRates;

        public String getInfo() {
            return info;
        }

        private  final String info;

        public String getDirector() {
            return director;
        }

        public String getStars() {
            return stars;
        }

        private final String director;
        private final String stars;

        public Movies(String title, Double rate, String publishdate, String gender, String myRate, String myComment, Double userRate, String info, String director, String stars) {
            this.title = title;
            this.rate = rate;
            this.gender=gender;
            this.publishdate=publishdate;
            this.myComment=myComment;
            this.myRate=myRate;
            this.userRates = userRate;
            this.info = info;
            this.director = director;
            this.stars = stars;
        }

        public String getTitle() {
            return title;
        }

        public double getRate() {
            return rate;
        }

        public String getPublishdate() {
            return publishdate;
        }

        public String getGender() {
            return gender;
        }

        public String getMyComment() {
            return myComment;
        }

        public String getMyRate() {
            return myRate;
        }
        public Double getUserRates() {
            return userRates;
        }

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
        login.show();login.centerOnScreen();
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



    public static int getMovieId() {
        return movieId;
    }

    public static int getUserId() {
        return userId;
    }
    @FXML
    public void search(ActionEvent e) throws SQLException {



        String query= "select * from Movies";
        PreparedStatement preparedStatement= connection.prepareStatement(query);
        if (starSearch.getValue().equals("-Select-")&&dirSearch.getValue().equals("-Select-")) {
            System.out.println("You should choose");

            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("You should choose at least one of them");
            alert.setHeaderText(null);
            alert.show();
        }
        else if (starSearch.getValue() != null && dirSearch.getValue().equals("-Select-")){
            list.clear();
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString("MName");
                Double rate = resultSet.getDouble("MRate");
                String date = resultSet.getString("MPublishDAte");
                String gender = resultSet.getString("MGender");
                String dir= resultSet.getString("MDirector");
                String act= resultSet.getString("MActors");
                String inf=resultSet.getString("MInfo");
                if (act.contains((CharSequence) starSearch.getValue())){
                    list.add(new OnerilerController.Movies(title,rate,date,gender,null,null, null, inf, dir, act));
                }
            }
            tableview.getItems().setAll(list);
            resultSet.close();
        }
        else if (starSearch.getValue().equals("-Select-")&& dirSearch.getValue()!=null){
            list.clear();
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString("MName");
                Double rate = resultSet.getDouble("MRate");
                String date = resultSet.getString("MPublishDAte");
                String gender = resultSet.getString("MGender");
                String dir= resultSet.getString("MDirector");
                String act= resultSet.getString("MActors");
                String info=resultSet.getString("MInfo");
                if (dir.contains((CharSequence) dirSearch.getValue())){
                    list.add(new OnerilerController.Movies(title,rate,date,gender,null,null, null, info, dir, act));
                }
            }
            tableview.getItems().setAll(list);
            resultSet.close();
        }
        else {
            list.clear();
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String title = resultSet.getString("MName");
                Double rate = resultSet.getDouble("MRate");
                String date = resultSet.getString("MPublishDAte");
                String gender = resultSet.getString("MGender");
                String dir= resultSet.getString("MDirector");
                String act= resultSet.getString("MActors");
                String info=resultSet.getString("MInfo");
                if (act.contains((CharSequence) starSearch.getValue())&&dir.contains((CharSequence) dirSearch.getValue())){
                    list.add(new OnerilerController.Movies(title,rate,date,gender,null,null, null, info, dir, act));
                }
            }
            tableview.getItems().setAll(list);
            resultSet.close();
        }
        preparedStatement.close();

    }
    @FXML
    private void clear(ActionEvent event) throws SQLException {
        list.clear();
        loadData();
    }
}



