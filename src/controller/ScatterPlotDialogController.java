package controller;

import core.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.Sample;

public class ScatterPlotDialogController {

	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	@FXML
	private ChoiceBox<Sample> xData;
	@FXML
	private ChoiceBox<Sample> yData;
	
	@FXML
	private TextField xLabel;
	@FXML 
	private TextField yLabel;

	@FXML
	private void initialize() {
	    // Setup Sample ChoiceBox
        ObservableList<Sample> samples = FXCollections.observableArrayList(
				Context.getInstance().getNonDefaultSamples()
		);
        xData.setItems(samples);
        yData.setItems(samples);
	}
	
	/*
	 * 	Handle selection of sample from the choicebox.
	 */
	@FXML
	private void handleSelectSample() {
		Sample x = xData.getSelectionModel().getSelectedItem();
		Sample y = yData.getSelectionModel().getSelectedItem();

		if (xLabel.getText().equals(""))
			x.setName("X-Axis");
		else
			x.setName(xLabel.getText());
		
		if (yLabel.getText().equals(""))
			y.setName("Y-Axis");
		else
			y.setName(yLabel.getText());
		
		showScatterPlot(x, y);
	}
	
	
	
    public void showScatterPlot(Sample x, Sample y){

            try {

                Stage graphStage = new Stage();
                graphStage.setTitle("Scatterplot");
                graphStage.initModality(Modality.NONE);
                graphStage.initOwner(dialogStage);

                final NumberAxis xAxis = new NumberAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel(x.getName());
                yAxis.setLabel(y.getName());

                final ScatterChart<Number,Number> scatterGraph =
                        new ScatterChart<Number,Number>(xAxis,yAxis);
                scatterGraph.setTitle("Scatterplot");

                XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();

                for (int i = 0; i < x.getSampleSize(); i++){
                    series.getData().add(new Data<Number, Number>(x.get(i),y.get(i)));
                }

                scatterGraph.setAnimated(false);
                //regressionGraph.setCreateSymbols(true);
                scatterGraph.getData().add(series);

                xAxis.setStyle("-fx-tick-label-font: 18 system");
                yAxis.setStyle("-fx-tick-label-font: 18 system");

                Scene scene  = new Scene(scatterGraph,800,600);
                //scene.getStylesheets().add(getClass().getResource("/view/RegressionChart.css").toExternalForm());
                graphStage.setScene(scene);
                graphStage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
