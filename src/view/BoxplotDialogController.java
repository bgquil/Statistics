package view;

import java.util.Iterator;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.Boxplot;
import statistics.MainApp;
import statistics.Sample;

public class BoxplotDialogController {
	
	@FXML
	private Label errLabel;
	@FXML
	private CheckBox s1Box;
	@FXML
	private CheckBox s2Box;
	@FXML
	private CheckBox s3Box;
	@FXML
	private CheckBox s4Box;
	
	private CheckBox[] boxes = {s1Box, s2Box,s3Box,s4Box};
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
public void handleShowBoxPlots(){
	LinkedList<Sample> bpToShow = new LinkedList<Sample>();
	if (s1Box.isSelected())
		bpToShow.add(SampleOverviewController.getSample1());
	if (s2Box.isSelected())
		bpToShow.add(SampleOverviewController.getSample2());
	if (s3Box.isSelected())
		bpToShow.add(SampleOverviewController.getSample3());
	if (s4Box.isSelected())
		bpToShow.add(SampleOverviewController.getSample4());
	
	
	if (bpToShow.isEmpty()){
		errLabel.setText("Select one or more data sets.");
	}
	else{
		System.out.println(bpToShow.size());
		showBoxplot(bpToShow, bpToShow.size());
	}
	
			
	
	
	
}
	
	
public void showBoxplot(LinkedList<Sample> samples, int num){
		
		try {
			
			//initialize min max values for y extent of graph
			double max = samples.getFirst().getSampleMax();
			double min = samples.getFirst().getSampleMin();
			
			Stage graphStage = new Stage();
			graphStage.setTitle("Boxplot");
			graphStage.initModality(Modality.WINDOW_MODAL);
			graphStage.initOwner(dialogStage);



	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setAutoRanging(false);
	        yAxis.setAutoRanging(false);
	        


	        final LineChart<Number,Number> bpGraph = 
	                new LineChart<Number,Number>(xAxis,yAxis);   
	        int space = 2;
			for (Sample s : samples){
				
				//get min max over the set of samples
				max = (max < s.getSampleMax()) ? s.getSampleMax() : max;
				min = (min > s.getSampleMin()) ? s.getSampleMin() : min;
				
				System.out.println(s.getSampleMean());
				Boxplot plot = new Boxplot(s, 1+space);
				
				bpGraph.getData().addAll(plot.getSeriesDataB(),plot.getSeriesDataT(),plot.getH1(),
						plot.getH2(),plot.getH3(),plot.getV1(),plot.getV2(),plot.getHMin(),plot.getHMax());
				space = space + 2;
			}
			//s.nodeProperty().get().setStyle("-fx-stroke: black;");
			
			
		
			yAxis.setLowerBound(min-1);
			yAxis.setUpperBound(max+1);
			xAxis.setLowerBound(0);
			if (num == 1)
				xAxis.setUpperBound(4);
			if (num == 2)
				xAxis.setUpperBound(6);
			if (num == 3)
				xAxis.setUpperBound(8);
			if (num == 4)
				xAxis.setUpperBound(10);
			

	        
	        Scene scene  = new Scene(bpGraph,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/Boxplot.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
			

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
