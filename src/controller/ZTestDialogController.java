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
import statistics.Sample;
import statistics.ZTest;

public class ZTestDialogController {
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	/*
	 * 
	 */
	@FXML
	private void initialize() {
		
		ObservableList<String> samples = FXCollections.observableArrayList("Data Set 1",
				"Data Set 2",
				"Data Set 3",
				"Data Set 4",
				"Data Set 5",
				"Data Set 6");
		ObservableList<String> alternativeList = FXCollections.observableArrayList(
				"Not Equal",
				"Less Than",
				"Greater Than"
				);
		
		sampleChoiceBox.setItems(samples);
		alternativeChoiceBox.setItems(alternativeList);
		summaryAlternativeChoiceBox.setItems(alternativeList);
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
	private ChoiceBox<String> sampleChoiceBox;
	@FXML
	private ChoiceBox<String> alternativeChoiceBox;
	@FXML
	private TextField resultZScore;
	@FXML
	private TextField resultPValue;
	
	//Tab From Summary
	@FXML
	private Button startTestSummary;
	@FXML
	private TextField nullHypMean;
	@FXML
	private TextField popStdDev;
	@FXML
	private TextField sampleMeanTextField;
	@FXML
	private TextField sampleNumTextField;
	@FXML
	private ChoiceBox<String> summaryAlternativeChoiceBox;
	@FXML
	private TextField summaryZScore;
	@FXML
	private TextField summaryPValue;
	

	/*
	 * 	Calculate and show the ZTest Graph.
	 */
	@FXML
	private void handleStartTestSample() {

		int selection = sampleChoiceBox.getSelectionModel().getSelectedIndex();
		switch (selection) {
		
		case 0:
			setupZTest(Context.getInstance().getS1());
			break;
		case 1:
			setupZTest(Context.getInstance().getS2());
			break;
		case 2:
			setupZTest(Context.getInstance().getS3());
			break;
		case 3:
			setupZTest(Context.getInstance().getS4());
			break;
		case 4:
			setupZTest(Context.getInstance().getS5());
			break;
		case 5:
			setupZTest(Context.getInstance().getS6());
			break;
		
			
		}
	}
	
	@FXML
	private void handleStartTestSummary(){
		
		double popMean = (double) Double.parseDouble(nullHypMean.getText());
		double stdDev = (double) Double.parseDouble(popStdDev.getText());
		double sMean = (double) Double.parseDouble(sampleMeanTextField.getText());
		double sNum = (double) Double.parseDouble(sampleNumTextField.getText());
		String selection = summaryAlternativeChoiceBox.getSelectionModel().getSelectedItem();
		ZTest zSummary = new ZTest(popMean, stdDev, sMean, sNum );
		summaryZScore.setText(Double.toString(zSummary.getZScore()));
		summaryPValue.setText(Double.toString(zSummary.getPValue()));
		
		setupZTest(zSummary, selection, "summary");
	}
	
	private void setupZTest(Sample s){
		String selection = alternativeChoiceBox.getSelectionModel().getSelectedItem();
		try {
			double popMean = (double) Double.parseDouble(popMeanTextBox.getText());
			double stdDev = (double) Double.parseDouble(popStdDeviationTextBox.getText());
			
			ZTest zSample = new ZTest(s, popMean, stdDev);
			resultZScore.setText(Double.toString(zSample.getZScore()));
//			resultPValue.setText(Double.toString(zSample.getPValue()));
			
			setupZTest(zSample, selection, "data");

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			message.setText("Error");
		}
	}
	

	
	private void setupZTest(ZTest z, String selection, String mode){
		System.out.println(z.getPValue());
		
		if (mode.equals("data")){
		
			switch (selection){
			case "Not Equal":
				if(z.getZScore() > 0)
					resultPValue.setText(Double.toString((1-z.getPValue())*2d));
				else
					resultPValue.setText(Double.toString((z.getPValue())*2d));
				showZTestGraph(z,-4d,z.getZScore(), selection);
				break;
			case "Less Than":
				resultPValue.setText(Double.toString(z.getPValue()));
				showZTestGraph(z,-4,z.getZScore(), selection);
				break;
			case "Greater Than":
				resultPValue.setText(Double.toString(1-z.getPValue()));
				showZTestGraph(z,z.getZScore(),4, selection);
				break;
			}
		}
		else if (mode.equals("summary")){

			
			switch (selection){
			case "Not Equal":
				if(z.getZScore() > 0)
					summaryPValue.setText(Double.toString((1-z.getPValue())*2d));
				else
					summaryPValue.setText(Double.toString((z.getPValue())*2d));
				showZTestGraph(z,-4d,z.getZScore(), selection);
				break;
			case "Less Than":
				summaryPValue.setText(Double.toString(z.getPValue()));
				showZTestGraph(z,-4,z.getZScore(), selection);				
				break;
			case "Greater Than":
				summaryPValue.setText(Double.toString(1-z.getPValue()));
				showZTestGraph(z,z.getZScore(),4, selection);
				break;
			}
		}
	}
	
	
	private void showZTestGraph(ZTest z, double start, double end, String selection){
		try {
			
			Stage graphStage = new Stage();
			graphStage.setTitle("Statistics - Normal Distribution");
			graphStage.initModality(Modality.NONE);
			graphStage.initOwner(dialogStage);
			
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("x");
	        yAxis.setLabel("P(x)");
	        
	        xAxis.setAutoRanging(false);
	        xAxis.setUpperBound(4);
	        xAxis.setLowerBound(-4);
	        xAxis.setTickUnit(1);

	        final AreaChart<Number,Number> areaChart = 
	                new AreaChart<Number,Number>(xAxis,yAxis);   
	        areaChart.setTitle("Normal Distribution");
	             
	        if (selection.equals("Not Equal")){
	        	if(z.getPValue() > .5){
	        		areaChart.getData().add(generateNormalDistribution());
	        		areaChart.getData().add(generateAreaUnderCurve(-4.0d, -end ));
	        		areaChart.getData().add(generateAreaUnderCurve(end, 4.0d ));
	        	}
	        	else{
	        		areaChart.getData().add(generateNormalDistribution());
	        		areaChart.getData().add(generateAreaUnderCurve(-4.0d, end ));
	        		areaChart.getData().add(generateAreaUnderCurve(-end, 4.0d ));
	        		
	        	}
	        	
	        }
	        else
	        	areaChart.getData().addAll(generateNormalDistribution(), generateAreaUnderCurve(start,end));
	        
	        Scene scene  = new Scene(areaChart,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/ZTestChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Generates the data points for the curve of a normal distribution
	 */
	private XYChart.Series<Number, Number> generateNormalDistribution(){
		XYChart.Series<Number, Number> normalDistributionCurve = new XYChart.Series<Number, Number>();
		/*
         * Curve of a Normal Distribution
         * 
         */
        double mu = 0; //mean = 0
        double sigma = 1; //variance = 1
        
        for (double x = -4.0d; x < 4.0d; x = x +.1d){
        	double A = 1.0d/(sigma*Math.sqrt(2*Math.PI));
        	double B = Math.exp(-(x-mu)*(x-mu) / (2.0d * sigma * sigma));
        	double y = A*B;
        	normalDistributionCurve.getData().add(new Data<Number, Number>(x, y));		
        }
		return normalDistributionCurve;
	}
	
	/*
	 * Generates the data points for the curve under a normal distribution curve
	 */
	private XYChart.Series<Number, Number> generateAreaUnderCurve(double start, double end){
		
        XYChart.Series<Number, Number> area = new XYChart.Series<Number, Number>();

        /*
         *  Area under normal curve generation
         */
        double mu = 0; //mean = 0
        double sigma = 1; //variance = 1
        for (double x = start; x < end; x = x +.01d){
        	double A = 1.0d/(sigma*Math.sqrt(2*Math.PI));
        	double B = Math.exp(-(x-mu)*(x-mu) / (2.0d * sigma * sigma));
        	double y = A*B;
        	area.getData().add(new Data<Number, Number>(x, y));	
        }
        return area;
		
	}
	
	
	
}
