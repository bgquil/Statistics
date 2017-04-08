package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.MainApp;
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
		
		ObservableList<String> samples = FXCollections.observableArrayList("Data Set 1","Data Set 2","Data Set 3","Data Set 4");
		ObservableList<String> alternativeList = FXCollections.observableArrayList(
				"Two-Sided Test",
				"less than",
				"greater than"
				);
		ObservableList<String> alternativeListSummary = FXCollections.observableArrayList(
				"Not Equal",
				"Less Than",
				"Greater Than"
				);
		
		sampleChoiceBox.setItems(samples);
		alternativeChoiceBox.setItems(alternativeList);
		//summaryAlternativeChoiceBox.setItems(alternativeList);
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
	private void handleZTestGraph() {

		int selection = sampleChoiceBox.getSelectionModel().getSelectedIndex();
		switch (selection) {
		
		case 0:
			setupZTest(SampleOverviewController.getSample1());
			break;
		case 1:
			setupZTest(SampleOverviewController.getSample2());
			break;
		case 2:
			setupZTest(SampleOverviewController.getSample3());
			break;
		case 3:
			setupZTest(SampleOverviewController.getSample4());
			break;
		
			
		}
		
		
	}
	
	private void setupZTest(Sample s){
		
		try {
			double popMean = (double) Double.parseDouble(popMeanTextBox.getText());
			double stdDev = (double) Double.parseDouble(popStdDeviationTextBox.getText());
			
			ZTest z = new ZTest(s, popMean, stdDev);
			resultZScore.setText(Double.toString(z.getZScore()));
			resultPValue.setText(Double.toString(z.getPValue()));
			showZTestGraph(z);

		} catch (Exception e) {
			e.getMessage();
			message.setText("Error");
		}
		
		
		
	}
	
	
	@FXML
	private void handleStartTestSummary(){
		System.out.println("ts");
		//int selection = summaryAlternativeChoiceBox.getSelectionModel().getSelectedIndex();
		double popMean = (double) Double.parseDouble(nullHypMean.getText());
		double stdDev = (double) Double.parseDouble(popStdDev.getText());
		double sMean = (double) Double.parseDouble(sampleMeanTextField.getText());
		double sNum = (double) Double.parseDouble(sampleNumTextField.getText());
		
		ZTest z = new ZTest(popMean, stdDev, sMean, sNum );
		summaryZScore.setText(Double.toString(z.getZScore()));
		summaryPValue.setText(Double.toString(z.getPValue()));

	}
	
	
	private void showZTestGraph(ZTest z){
		
		
		try {
			
			Stage graphStage = new Stage();
			graphStage.setTitle("Statistics - Normal Distribution");
			graphStage.initModality(Modality.WINDOW_MODAL);
			graphStage.initOwner(dialogStage);
			
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("x");
	        yAxis.setLabel("P(x)");

	        final AreaChart<Number,Number> areaChart = 
	                new AreaChart<Number,Number>(xAxis,yAxis);   
	        areaChart.setTitle("Normal Distribution");
	             
	        areaChart.getData().addAll(generateNormalDistribution(), generateAreaUnderCurve(-4.0d, z.getZScore()));
	        
	        Scene scene  = new Scene(areaChart,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/ZTestChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
			
	        //FXML file scene
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("/view/ZTestGraph.fxml"));
//			AnchorPane zTestGraph = (AnchorPane) loader.load();
//			Scene scene = new Scene(zTestGraph);
//			graphStage.setScene(scene);
//			
//		
//			
//			ZTestDialogController controller = loader.getController();
//			controller.setDialogStage(graphStage);
//			
//			graphStage.showAndWait();
			
			
			
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
