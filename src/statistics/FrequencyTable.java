package statistics;

import java.util.HashMap;
import java.util.Map;

public class FrequencyTable {
	
	
	private Map<Double, Integer> ft =  new HashMap<Double, Integer>();
	
	public FrequencyTable(Sample s){
		for (double d : s.getSortedSampleData()){
			check(d);
		}
		
	}
	
	private int count(double key){
		
		if (!ft.containsKey(key))
			return 0;
		else 
			return ft.get(key);
	}
	
	private void check(double d){
		ft.put(d, count(d)+1);
	}
	
	public Map<Double, Integer> getMap(){
		return ft;
	}
	
	

}
