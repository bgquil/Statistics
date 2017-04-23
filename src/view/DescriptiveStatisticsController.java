package view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import statistics.Context;
import statistics.FrequencyTable;
import statistics.FrequencyTableEntry;
import statistics.Sample;

public class DescriptiveStatisticsController {
	
	private Stage dialogStage;
	
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private ChoiceBox<String> sampleChoiceBox;
	@FXML
	private TableView<FrequencyTableEntry> freqTable;
	@FXML
	private TableColumn<FrequencyTableEntry,Number> numCol;
	@FXML
	private TableColumn<FrequencyTableEntry,Number> countCol;
	@FXML
	private TableColumn<FrequencyTableEntry,Number> percentCol;
	@FXML
	private TextField num;
	@FXML
	private TextField mean;
	@FXML
	private TextField sum;
	@FXML
	private TextField deviation;
	@FXML
	private TextField min;
	@FXML
	private TextField q1;
	@FXML
	private TextField median;
	@FXML
	private TextField q3;
	@FXML
	private TextField max;
	@FXML
	private TextField range;
	
	/*
	 * 
	 */
	@FXML
	private void initialize() {
		
		ObservableList<String> samples = FXCollections.observableArrayList("Data Set 1","Data Set 2","Data Set 3","Data Set 4");
		sampleChoiceBox.setItems(samples);
		
		//numCol.setCellValueFactory(new PropertyValueFactory<>("Number"));
		numCol.setCellValueFactory(cellData -> cellData.getValue().numProperty());
		countCol.setCellValueFactory(cellData -> cellData.getValue().countProperty());
		percentCol.setCellValueFactory(cellData -> cellData.getValue().percentProperty());
		
		
	}
	
	
	
	/*
	 * 	Handle selection of sample from the choicebox.
	 */
	@FXML
	private void handleSelectSample() {
		
		int selection = sampleChoiceBox.getSelectionModel().getSelectedIndex();
		System.out.println(selection);
		
		switch (selection) {
		
		case 0:
			showStats(Context.getInstance().getS1());
			break;
		case 1:
			showStats(Context.getInstance().getS2());
			break;
		case 2:
			showStats(Context.getInstance().getS3());
			break;
		case 3:
			showStats(Context.getInstance().getS4());
			break;
		case 4:
			showStats(Context.getInstance().getS5());
			break;
		case 5:
			showStats(Context.getInstance().getS6());
			break;
		}
		
		
	}
	
	public double formatDouble(double val, int places){
		
		BigDecimal d = new BigDecimal(val);
		d = d.setScale(places, RoundingMode.HALF_UP);
		return d.doubleValue();
	}
	
	private void showStats(Sample s){
		final int places = 4;
		num.setText(Integer.toString(s.getSampleSize()));
		mean.setText(Double.toString(formatDouble(s.getSampleMean(),places)));
		sum.setText(Double.toString(formatDouble(s.getSampleSum(),places)));
		deviation.setText(Double.toString(formatDouble(s.getSampleStdDev(),places)));
		min.setText(Double.toString(formatDouble(s.getSampleMin(),places)));
		q1.setText(Double.toString(formatDouble(s.getSampleQ1(),places)));
		median.setText(Double.toString(formatDouble(s.getSampleMedian(),places)));
		q3.setText(Double.toString(formatDouble(s.getSampleQ3(),places)));
		max.setText(Double.toString(formatDouble(s.getSampleMax(),places)));
		range.setText(Double.toString(formatDouble(s.getSampleRange(),places)));
		
		
		showFreqTable(s);
		
		
	}
	
	private void showFreqTable(Sample s){
		FrequencyTable ft = new FrequencyTable(s);
		
		ObservableList<FrequencyTableEntry> data = FXCollections.observableArrayList();
		Map<Double, Integer> m = ft.getMap();
		double n = (double) s.getSampleSize();
		Iterator<Entry<Double, Integer>> it = m.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<Double, Integer> pair = (Map.Entry<Double, Integer>) it.next();
			FrequencyTableEntry entry = new FrequencyTableEntry(pair.getKey(), pair.getValue(), (pair.getValue()/(n))*100d);
			data.add(entry);
		}

		freqTable.setItems(data);

	}
	
	
	
	
	
	
	
	

}
