package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import statistics.MainApp;
import statistics.Sample;

public class SampleOverviewController{
	
	
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp){
		
		this.mainApp = mainApp;
	}
	
	public SampleOverviewController() {
	}
	
	//containing all Sample objects
	private static Sample s1,s2,s3,s4;

	

	

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
	

	
//	private static void generateSample(ObservableList<String> list, int sampleNum){
//
//		double d[] = new double[list.size()-1];
//		for (int i = 0; i < list.size(); i++){
//			try {
//				 d[i] = (double) Double.parseDouble(list.get(i));
//				 //System.out.println(d[i]);
//			} catch (Exception e) {
//				
//				// TODO: handle exception
//			}
//		}
//		Sample s = new Sample(d);
//		
//		switch (sampleNum) {
//		
//		case 1:
//			s1 = s;
//		case 2:
//			s2 = s;
//		case 3:
//			s3 = s;
//		case 4:
//			s4 = s;
//		}
//		
//	}
//	
//	public static Sample getSample1(){
//		generateSample(l1 , 1);
//		return s1;
//	}
//	public static Sample getSample2(){
//		generateSample(l2 , 2);
//		return s2;
//	}
//	public static Sample getSample3(){
//		generateSample(l3 , 3);
//		return s3;
//	}
//	public static Sample getSample4(){
//		generateSample(l4 , 4);
//		return s4;
//	}

	
	private void showMessage(String m){
		message.setText(m);
	}

	
	@FXML
	private void showData(){
		
		ObservableList<String> z1 = FXCollections.observableArrayList();
		ObservableList<String> z2 = FXCollections.observableArrayList();
		for (int i = 0; i < 100; i++){
			String z = Double.toString((Math.random()+1)*100);
			z1.add(z);
			z2.add(Double.toString(Math.PI*Math.random()));
			
			
		}
		sampleList1.setItems(z1);
		sampleList2.setItems(z2);
		
		
		
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

	
	
	
	

}
