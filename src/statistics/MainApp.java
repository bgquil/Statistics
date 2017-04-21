


package statistics;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.BoxplotDialogController;
import view.ChiSquaredDialogController;
import view.DescriptiveStatisticsController;
import view.RegressionDialogController;
import view.RootLayoutController;
import view.SampleOverviewController;
import view.SampleOverviewTableController;
import view.ScatterplotDialogController;
import view.TTestDialogController;
import view.ZTestDialogController;

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
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
			
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
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/DescriptiveStatisticsView.fxml"));
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
	
	
	public void showScatterplot(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/ScatterplotDialog.fxml"));
			AnchorPane Scatterplot = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Scatterplot");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(Scatterplot);
			dialogStage.setScene(scene);
			
		
			
			ScatterplotDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void showSamples(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/SampleOverview.fxml"));
			ScrollPane samplesOverview = (ScrollPane) loader.load();
			
			rootLayout.setCenter(samplesOverview);
			
			SampleOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showSamplesTable(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/SampleOverviewTable.fxml"));
			AnchorPane samplesTableOverview = (AnchorPane) loader.load();
			
			rootLayout.setCenter(samplesTableOverview);
			
			SampleOverviewTableController controller = loader.getController();
			controller.setMainApp(this);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showZTestDialog(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/ZTestDialog.fxml"));
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
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/TTestDialog.fxml"));
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
	
	public void showBoxplotDialog(){
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/BoxplotDialog.fxml"));
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
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/RegressionDialog.fxml"));
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
		
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/ChiSquaredDialog.fxml"));
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
