package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
		if (list.size() > 5){
		
			double d[] = new double[list.size()-1];
			for (int i = 0; i < list.size(); i++){
				try {
					if (parseHelper(list.get(i))){
						d[i] = (double) Double.parseDouble(list.get(i));	
					}
					
					 //System.out.println(d[i]);
				} catch (Exception e) {
					
					// TODO: handle exception
				}
			}
			
			s = new Sample(d);
		}
		else{
			double d[] = {1,2,3,4,5};
			s = new Sample(d);
			s.setName("DEFAULT SAMPLE");
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
		boolean done = false;


        String line = "";
        String cvsSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	int index = 0;
        	while ((line = br.readLine()) != null) {
        		String[] splitEntry = line.split(cvsSplitBy);
        		if(title.isSelected() && !done) {
        			if(splitEntry.length > 0)
	        			sampleLabel1.setText(splitEntry[0]);
        			if(splitEntry.length > 1)
	        			sampleLabel2.setText(splitEntry[1]);
        			if(splitEntry.length > 2)
	        			sampleLabel3.setText(splitEntry[2]);
        			if(splitEntry.length > 3)	
	        			sampleLabel4.setText(splitEntry[3]);
        			if(splitEntry.length > 4)	
	        			sampleLabel5.setText(splitEntry[4]);
        			if(splitEntry.length > 5)	
	        			sampleLabel6.setText(splitEntry[5]);
        			done = true;
        			continue;
        		}
        		if(splitEntry.length > 0)
        			sampleList1.getItems().add(index, splitEntry[0]);
        		if(splitEntry.length > 1)
        			sampleList2.getItems().add(index, splitEntry[1]);
        		if(splitEntry.length > 2)
        			sampleList3.getItems().add(index, splitEntry[2]);
        		if(splitEntry.length > 3)
        			sampleList4.getItems().add(index, splitEntry[3]);
        		if(splitEntry.length > 4)
        			sampleList5.getItems().add(index, splitEntry[4]);
        		if(splitEntry.length > 5)
        			sampleList6.getItems().add(index, splitEntry[5]);
        		
        		index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
	
	@FXML
	private void handleExport(){
		
	}

	/*
	 * Open Test Buttons
	 */


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
	 * 	Open the Boxplot Dialog.
	 */
	@FXML
	private void handleShowBoxplotDialog() {
		set();
		mainApp.showBoxplotDialog();
		
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
	private void handleShowScatterplot() {
		set();
		mainApp.showScatterplot();
	}
	
	
	
	/*
	 * 	Close the Application
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
	
	
	
	
	
	
	

}
