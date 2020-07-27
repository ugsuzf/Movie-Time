import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {

    Connection connection = DBConnection.getInstance().getConnection();
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.centerOnScreen();
        Parent root = FXMLLoader.load(getClass().getResource("LoginMain.fxml"));
        primaryStage.setTitle("Movie Time");
            primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException, SQLException {
        launch(args);
//        String insert="insert into Movies(MName,MGender,MRate,MPublishDate,MDirector,MActors,MInfo) values(?,?,?,?,?,?,?)";
//        PreparedStatement preparedStatement=OnerilerController.connection.prepareStatement(insert);
//        Document document = Jsoup.connect("https://www.imdb.com/search/title/?genres=action&sort=user_rating,desc&title_type=feature&num_votes=25000,&pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=5aab685f-35eb-40f3-95f7-c53f09d542c3&pf_rd_r=DA4GPRJCDMWWCES6X0X6&pf_rd_s=right-6&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_gnr_1").get();
//        Elements body = document.select("div.lister-list");
//
//        int x = 0;
//        for (Element e : body.select("div.lister-item-content")) {
//            String title = e.select("h3.lister-item-header a").text();
//            String publishYear = e.select("h3.lister-item-header span").text();
//            String genre = e.select("span.genre").text();
//            Double rate = Double.valueOf(e.select("div.ratings-bar strong").text());
//            String info = e.select("p.text-muted").text();
//            String director = e.select("p ").text();
//            String stars = e.select("p ").text();
//            publishYear = publishYear.substring((publishYear.indexOf("(")) + 1, publishYear.indexOf(")"));
//            info = info.substring((info.indexOf(genre)) + genre.length() + 1);
//            director = director.substring((director.indexOf("Director:")) + 10, (director.indexOf("| Stars")));
//            stars = stars.substring(stars.indexOf("Stars:") + 7, stars.indexOf("Votes"));
//            System.out.println(title);
//            System.out.println(publishYear);
//            System.out.println(genre);
//            System.out.println(rate);
//            System.out.println(info);
//            System.out.println(director);
//            System.out.println(stars);
//
//            preparedStatement.setString(1,title);
//            preparedStatement.setString(2,genre);
//            preparedStatement.setDouble(3,rate);
//            preparedStatement.setString(4,publishYear);
//            preparedStatement.setString(5,director);
//            preparedStatement.setString(6,stars);
//            preparedStatement.setString(7,info);
//            preparedStatement.executeUpdate();
//            System.out.println("done "+(++x));
//        }
    }
}
