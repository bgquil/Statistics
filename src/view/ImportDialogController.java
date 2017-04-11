package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import statistics.DataEntry;

public class ImportDialogController {
	


	
	private Stage dialogStage;
	private Stage fStage;
	
	@FXML
	private TextField fileName;
	
	
	/*
	 * 
	 */
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	@FXML
	private void handleImport(){
		FileChooser f = new FileChooser();
		f.setTitle("Open File");
		
		File file = f.showOpenDialog(fStage);
		if (file != null){
			fileName.setText(file.getAbsolutePath());	
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
                d.setC13(entry[12]);
                d.setC13(entry[12]);
               
                o.add(d);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	
	
	
	
	
	
	
	

}
