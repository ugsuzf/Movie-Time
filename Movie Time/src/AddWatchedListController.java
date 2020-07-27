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

public class AddWatchedListController implements Initializable {
    @FXML
    private Button add;
    @FXML
    private TextField Mrate;
    @FXML
    private TextField Mcomment;
    private static String comment;
    private static String rate ;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    private void addButton(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO  watched_list( IdMovie,IdUsers,Comment,MyRate) VALUES(?,?,?,?)";

        try  {
            PreparedStatement pstmt =OnerilerController.connection.prepareStatement(sql);
            pstmt.setInt(1, OnerilerController.getMovieId());
            pstmt.setInt(2, OnerilerController.getUserId());
            pstmt.setString(3,Mcomment.getText());
            pstmt.setString(4,Mrate.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            pstmt.executeUpdate();
            pstmt.close();



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
