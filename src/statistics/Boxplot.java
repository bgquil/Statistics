/*
 * 
 * Boxplot.java
 * 
 * Helper class to generate the x,y coordinates and lines for sections of a Sample's boxplot.
 * 
 */

package statistics;

import core.Sample;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class Boxplot {
	private ObservableList<XYChart.Series<Number, Number>> list;
	
	private XYChart.Series<Number, Number> seriesDataB = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> seriesDataT = new XYChart.Series<Number, Number>();
	
    private XYChart.Series<Number, Number> h1 = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> h2 = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> h3 = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> v1 = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> v2 = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> hmin = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> hmax = new XYChart.Series<Number, Number>();
    
    
    
    public Boxplot(Sample s, int offset){
    	
    	calculateCoordinates(s, offset);
    	
    	
    }
    
    private void calculateCoordinates(Sample s, int offset){
    	double w = .5;
    	
    	hmin.getData().add(new Data<Number, Number>( offset - w, s.getSampleMin()));
    	hmin.getData().add(new Data<Number, Number>( offset + w, s.getSampleMin()));
    	
        seriesDataB.getData().add(new Data<Number, Number>( offset, s.getSampleMin()));
        seriesDataB.getData().add(new Data<Number, Number>( offset, s.getSampleQ1()));


        h1.getData().add(new Data<Number, Number>( offset - w, s.getSampleQ1()));
        h1.getData().add(new Data<Number, Number>( offset + w, s.getSampleQ1()));
        
        v1.getData().add(new Data<Number, Number>( offset - w, s.getSampleQ1()));
        v1.getData().add(new Data<Number, Number>( offset - w, s.getSampleQ3()));
        
 
        h2.getData().add(new Data<Number, Number>( offset - w, s.getSampleMean()));
        h2.getData().add(new Data<Number, Number>( offset + w, s.getSampleMean()));
        

        h3.getData().add(new Data<Number, Number>( offset - w, s.getSampleQ3()));
        h3.getData().add(new Data<Number, Number>( offset + w, s.getSampleQ3()));
        
        v2.getData().add(new Data<Number, Number>( offset + w, s.getSampleQ1()));
        v2.getData().add(new Data<Number, Number>( offset + w, s.getSampleQ3()));
        
        seriesDataT.getData().add(new Data<Number, Number>( offset, s.getSampleQ3()));
        seriesDataT.getData().add(new Data<Number, Number>( offset, s.getSampleMax()));
        
    	hmax.getData().add(new Data<Number, Number>( offset - w, s.getSampleMax()));
    	hmax.getData().add(new Data<Number, Number>( offset + w, s.getSampleMax()));
    	
    	
    }
    
    
    
    
	public XYChart.Series<Number, Number> getSeriesDataB() {
		return seriesDataB;
		
	}
	public XYChart.Series<Number, Number> getSeriesDataT() {
		return seriesDataT;
	}
	public XYChart.Series<Number, Number> getH1() {
		return h1;
	}
	public XYChart.Series<Number, Number> getH2() {
		return h2;
	}
	public XYChart.Series<Number, Number> getH3() {
		return h3;
	}
	public XYChart.Series<Number, Number> getV1() {
		return v1;
	}
	public XYChart.Series<Number, Number> getV2() {
		return v2;
	}
	public XYChart.Series<Number, Number> getHMin() {
		return hmin;
	}
	public XYChart.Series<Number, Number> getHMax() {
		return hmax;
	}
	
	public ObservableList<XYChart.Series<Number, Number>> getSeries(){
		System.out.println("getSeries");
		list.add(seriesDataB);
		list.add(seriesDataT);
		list.add(h1);
		list.add(h2);
		list.add(h3);
		list.add(v1);
		list.add(v2);
		list.add(hmin);
		list.add(hmax);
		System.out.println(list);
		h2.nodeProperty().get().setStyle("-fx-stroke: black;");
		return list;
		
	}
    
    
	
	

}
