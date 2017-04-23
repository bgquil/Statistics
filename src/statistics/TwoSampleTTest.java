package statistics;

public class TwoSampleTTest {
	
	private double tStatistic;
	private Sample s1;
	private Sample s2;
	private double q;
	private double pValue;
	private int DOF;
	

	public TwoSampleTTest(Sample s1, Sample s2){
		this.s1 = s1;
		this.s2 = s2;
		calcTStatistic();
		//this.DOF = s1.getSampleSize() + s2.getSampleSize() - 2;
		//this.q = calculateQ();
		//this.pValue = 1-q;
	}
	
	
	private void calcTStatistic(){
		
		double s1Var = s1.getSampleVariance();
		double s2Var = s2.getSampleVariance();
		
		double numerator = s1.getSampleMean() - s2.getSampleMean();
		double denominator = Math.sqrt(
				(Math.pow(s1Var, 2)/s1.getSampleSize()) + (Math.pow(s2Var, 2)/s2.getSampleSize())
				);
//		double sp = ((s1.getSampleSize()-1)*s1.getSampleVariance() + (s2.getSampleSize()-1)*s2.getSampleVariance())
//				/
//				(s1.getSampleSize()+s2.getSampleSize()-2) ;
//		double denominator = Math.sqrt( (1/s1.getSampleSize())*(1/s2.getSampleSize()) );
		this.tStatistic = numerator/(denominator);
	}
	
	private double calculateQ(){
		return MathFunctions.stDist(this.DOF, this.tStatistic);
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
