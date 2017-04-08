package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class ImportDialogController {
	
	@FXML
	private Button startTest;
	@FXML
	private CheckBox s1,s2;

	
	private Stage dialogStage;
	
	
	/*
	 * 
	 */
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	

}
