package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.Context;
import statistics.LinearRegression;
import statistics.Sample;

public class RegressionDialogController {
	
	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private ChoiceBox<Sample> indSampleChoiceBox;
	@FXML
	private ChoiceBox<Sample> depSampleChoiceBox;
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
        // Setup Sample selection ChoiceBoxes
	    ObservableList<Sample> samples = FXCollections.observableArrayList(
	            Context.getInstance().getNonDefaultSamples()
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
        Sample ind = indSampleChoiceBox.getSelectionModel().getSelectedItem();
		Sample dep = depSampleChoiceBox.getSelectionModel().getSelectedItem();

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
	        xAxis.setAutoRanging(true);
	        yAxis.setAutoRanging(true);
	        
	        final LineChart<Number,Number> regressionGraph = 
	                new LineChart<Number,Number>(xAxis,yAxis);   
	        

	        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	               

			
			for (int i = 0; i < ind.getSampleSize(); i++){
				series.getData().add(new Data<Number, Number>(ind.get(i),dep.get(i)));
			}
	        
	        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<Number, Number>();
	        
	        
	        LinearRegression lr = new LinearRegression(ind,dep);
	        double lr_b0 = lr.getB0();
	        double lr_b1 = lr.getB1();
	        double min = ind.getSampleMin();
	        
	        for ( double x = xAxis.getLowerBound(); x < ind.getSampleMax()+ind.getSampleStdDev(); x = x + .5){
	        	
	        	double y = lr_b0 + lr_b1*x ;
	        	lineSeries.getData().add(new XYChart.Data<>(x, y));
	        }
	        
	        line.setText(lr.toString());
	        r.setText(Double.toString(lr.getR()));
	        r2.setText(Double.toString(lr.getR2()));
	        line.setVisible(true);
	        r.setVisible(true);
	        r2.setVisible(true);
	        
	        regressionGraph.setTitle("Line: "+lr.toString()+"\tr: "+lr.getR()+" R-squared: "+lr.getR2());
	        
	        regressionGraph.setAnimated(true);
	        regressionGraph.setCreateSymbols(true);
	        regressionGraph.getData().addAll(series,lineSeries);
	        regressionGraph.setLegendVisible(false);
	        
//	        xAxis.setLowerBound(ind.getSampleMin()-ind.getSampleStdDev());
//	        xAxis.setUpperBound(ind.getSampleMax()+ind.getSampleStdDev());
//	        
//	        yAxis.setLowerBound(dep.getSampleMin()-dep.getSampleStdDev());
//	        yAxis.setUpperBound(dep.getSampleMax()+dep.getSampleStdDev());
	        
	        xAxis.getUpperBound();



	        Scene scene  = new Scene(regressionGraph,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/RegressionChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
