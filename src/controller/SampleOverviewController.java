package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import core.Context;
import core.MainApp;
import statistics.Sample;

public class SampleOverviewController{


    private Stage fStage;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp){

        this.mainApp = mainApp;
    }

    public SampleOverviewController() {
    }

    @FXML
    private Label message;

    @FXML
    private ListView<String> sampleList1;
    @FXML
    private ListView<String> sampleList2;
    @FXML
    private ListView<String> sampleList3;
    @FXML
    private ListView<String> sampleList4;
    @FXML
    private ListView<String> sampleList5;
    @FXML
    private ListView<String> sampleList6;

    @FXML
    private Label sampleLabel1;
    @FXML
    private Label sampleLabel2;
    @FXML
    private Label sampleLabel3;
    @FXML
    private Label sampleLabel4;
    @FXML
    private Label sampleLabel5;
    @FXML
    private Label sampleLabel6;

    @FXML
    private Label numLabel1;
    @FXML
    private Label numLabel2;
    @FXML
    private Label numLabel3;
    @FXML
    private Label numLabel4;
    @FXML
    private Label numLabel5;
    @FXML
    private Label numLabel6;

    @FXML
    private CheckBox title;


    @FXML
    private void initialize() {

        // Setup ListView keyboard/editing operations
        setupList(sampleList1);
        setupList(sampleList2);
        setupList(sampleList3);
        setupList(sampleList4);
        setupList(sampleList5);
        setupList(sampleList6);

        // Bind numLabels to list Size
        numLabel1.textProperty().bind(Bindings.concat(
                "N= ",Bindings.size(sampleList1.getItems()).subtract(1).asString()));
        numLabel2.textProperty().bind(Bindings.concat(
                "N= ",Bindings.size(sampleList2.getItems()).subtract(1).asString()));
        numLabel3.textProperty().bind(Bindings.concat(
                "N= ",Bindings.size(sampleList3.getItems()).subtract(1).asString()));
        numLabel4.textProperty().bind(Bindings.concat(
                "N= ",Bindings.size(sampleList4.getItems()).subtract(1).asString()));
        numLabel5.textProperty().bind(Bindings.concat(
                "N= ",Bindings.size(sampleList5.getItems()).subtract(1).asString()));
        numLabel6.textProperty().bind(Bindings.concat(
                "N= ",Bindings.size(sampleList6.getItems()).subtract(1).asString()));


    }

    /**
     * Initializes the ListView data sets and ObservableLists
     * @param v  The ListView to be initialized.
     */
    private void setupList(ListView<String> v){

            v.getItems().add("");
            v.setEditable(true);
            v.setCellFactory(TextFieldListCell.forListView());
            v.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>(){
                @Override
                public void handle(ListView.EditEvent<String> t) {
                    if (!t.getNewValue().equals("")){
                        v.getItems().set(t.getIndex(), t.getNewValue());
                        if (t.getIndex() == v.getItems().size()-1){
                            v.getItems().add("");
                            v.getSelectionModel().select(t.getIndex()+1);
                        }
                    }
                }
            });

            /*
             * Handles the keyboard input of an item in the list.
             */
            v.setOnKeyPressed(new EventHandler<KeyEvent>(){

                public void handle( final KeyEvent ke){
                        if (ke.getCode().equals(KeyCode.DELETE) && v.getItems().size() > 1){
                            int currentIndex = v.getSelectionModel().getSelectedIndex();

                            v.getItems().remove(currentIndex);
                            v.getSelectionModel().select(currentIndex-1);
                        }
                        else if (!(ke.getCode().equals(KeyCode.ENTER))){
                            v.edit(v.getSelectionModel().getSelectedIndex());
                        }
                    }
            });
    }


    private void generateSample(ListView<String> view, int sampleNum){
        Sample s = null;
        ObservableList<String> list = view.getItems();
        if (list.size() - 1 > 0){ // list.size() - 1 to account for 'current' editing cell

            double d[] = new double[list.size()-1];
            for (int i = 0; i < list.size(); i++){
                try {
                    if (parseHelper(list.get(i))){
                        d[i] = (double) Double.parseDouble(list.get(i));
                    }
                } catch (NumberFormatException e) {

                    // TODO: handle exception
                }
            }

            s = new Sample(d, "Sample " + sampleNum);
        }
        else{ // generate default empty sample object

            s = new Sample();
        }

        switch (sampleNum) {

        case 1:
            Context.getInstance().setS1(s);
            break;
        case 2:
            Context.getInstance().setS2(s);
            break;
        case 3:
            Context.getInstance().setS3(s);
            break;
        case 4:
            Context.getInstance().setS4(s);
            break;
        case 5:
            Context.getInstance().setS5(s);
            break;
        case 6:
            Context.getInstance().setS6(s);
            break;
        }



    }

    private boolean parseHelper(String input){
        if (input.equals(null) || input.equals("")){
            return false;
        }
        return true;
    }


    @FXML
    private void set(){
        generateSample(sampleList1, 1);
        generateSample(sampleList2, 2);
        generateSample(sampleList3, 3);
        generateSample(sampleList4, 4);
        generateSample(sampleList5, 5);
        generateSample(sampleList6, 6);
    }


    private void showMessage(String m){
        message.setText(m);
    }


    @FXML
    private void showData(){


        for (int i = 0; i < 100; i++){

            sampleList1.getItems().add(i,Double.toString(randomRange(2.7, 4.0)));
            sampleList2.getItems().add(i,Double.toString(randomRange(60, 99)));

        }

    }

    private void addData(ListView<String> l, double[] array ){

    }

    private double randomRange(double min, double max){
        double range = max-min;
        return (Math.random() * range)+min;
    }


    /**
     * Remove all items from all ListViews.
     */
    private void clearAllListViews(ListView<String>[] listViews) {
        for (ListView<String> lv : listViews)
            lv.getItems().clear();
    }

    @FXML
    private void handleClearAll(){
        handleClear1();
        handleClear2();
        handleClear3();
        handleClear4();
        handleClear5();
        handleClear6();
    }

    @FXML
    private void handleClear1(){
        sampleLabel1.setText("Data Set 1");
        sampleList1.getItems().clear();
        sampleList1.getItems().add("");
    }
    @FXML
    private void handleClear2(){
        sampleLabel2.setText("Data Set 2");
        sampleList2.getItems().clear();
        sampleList2.getItems().add("");

    }
    @FXML
    private void handleClear3(){
        sampleLabel3.setText("Data Set 3");
        sampleList3.getItems().clear();
        sampleList3.getItems().add("");
    }
    @FXML
    private void handleClear4(){
        sampleLabel4.setText("Data Set 4");
        sampleList4.getItems().clear();
        sampleList4.getItems().add("");
    }
    @FXML
    private void handleClear5(){
        sampleLabel5.setText("Data Set 5");
        sampleList5.getItems().clear();
        sampleList5.getItems().add("");
    }
    @FXML
    private void handleClear6(){
        sampleLabel6.setText("Data Set 6");
        sampleList6.getItems().clear();
        sampleList6.getItems().add("");

    }



    /*
     * Imports
     */

    // Works with excel DOS line endings
    @FXML
    private void handleImport(){

        handleClearAll();
        FileChooser f = new FileChooser();
        f.setTitle("Open File");

        File file = f.showOpenDialog(fStage);
        if (file != null){
            //fileName.setText(file.getAbsolutePath());
            readFile(file.getAbsolutePath());
        }

    }


    private void readFile(String filePath){

        ListView[] listViews = {
                this.sampleList1,
                this.sampleList2,
                this.sampleList3,
                this.sampleList4,
                this.sampleList5,
                this.sampleList6
        };

        Label[] labels = {
                this.sampleLabel1,
                this.sampleLabel2,
                this.sampleLabel3,
                this.sampleLabel4,
                this.sampleLabel5,
                this.sampleLabel6
        };

        clearAllListViews(listViews);
        boolean firstLine = true;
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ( (line = br.readLine()) != null) {
                String[] splitEntry = line.split(cvsSplitBy);
                if (firstLine && title.isSelected()) {
                    for (int i = 0; i < splitEntry.length; i++) {
                        labels[i].setText(splitEntry[i]);
                    }

                }
                else {
                    for (int i = 0; i < splitEntry.length; i++) {
                        listViews[i].getItems().add(splitEntry[i]);
                    }
                }
                firstLine = false;
            }
        br.close();
        } catch (IOException e) {
            showImportError();
            handleClearAll();
        }
    }

    /*
     * 	Open the Samples page.
     */
    @FXML
    private void handleShowSample() {
        mainApp.showSamples();

    }

    /*
     * 	Open the ZTestDialog.
     */
    @FXML
    private void handleShowZTestDialog() {
        set();
        mainApp.showZTestDialog();

    }

    /*
     * 	Open the TTestDialog.
     */
    @FXML
    private void handleShowTTestDialog() {
        set();
        mainApp.showTTestDialog();

    }


    @FXML
    private void handleShow2TTestDialog(){
        set();
        mainApp.show2TTestDialog();
    }


    /*
     * 	Open the Regression Dialog.
     */
    @FXML
    private void handleShowRegressionDialog() {
        set();
        mainApp.showRegressionDialog();

    }

    /*
     * 	Open the ChiSquared Dialog.
     */
    @FXML
    private void handleShowChiSquaredDialog() {
        set();
        mainApp.showChiSquaredDialog();

    }


    /*
     * 	Open the Sample Statistics Dialog.
     */
    @FXML
    private void handleShowDescriptiveStatistics() {
        set();
        mainApp.showDescriptiveStatistics();

    }

    /*
     * 	Open the Sample Statistics Dialog.
     */
    @FXML
    private void handleShowScatterPlot() {
        set();
        mainApp.showScatterPlot();
    }

    /*
    Error Alerts
     */

    @FXML
    private void showImportError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Import Error");
        alert.setContentText("There was a problem importing a file.");
        alert.showAndWait();
    }



    /*
     * 	Close the Application
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }








}
