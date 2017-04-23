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
		this.DOF = s1.getSampleSize() + s2.getSampleSize() - 2;
		//this.q = calculateQ();
//		if (tStatistic < 0){
//			tStatistic = tStatistic*-1;
//			this.q = calculateQ();
//			System.out.println(q);
//		}
//		else{
//			this.q = calculateQ();
//			this.pValue = 1-q;
//		}

	}
	
	
	private void calcTStatistic(){
		double s1Var = Math.pow(s1.getSampleStdDev(), 2);
		double s2Var = Math.pow(s2.getSampleStdDev(), 2);
		
		double numerator = s1.getSampleMean() - s2.getSampleMean();
	

		double sp = Math.sqrt(((s1.getSampleSize()-1)*s1Var + (s2.getSampleSize()-1)*s2Var)
				/
				(s1.getSampleSize()+s2.getSampleSize()-2)) ;
		
		double denominator = sp*Math.sqrt( (1d/s1.getSampleSize())+(1d/s2.getSampleSize()) );
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
