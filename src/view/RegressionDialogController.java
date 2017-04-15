package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.Context;
import statistics.LinearRegression;
import statistics.MainApp;
import statistics.Sample;

public class RegressionDialogController {
	
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
		ObservableList<String> samples = FXCollections.observableArrayList("Data Set 1",
				"Data Set 2",
				"Data Set 3",
				"Data Set 4"
				);
	indSampleChoiceBox.setItems(samples);
	depSampleChoiceBox.setItems(samples);
	line.setVisible(false);
	r.setVisible(false);
	r2.setVisible(false);

	}
	
	/*
	 * 	Handle selection of sample from the choicebox.
	 */
	@FXML
	private void handleSelectSample() {
		
		int selectionInd = indSampleChoiceBox.getSelectionModel().getSelectedIndex();
		int selectionDep = depSampleChoiceBox.getSelectionModel().getSelectedIndex();

		
		Sample ind = null;
		Sample dep = null;
		
		switch (selectionInd) {
		
		case 0:
			ind = Context.getInstance().getS1();
			break;
		case 1:
			ind = Context.getInstance().getS2();
			break;
		case 2:
			ind = Context.getInstance().getS3();
			break;
		case 3:
			ind = Context.getInstance().getS4();
			break;
		
			
		}
		
		switch (selectionDep) {
		
		case 0:
			dep = Context.getInstance().getS1();
			break;
		case 1:
			dep = Context.getInstance().getS2();
			break;
		case 2:
			dep = Context.getInstance().getS3();
			break;
		case 3:
			dep = Context.getInstance().getS4();
			break;
		
			
		}
		if (xLabel.getText().equals(""))
			ind.setName("Independent Variable");
		else
			ind.setName(xLabel.getText());
		
		if (yLabel.getText().equals(""))
			dep.setName("Dependent Variable");
		else
			dep.setName(yLabel.getText());
		
		showRegressionGraph(ind, dep);
	}

	public void showRegressionGraph(Sample ind, Sample dep){
		
		try {
			
			Stage graphStage = new Stage();
			graphStage.setTitle("Statistics - Linear Regression");
			graphStage.initModality(Modality.NONE);
			graphStage.initOwner(dialogStage);
			

			
			
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel(ind.getName());
	        yAxis.setLabel(dep.getName());

	        final LineChart<Number,Number> regressionGraph = 
	                new LineChart<Number,Number>(xAxis,yAxis);   
	        regressionGraph.setTitle("Linear Regression");

	        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	               

			
			for (int i = 0; i < ind.getSampleSize(); i++){
				series.getData().add(new Data<Number, Number>(ind.get(i),dep.get(i)));
			}
	        
	        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<Number, Number>();
	        
	        
	        LinearRegression lr = new LinearRegression(ind,dep);
	        double lr_b0 = lr.getB0();
	        double lr_b1 = lr.getB1();
	        double stddev = ind.getSampleStdDev();
	        for ( double x = 0; x < ind.getSampleMax()+stddev; x++){
	        	
	        	double y = lr_b0 + lr_b1*x ;
	        	lineSeries.getData().add(new XYChart.Data<>(x, y));
	        }
	        
	        line.setText(lr.toString());
	        r.setText(Double.toString(lr.getR()));
	        r2.setText(Double.toString(lr.getR2()));
	        line.setVisible(true);
	        r.setVisible(true);
	        r2.setVisible(true);

	        regressionGraph.setAnimated(false);
	        regressionGraph.setCreateSymbols(true);
	        regressionGraph.getData().addAll(series,lineSeries);



	        Scene scene  = new Scene(regressionGraph,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/RegressionChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
