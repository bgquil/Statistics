package statistics;
public class ZTest {
	
	private final double Z_MAX = 6;
	private double popMean;
	private double popStdDev; //sigma
	private double zScore;
	private double standardError;
	private double pValue;
	private double q;
	
	// For a Sample
	public ZTest(Sample s, double popMean, double popStdDev){
		this.popMean = popMean;
		this.popStdDev = popStdDev;
		this.zScore = calculateZScore(s);
		this.pValue = calculateProbability(zScore);
		this.q = 1 - pValue;
	}
	// For a summary
	public ZTest(double popMean, double popStdDev, double sampleMean, double sampleN){
		this.popMean = popMean;
		this.popStdDev = popStdDev;
		this.zScore = calculateZScore(sampleMean,sampleN);
		this.pValue = calculateProbability(zScore);
		this.q = 1 - pValue;
		
		
	}
	
	/*
	 * For a user-entered summary statistics
	 */
	private double calculateZScore(double sampleMean, double sampleN){
		return ((sampleMean - popMean)/(popStdDev/Math.sqrt(sampleN))); 
	}
	
	
	/*
	 * For a a provided Sample
	 */
	private double calculateZScore(Sample s){
		return(s.getSampleMean() - popMean)/popStdDev; 
		
	}
	
	public double getZScore(){
		return this.zScore;
	}
	
	public double getPValue(){
		return pValue;
	}
	
	
	/*
	 * Returns 1-pValue
	 */
	
	public double getQ(){
		return this.q;
	}

	
	/*
	 *  https://www.fourmilab.ch/rpkp/experiments/analysis/zCalc.js
	 */
	
	public double calculateProbability(double z){
		
		double  y, x, w;
	        
	        if (z == 0.0) {
	            x = 0.0;
	        } else {
	            y = 0.5 * Math.abs(z);
	            if (y > (6 * 0.5)) {
	                x = 1.0;
	            } else if (y < 1.0) {
	                w = y * y;
	                x = ((((((((0.000124818987 * w
	                         - 0.001075204047) * w + 0.005198775019) * w
	                         - 0.019198292004) * w + 0.059054035642) * w
	                         - 0.151968751364) * w + 0.319152932694) * w
	                         - 0.531923007300) * w + 0.797884560593) * y * 2.0;
	            } else {
	                y -= 2.0;
	                x = (((((((((((((-0.000045255659 * y
	                               + 0.000152529290) * y - 0.000019538132) * y
	                               - 0.000676904986) * y + 0.001390604284) * y
	                               - 0.000794620820) * y - 0.002034254874) * y
	                               + 0.006549791214) * y - 0.010557625006) * y
	                               + 0.011630447319) * y - 0.009279453341) * y
	                               + 0.005353579108) * y - 0.002141268741) * y
	                               + 0.000535310849) * y + 0.999936657524;
	            }
	        }
	        return z > 0.0 ? ((x + 1.0) * 0.5) : ((1.0 - x) * 0.5);
		
		
		
	}
	
	
	

}
