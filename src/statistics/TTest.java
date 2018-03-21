package statistics;

public class TTest {
	
	private double tStatistic;
	private double q;
	private double pValue;
	private int DOF;

	/**
	 * One Sample t-Test from a data set
	 * @param s The sample 
	 * @param mu The population mean
	 */
	public TTest(Sample s, double mu ){
		
		this.tStatistic = calcTStatistic(s.getSampleMean(),
				s.getSampleSize(), s.getSampleStdDev(), mu);
		this.DOF = s.getSampleSize()-1;
		this.q = calculateQ();
		this.pValue = 1-q;
		
	}
	
	/**
	 * One sample t-Test for a user-entered summary
	 * @param xbar - sample mean
	 * @param n - sample size
	 * @param s - sample standard deviation
	 * @param mu - population mean
	 */
	public TTest(double xbar, int n, double s , double mu ){
		
		this.tStatistic = calcTStatistic(xbar, n, s,  mu);
		this.DOF = n-1;
		this.q = calculateQ();
		this.pValue = 1-q;
		
	}
	
	private double calcTStatistic(double xbar, int n, double sampleStdDev, double mu){
		double tStat = 0;
		tStat= (xbar - mu)/(sampleStdDev/Math.sqrt(n));
		return tStat;
	}
	
	private double calculateQ(){
		return SpecialMathFunctions.stDist(this.DOF, this.tStatistic);
	}

	public double getTStatistic() {
		return tStatistic;
	}

	public double getQ() {
		return q;
	}

	public double getPValue() {
		return pValue;
	}

	public int getDOF() {
		return DOF;
	}
}
