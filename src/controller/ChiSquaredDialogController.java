package controller;



import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
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
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private AreaChart<Number, Number> chiSquaredChart;
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
    @FXML
    private AnchorPane tablePane;

    int rSize;
    int cSize;

    /*
	 * 
	 */
    @FXML
    private void initialize() {
        row.getItems().addAll(1, 2, 3, 4);
        col.getItems().addAll(1, 2, 3, 4);
        DOFTextField.setEditable(false);
        ChiStatisticTextField.setEditable(false);
        pValueTexField.setEditable(false);

    }

    @FXML
    private void handleSetupGrid() {
        gp.getChildren().clear();

        //get the users desired size +1, index starts at 0.
        rSize = row.getSelectionModel().getSelectedIndex() + 1;
        cSize = col.getSelectionModel().getSelectedIndex() + 1;


        for (int rowIndex = 0; rowIndex < rSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < cSize; columnIndex++) {
                TextField t0 = new TextField();
                t0.setStyle("-fx-font-size: 12pt;");
                gp.add(t0, columnIndex, rowIndex);
            }
        }
        gp.autosize();


    }

    @FXML
    private void clearGP() {

    }

    public double formatDouble(double val, int places) {

        BigDecimal d = new BigDecimal(val);
        d = d.setScale(places, RoundingMode.HALF_UP);
        return d.doubleValue();
    }

    @FXML
    private void handleGetMatrixData() {

        double[][] data = new double[rSize][cSize];
        try {
            // Traverses by row so 2 x 2: [1 2]
            //							: [3 4]

            for (Node child : gp.getChildren()) {
                Integer row = gp.getRowIndex(child);
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
    private void handleCalculateChiSquared(double[][] matrixData) {
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
        for (int rowIndex = 0; rowIndex < rSize; rowIndex++) {

            TextField t = new TextField(Double.toString(calcMatrix[rowIndex][cSize]));
            t.setStyle("-fx-background-color: white, white, #c0c0c0; -fx-font-size: 12pt;");
            t.setEditable(false);
            gp.add(t, cSize, rowIndex);
        }

        //columns
        for (int columnIndex = 0; columnIndex < cSize; columnIndex++) {

            TextField t = new TextField(Double.toString(calcMatrix[rSize][columnIndex]));
            t.setStyle("-fx-background-color: white, white, #c0c0c0; -fx-font-size: 12pt;");
            t.setEditable(false);
            gp.add(t, columnIndex, rSize);
        }

        TextField tTotal = new TextField(Double.toString(calcMatrix[rSize][cSize]));
        tTotal.setStyle("-fx-background-color: white, white, #00ffff; -fx-font-size: 12pt;");
        tTotal.setEditable(false);
        gp.add(tTotal, cSize, rSize);


        // Setup bottom gridpane matrix containing expected matrix values
        double[][] dataExpected = c.getDataExpected();

        for (int rowIndex = 0; rowIndex < rSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < cSize; columnIndex++) {
                TextField t0 = new TextField(Double.toString(formatDouble(dataExpected[rowIndex][columnIndex], 3)));
                t0.setStyle("-fx-font-size: 12pt;");
                t0.setEditable(false);
                gpHypothesis.add(t0, columnIndex, rowIndex);
            }
        }
        gpHypothesis.autosize();

        double DOF = c.getDOF();
        DOFTextField.setText(Integer.toString((int) DOF));
        ChiStatisticTextField.setText(Double.toString(formatDouble(c.getChiStatistic(), 5)));
        pValueTexField.setText(Double.toString(formatDouble(c.getpValue(), 5)));

        showChiSquaredGraph(DOF, c.getChiStatistic());

    }

	private void showChiSquaredGraph(double DOF, double x2){

		try{

	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("x");
	        yAxis.setLabel("P(x)");

	        yAxis.setAutoRanging(false);

	        yAxis.setUpperBound(.5);
	        yAxis.setLowerBound(0);
	        yAxis.setTickUnit(.1);

	        xAxis.setAutoRanging(false);
	        xAxis.setLowerBound(0);
	        if (DOF < 3){

		        xAxis.setUpperBound(8);
		        xAxis.setTickUnit(1);
	        }
	        else{
	        	xAxis.setUpperBound(12);
		        xAxis.setTickUnit(1);

	        }


	        final AreaChart<Number,Number> areaChart =
	                new AreaChart<Number,Number>(xAxis,yAxis);
	        areaChart.setTitle("Chi-Squared Distribution with DOF = "+(int)DOF);
	        areaChart.setLegendVisible(false);
	        //areaChart.getData().addAll(generateChiSquaredDistribution(3.0), generateAreaUnderCurve(-4.0d, z.getZScore()));
	        areaChart.getData().add(generateChiSquaredDistribution(DOF));
	        areaChart.getData().add(generateAreaUnderCurve(x2,DOF));
            tablePane.getChildren().add(areaChart);


		}
		catch (Exception e) {
			// TODO: handle exception
		}


	}

//    @FXML
//    private void showChiSquaredGraph(double DOF, double x2) {
//        try {
//
//            xAxis.setLabel("x");
//            yAxis.setLabel("P(x)");
//
//            yAxis.setAutoRanging(false);
//
//            yAxis.setUpperBound(.5);
//            yAxis.setLowerBound(0);
//            yAxis.setTickUnit(.1);
//
//            xAxis.setAutoRanging(false);
//            xAxis.setLowerBound(0);
//            if (DOF < 3) {
//
//                xAxis.setUpperBound(8);
//                xAxis.setTickUnit(1);
//            } else {
//                xAxis.setUpperBound(12);
//                xAxis.setTickUnit(1);
//
//            }
//
//            chiSquaredChart.setTitle("Chi-Squared Distribution with DOF = " + (int) DOF);
//            chiSquaredChart.setLegendVisible(false);
////            areaChart.getData().addAll(generateChiSquaredDistribution(3.0), generateAreaUnderCurve(-4.0d, z.getZScore()));
//            chiSquaredChart.getData().add(generateChiSquaredDistribution(DOF));
//            chiSquaredChart.getData().add(generateAreaUnderCurve(x2, DOF));
//
//            //tablePane.getChildren().add(chiSquaredChart);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    /**
     * Generates the coordinates for a Chi-Squared distribution with a given degrees of freedom.
     * @param DOF degrees of freedom
     * @return
     */
    private XYChart.Series<Number, Number> generateChiSquaredDistribution(double DOF) {
        XYChart.Series<Number, Number> chiSquaredCurve = new XYChart.Series<Number, Number>();

        // Curve of a Chi-Squared Distribution
        for (double x = .001d; x < 16d; x = x + .01d) {

            double numerator = Math.pow(x, ((DOF / 2d) - 1d)) * Math.exp(-x / 2d);
            double denominator = Math.pow(2, (DOF / 2d)) * MathFunctions.gamma(DOF / 2d);

            double y = numerator / denominator;
            chiSquaredCurve.getData().add(new Data<Number, Number>(x, y));
        }
        return chiSquaredCurve;
    }

    @FXML
    /**
     * Generates the data points for the curve under a normal distribution curve
     * @param start
     * @param n
     * @return
     */
    private XYChart.Series<Number, Number> generateAreaUnderCurve(double start, double n) {

        XYChart.Series<Number, Number> area = new XYChart.Series<Number, Number>();

        /*
         * Curve of a Chi-Squared Distribution
         *
         */
        for (double x = start; x < 16d; x = x + .01d) {

            double numerator = Math.pow(x, ((n / 2d) - 1d)) * Math.exp(-x / 2d);
            double denominator = Math.pow(2, (n / 2d)) * MathFunctions.gamma(n / 2d);

            double y = numerator / denominator;

            area.getData().add(new XYChart.Data<Number, Number>(x, y));
        }
        return area;

    }

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
	


