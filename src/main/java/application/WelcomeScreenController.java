package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class WelcomeScreenController {

    @FXML
    private void Sat(ActionEvent event) throws IOException {
        Parent SatelitParent = FXMLLoader.load(getClass().getResource("/scene/sceneSatellities.fxml"));

        Scene SatelitScene = new Scene(SatelitParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(SatelitScene);
        window.show();
    }

    @FXML
    private void Planet(ActionEvent event)throws IOException{
        Parent PlanetsParent = FXMLLoader.load(getClass().getResource("/scene/scenePlanets.fxml"));

        Scene PlanetsScene = new Scene(PlanetsParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(PlanetsScene);
        window.show();
    }
}
