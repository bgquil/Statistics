/*
 * ChiSquared
 * 
 */


package statistics;

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
		ZTest z = new ZTest(2000,500,2150,100000);
		System.out.println(z.getZScore());
		
	
		




		

	}
	


}
