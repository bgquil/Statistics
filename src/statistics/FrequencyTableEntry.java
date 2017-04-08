package statistics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class FrequencyTableEntry {
	private SimpleDoubleProperty num;
	private SimpleIntegerProperty count;
	private SimpleDoubleProperty percent;
	
	
	
	/**
	 * @param n
	 * @param c
	 * @param p
	 */

	public FrequencyTableEntry(double n, int c, double p){
		this.num = new SimpleDoubleProperty(n);
		this.count = new SimpleIntegerProperty(c);
		this.percent = new SimpleDoubleProperty(p);
	}
	
	
	/*
	 * num
	 */
	public double getNum(){
		return num.get();
	}
	
	public void setNum(double n){
		this.num.set(n);
	}
	
	public DoubleProperty numProperty(){
		return num;
	}
	
	/*
	 * count
	 */
	public int getCount(){
		return count.get();
	}
	
	public void setCount(int c){
		this.count.set(c);
	}
	
	public IntegerProperty countProperty(){
		return count;
	}
	
	/*
	 * percent
	 */
	public double getPercent(){
		return percent.get();
	}
	
	public void setPercent(double p){
		this.percent.set(p);
	}
	
	public DoubleProperty percentProperty(){
		return percent;
	}
	
	
	
	public String toString(){
		return "num: "+num+
				"\ncount :"+count+
				"\npercent :"+percent;
	}
	


}
