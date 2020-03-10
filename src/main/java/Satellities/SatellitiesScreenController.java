package Satellities;

import Net.NetController;
import application.Archive_position_sat;
import application.CSVCreator;
import dbController.DbConnection;
import dbController.dbQuery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SatellitiesScreenController {

    dbQuery dbQuery = new dbQuery();
    Connection connection;
    CSVCreator csvCreator = new CSVCreator();
    NetController netController = new NetController();
    Timeline timeline = new Timeline();

    @FXML
    Button GTP;

    @FXML
    AnchorPane changeScreentoPlanets;

    @FXML
    Button select;
    @FXML
    Button GOTO;
    @FXML
    Button GOTODF;
    @FXML
    Button GOTODF1;

    @FXML
    private ChoiceBox<String> choiceBox ;

    @FXML
    TableView<Archive_position_sat> tableView;

    @FXML
    TableColumn<Archive_position_sat, Integer> id;
    @FXML
    TableColumn<Archive_position_sat, String> name;
    @FXML
    TableColumn<Archive_position_sat, String> intl;
    @FXML
    TableColumn<Archive_position_sat, String> dat;
    @FXML
    TableColumn<Archive_position_sat, String> typ;
    @FXML
    TableColumn<Archive_position_sat, String> peri;
    @FXML
    TableColumn<Archive_position_sat, String> inc;
    @FXML
    TableColumn<Archive_position_sat, String> apo;
    @FXML
    TableColumn<Archive_position_sat, String> longi;

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
    Button help5;

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
    Button net;
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

    Timestamp dataFromString;
    Timestamp zmTimestamp;

    int idSat;
    int idZmienna;
    int zmId;
    int zmC = 0;
    Boolean on =true;
    int m = 0;
    public String namezm = null;
    int whichclicked = 0;

    //Operacja ok
    int SelectOperation = 0;


    @FXML
    private void initialize() throws IOException, SQLException{
        loadAllSatToList();
        choiceSat();
        tableView.getSortOrder().add(id);
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
    private void setHelp5(){whichclicked = 5; Help();}


    @FXML
    private void Help(){

        helpclos.setVisible(true);
      if(whichclicked == 1){

          helpText.setVisible(true);
          TextFieldHelp.setVisible(true);
          TextFieldHelp.setText("Int'l Designator - also known as COSPAR ID, and NSSDC ID, is an international identifier assigned to man-made objects in space.");

      }else if(whichclicked == 2){
          helpText.setVisible(true);

          TextFieldHelp.setVisible(true);
          TextFieldHelp.setText("Perigee - its the point when satellite is closest to the Earth");
      }else if(whichclicked == 3){
          helpText.setVisible(true);
          TextFieldHelp.setVisible(true);
          TextFieldHelp.setText("Longitude - shows your location in an east-west direction, relative to the Greenwich meridian.");
      }else if(whichclicked == 4){
          helpText.setVisible(true);
          TextFieldHelp.setVisible(true);
          TextFieldHelp.setText("Inclination - is the angle of the orbit in relation to Earth’s equator. A satellite that orbits directly above the equator has zero inclination.");
      }else if(whichclicked == 5){
          helpText.setVisible(true);
          TextFieldHelp.setVisible(true);
          TextFieldHelp.setText("Apogee - its the point when satellite is furthest from the Earth");
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
    private void AllSat()throws IOException, SQLException{
        tableView.getItems().clear();
        loadAllSatToList();
        TextFieldError.setText("Ok");
    }

    @FXML
    private void changeScreentoPlanets(ActionEvent event) throws IOException {

        Parent PlanetsParent = FXMLLoader.load(getClass().getResource("/scene/scenePlanets.fxml"));

        Scene PlanetsScene = new Scene(PlanetsParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(PlanetsScene);
        window.show();
    }

    @FXML
    private void choiceSat()throws IOException {
        this.connection = DbConnection.getConnection();

        ObservableList<String> SatNameList = FXCollections.observableArrayList();
        try {

            SatNameList.addAll(dbQuery.GetSatName());

            ObservableList<String> satNameListWithoutRepeats = FXCollections.observableArrayList(new HashSet<String>(SatNameList));

            choiceBox.getItems().addAll(satNameListWithoutRepeats);

            namezm = choiceBox.getValue();

            TextFieldError.setText("Ok");
        } catch (SQLException e) {
            e.printStackTrace();
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
        on = true;
        buttonALT.setDisable(false);
        net.setDisable(false);
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
    private void AutoUpd(){

        blockbutton();
        on = false;
        if (TextField4.getText() == "") {

            TextFieldError.setText("Choose planet to update!");
        } else {
            if (checkbox.isSelected()) {

                timeline = new Timeline(new KeyFrame(Duration.hours(2), e -> {

                    try {
                        setNet();
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
    private void setNet()throws SQLException, IOException {

        if(TextField4.getText() == ""){

            TextFieldError.setText("Choose satelite to update!");
        }else {
            TextFieldError.setText("Wait! Download!");
            String s = TextField4.getText();
            blockbutton();
            on = false;
            Cancel.setDisable(false);
            AutoBlock();
            m=1;


            AutoBlock();
            m=1;

            dataFromString = Localtime();

                 String apo= netController.ControllerNetSatelApogee(s);
                 String inc= netController.ControllerNetSatelInc(s);
               String per= netController.ControllerNetSatelPerigee(s);

            TextField9.setText(Integer.toString(dbQuery.getNewestIDSat()));
            TextField5.setText(dataFromString.toString());

            dataFromString = Localtime();

            idSat = Integer.parseInt(TextField9.getText());

            dbQuery.AddSatellities(new Satellities(Short.valueOf("1"),
                    TextField1.getText(),
                    TextField2.getText(),
                    TextField3.getText(),
                    TextField4.getText(),
                    apo,
                    per,
                    inc
            ));

            dbQuery.AddArchPos(new Archive_position_sat(Short.valueOf("1"),
                    dataFromString,
                    TextField10.getText(),
                    idSat
            ), idSat);

            choiceBox.getItems().clear();
            choiceSat();
            choiceBox.setValue(TextField1.getText());

            tableView.getItems().clear();
            loadList();

            tableView.getSelectionModel().select(0);
            Selected();

            TextFieldError.setText("Download end!");
            TextFieldError.setText("Add ok!");
        }

        if(checkbox.isSelected()){
            AutoUpd();
        }else{
            timeline.stop();
            unblockbutton();
            on = true;
            Cancel.setDisable(true);
        }
    }

    @FXML
    private void loadAllSatToList() throws IOException, SQLException{

        ObservableList<Archive_position_sat> archive_position_satObservableList = FXCollections.observableArrayList();

        AutoBlock();
        m=1;
        this.connection = DbConnection.getConnection();

        try{
            archive_position_satObservableList.addAll(dbQuery.GetUniqArchDate());

        } catch (SQLException e) {
            System.out.println("Error:"+ e);
            e.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("idSat"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        intl.setCellValueFactory(new PropertyValueFactory<>("intlDesignator"));
        dat.setCellValueFactory(new PropertyValueFactory<>("data"));
        typ.setCellValueFactory(new PropertyValueFactory<>("type"));
        peri.setCellValueFactory(new PropertyValueFactory<>("perigee"));
        inc.setCellValueFactory(new PropertyValueFactory<>("inclination"));
        apo.setCellValueFactory(new PropertyValueFactory<>("apogee"));
        longi.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        tableView.getItems().addAll(archive_position_satObservableList);
        tableView.getSortOrder().add(id);
        tableView.getSelectionModel().select(0);
        Selected();
    }

    @FXML
    private void loadList() throws IOException, SQLException{
        AutoBlock();
        m=1;
        ObservableList<Archive_position_sat> archive_position_satsObservableList = FXCollections.observableArrayList();

        ArrayList<Integer> integerArrayList = new ArrayList<>();

        namezm = choiceBox.getValue();
        integerArrayList = dbQuery.GetSatId(namezm);

            this.connection = DbConnection.getConnection();

        try{

            for(int i = 0; i < integerArrayList.size(); i++){
                idZmienna = integerArrayList.get(i);
                archive_position_satsObservableList.addAll(dbQuery.GetArchWhereId(idZmienna));
            }


        } catch (SQLException e) {
           System.out.println("Error:"+ e);
           e.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("idSat"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        intl.setCellValueFactory(new PropertyValueFactory<>("intlDesignator"));
        dat.setCellValueFactory(new PropertyValueFactory<>("data"));
        typ.setCellValueFactory(new PropertyValueFactory<>("type"));
        peri.setCellValueFactory(new PropertyValueFactory<>("perigee"));
        inc.setCellValueFactory(new PropertyValueFactory<>("inclination"));
        apo.setCellValueFactory(new PropertyValueFactory<>("apogee"));
        longi.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        tableView.getItems().addAll(archive_position_satsObservableList);
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
    private void deleteRow(ActionEvent event)throws IOException, SQLException{

        AutoBlock();
        m=1;
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            TextFieldError.setText("Nothing is clicked!");
        }else {
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
            TextFieldError.setText("");
            dbQuery.deleteArchPos(zmId);
            dbQuery.DeleteSat(zmId);
            choiceBox.getItems().clear();
            choiceSat();
            TextFieldError.setText("Ok");
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
    private void setButtonADD(ActionEvent event)throws SQLException{

        setEditable();
        ValWSKAll();
        ValWSKSel();
        blockbutton();
        on = false;
        buttonADD.setDisable(false);
        Cancel.setDisable(false);
        TextField9.setText(Integer.toString(dbQuery.getNewestIDSat()));
        dataFromString = Localtime();
        if (tableView.getSelectionModel().isEmpty()) {
            TextField6.setText(" km");
            TextField7.setText(" °");
            TextField8.setText(" km");
            TextField10.setText(" °");
        }
        TextField5.setText(dataFromString.toString());
        TextFieldError.setText("Add new satellities!");
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
            buttonALTPos.setDisable(false);
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
            buttonALTPos.setDisable(false);
            TextField9.setText(Integer.toString(dbQuery.getNewestIDSat()));
            dataFromString = Localtime();
            TextField5.setText(dataFromString.toString());
            Cancel.setDisable(false);

            TextFieldError.setText("Edit values!");
            SelectOperation = 3;
        }
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
    private void GoURLHyperlink(ActionEvent event)throws Exception{

        String url = "https://www.n2yo.com/satellites/?c=10";
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("/usr/bin/chrome -new-window " + url);
        }
            TextFieldError.setText("OK!");
    }

    @FXML
    private void setCancel(){
        SelectOperation = 0;
        Cancel.setDisable(true);
        Ok.setDisable(true);
        setUneditable();
        UNValWSKAll();
        UNValWSKSel();
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
                zmId = tableView.getSelectionModel().getSelectedItem().getIdSat();
                zmTimestamp = tableView.getSelectionModel().getSelectedItem().getData();
                TextField9.setText(Integer.toString(zmId));
                TextField5.setText(zmTimestamp.toString());

            }
        TextFieldError.setText("Ok");
    }

    @FXML
    public void setOk(ActionEvent event)throws SQLException, IOException{
        if(SelectOperation == 1){

            if(checkPool() == true) {
                String h = TextField4.getText();
                if(h.contains("https://www.n2yo.com/")) {
                    dataFromString = Localtime();

                    idSat = Integer.parseInt(TextField9.getText());

                    dbQuery.AddSatellities(new Satellities(Short.valueOf("1"),
                            TextField1.getText(),
                            TextField2.getText(),
                            TextField3.getText(),
                            TextField4.getText(),
                            TextField8.getText(),
                            TextField6.getText(),
                            TextField7.getText()
                    ));

                    dbQuery.AddArchPos(new Archive_position_sat(Short.valueOf("1"),
                            dataFromString,
                            TextField10.getText(),
                            idSat
                    ), idSat);

                    choiceBox.getItems().clear();
                    choiceSat();
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
            }else {
                TextFieldError.setText("Error");
            }
        }else if(SelectOperation == 2) {
            if (checkPool() == true) {
                String h = TextField4.getText();
                if(h.contains("https://www.n2yo.com/")) {
                dataFromString = Timestamp.valueOf(TextField5.getText());

                idSat = dbQuery.getSatIdbyName(TextField1.getText());

                dbQuery.editSatellities(new Satellities(Integer.parseInt(TextField9.getText()),
                        TextField1.getText(),
                        TextField2.getText(),
                        TextField3.getText(),
                        TextField4.getText(),
                        TextField8.getText(),
                        TextField6.getText(),
                        TextField7.getText()
                ));

                dbQuery.editArchPos(new Archive_position_sat(dbQuery.getIdArchPos(
                        Integer.valueOf(tableView.getSelectionModel().getSelectedItem().getId())),
                        dataFromString,
                        TextField10.getText(),
                        idSat
                ));

                choiceBox.getItems().clear();


                choiceSat();
                choiceBox.setValue(TextField1.getText());

                tableView.getItems().clear();
                loadList();

                TextFieldError.setText("Ok");

                setUneditable();
                on = true;
                UNValWSKAll();
                    UNValWSKSel();
                    Cancel.setDisable(true);
                    Ok.setDisable(true);
                unblockbutton();
                    AutoBlock();
                    m=1;
                SelectOperation = 0;
                }else{
                    TextFieldError.setText("Change URL!");
                }
            } else {
                TextFieldError.setText("Error");
            }
        }else if(SelectOperation == 3){
            if(checkPool() == true) {
                dataFromString = Localtime();

                idSat = Integer.parseInt(TextField9.getText());

                dbQuery.AddSatellities(new Satellities(Short.valueOf("1"),
                        TextField1.getText(),
                        TextField2.getText(),
                        TextField3.getText(),
                        TextField4.getText(),
                        TextField8.getText(),
                        TextField6.getText(),
                        TextField7.getText()
                ));

                dbQuery.AddArchPos(new Archive_position_sat(Short.valueOf("1"),
                        dataFromString,
                        TextField10.getText(),
                        idSat
                ),idSat);

                    choiceBox.getItems().clear();
                    choiceSat();
                    choiceBox.setValue(TextField1.getText());

                    tableView.getItems().clear();
                    loadList();

                    TextFieldError.setText("Ok");

                    setSelUneditable();
                    UNValWSKSel();
                Cancel.setDisable(true);
                Ok.setDisable(true);
                    on = true;
                    unblockbutton();
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
    public void CSVButtonALL()throws IOException, SQLException{
        String saveCSV = "C:\\Users\\WoxiQ\\Desktop\\CSVSatellities.csv";

        Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(saveCSV), StandardCharsets.UTF_8));
        Path path = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVSatellities.csv");

        ObservableList<Archive_position_sat> archive_position_satsObservableList = FXCollections.observableArrayList();

        this.connection = DbConnection.getConnection();

        archive_position_satsObservableList.addAll(dbQuery.GetAllArchDate());

            csvCreator.write(fileWriter, Arrays.asList("Id","Name","Int'l Designator","Type","Perigee", "Inclination",
                    "Apogee" ,"Data", "Longitude", "SatelitId_FK","Id_Archive"));

            for (Archive_position_sat archive_position_sat : archive_position_satsObservableList){
                List<String> stringList = new ArrayList<>();
                stringList.add(Integer.toString(archive_position_sat.getIdSat()));
                stringList.add(archive_position_sat.getName());
                stringList.add(archive_position_sat.getIntlDesignator());
                stringList.add(archive_position_sat.getType());
                stringList.add(archive_position_sat.getPerigee());
                stringList.add(archive_position_sat.getInclination());
                stringList.add(archive_position_sat.getApogee());
                stringList.add(archive_position_sat.getData().toString());
                stringList.add(archive_position_sat.getLongitude());
                stringList.add(Integer.toString(archive_position_sat.getSatelitId_FK()));
                stringList.add(Integer.toString(archive_position_sat.getId()));

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
                zm = choiceBox.getValue();

                String saveCSV = "C:\\Users\\WoxiQ\\Desktop\\CSVSatellities " + zm + " " + zmC + ".csv";

            Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(saveCSV), StandardCharsets.UTF_8));
                Path path = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVSatellities " + zm + " " + zmC + ".csv");

                ObservableList<Archive_position_sat> archive_position_satsObservableList = FXCollections.observableArrayList();

                this.connection = DbConnection.getConnection();

                ArrayList<Integer> integerArrayList = dbQuery.getAllSatIdbyName(choiceBox.getValue());
                int zmid;

                for (int i = 0; i < integerArrayList.size(); i++) {
                    zmid = integerArrayList.get(i);
                    archive_position_satsObservableList.addAll(dbQuery.GetArchWhereId(zmid));
                }

                csvCreator.write(fileWriter, Arrays.asList("Id", "Name", "Int'l Designator", "Type", "Perigee", "Inclination", "Apogee", "Data", "Longitude", "SatelitId_FK", "Id_Archive"));

                for (Archive_position_sat archive_position_sat : archive_position_satsObservableList) {
                    List<String> stringList = new ArrayList<>();
                    stringList.add(Integer.toString(archive_position_sat.getIdSat()));
                    stringList.add(archive_position_sat.getName());
                    stringList.add(archive_position_sat.getIntlDesignator());
                    stringList.add(archive_position_sat.getType());
                    stringList.add(archive_position_sat.getPerigee());
                    stringList.add(archive_position_sat.getInclination());
                    stringList.add(archive_position_sat.getApogee());
                    stringList.add(archive_position_sat.getData().toString());
                    stringList.add(archive_position_sat.getLongitude());
                    stringList.add(Integer.toString(archive_position_sat.getSatelitId_FK()));
                    stringList.add(Integer.toString(archive_position_sat.getId()));

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
    private void CSVButtontoDatabase(ActionEvent event)throws IOException, SQLException{
        String saveCSV = "C:\\Users\\WoxiQ\\Desktop\\CSVSatellities.csv";
        String saveCSVAr = "C:\\Users\\WoxiQ\\Desktop\\CSVArchive_position_sat.csv";

        Writer fileWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(saveCSV), StandardCharsets.UTF_8));
        Writer fileWriteAr = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(saveCSVAr), StandardCharsets.UTF_8));

        Path path = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVSatellities.csv");
        Path pathAr = Paths.get("C:\\Users\\WoxiQ\\Desktop\\CSVArchive_position_sat.csv");

        ObservableList<Archive_position_sat> archive_position_satsObservableList = FXCollections.observableArrayList();

        this.connection = DbConnection.getConnection();

        archive_position_satsObservableList.addAll(dbQuery.GetAllArchDate());

        csvCreator.write(fileWriter, Arrays.asList("id","name","intl","type","url","apogee","perigee","inclination"));

        for (Archive_position_sat archive_position_sat : archive_position_satsObservableList){
            List<String> stringList = new ArrayList<>();
            stringList.add(Integer.toString(archive_position_sat.getIdSat()));
            stringList.add(archive_position_sat.getName());
            stringList.add(archive_position_sat.getIntlDesignator());
            stringList.add(archive_position_sat.getType());
            stringList.add(archive_position_sat.getUrl());
            stringList.add(archive_position_sat.getApogee());
            stringList.add(archive_position_sat.getPerigee());
            stringList.add(archive_position_sat.getInclination());

            csvCreator.write(fileWriter, stringList);
        }

        csvCreator.write(fileWriteAr, Arrays.asList("id","data", "longitude", "satelitId_FK"));
        for (Archive_position_sat archive_position_sat : archive_position_satsObservableList){
            List<String> stringListAr = new ArrayList<>();
            stringListAr.add(Integer.toString(archive_position_sat.getId()));
            stringListAr.add(archive_position_sat.getData().toString());
            stringListAr.add(archive_position_sat.getLongitude());
            stringListAr.add(Integer.toString(archive_position_sat.getSatelitId_FK()));

            csvCreator.write(fileWriteAr, stringListAr);
        }

        if(Files.exists(path) || Files.exists(pathAr)) {
            TextFieldError.setText("CSV File was created!");
        }else{
            TextFieldError.setText("CSV File wasn't created!");
        }

        fileWriter.flush();
        fileWriteAr.flush();
        fileWriter.close();
        fileWriteAr.close();

    }

    @FXML
    private void Selected() throws IOException,SQLException{
if(on == true) {
    ObservableList<Archive_position_sat> table = tableView.getItems();

    if (table.isEmpty() || tableView.getSelectionModel().isEmpty()) {
        TextFieldError.setText("Empty Cell!");
    } else if (SelectOperation == 1 || SelectOperation == 2) {
        TextFieldError.setText("Can't do that!");
    } else {
        buttonALT.setDisable(false);
        TextField1.setText(tableView.getSelectionModel().getSelectedItem().getName());
        TextField2.setText(tableView.getSelectionModel().getSelectedItem().getIntlDesignator());
        TextField3.setText(tableView.getSelectionModel().getSelectedItem().getType());
        TextField4.setText(tableView.getSelectionModel().getSelectedItem().getUrl());
        TextField5.setText(tableView.getSelectionModel().getSelectedItem().getData().toString());
        TextField6.setText(tableView.getSelectionModel().getSelectedItem().getPerigee());
        TextField7.setText(tableView.getSelectionModel().getSelectedItem().getInclination());
        TextField8.setText(tableView.getSelectionModel().getSelectedItem().getApogee());
        TextField10.setText(tableView.getSelectionModel().getSelectedItem().getLongitude());
        zmId = tableView.getSelectionModel().getSelectedItem().getIdSat();
        TextField9.setText(Integer.toString(zmId));

        choiceBox.setValue(TextField1.getText());
        TextFieldError.setText("");
        checkbox.setDisable(false);
    }
}else{
    TextFieldError.setText("End rest option!");
}
    }

    @FXML
    private void setSelEditable(){
        TextField6.setEditable(true);
        TextField7.setEditable(true);
        TextField8.setEditable(true);
        TextField10.setEditable(true);


        Ok.setDisable(false);
    }

    @FXML
    private void setSelUneditable(){
        TextField6.setEditable(false);
        TextField7.setEditable(false);
        TextField8.setEditable(false);
        TextField10.setEditable(false);
    }

    @FXML
    private void ValWSKAll(){
        Text1.setVisible(true);
        Text2.setVisible(true);
        Text3.setVisible(true);
        Text4.setVisible(true);
    }

    @FXML
    private void UNValWSKAll(){
        Text1.setVisible(false);
        Text2.setVisible(false);
        Text3.setVisible(false);
        Text4.setVisible(false);
    }

    @FXML
    private void ValWSKSel(){
        Text6.setVisible(true);
        Text7.setVisible(true);
        Text8.setVisible(true);
        Text9.setVisible(true);
    }

    @FXML
    private void UNValWSKSel(){
        Text6.setVisible(false);
        Text7.setVisible(false);
        Text8.setVisible(false);
        Text9.setVisible(false);
    }


    @FXML
    private void blockbutton(){
        GTP.setDisable(true);
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
        GTP.setDisable(false);
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


