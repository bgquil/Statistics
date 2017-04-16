package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import statistics.Context;
import statistics.DataEntry;
import statistics.MainApp;
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
	private Label sampleLabel1;
	@FXML
	private Label sampleLabel2;
	@FXML
	private Label sampleLabel3;
	@FXML
	private Label sampleLabel4;

	@FXML
	private Label numLabel1;
	@FXML
	private Label numLabel2;
	@FXML
	private Label numLabel3;
	@FXML
	private Label numLabel4;

	
	@FXML
	private void initialize() {
		
		// Setup ListView keyboard/editing operations
		setupList(sampleList1);
		setupList(sampleList2);
		setupList(sampleList3);
		setupList(sampleList4);	
		
		// Bind numLabels to list Size
		numLabel1.textProperty().bind(Bindings.concat("N= ",Bindings.size(sampleList1.getItems()).subtract(1).asString()));
		numLabel2.textProperty().bind(Bindings.concat("N= ",Bindings.size(sampleList2.getItems()).subtract(1).asString()));
		numLabel3.textProperty().bind(Bindings.concat("N= ",Bindings.size(sampleList3.getItems()).subtract(1).asString()));
		numLabel4.textProperty().bind(Bindings.concat("N= ",Bindings.size(sampleList4.getItems()).subtract(1).asString()));
		
		
	}
	
	/*
	 * Initializes the ListView data sets and ObservableLists
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
					System.out.println(list.get(i));
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
		}
		
			
		
	}
	
	private boolean parseHelper(String input){
		if (input.equals(null)){
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
	private void handleClear1(){
		sampleList1.getItems().clear();
		
		sampleList1.getItems().add("");
	}
	@FXML
	private void handleClear2(){
		sampleList2.getItems().clear();		
		sampleList2.getItems().add("");

	}
	@FXML
	private void handleClear3(){
		sampleList3.getItems().clear();
		sampleList3.getItems().add("");
	}
	@FXML
	private void handleClear4(){
		sampleList4.getItems().clear();
		sampleList4.getItems().add("");

		
	}
	
	
	/*
	 * Imports
	 */

	
	@FXML
	private void handleImport(){
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
        		sampleList1.getItems().add(index, splitEntry[0]);
        		sampleList2.getItems().add(index, splitEntry[1]);
        		sampleList3.getItems().add(index, splitEntry[2]);
        		sampleList4.getItems().add(index, splitEntry[3]);

        		index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
	
	
	
	

}
