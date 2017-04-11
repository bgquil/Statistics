package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import statistics.DataEntry;
import statistics.MainApp;

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
	
	private void test(TableColumn<DataEntry,String> c){
		int size = table.getItems().size();
		for (int i = 0; i < size; i++){
			Double.parseDouble(c.getCellData(i));	
		}
		
	}
	
	
	
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
            	
            	

                // use comma as separator
                String[] entry = line.split(cvsSplitBy);
                d = new DataEntry();
                d.setC1(entry[0]);
                d.setC2(entry[1]);
                d.setC3(entry[2]);
                d.setC4(entry[3]);
                d.setC5(entry[4]);
                d.setC6(entry[5]);
                d.setC7(entry[6]);
                d.setC8(entry[7]);
                d.setC9(entry[8]);
                d.setC10(entry[9]);
                d.setC11(entry[10]);
                d.setC12(entry[11]);
                //d.setC13(entry[12]);
               
                o.add(d);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(o);
		
	}
	
	

}
