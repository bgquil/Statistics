package view;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private Label num;
	@FXML
	private Label mean;
	@FXML
	private Label deviation;
	@FXML
	private Label min;
	@FXML
	private Label q1;
	@FXML
	private Label median;
	@FXML
	private Label q3;
	@FXML
	private Label max;
	@FXML
	private Label range;
	
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
		
			
		}
		
		
	}
	
	private void showStats(Sample s){
		
		num.setText(Integer.toString(s.getSampleSize()));
		mean.setText(Double.toString(s.getSampleMean()));
		deviation.setText(Double.toString(s.getSampleStdDev()));
		min.setText(Double.toString(s.getSampleMin()));
		q1.setText(Double.toString(s.getSampleQ1()));
		median.setText(Double.toString(s.getSampleMedian()));
		q3.setText(Double.toString(s.getSampleQ3()));
		max.setText(Double.toString(s.getSampleMax()));
		range.setText(Double.toString(s.getSampleRange()));
		
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
