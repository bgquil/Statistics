package controller;

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
import statistics.MathFunctions;
import statistics.Sample;
import statistics.TTest;

public class TTestDialogController {
	
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
	private TextField popMeanTextBox;
	@FXML
	private TextField popStdDeviationTextBox;
	@FXML
	private ChoiceBox<Sample> sampleChoiceBox;
	@FXML
	private ChoiceBox<String> alternativeChoiceBox;
	@FXML
	private TextField resultTScore;
	@FXML
	private TextField resultPValue;
	
	//Tab From Summary
	@FXML
	private Button startTestSummary;
	@FXML
	private TextField nullHypMean;
	@FXML
	private TextField sampleStdDev;
	@FXML
	private TextField sampleMeanTextField;
	@FXML
	private TextField sampleNumTextField;
	@FXML
	private ChoiceBox<String> summaryAlternativeChoiceBox;
	@FXML
	private TextField summaryTScore;
	@FXML
	private TextField summaryPValue;


    /*
 *
 */
    @FXML
    private void initialize() {

        // Setup Sample selection ChoiceBoxes
        ObservableList<Sample> samples = FXCollections.observableArrayList(
                Context.getInstance().getNonDefaultSamples()
        );

        ObservableList<String> alternativeList = FXCollections.observableArrayList(
                "Not Equal",
                "Less Than",
                "Greater Than"
        );

        sampleChoiceBox.setItems(samples);
        alternativeChoiceBox.setItems(alternativeList);
        summaryAlternativeChoiceBox.setItems(alternativeList);
    }
	

	final double LEFT = -8d;
	final double RIGHT = 8d;

	
	

	/*
	 * 	Calculate and show the TTest Graph.
	 */
	@FXML
	private void handleStartTestSample() {
		Sample selection = sampleChoiceBox.getSelectionModel().getSelectedItem();
		setupTTest(selection);
	}
	//From Summary
	@FXML
	private void handleStartTestSummary(){
		
		double popMean = (double) Double.parseDouble(nullHypMean.getText());
		double stdDev = (double) Double.parseDouble(sampleStdDev.getText());
		double sMean = (double) Double.parseDouble(sampleMeanTextField.getText());
		int sNum = (int) Double.parseDouble(sampleNumTextField.getText());
		String selection = summaryAlternativeChoiceBox.getSelectionModel().getSelectedItem();
		TTest tSummary = new TTest(sMean,sNum,stdDev, popMean);
		summaryTScore.setText(Double.toString(tSummary.getTStatistic()));
		
		setupTTest(tSummary, selection, "summary");
	}
	
	//From Sample
	private void setupTTest(Sample s){
		
		try {
			double popMean = (double) Double.parseDouble(popMeanTextBox.getText());
			//double stdDev = (double) Double.parseDouble(popStdDeviationTextBox.getText());
			String selection = alternativeChoiceBox.getSelectionModel().getSelectedItem();
			TTest tTest = new TTest(s, popMean);
			resultTScore.setText(Double.toString(tTest.getTStatistic()));
			setupTTest(tTest, selection, "data");

		} catch (Exception e) {
			e.getMessage();
			message.setText("Error");
		}
		
		
		
	}
	

	
	private void setupTTest(TTest t, String selection, String mode){
		
		
		if (mode.equals("data")){
			
		switch (selection){
			case "Not Equal":
				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
				resultPValue.setText(Double.toString(t.getPValue()*2d));
				break;
			case "Less Than":
				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
				resultPValue.setText(Double.toString(t.getPValue()));
				break;
			case "Greater Than":
				showTTestGraph(t,t.getTStatistic(),RIGHT, selection);
				resultPValue.setText(Double.toString(1d-t.getPValue()));
				break;
				}
		}
		else if (mode.equals("summary")){
			System.out.println(mode);
			
			switch (selection){
			case "Not Equal":
				if(t.getTStatistic() > 0)
					summaryPValue.setText(Double.toString(1-t.getPValue()*2d));
				else
					summaryPValue.setText(Double.toString(t.getPValue()*2d));
				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
				
				break;
			case "Less Than":
				summaryPValue.setText(Double.toString(t.getPValue()));
				showTTestGraph(t,LEFT,t.getTStatistic(), selection);
				break;
			case "Greater Than":
				summaryPValue.setText(Double.toString(1d-t.getPValue()));
				showTTestGraph(t,t.getTStatistic(),RIGHT, selection);
				break;
			}
		}	
	}
	
	
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
        	double A = Math.exp(Math.log(MathFunctions.gamma((DOF+1d)/2d)));
        	double B = Math.exp(Math.log(MathFunctions.gamma(DOF/2d))) * Math.sqrt(DOF*Math.PI);
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
        	double A = Math.exp(Math.log(MathFunctions.gamma((DOF+1d)/2d)));
        	double B = Math.exp(Math.log(MathFunctions.gamma(DOF/2d))) * Math.sqrt(DOF*Math.PI);
        	double C = Math.pow((1+x*x/DOF),(-(DOF+1)/2d));
        	
        	double y = A*C/B;
        	tDistributionCurve.getData().add(new Data<Number, Number>(x, y));		
        }
		return tDistributionCurve;
		
	}
	
	
	/*	Helper function to draw the curve of a chisquared distribution
	 * BQ: Adapted from:
	 * 
	 *	`20-July-1997
	 *	`Bryan Lewis
	 *	`Department of Mathematics and Computer Science
	 *	`Kent State University
	 *
	 * 	`This software is in the public domain and can
	 * 	`be copied, modified and used without restriction.
	 *
	 *		`(*) Press, Flannery, Teukolsky, Vetterling, Numerical Recipes,
	 *		`Cambridge University Press, 1986
	 * 
	 * 
	 * 
	 */
		
	public static double gamma(double x) {
		// An approximation of gamma(x)
		double f = 10E99;
		double g = 1;
		if ( x > 0 ) {
			while (x < 3) {
				g = g * x;
				x = x + 1;
			}
			f = (1 - (2/(7*Math.pow(x,2))) * (1 - 2/(3*Math.pow(x,2))))/(30*Math.pow(x,2));
			f = (1-f)/(12*x) + x*(Math.log(x)-1);
			f = (Math.exp(f)/g)*Math.pow(2*Math.PI/x,0.5);
		}
		else {
			Double er = new Double(0);
			f = Double.POSITIVE_INFINITY;
		}
		return f;
	}
		
	/*
	 * end Gamma
	 */
	
	
	
}
