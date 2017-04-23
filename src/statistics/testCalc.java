/*
 * ChiSquared
 * 
 */


package statistics;

import java.util.ArrayList;

public class testCalc {
	
	public static void main(String[] args) {
		
		
//		double act[] = {2.5,2.6, 2.2, 2.3, 2.0, 2.2, 2.5, 2.0};
//		double gpa[] = {3.5,3.6, 2.5, 2.8, 3.0, 3.2, 3.5, 4.0}; //GPA
//		
//		Sample sAct = new Sample(act);
//		Sample sGPA = new Sample(gpa);
		
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
	
//	TTest t = new TTest(110, 20, 16, 100);
//	
//	System.out.println(t.getTStatistic());
//	System.out.println(1-t.getPValue());
		
		double[] a1 = {42.1,				
				41.3,					
				42.4,
				43.2,
				41.8,
				41.0,				
				41.8,				
				42.8,
				42.3,
				42.7,		
		};
		
		double[] a2 = {42.7,
				43.8,			
				42.5,	
				43.1,	
				44.0,
				43.6,
				43.3,			
				43.5,			
				41.7,			
				44.1
		};
		
//		Sample s11 = new Sample(a1);
//		Sample s22 = new Sample(a2);
//		
//		System.out.println(s11.getSampleMean());
//		System.out.println(s22.getSampleMean());
//		System.out.println(s11.getSampleStdDev());
//		System.out.println(s22.getSampleStdDev());
//		
//	TwoSampleTTest t = new TwoSampleTTest(s11, s22);
//	System.out.println(t.getTStatistic());
	
		System.out.println(MathFunctions.stDist(18, 3.40));
	
		
//		double[] a = {170,300,430,470,600};
//		Sample as = new Sample(a);
//
//		System.out.println(as.getSampleVariance());
//		System.out.println(Math.pow(as.getSampleVariance(), .5));
		
	}
	
	

	

	


}
