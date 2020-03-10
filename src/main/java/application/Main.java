package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dbController.DbConnection;

import java.sql.Connection;


public class Main extends Application{

    private Parent root;
    Connection connection;

    @Override
    public void start(Stage stage)throws Exception{
        root = FXMLLoader.load(getClass().getResource("/scene/sceneWelcome.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Astronomy Application");

        this.connection = DbConnection.getConnection();

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) { launch(args); }
}
