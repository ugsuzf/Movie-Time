import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddFavoritesController implements Initializable {
    @FXML
    private Button add;

    @FXML
    private TextField Mrate;
    @FXML
    private TextField Mcomment;
    private static String comment;
    private static String rate ;

    public AddFavoritesController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    private void addButton(ActionEvent event){

        String sql = "INSERT INTO  favorites( IdMovie,IdUser,Comment,MyRate) VALUES(?,?,?,?)";
        try (

                PreparedStatement preparedStatement = OnerilerController.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, OnerilerController.getMovieId());
            preparedStatement.setInt(2, OnerilerController.getUserId());
            preparedStatement.setString(3,Mcomment.getText());
            preparedStatement.setString(4,Mrate.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            preparedStatement.executeUpdate();

//                alert.setHeaderText("Add this movie: " +item.getTitle());
//                alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");
//                Optional<ButtonType> result = alert.showAndWait();
//                if(result.isPresent() && (result.get() == ButtonType.OK)) {
//                    pstmt.executeUpdate();
//                    alert.setTitle("Add to watched list");
//                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
