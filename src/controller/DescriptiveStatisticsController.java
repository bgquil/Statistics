package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.stage.Stage;
import core.Context;
import statistics.FrequencyTable;
import statistics.FrequencyTableEntry;
import statistics.Sample;

public class DescriptiveStatisticsController {
	
	private Stage dialogStage;
	
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private ChoiceBox<Sample> sampleChoiceBox;
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

	@FXML
	private void initialize() {

	    // Setup Sample ChoiceBox
		ObservableList<Sample> samples = FXCollections.observableArrayList(
				Context.getInstance().getNonDefaultSamples()
		);
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
		showStats(sampleChoiceBox.getSelectionModel().getSelectedItem());
	}
	
	public double formatDouble(double val, int places){
		
		BigDecimal d = new BigDecimal(val);
		d = d.setScale(places, RoundingMode.HALF_UP);
		return d.doubleValue();
	}
	
	private void showStats(Sample s){
		final int PRECISION = 4;

		if (s != null) {
            num.setText(Integer.toString(s.getSampleSize()));
            mean.setText(Double.toString(formatDouble(s.getSampleMean(), PRECISION)));
            sum.setText(Double.toString(formatDouble(s.getSampleSum(), PRECISION)));
            deviation.setText(Double.toString(formatDouble(s.getSampleStdDev(), PRECISION)));
            min.setText(Double.toString(formatDouble(s.getSampleMin(), PRECISION)));
            q1.setText(Double.toString(formatDouble(s.getSampleQ1(), PRECISION)));
            median.setText(Double.toString(formatDouble(s.getSampleMedian(), PRECISION)));
            q3.setText(Double.toString(formatDouble(s.getSampleQ3(), PRECISION)));
            max.setText(Double.toString(formatDouble(s.getSampleMax(), PRECISION)));
            range.setText(Double.toString(formatDouble(s.getSampleRange(), PRECISION)));
            showFreqTable(s);
        }
        else {
            showNullSampleError();
        }
	}

	
	private void showFreqTable(Sample s){
		FrequencyTable ft = new FrequencyTable(s);
		ObservableList<FrequencyTableEntry> data = FXCollections.observableArrayList();
		Map<Double, Integer> m = ft.getMap();
		double n = (double) s.getSampleSize();
		Iterator<Entry<Double, Integer>> it = m.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<Double, Integer> pair = (Map.Entry<Double, Integer>) it.next();
			FrequencyTableEntry entry = new FrequencyTableEntry(
					pair.getKey(),
					pair.getValue(),
					(pair.getValue()/(n)) * 100d);
			data.add(entry);
		}
		freqTable.setItems(data);
	}

    /*
     *  Error Dialogs
     */
    @FXML
    private void showNullSampleError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sample Error");
        alert.setHeaderText("Cannot display statistics");
        alert.setContentText("The selected sample contains no data.");
        alert.showAndWait();
    }
}
