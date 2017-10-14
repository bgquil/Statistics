package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import core.MainApp;


public class RootLayoutController {
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/*
	 *	Opens an about dialog. 
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Statistics - About");
		alert.setHeaderText("About");
		alert.setContentText("This is an about page");
		
		alert.showAndWait();
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
		mainApp.showZTestDialog();
		
	}
	
	/*
	 * 	Open the TTestDialog.
	 */
	@FXML
	private void handleShowTTestDialog() {
		mainApp.showTTestDialog();
		
	}
	
	/*
	 * 	Open the Regression Dialog.
	 */
	@FXML
	private void handleShowRegressionDialog() {
		mainApp.showRegressionDialog();
		
	}
	
	/*
	 * 	Open the ChiSquared Dialog.
	 */
	@FXML
	private void handleShowChiSquaredDialog() {
		mainApp.showChiSquaredDialog();
		
	}
	
	/*
	 * 	Open the Boxplot Dialog.
	 */
	@FXML
	private void handleShowBoxplotDialog() {
		mainApp.showBoxplotDialog();
		
	}
	
	/*
	 * 	Open the Sample Statistics Dialog.
	 */
	@FXML
	private void handleShowDescriptiveStatistics() {
		mainApp.showDescriptiveStatistics();
		
	}
	
	/*
	 * 	Open the Sample Statistics Dialog.
	 */
	@FXML
	private void handleShowScatterplot() {
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
