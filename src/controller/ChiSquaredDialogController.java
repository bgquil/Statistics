package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import statistics.ChiSquared;
import statistics.SpecialMathFunctions;

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
    private TextField chiStatisticTextField;
    @FXML
    private TextField pValueTexField;
    @FXML
    private VBox chartBox;

    int rSize;
    int cSize;

    @FXML
    private void initialize() {
        row.getItems().addAll(1, 2, 3, 4);
        col.getItems().addAll(1, 2, 3, 4);
        DOFTextField.setEditable(false);
        chiStatisticTextField.setEditable(false);
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
    private void handleReset() {
        chartBox.getChildren().clear();
        gp.getChildren().clear();
        gpHypothesis.getChildren().clear();
        DOFTextField.clear();
        chiStatisticTextField.clear();
        pValueTexField.clear();
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
        chiStatisticTextField.setText(Double.toString(formatDouble(c.getChiStatistic(), 5)));
        pValueTexField.setText(Double.toString(formatDouble(c.getpValue(), 5)));

        showChiSquaredGraph(DOF, c.getChiStatistic());

    }

	private void showChiSquaredGraph(double DOF, double x2){

        // Setup axes
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

        // Setup AreaChart.
        final AreaChart<Number,Number> areaChart =
                new AreaChart<Number,Number>(xAxis,yAxis);
        areaChart.setTitle("Chi-Squared Distribution with DOF = "+(int)DOF);
        areaChart.setLegendVisible(false);
        //areaChart.getData().addAll(generateChiSquaredDistribution(3.0), generateAreaUnderCurve(-4.0d, z.getZScore()));
        areaChart.getData().add(generateChiSquaredDistribution(DOF));
        areaChart.getData().add(generateAreaUnderCurve(x2,DOF));

        // Insert areaChart into pane.
        chartBox.getChildren().add(areaChart);

	}

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
            double denominator = Math.pow(2, (DOF / 2d)) * SpecialMathFunctions.gamma(DOF / 2d);

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
            double denominator = Math.pow(2, (n / 2d)) * SpecialMathFunctions.gamma(n / 2d);

            double y = numerator / denominator;

            area.getData().add(new XYChart.Data<Number, Number>(x, y));
        }
        return area;

    }
}

	


