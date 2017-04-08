package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.LinearRegression;
import statistics.Sample;

public class ScatterplotDialogController {
	
	
	private Stage dialogStage;
	
	
	
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	@FXML
	private ChoiceBox<String> indSampleChoiceBox;
	@FXML
	private ChoiceBox<String> depSampleChoiceBox;
	@FXML
	private TextField xLabel;
	@FXML 
	private TextField yLabel;
	@FXML
	private Label line;
	@FXML
	private Label r;
	@FXML
	private Label r2;
	
	
	/*
	 * 
	 */
	@FXML
	private void initialize() {


	}
	
	
	
public void showRegressionGraph(Sample ind, Sample dep){
		
		try {
			
			Stage graphStage = new Stage();
			graphStage.setTitle("Statistics - Linear Regression");
			graphStage.initModality(Modality.WINDOW_MODAL);
			graphStage.initOwner(dialogStage);
			

			
			
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel(ind.getName());
	        yAxis.setLabel(dep.getName());

	        final ScatterChart<Number,Number> scatterGraph = 
	                new ScatterChart<Number,Number>(xAxis,yAxis);   
	        scatterGraph.setTitle("Linear Regression");

	        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	               

			
			for (int i = 0; i < ind.getSampleSize(); i++){
				series.getData().add(new Data<Number, Number>(ind.get(i),dep.get(i)));
			}
	        
	        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<Number, Number>();
	        
	        

	        



	        scatterGraph.setAnimated(false);
	        //regressionGraph.setCreateSymbols(true);
	        scatterGraph.getData().addAll(series,lineSeries);



	        Scene scene  = new Scene(scatterGraph,800,600);
	        //scene.getStylesheets().add(getClass().getResource("/view/RegressionChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
