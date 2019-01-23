package statistics;

public class ZTest {
	
	private final double Z_MAX = 6;
	private double popMean;
	private double popStdDev; //sigma
	private double zScore;
	private double standardError;
	private double pValue;
	private double q;
	
	/**
	 * For a z-Test with a supplied data set
	 * @param s
	 * @param popMean
	 * @param popStdDev
	 */
	public ZTest(Sample s, double popMean, double popStdDev){
		this.popMean = popMean;
		this.popStdDev = popStdDev;
		this.zScore = calculateZScore(s);
		this.q = 1 - pValue;
	}
	/**
	 * For a summary calculation
	 * @param popMean
	 * @param popStdDev
	 * @param sampleMean
	 * @param sampleN
	 */
	public ZTest(double popMean, double popStdDev, double sampleMean, double sampleN){
		this.popMean = popMean;
		this.popStdDev = popStdDev;
		this.zScore = calculateZScore(sampleMean,sampleN);
		this.pValue = SpecialMathFunctions.calculateZTestProbability(zScore);
		this.q = 1 - pValue;
	}

    /**
     * Used to calculate a Z-score from a summary.
     * @param sampleMean the user-provided mean
     * @param sampleN the user-provided quantity
     * @return z-score
     */
	private double calculateZScore(double sampleMean, double sampleN){
		return ((sampleMean - popMean)/(popStdDev/Math.sqrt(sampleN))); 
	}


    /**
     * Used to calculate a Z-score of a Sample object.
     * @param s the Sample to be used.
     * @return z-score
     */
	private double calculateZScore(Sample s){
		return(s.getSampleMean() - popMean)/(popStdDev/Math.sqrt((double)s.getSampleSize())); 
		
	}
	
	public double getZScore(){
		return this.zScore;
	}
	
	public double getPValue(){
		return pValue;
	}

	public double getQ(){
		return this.q;
	}
}
