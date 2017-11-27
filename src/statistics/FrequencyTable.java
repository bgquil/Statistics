package statistics;

import java.util.HashMap;
import java.util.Map;

public class FrequencyTable {

	private Map<Double, Integer> ft =  new HashMap<Double, Integer>();
	
	public FrequencyTable(Sample s){
		for (double d : s.getSortedSampleData()){
            if (!ft.containsKey(d)) {
                ft.put(d, 1);
            }
            else {
                ft.put(d, ft.get(d) + 1);
            }
		}
	}

	public Map<Double, Integer> getMap(){
		return ft;
	}
	
	

}
