package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import statistics.DataEntry;
import statistics.MainApp;
import statistics.Sample;

public class SampleOverviewTableController {
	
	
	private Stage fStage;
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp){
		
		this.mainApp = mainApp;
	}
	
	public SampleOverviewTableController() {
	}
	
	@FXML
	private TableView<DataEntry> table;
	@FXML
	private TableColumn<DataEntry,String> c1;
	@FXML
	private TableColumn<DataEntry,String> c2;
	@FXML
	private TableColumn<DataEntry,String> c3;
	@FXML
	private TableColumn<DataEntry,String> c4;
	@FXML
	private TableColumn<DataEntry,String> c5;
	@FXML
	private TableColumn<DataEntry,String> c6;
	@FXML
	private TableColumn<DataEntry,String> c7;
	@FXML
	private TableColumn<DataEntry,String> c8;
	@FXML
	private TableColumn<DataEntry,String> c9;
	@FXML
	private TableColumn<DataEntry,String> c10;
	@FXML
	private TableColumn<DataEntry,String> c11;
	@FXML
	private TableColumn<DataEntry,String> c12;
	@FXML
	private TableColumn<DataEntry,String> c13;

	
	@FXML
	private void initialize(){
		
		c1.setCellValueFactory(cellData -> cellData.getValue().c1Property());
		c2.setCellValueFactory(cellData -> cellData.getValue().c2Property());
		c3.setCellValueFactory(cellData -> cellData.getValue().c3Property());
		c4.setCellValueFactory(cellData -> cellData.getValue().c4Property());
		c5.setCellValueFactory(cellData -> cellData.getValue().c5Property());
		c6.setCellValueFactory(cellData -> cellData.getValue().c6Property());
		c7.setCellValueFactory(cellData -> cellData.getValue().c7Property());
		c8.setCellValueFactory(cellData -> cellData.getValue().c8Property());
		c9.setCellValueFactory(cellData -> cellData.getValue().c9Property());
		c10.setCellValueFactory(cellData -> cellData.getValue().c10Property());
		c11.setCellValueFactory(cellData -> cellData.getValue().c11Property());
		c12.setCellValueFactory(cellData -> cellData.getValue().c12Property());
		c13.setCellValueFactory(cellData -> cellData.getValue().c13Property());
		
		
	}
	
	private double[] generateDouble(TableColumn<DataEntry,String> c){
		
		ObservableList<Double> l = FXCollections.observableArrayList();  
		int size = table.getItems().size();
		for (int i = 0; i < size; i++){
			try {
				l.add(Double.parseDouble(c.getCellData(i)));	
			} catch (Exception e) {
				// TODO: handle exception
			}
				
		}
		
		double d[] = new double[l.size()];
		for(int i = 0; i < l.size(); i++){
			d[i] = l.get(i);
		}
		return d;
		
	}
	
//	public static Sample getSample(){
//		Sample s = 
//		return s1;
//	}
//	
	
	
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
		
        String line = "";
        String cvsSplitBy = ",";
        ObservableList<DataEntry> o  = FXCollections.observableArrayList();
        DataEntry d = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
            	
            	String entry[] = new String[13];
            	 String[] splitEntry = line.split(cvsSplitBy);
            	 for (int i = 0; i < splitEntry.length; i++){
            		entry[i] = splitEntry[i];
            	 }
            	 System.out.println(entry.length);
            
            	
                // use comma as separator
               
                d = new DataEntry();
                if (entry[0] != null)
                	d.setC1(entry[0]);
                if (entry[1] != null)
                	d.setC2(entry[1]);
                if (entry[2] != null)
                	d.setC3(entry[2]);
                if (entry[3] != null)
	                d.setC4(entry[3]);
                if (entry[4] != null)
	                d.setC5(entry[4]);
                if (entry[5] != null)
	                d.setC6(entry[5]);
                if (entry[6] != null)
	                d.setC7(entry[6]);
                if (entry[7] != null)
	                d.setC8(entry[7]);
                if (entry[8] != null)
	                d.setC9(entry[8]);
                if (entry[9] != null)
	                d.setC10(entry[9]);
                if (entry[10] != null)
	                d.setC11(entry[10]);
                if (entry[11] != null)
	                d.setC12(entry[11]);
                if (entry[12] != null)
	                d.setC13(entry[12]);
	  
               
                o.add(d);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(o);
		
	}
	
	

}
