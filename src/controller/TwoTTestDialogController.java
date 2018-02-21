package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.Context;
import statistics.SpecialMathFunctions;
import statistics.Sample;
import statistics.TTest;
import statistics.TwoSampleTTest;

public class TwoTTestDialogController {
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	//Tab Data
	@FXML
	private Label message;
	@FXML
	private Button startTest;
	@FXML
	private ChoiceBox<Sample> sample1ChoiceBox;
	@FXML
	private ChoiceBox<Sample> sample2ChoiceBox;
	@FXML
	private ChoiceBox<String> alternativeChoiceBox;
	@FXML
	private TextField resultTScore;
	@FXML
	private TextField resultPValue;

	final double LEFT = -8d;
	final double RIGHT = 8d;


    @FXML
    private void initialize() {

        ObservableList<Sample> samples = FXCollections.observableArrayList(
                Context.getInstance().getNonDefaultSamples()
        );
        ObservableList<String> alternativeList = FXCollections.observableArrayList(
                "Not Equal",
                "Less Than",
                "Greater Than"
        );

        sample1ChoiceBox.setItems(samples);
        sample2ChoiceBox.setItems(samples);
        //alternativeChoiceBox.setItems(alternativeList);

    }


	/*
	 * 	Calculate and show the TTest Graph.
	 */
	@FXML
	private void handleStartTestSample() {
		Sample sample1 = sample1ChoiceBox.getSelectionModel().getSelectedItem();
		Sample sample2= sample2ChoiceBox.getSelectionModel().getSelectedItem();
		setupTTest(sample1, sample2);
	}


	private double formatDouble(double val, int places){
		
		BigDecimal d = new BigDecimal(val);
		d = d.setScale(places, RoundingMode.HALF_UP);
		return d.doubleValue();
	}
	
	//From Sample
	private void setupTTest(Sample s1, Sample s2){
		
		TwoSampleTTest tTest = new TwoSampleTTest(s1, s2);
		resultTScore.setText((Double.toString(formatDouble(tTest.getTStatistic(), 4))));
//		try {
//			String selection = alternativeChoiceBox.getSelectionModel().getSelectedItem();
//			
//			
//			
//			//setupTTest(tTest, selection, "data");
//
//		} catch (Exception e) {
//			e.getMessage();
//			message.setText("Error");
//		}
	}
	

	
//	private void setupTTest(TTest t, String selection, String mode){
//		
//		
//		if (mode.equals("data")){
//			
//		switch (selection){
//			case "Not Equal":
//				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
//				resultPValue.setText(Double.toString(t.getPValue()*2d));
//				break;
//			case "Less Than":
//				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
//				resultPValue.setText(Double.toString(t.getPValue()));
//				break;
//			case "Greater Than":
//				showTTestGraph(t,t.getTStatistic(),RIGHT, selection);
//				resultPValue.setText(Double.toString(1d-t.getPValue()));
//				break;
//				}
//		}
//		else if (mode.equals("summary")){
//			System.out.println(mode);
//			
//			switch (selection){
//			case "Not Equal":
//				summaryPValue.setText(Double.toString(t.getPValue()*2d));
//				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
//				
//				break;
//			case "Less Than":
//				summaryPValue.setText(Double.toString(t.getPValue()));
//				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
//				break;
//			case "Greater Than":
//				summaryPValue.setText(Double.toString(1d-t.getPValue()));
//				showTTestGraph(t,t.getTStatistic(),RIGHT, selection);
//				break;
//			}
//		}	
//	}
	
	
	private void showTTestGraph(TTest t, double start, double end, String selection){

		try {
			double DOF = 1;
			Stage graphStage = new Stage();
			graphStage.setTitle("Statistics - T Distribution");
			graphStage.initModality(Modality.NONE);
			graphStage.initOwner(dialogStage);
			
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("x");
	        yAxis.setLabel("P(x)");
	        
	        xAxis.setAutoRanging(false);
	        xAxis.setUpperBound(5);
	        xAxis.setLowerBound(-5);
	        xAxis.setTickUnit(1);
	        

	        final AreaChart<Number,Number> areaChart = 
	                new AreaChart<Number,Number>(xAxis,yAxis);   
	        areaChart.setTitle("t-Distribution");
	       
	        if (selection.equals("Not Equal")){
	        	if(t.getPValue() > .5){
	        		areaChart.getData().add(generateTDistribution(DOF));
	        		areaChart.getData().add(generateAreaUnderCurve(LEFT, -end, DOF ));
	        		areaChart.getData().add(generateAreaUnderCurve(end, RIGHT, DOF ));
	        	}
	        	else{
	        		areaChart.getData().add(generateTDistribution(DOF));
	        		areaChart.getData().add(generateAreaUnderCurve(LEFT, end, DOF ));
	        		areaChart.getData().add(generateAreaUnderCurve(-end, RIGHT, DOF ));
	        		
	        	}
	        	
	        }
	        else
	        	areaChart.getData().addAll(generateTDistribution(DOF), generateAreaUnderCurve(start,end, DOF));
	             
	        
	        Scene scene  = new Scene(areaChart,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/style/TTestChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Generates the data points for the curve of a t distribution
	 */
	private XYChart.Series<Number, Number> generateTDistribution(double DOF){
		XYChart.Series<Number, Number> tDistributionCurve = new XYChart.Series<Number, Number>();
		/*
         * Curve of a t Distribution
         * 
         */
        double mu = 0; //mean = 0
        
        for (double x = LEFT; x < RIGHT; x = x +.1d){
        	double A = Math.exp(Math.log(SpecialMathFunctions.gamma((DOF+1d)/2d)));
        	double B = Math.exp(Math.log(SpecialMathFunctions.gamma(DOF/2d))) * Math.sqrt(DOF*Math.PI);
        	double C = Math.pow((1+x*x/DOF),(-(DOF+1)/2d));
        	
        	double y = A*C/B;
        	tDistributionCurve.getData().add(new Data<Number, Number>(x, y));		
        }
		return tDistributionCurve;
	}
	
	/*
	 * Generates the data points for the area under a t distribution curve
	 */
	private XYChart.Series<Number, Number> generateAreaUnderCurve(double start, double end, double DOF){
		
		XYChart.Series<Number, Number> tDistributionCurve = new XYChart.Series<Number, Number>();
		/*
         * area under Curve of a t Distribution
         * 
         */
        double mu = 0; //mean = 0
        
        for (double x = start; x < end; x = x +.1d){
        	double A = Math.exp(Math.log(SpecialMathFunctions.gamma((DOF+1d)/2d)));
        	double B = Math.exp(Math.log(SpecialMathFunctions.gamma(DOF/2d))) * Math.sqrt(DOF*Math.PI);
        	double C = Math.pow((1+x*x/DOF),(-(DOF+1)/2d));
        	
        	double y = A*C/B;
        	tDistributionCurve.getData().add(new Data<Number, Number>(x, y));		
        }
		return tDistributionCurve;
		
	}
}
