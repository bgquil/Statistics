/*
 * ChiSquared
 * 
 */


package statistics;

import java.util.ArrayList;

public class testCalc {
	
	public static void main(String[] args) {
		
		
//		double act[] = {15,23, 24, 30};
//		double gpa[] = {2.5,2.6, 2.5, 2.8, 3.0, 3.2, 3.5, 4.0}; //GPA
//		
//		Sample sAct = new Sample(act);
//		Sample sGPA = new Sample(gpa);
//		
//		System.out.println(sAct);
	
//		LinearRegression lr = new LinearRegression(sGPA,sAct);
//		
//		System.out.println(lr);
//		System.out.println("r: "+lr.getR());
//		System.out.println("r2: "+lr.getR2());
//		
//		ZTest z = new ZTest(100,15,140,30);
//		System.out.println(z.getZScore());
//		ArrayList<Double> a = new ArrayList<Double>();
//	//test change git
//		try {
//			a.add(Double.parseDouble("3"));
//		} catch (Exception e) {
//			
//		}
//		
//		try {
//			a.add(Double.parseDouble("20.2"));
//		} catch (Exception e) {
//			
//		}
//		
//		System.out.println(a.size());
//		
//		DataEntry d = new DataEntry();
//		d.setC1("Apple");
//		System.out.println(d.getC1());
//		
//		d.c13.setValue("10");
//		System.out.println(d.getC13());
//
//		double t = MathFunctions.stDist(3, 2.1);
//		System.out.println(t);
	
	TTest t = new TTest(110, 20, 16, 100);
	
	System.out.println(t.getTStatistic());
	System.out.println(1-t.getPValue());
	}
	
	

	


}
