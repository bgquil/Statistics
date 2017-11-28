


package core;

import java.io.IOException;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controller.ScatterPlotDialogController;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Statistics");
		
		initRootLayout();
		
		showSamples();
		
	}
	
	public void initRootLayout() {
	    final String viewLocation = "/view/RootLayout.fxml";
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
			
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void showDescriptiveStatistics(){
		final String viewLocation = "/view/DescriptiveStatisticsView.fxml";
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			AnchorPane DescriptiveStatistics = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Descriptive Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(DescriptiveStatistics);
			dialogStage.setScene(scene);
			
		
			
			DescriptiveStatisticsController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void showScatterPlot(){
		final String viewLocation  = "/view/ScatterPlotDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			AnchorPane ScatterPlot = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Scatter Plot");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(ScatterPlot);
			dialogStage.setScene(scene);
			
		
			
			ScatterPlotDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void showSamples(){
        final String viewLocation  = "/view/SampleOverview.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			ScrollPane samplesOverview = (ScrollPane) loader.load();
			
			rootLayout.setCenter(samplesOverview);
			
			SampleOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showZTestDialog(){
        final String viewLocation  = "/view/ZTestDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			TabPane zTestDialog = (TabPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Z Test");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(zTestDialog);
			dialogStage.setScene(scene);
			
		
			
			ZTestDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showTTestDialog(){
        final String viewLocation  = "/view/TTestDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			TabPane tTestDialog = (TabPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("T Test");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(tTestDialog);
			dialogStage.setScene(scene);
			
		
			
			TTestDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void show2TTestDialog(){
        final String viewLocation  = "/view/TwoTTestDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			AnchorPane twoTTestDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Two Sample T-Test");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(twoTTestDialog);
			dialogStage.setScene(scene);
			
		
			
			TwoTTestDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showBoxplotDialog(){
        final String viewLocation  = "/view/BoxplotDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			AnchorPane boxplotDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Boxplot");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(boxplotDialog);
			dialogStage.setScene(scene);
			
		
			
			BoxplotDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showRegressionDialog(){
        final String viewLocation  = "/view/RegressionDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			AnchorPane regressionDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Linear Regression");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(regressionDialog);
			dialogStage.setScene(scene);
			
		
			
			RegressionDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
	public void showChiSquaredDialog(){
        final String viewLocation  = "/view/ChiSquaredDialog.fxml";
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(viewLocation));
			AnchorPane chiSquaredDialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Chi-Squared Test");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(chiSquaredDialog);
			dialogStage.setScene(scene);
			
		
			
			ChiSquaredDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	


	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	

	public static void main(String[] args) {
		launch(args);
	}
}
