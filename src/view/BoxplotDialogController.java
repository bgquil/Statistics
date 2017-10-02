package view;

import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import statistics.Boxplot;
import statistics.Context;
import core.Sample;

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
	@FXML
	private CheckBox s5Box;
	@FXML
	private CheckBox s6Box;
	
	private CheckBox[] boxes = {s1Box, s2Box,s3Box,s4Box,s5Box,s6Box};
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
public void handleShowBoxPlots(){
	LinkedList<Sample> bpToShow = new LinkedList<Sample>();
	if (s1Box.isSelected())
		bpToShow.add(Context.getInstance().getS1());
	if (s2Box.isSelected())
		bpToShow.add(Context.getInstance().getS2());
	if (s3Box.isSelected())
		bpToShow.add(Context.getInstance().getS3());
	if (s4Box.isSelected())
		bpToShow.add(Context.getInstance().getS4());
	if (s5Box.isSelected())
		bpToShow.add(Context.getInstance().getS5());
	if (s6Box.isSelected())
		bpToShow.add(Context.getInstance().getS6());
	
	
	
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
			if (num == 5)
				xAxis.setUpperBound(12);
			if (num == 6)
				xAxis.setUpperBound(14);
			

	        
	        Scene scene  = new Scene(bpGraph,800,600);
	        scene.getStylesheets().add(getClass().getResource("/view/Boxplot.css").toExternalForm());
	        graphStage.setScene(scene);
	        graphStage.showAndWait();
			
			

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
