package Planets;
import Net.NetController;
import application.Archive_position_planet;
import application.CSVCreator;
import dbController.DbConnection;
import dbController.dbQuery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class PlanetsScreenController {

    dbQuery dbQuery = new dbQuery();
    Connection connection;
    CSVCreator csvCreator = new CSVCreator();
    NetController netController = new NetController();
    Timeline timeline = new Timeline();


    @FXML
    Button GTS;

    @FXML
    AnchorPane changeScreentoSatellities;

    @FXML
    Button select;
    @FXML
    Button GOTO;
    @FXML
    Button GOTODF;
    @FXML
    Button help;


    @FXML
    private ChoiceBox<String> choiceBox ;

    @FXML
    TableView<Archive_position_planet> tableView;

    @FXML
    TableColumn<Archive_position_planet, Integer> id;
    @FXML
    TableColumn<Archive_position_planet, String> name;
    @FXML
    TableColumn<Archive_position_planet, String> typ;
    @FXML
    TableColumn<Archive_position_planet, String> temp;
    @FXML
    TableColumn<Archive_position_planet, String> dat;
    @FXML
    TableColumn<Archive_position_planet, String> mass;
    @FXML
    TableColumn<Archive_position_planet, String> CS;
    @FXML
    TableColumn<Archive_position_planet, String> dec;

    @FXML
    Text helpText;
    @FXML
    TextField TextFieldHelp;
    @FXML
    Button help1;
    @FXML
    Button help2;
    @FXML
    Button help3;
    @FXML
    Button help4;

    @FXML
    TextField TextField1;
    @FXML
    TextField TextField2;
    @FXML
    TextField TextField3;
    @FXML
    TextField TextField4;
    @FXML
    TextField TextField5;
    @FXML
    TextField TextField6;
    @FXML
    TextField TextField7;
    @FXML
    TextField TextField8;
    @FXML
    TextField TextField9;
    @FXML
    TextField TextField10;
    @FXML
    TextField TextFieldError;
    @FXML
    Text Text;
    @FXML
    Text Text1;
    @FXML
    Text Text2;
    @FXML
    Text Text3;
    @FXML
    Text Text4;
    @FXML
    Text Text5;
    @FXML
    Text Text6;
    @FXML
    Text Text7;
    @FXML
    Text Text8;
    @FXML
    Text Text9;
    @FXML
    Button buttonADD;
    @FXML
    Button buttonALT;
    @FXML
    Button buttonALTPos;
    @FXML
    Button buttonDEL;
    @FXML
    Button Ok;
    @FXML
    Button Cancel;
    @FXML
    Button All;
    @FXML
    Button buttonCSV;
    @FXML
    Button buttonCSVSelected;
    @FXML
    Button buttonCSVtoDatab;
    @FXML
    Button helpclos;
    @FXML
    CheckBox checkbox;
    @FXML
    CheckBox checkboxCSV;
    @FXML
    Button net;

    Timestamp dataFromString;
    Timestamp zmTimestamp;

    int idPlanet;
    int idZmienna;
    int zmId;
    Boolean on = true;
    public String namezm = null;
    int m = 0;
    int whichclicked = 0;

    //Operacja ok
    int SelectOperation = 0;

    @FXML
    private void initialize() throws IOException, SQLException {

        loadAllPlanetToList();
        choicePlanet();
        tableView.getSortOrder().add(id);
    }

    @FXML
    private void changeScreentoSatellities(ActionEvent event) throws IOException {

        Parent SatelitParent = FXMLLoader.load(getClass().getResource("/scene/sceneSatellities.fxml"));

        Scene SatelitScene = new Scene(SatelitParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(SatelitScene);
        window.show();
    }

    @FXML
    private void setHelp1(){whichclicked = 1; Help();}
    @FXML
    private void setHelp2(){whichclicked = 2; Help();}
    @FXML
    private void setHelp3(){whichclicked = 3; Help();}
    @FXML
    private void setHelp4(){whichclicked = 4; Help();}

    @FXML
    private void Help(){

        helpclos.setVisible(true);
        if(whichclicked == 1){

            helpText.setVisible(true);
            TextFieldHelp.setVisible(true);
            TextFieldHelp.setText("Temperature (°C)- it's mean Temperature on the planet.");

        }else if(whichclicked == 2){
            helpText.setVisible(true);
            TextFieldHelp.setVisible(true);
            TextFieldHelp.setText("Distance from Earth - its distance from Earh core to another planet's core.");
        }else if(whichclicked == 3){
            helpText.setVisible(true);
            TextFieldHelp.setVisible(true);
            TextFieldHelp.setText("Mass - it's shows how much weight have planets.");
        }else if(whichclicked == 4) {
            helpText.setVisible(true);
            TextFieldHelp.setVisible(true);
            TextFieldHelp.setText("Declination - angular distance north or south from the celestial equator measured along a great circle passing through the celestial poles. Celestial equator ring around Earth's equator.");
        }
    }

    @FXML
    private void closehelp(){
        helpText.setVisible(false);
        TextFieldHelp.setVisible(false);
        helpclos.setVisible(false);
        whichclicked = 0;
    }

    @FXML
    private void AllPlanet()throws IOException, SQLException{
        tableView.getItems().clear();
        loadAllPlanetToList();
        TextFieldError.setText("Ok");
    }

    @FXML
    private void loadAllPlanetToList() throws IOException, SQLException{

        ObservableList<Archive_position_planet> archive_position_planetsObservableList = FXCollections.observableArrayList();

        AutoBlock();
        m=1;
        this.connection = DbConnection.getConnection();

        try{

            archive_position_planetsObservableList.addAll(dbQuery.GetUniqueArchDatePlanet());

        } catch (SQLException e) {
            System.out.println("Error:"+ e);
            e.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        typ.setCellValueFactory(new PropertyValueFactory<>("type"));
        temp.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        dat.setCellValueFactory(new PropertyValueFactory<>("data"));
        mass.setCellValueFactory(new PropertyValueFactory<>("mass"));
        CS.setCellValueFactory(new PropertyValueFactory<>("centerStar"));
        dec.setCellValueFactory(new PropertyValueFactory<>("declination"));

        tableView.getItems().addAll(archive_position_planetsObservableList);
        tableView.getSortOrder().add(id);
        tableView.getSelectionModel().select(0);
        Selected();
    }


    @FXML
    private void loadList() throws IOException, SQLException{
        AutoBlock();
        m=1;
        ObservableList<Archive_position_planet> archive_position_planetsObservableList = FXCollections.observableArrayList();

        ArrayList<Integer> integerArrayList = new ArrayList<>();

        namezm = choiceBox.getValue();
        integerArrayList = dbQuery.GetPlanetId(namezm);

        this.connection = DbConnection.getConnection();

        try{

            for(int i = 0; i < integerArrayList.size(); i++){
                idZmienna = integerArrayList.get(i);
                archive_position_planetsObservableList.addAll(dbQuery.GetArchPlanetWhereId(idZmienna));
            }
            TextFieldError.setText("Ok");
        } catch (SQLException e) {
            System.out.println("Error:"+ e);
            e.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        typ.setCellValueFactory(new PropertyValueFactory<>("type"));
        temp.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        dat.setCellValueFactory(new PropertyValueFactory<>("data"));
        mass.setCellValueFactory(new PropertyValueFactory<>("mass"));
        CS.setCellValueFactory(new PropertyValueFactory<>("centerStar"));
        dec.setCellValueFactory(new PropertyValueFactory<>("declination"));

        tableView.getItems().addAll(archive_position_planetsObservableList);
        tableView.getSortOrder().add(id);
        tableView.getSelectionModel().select(0);
        Selected();
    }

    @FXML
    private void refreshTable(ActionEvent event)throws IOException, SQLException{
        if(choiceBox.getValue() == null && TextField1.getText().isEmpty()){
            TextFieldError.setText("Can't do that!");
        }else{
            TextFieldError.setText("");
            tableView.getItems().clear();
            loadList();
        }

    }

    @FXML
    private void AutoSaveCSV(){
        if (checkboxCSV.isSelected()) {

                timeline = new Timeline(new KeyFrame(Duration.minutes(20), e -> {
                    try {
                       CSVButtonALL();
                    } catch (Exception h) {
                        h.printStackTrace();
                    }
                }));
                timeline.play();
        } else {

            timeline.stop();

        }
    }

    @FXML
    private void AutoUpd(){

        blockbutton();
        on = false;
        if (TextField4.getText() == "") {

            TextFieldError.setText("Choose planet to update!");
        } else {
            if (checkboxCSV.isSelected()) {

                timeline = new Timeline(new KeyFrame(Duration.hours(2), e -> {

                    try {
                        netPlanet();
                    } catch (Exception h) {
                        h.printStackTrace();
                    }
                }));
                timeline.play();

            } else {


                timeline.stop();

                unblockbutton();
                on = true;
            }

        }
    }

    @FXML
    private void AutoBlock(){
        if(m==1) {
            blockbutton();
            buttonALT.setDisable(true);
            net.setDisable(true);
            checkbox.setDisable(true);
            TextFieldError.setText("Wait!");
            on = false;
            timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
                unblockbutton();
                buttonALT.setDisable(false);
                net.setDisable(false);
                on = true;
                checkbox.setDisable(false);

                m = 2;
            }));
            timeline.play();
        }else if(m == 2){
            timeline.stop();
            TextFieldError.setText("Done!");
            m = 0;
        }
    }

    @FXML
    private void choicePlanet()throws IOException {
        this.connection = DbConnection.getConnection();

        AutoBlock();
        m=1;

        ObservableList<String> PlaNameList = FXCollections.observableArrayList();
        try {

            PlaNameList.addAll(dbQuery.GetPlanetName());

            ObservableList<String> plaNameListWithoutRepeats = FXCollections.observableArrayList(new HashSet<>(PlaNameList));

            choiceBox.getItems().addAll(plaNameListWithoutRepeats);

            namezm = choiceBox.getValue();

            TextFieldError.setText("Ok");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void deleteRow(ActionEvent event)throws IOException, SQLException{

        if(tableView.getSelectionModel().getSelectedItem() == null) {
            TextFieldError.setText("Nothing is clicked!");
        }else {
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
            TextFieldError.setText("");
            dbQuery.deleteArchPlanet(zmId);
            dbQuery.DeletePlanet(zmId);

            choiceBox.getItems().clear();

            choicePlanet();
            TextFieldError.setText("Ok");
        }

    }

    @FXML
    private void setButtonADD(ActionEvent event)throws SQLException{

        setEditable();
        ValWSKAll();
        ValWSKSel();
        blockbutton();
        on = false;
        Cancel.setDisable(false);
        TextField9.setText(Integer.toString(dbQuery.getNewestIDPlan()));
        dataFromString = Localtime();
        if (tableView.getSelectionModel().isEmpty()) {
            TextField8.setText(" °  '  ''");
        }
        TextField5.setText(dataFromString.toString());
        TextFieldError.setText("Add new planet!");
        SelectOperation = 1;
    }

    @FXML
    private void setButtonALT(ActionEvent event){

        if(tableView.getSelectionModel().isEmpty()){
            buttonALT.setDisable(true);
            TextFieldError.setText("Choose cell to edit!");
        }else{
            buttonALT.setDisable(false);
            setEditable();
            ValWSKAll();
            ValWSKSel();
            blockbutton();
            on = false;
            Cancel.setDisable(false);

            TextFieldError.setText("Edit values!");
            SelectOperation = 2;
        }
    }

    @FXML
    private void setButtonALTValue(ActionEvent event)throws SQLException{

        if(tableView.getSelectionModel().isEmpty()){
            buttonALT.setDisable(true);
            TextFieldError.setText("Choose cell to edit!");
        }else{
            buttonALT.setDisable(false);
            setSelEditable();
            ValWSKSel();
            blockbutton();
            on = false;
            TextField9.setText(Integer.toString(dbQuery.getNewestIDPlan()));
            dataFromString = Localtime();
            TextField5.setText(dataFromString.toString());
            Cancel.setDisable(false);

            TextFieldError.setText("Edit values!");
            SelectOperation = 3;
        }
    }

    @FXML
    private void GoURLHyperlinkPL(ActionEvent event)throws Exception{

        String url = "https://theskylive.com/";
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("/usr/bin/chrome -new-window " + url);
        }
        TextFieldError.setText("OK!");
    }


    @FXML
    private void netPlanet() throws IOException, SQLException{

        if(TextField4.getText() == ""){

            TextFieldError.setText("Choose planet to update!");
        }else {
            String s = TextField4.getText();
            TextFieldError.setText("Wait! Download!");
            blockbutton();
            on = false;
            Cancel.setDisable(false);

            dataFromString = Localtime();
            TextField9.setText(Integer.toString(dbQuery.getNewestIDPlan()));
            TextField5.setText(dataFromString.toString());

            String d1 = netController.ControllerNetPlanetDec(s);

            String d2 = netController.ControllerNetPlanetDist(s);

            idPlanet = Integer.parseInt(TextField9.getText());

            dbQuery.AddPlanets(new Planets(Short.valueOf("1"),
                    TextField1.getText(),
                    TextField2.getText(),
                    TextField3.getText(),
                    TextField4.getText(),
                    d2,
                    TextField10.getText(),
                    TextField7.getText()
            ));

            dbQuery.AddArchPlanet(new Archive_position_planet(Short.valueOf("1"),
                    dataFromString,
                    d1,
                    idPlanet
            ),idPlanet);

            choiceBox.getItems().clear();
            choicePlanet();
            choiceBox.setValue(TextField1.getText());

            tableView.getItems().clear();
            loadList();

            tableView.getSelectionModel().select(0);
            Selected();
            AutoBlock();
            m=1;
            TextFieldError.setText("Download end!");
            TextFieldError.setText("Add ok!");


        if(checkbox.isSelected()){
            AutoUpd();
        }else{
            timeline.stop();
            unblockbutton();
            on = true;
            Cancel.setDisable(true);
        }
    }

    }

    @FXML
    private void setCancel(){
        SelectOperation = 0;
        Cancel.setDisable(true);
        Ok.setDisable(true);
        setUneditable();
        UNValWSKSel();
        UNValWSKAll();
        unblockbutton();
        on = true;

        if (tableView.getSelectionModel().isEmpty()) {
            TextField9.setText("");
            TextField1.setText("");
            TextField2.setText("");
            TextField3.setText("");
            TextField4.setText("");
            TextField5.setText("");
            TextField6.setText("");
            TextField7.setText("");
            TextField8.setText("");
            TextField10.setText("");
        }else{
            zmId = tableView.getSelectionModel().getSelectedItem().getIdPlanets();
            zmTimestamp = tableView.getSelectionModel().getSelectedItem().getData();
            TextField9.setText(Integer.toString(zmId));
            TextField5.setText(zmTimestamp.toString());
        }
        TextFieldError.setText("Ok");
    }

    @FXML
    private void setOk(ActionEvent event)throws SQLException, IOException{

        if(SelectOperation == 1){

            if(checkPool() == true) {
                String h = TextField4.getText();
                if(h.contains("https://theskylive.com/")) {
                dataFromString = Localtime();

                idPlanet = Integer.parseInt(TextField9.getText());

                dbQuery.AddPlanets(new Planets(Short.valueOf("1"),
                        TextField1.getText(),
                        TextField2.getText(),
                        TextField3.getText(),
                        TextField4.getText(),
                        TextField6.getText(),
                        TextField10.getText(),
                        TextField7.getText()
                ));

                dbQuery.AddArchPlanet(new Archive_position_planet(Short.valueOf("1"),
                        dataFromString,
                        TextField8.getText(),
                        idPlanet
                ),idPlanet);

                choiceBox.getItems().clear();
                choicePlanet();
                choiceBox.setValue(TextField1.getText());

                tableView.getItems().clear();
                loadList();

                TextFieldError.setText("Ok");

                setUneditable();
                UNValWSKAll();
                UNValWSKSel();
                    Cancel.setDisable(true);
                    Ok.setDisable(true);
                on = true;
                unblockbutton();
                    AutoBlock();
                    m=1;
                SelectOperation = 0;
                }else{
                    TextFieldError.setText("Change URL!");
                }
            }else {
                TextFieldError.setText("Error");
            }
        }else if(SelectOperation == 2) {
            if (checkPool() == true) {
                String h = TextField4.getText();
                if(h.contains("https://theskylive.com/")) {
                dataFromString = Localtime();

                idPlanet = dbQuery.getPlanetIdbyName(TextField1.getText());

                dbQuery.editPlanets(new Planets(Integer.parseInt(TextField9.getText()),
                        TextField1.getText(),
                        TextField2.getText(),
                        TextField3.getText(),
                        TextField4.getText(),
                        TextField6.getText(),
                        TextField10.getText(),
                        TextField7.getText()
                ));

                dbQuery.editArchPlanet(new Archive_position_planet(dbQuery.getIdArchPlanet(Integer.valueOf(tableView.getSelectionModel().getSelectedItem().getId())),
                        dataFromString,
                        TextField8.getText(),
                        idPlanet
                ));

                choiceBox.getItems().clear();


                choicePlanet();
                choiceBox.setValue(TextField1.getText());

                tableView.getItems().clear();
                loadList();

                TextFieldError.setText("Ok");

                setUneditable();
                UNValWSKAll();
                UNValWSKSel();
                    on = true;
                    unblockbutton();
                    Cancel.setDisable(true);
                    Ok.setDisable(true);
                    AutoBlock();
                    m=1;
                SelectOperation = 0;
                }else{
                    TextFieldError.setText("Change URL!");
                }
            } else {
                TextFieldError.setText("Error");
            }
        }else if(SelectOperation == 3) {

            if(checkPool() == true) {
                dataFromString = Localtime();

                idPlanet = Integer.parseInt(TextField9.getText());

                dbQuery.AddPlanets(new Planets(Short.valueOf("1"),
                        TextField1.getText(),
                        TextField2.getText(),
                        TextField3.getText(),
                        TextField4.getText(),
                        TextField6.getText(),
                        TextField10.getText(),
                        TextField7.getText()
                ));

                dbQuery.AddArchPlanet(new Archive_position_planet(Short.valueOf("1"),
                        dataFromString,
                        TextField8.getText(),
                        idPlanet
                ),idPlanet);

                choiceBox.getItems().clear();
                choicePlanet();
                choiceBox.setValue(TextField1.getText());

                tableView.getItems().clear();
                loadList();

                TextFieldError.setText("Ok");

                setSelUneditable();
                UNValWSKSel();
                on = true;
                unblockbutton();
                Cancel.setDisable(true);
                Ok.setDisable(true);
                AutoBlock();
                m=1;

                SelectOperation = 0;
            }else {
                TextFieldError.setText("Error");
            }

        }else{
            Ok.setDisable(true);
        }

    }

    @FXML
    private void CSVButtonALL()throws IOException, SQLException{
        String saveCSV = "C:\\Users\\WoxiQ\\Desktop\\CSVPlanets.csv";

        Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(saveCSV), StandardCharsets.UTF_8));
        Path path = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVPlanets.csv");

        ObservableList<Archive_position_planet> archive_position_planetObservableListt = FXCollections.observableArrayList();

        this.connection = DbConnection.getConnection();

        archive_position_planetObservableListt.addAll(dbQuery.GetAllArchDatePlanet());

        csvCreator.write(fileWriter, Arrays.asList("Id","Name","Type","Temperature (C)", "Mass (10^24 kg)","Distance From Earth", "Center Star" ,"Data", "Declination", "PlanetId_FK","Id_Archive"));

        for (Archive_position_planet archive_position_planet : archive_position_planetObservableListt){
            List<String> stringList = new ArrayList<>();
            stringList.add(Integer.toString(archive_position_planet.getIdPlanets()));
            stringList.add(archive_position_planet.getName());
            stringList.add(archive_position_planet.getType());
            stringList.add(archive_position_planet.getTemperature());
            stringList.add(archive_position_planet.getMass());
            stringList.add(archive_position_planet.getDistanceFromEarth());
            stringList.add(archive_position_planet.getCenterStar());
            stringList.add(archive_position_planet.getData().toString());
            stringList.add(archive_position_planet.getDeclination());
            stringList.add(Integer.toString(archive_position_planet.getPlanetId_FK()));
            stringList.add(Integer.toString(archive_position_planet.getId()));

            csvCreator.write(fileWriter, stringList);
        }

        if(Files.exists(path)) {
            TextFieldError.setText("CSV File was created!");
        }else{
            TextFieldError.setText("CSV File wasn't created!");
        }

        fileWriter.flush();
        fileWriter.close();

        if(checkboxCSV.isSelected()){
            AutoSaveCSV();
        }else {
            timeline.stop();
        }
    }

    @FXML
    private void CSVButtonSelected(ActionEvent event)throws IOException, SQLException{
        if(choiceBox.getValue() != null) {

            String zm;
            int zmC = 0;
            zm = choiceBox.getValue();
            String saveCSV = "C:\\Users\\WoxiQ\\Desktop\\CSVPlanets "+ zm + " " + zmC +".csv";

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(saveCSV), StandardCharsets.UTF_8));
            Path path = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVPlanets "+ zm + " " + zmC +".csv");

            ObservableList<Archive_position_planet> archive_position_planetObservableListt = FXCollections.observableArrayList();

            this.connection = DbConnection.getConnection();

            ArrayList<Integer> integerArrayList = dbQuery.getAllPlanetIdbyName(choiceBox.getValue());
            int zmid;

            for (int i = 0; i < integerArrayList.size(); i++) {
                zmid = integerArrayList.get(i);
                archive_position_planetObservableListt.addAll(dbQuery.GetArchPlanetWhereId(zmid));
            }

            csvCreator.write(fileWriter, Arrays.asList("Id", "Name", "Type", "Temperature (C)", "Mass (10^24 kg)", "Center Star", "Data", "Declination", "PlanetId_FK", "Id_Archive"));

            for (Archive_position_planet archive_position_planet : archive_position_planetObservableListt) {
                List<String> stringList = new ArrayList<>();
                stringList.add(Integer.toString(archive_position_planet.getIdPlanets()));
                stringList.add(archive_position_planet.getName());
                stringList.add(archive_position_planet.getType());
                stringList.add(archive_position_planet.getTemperature());
                stringList.add(archive_position_planet.getMass());
                stringList.add(archive_position_planet.getCenterStar());
                stringList.add(archive_position_planet.getData().toString());
                stringList.add(archive_position_planet.getDeclination());
                stringList.add(Integer.toString(archive_position_planet.getPlanetId_FK()));
                stringList.add(Integer.toString(archive_position_planet.getId()));

                csvCreator.write(fileWriter, stringList);
            }

            if (Files.exists(path)) {
                TextFieldError.setText("CSV File was created!");
                zmC++;
            } else {
                TextFieldError.setText("CSV File wasn't created!");
            }

            fileWriter.flush();
            fileWriter.close();
        }else{
            TextFieldError.setText("CSV File can't be created!");
        }
    }

    @FXML
    private void CSVButtonALLtoDatabase(ActionEvent event)throws IOException, SQLException{
        String saveCSV = "C:\\Users\\WoxiQ\\Desktop\\CSVPlanets.csv";
        String saveCSVARPL = "C:\\Users\\WoxiQ\\Desktop\\CSVArchive_position_planet.csv";

        Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(saveCSV), StandardCharsets.UTF_8));
        Writer fileWriterARPL = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(saveCSVARPL), StandardCharsets.UTF_8));

        Path path = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVPlanets.csv");
        Path pathARPL = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVArchive_position_planet.csv");

        ObservableList<Archive_position_planet> archive_position_planetObservableListt = FXCollections.observableArrayList();

        this.connection = DbConnection.getConnection();

        archive_position_planetObservableListt.addAll(dbQuery.GetAllArchDatePlanet());

        csvCreator.write(fileWriter, Arrays.asList("id","name","type","temperature","url","distancefromEarth","mass","centerStar"));

        for (Archive_position_planet archive_position_planet : archive_position_planetObservableListt){
            List<String> stringList = new ArrayList<>();
            stringList.add(Integer.toString(archive_position_planet.getIdPlanets()));
            stringList.add(archive_position_planet.getName());
            stringList.add(archive_position_planet.getType());
            stringList.add(archive_position_planet.getTemperature());
            stringList.add(archive_position_planet.getUrl());
            stringList.add(archive_position_planet.getDistanceFromEarth());
            stringList.add(archive_position_planet.getMass());
            stringList.add(archive_position_planet.getCenterStar());

            csvCreator.write(fileWriter, stringList);
        }

        csvCreator.write(fileWriterARPL, Arrays.asList("id","data","declination", "planetId_FK"));

        for (Archive_position_planet archive_position_planet : archive_position_planetObservableListt){
            List<String> stringListARPL = new ArrayList<>();
            stringListARPL.add(Integer.toString(archive_position_planet.getId()));
            stringListARPL.add(archive_position_planet.getData().toString());
            stringListARPL.add(archive_position_planet.getDeclination());
            stringListARPL.add(Integer.toString(archive_position_planet.getPlanetId_FK()));

            csvCreator.write(fileWriterARPL, stringListARPL);
        }

        if(Files.exists(path) || Files.exists(pathARPL)) {
            TextFieldError.setText("CSV File was created!");
        }else{
            TextFieldError.setText("CSV File wasn't created!");
        }

        fileWriter.flush();
        fileWriter.close();
        fileWriterARPL.flush();
        fileWriterARPL.close();

    }

    @FXML
    private void GoURL(ActionEvent event)throws Exception{

        String url = TextField4.getText();
            if(TextField4.getText().isEmpty()){
    TextFieldError.setText("Select URL!");
        }else {
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(new URI(url));
    } else {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("/usr/bin/chrome -new-window " + url);
    }
    TextFieldError.setText("OK!");
}
    }

    @FXML
    private void Selected() throws IOException,SQLException{
if(on == true){
        ObservableList<Archive_position_planet> table = tableView.getItems();

        if(table.isEmpty() || tableView.getSelectionModel().isEmpty()) {
            TextFieldError.setText("Empty Cell!");
        }else if(SelectOperation == 1 || SelectOperation == 2){
            TextFieldError.setText("Can't do that!");
        }else {
            buttonALT.setDisable(false);
            TextField1.setText(tableView.getSelectionModel().getSelectedItem().getName());
            TextField2.setText(tableView.getSelectionModel().getSelectedItem().getType());
            TextField3.setText(tableView.getSelectionModel().getSelectedItem().getTemperature());
            TextField4.setText(tableView.getSelectionModel().getSelectedItem().getUrl());
            TextField5.setText(tableView.getSelectionModel().getSelectedItem().getData().toString());
            TextField6.setText(tableView.getSelectionModel().getSelectedItem().getDistanceFromEarth());
            TextField7.setText(tableView.getSelectionModel().getSelectedItem().getCenterStar());
            TextField8.setText(tableView.getSelectionModel().getSelectedItem().getDeclination());
            zmId = tableView.getSelectionModel().getSelectedItem().getIdPlanets();
            TextField9.setText(Integer.toString(zmId));
            TextField10.setText(tableView.getSelectionModel().getSelectedItem().getMass());

            choiceBox.setValue(TextField1.getText());
            TextFieldError.setText("");
            checkbox.setDisable(false);
        }
}else{
    TextFieldError.setText("End rest option!");
}
    }

    @FXML
    private void setEditable(){
        TextField1.setEditable(true);
        TextField2.setEditable(true);
        TextField3.setEditable(true);
        TextField4.setEditable(true);
        TextField6.setEditable(true);
        TextField7.setEditable(true);
        TextField8.setEditable(true);
        TextField10.setEditable(true);


        Ok.setDisable(false);
    }

    @FXML
    private void setSelEditable(){
        TextField3.setEditable(true);
        TextField6.setEditable(true);
        TextField8.setEditable(true);
        TextField10.setEditable(true);


        Ok.setDisable(false);
    }
    @FXML
    private void setUneditable(){
        TextField1.setEditable(false);
        TextField2.setEditable(false);
        TextField3.setEditable(false);
        TextField4.setEditable(false);
        TextField6.setEditable(false);
        TextField7.setEditable(false);
        TextField8.setEditable(false);
        TextField10.setEditable(false);

    }

    @FXML
    private void setSelUneditable(){
        TextField3.setEditable(false);
        TextField6.setEditable(false);
        TextField8.setEditable(false);
        TextField10.setEditable(false);
    }

    @FXML
    private void ValWSKAll() {
        Text1.setVisible(true);
        Text2.setVisible(true);
        Text4.setVisible(true);
        Text8.setVisible(true);
    }

    @FXML
    private void UNValWSKAll(){
        Text1.setVisible(false);
        Text2.setVisible(false);
        Text4.setVisible(false);
        Text8.setVisible(false);

    }

    @FXML
    private void ValWSKSel(){
        Text6.setVisible(true);
        Text7.setVisible(true);
        Text3.setVisible(true);
        Text9.setVisible(true);
    }

    @FXML
    private void UNValWSKSel(){
        Text6.setVisible(false);
        Text7.setVisible(false);
        Text3.setVisible(false);
        Text9.setVisible(false);
    }

    @FXML
    private void blockbutton(){
        GTS.setDisable(true);
        All.setDisable(true);
        select.setDisable(true);
        buttonCSV.setDisable(true);
        buttonALT.setDisable(true);
        buttonADD.setDisable(true);
        buttonDEL.setDisable(true);
        buttonALTPos.setDisable(true);
        buttonCSVSelected.setDisable(true);
        buttonCSVtoDatab.setDisable(true);
        checkboxCSV.setDisable(true);
    }

    @FXML
    private void unblockbutton(){
        GTS.setDisable(false);
        All.setDisable(false);
        select.setDisable(false);
        buttonCSV.setDisable(false);
        buttonALT.setDisable(false);
        buttonADD.setDisable(false);
        buttonDEL.setDisable(false);
        buttonALTPos.setDisable(false);
        buttonCSVSelected.setDisable(false);
        buttonCSVtoDatab.setDisable(false);
        checkboxCSV.setDisable(false);
    }


    private Timestamp Localtime(){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp;
    }

    private boolean checkPool(){
        if(TextField1.getText() == "" || TextField2.getText() == ""||TextField3.getText() == ""||TextField4.getText() == ""||
                TextField5.getText() == ""||TextField6.getText() == ""||TextField7.getText() == ""||TextField8.getText() == ""){
            return false;
        }else{
            return true;
        }

    }
}
