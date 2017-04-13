package view;



import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.ChiSquared;
import statistics.MathFunctions;

public class ChiSquaredDialogController {
	
	
private Stage dialogStage;
	
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private Label errorMessage;
	@FXML 
	private ChoiceBox<Number> row;
	@FXML 
	private ChoiceBox<Number> col;
	@FXML
	private GridPane gp;
	@FXML
	private GridPane gpHypothesis;
	@FXML
	private TextField DOFTextField;
	@FXML
	private TextField ChiStatisticTextField;
	@FXML
	private TextField pValueTexField;
	
	
	int rSize;
	int cSize;

	/*
	 * 
	 */
	@FXML
	private void initialize() {
		row.getItems().addAll(1,2,3,4);
		col.getItems().addAll(1,2,3,4);
		DOFTextField.setEditable(false);
		ChiStatisticTextField.setEditable(false);
		pValueTexField.setEditable(false);
	}
	
	@FXML
	private void handleSetupGrid(){
		gp.getChildren().clear();
		
		//get the users desired size +1, index starts at 0.
		rSize = row.getSelectionModel().getSelectedIndex()+1;
		cSize = col.getSelectionModel().getSelectedIndex()+1;
		

		
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
				TextField t0 = new TextField();
				t0.setStyle("-fx-font-size: 12pt;");
				gp.add(t0, columnIndex, rowIndex );
			}
		}
		gp.autosize();
		
	
		
	}
	
	@FXML
	private void clearGP(){
		
	}
	
	@FXML
	private void handleGetMatrixData(){
		
		double[][] data = new double[rSize][cSize];
		try {
			// Traverses by row so 2 x 2: [1 2] 
			//							: [3 4]

			for (Node child : gp.getChildren()){
				Integer row =  gp.getRowIndex(child);
				Integer column = gp.getColumnIndex(child);
				if (row != null && column != null)
					data[row][column] = Double.parseDouble(((TextField) child).getText());
			}
			handleCalculateChiSquared(data);
			
		} catch (Exception e) {
			errorMessage.setText("Error: only numbers may be entered into the matrix.");
			
		}

		
	}
	
	
	@FXML
	private void handleCalculateChiSquared(double[][] matrixData){
		//gp.getChildren().clear();
		//gpHypothesis.getChildren().clear();
		
		double[][] data = new double[rSize][cSize];
		
//		// Traverses by row so 2 x 2 1 2 
//		//							 3 4
//		for (Node child : gp.getChildren()){
//			Integer row =  gp.getRowIndex(child);
//			Integer column = gp.getColumnIndex(child);
//			if (row != null && column != null)
//				data[row][column] = Double.parseDouble(((TextField) child).getText());
//		}
		
		ChiSquared c = new ChiSquared(matrixData, rSize, cSize);
		
		
		c.printMatrix();
		c.printHypothesisMatrix();
		c.printExpectedMatrix();
		
		
		//Setup totals
		//rows
		double[][] calcMatrix = c.getDataHypothesis(); 
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){

			TextField t = new TextField(Double.toString(calcMatrix[rowIndex][cSize]));
			t.setStyle("-fx-background-color: white, white, #c0c0c0; -fx-font-size: 12pt;");
			t.setEditable(false);
			gp.add(t, cSize, rowIndex );
		}
		
		//columns
		for (int columnIndex = 0; columnIndex < cSize; columnIndex++){

			TextField t = new TextField(Double.toString(calcMatrix[rSize][columnIndex]));
			t.setStyle("-fx-background-color: white, white, #c0c0c0; -fx-font-size: 12pt;");
			t.setEditable(false);
			gp.add(t, columnIndex, rSize );
		}
		
		TextField tTotal = new TextField(Double.toString(calcMatrix[rSize][cSize]));
		tTotal.setStyle("-fx-background-color: white, white, #00ffff; -fx-font-size: 12pt;");
		tTotal.setEditable(false);
		gp.add(tTotal, cSize, rSize );
		
		
		
		

		// Setup bottom gridpane matrix containing expected matrix values
		double[][] dataExpected = c.getDataExpected();

		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
				TextField t0 = new TextField(Double.toString(dataExpected[rowIndex][columnIndex]));
				t0.setStyle("-fx-font-size: 12pt;");
				t0.setEditable(false);
				gpHypothesis.add(t0, columnIndex, rowIndex );
			}
		}
		gpHypothesis.autosize();
		
		double DOF = c.getDOF();
		DOFTextField.setText(Integer.toString((int) DOF));
		ChiStatisticTextField.setText(Double.toString(c.getChiStatistic()));
		pValueTexField.setText(Double.toString(c.getpValue()));
		
		showChiSquaredGraph(DOF, c.getChiStatistic());

	}
		
	private void showChiSquaredGraph(double DOF, double x2){
		

		
		try{
			
			Stage graphStage = new Stage();
			graphStage.setTitle("Statistics - Chi-Squared Distribution");
			graphStage.initModality(Modality.NONE);
			graphStage.initOwner(dialogStage);
			
			
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("x");
	        yAxis.setLabel("P(x)");

	        final AreaChart<Number,Number> areaChart = 
	                new AreaChart<Number,Number>(xAxis,yAxis);   
	        areaChart.setTitle("Normal Distribution");
	             
	        //areaChart.getData().addAll(generateChiSquaredDistribution(3.0), generateAreaUnderCurve(-4.0d, z.getZScore()));
	        areaChart.getData().add(generateChiSquaredDistribution(DOF));
	        areaChart.getData().add(generateAreaUnderCurve(x2,DOF));
	        Scene scene  = new Scene(areaChart,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/ZTestChart.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
			
		
	}
	
	
	private XYChart.Series<Number, Number> generateChiSquaredDistribution(double n){
		XYChart.Series<Number, Number> chiSquaredCurve = new XYChart.Series<Number, Number>();
		
		
		/*
         * Curve of a Chi-Squared Distribution
         * 
         */
        for (double x = 0d; x < 16d; x = x +.01d){
        	
        	double numerator = Math.pow(x, ((n/2d)-1d)) * Math.exp(-x/2d);
        	double denominator = Math.pow(2, (n/2d)) * gamma(n/2d);
        	
        	double y = numerator/denominator;
        	
        	chiSquaredCurve.getData().add(new Data<Number, Number>(x, y));		
        }
		return chiSquaredCurve;
	}
	
	
	/*
	 * Generates the data points for the curve under a normal distribution curve
	 */
	private XYChart.Series<Number, Number> generateAreaUnderCurve(double start, double n){
		
        XYChart.Series<Number, Number> area = new XYChart.Series<Number, Number>();

        /*
         * Curve of a Chi-Squared Distribution
         * 
         */
        for (double x = start; x < 16d; x = x +.01d){
        	
        	double numerator = Math.pow(x, ((n/2d)-1d)) * Math.exp(-x/2d);
        	double denominator = Math.pow(2, (n/2d)) * MathFunctions.gamma(n/2d);
        	
        	double y = numerator/denominator;
        	
        	area.getData().add(new Data<Number, Number>(x, y));		
        }
		return area;
		
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
	 //-fx-alignment: center;
	
}
	 

//	private void addTextField(int row, int column){
//		for (int i = 0; i < row; i++){
//			
//			TextField  = new TextField();
//		}
//		
//		p.getChildren().add(newField);
//		
//	}
	


