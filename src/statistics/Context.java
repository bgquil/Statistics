package statistics;

import core.Sample;

public class Context {
	private final static Context instance = new Context();
	
	private Sample s1 = new Sample();
	private Sample s2 = new Sample();
	private Sample s3 = new Sample();
	private Sample s4 = new Sample();
	private Sample s5 = new Sample();
	private Sample s6 = new Sample();
	
	public static Context getInstance(){
		return instance;
	}
	
	public Sample getS1() {
		return s1;
	}
	public void setS1(Sample s1) {
		this.s1 = s1;
	}
	public Sample getS2() {
		return s2;
	}
	public void setS2(Sample s2) {
		this.s2 = s2;
	}
	public Sample getS3() {
		return s3;
	}
	public void setS3(Sample s3) {
		this.s3 = s3;
	}
	public Sample getS4() {
		return s4;
	}
	public void setS4(Sample s4) {
		this.s4 = s4;
	}

	/**
	 * @return the s5
	 */
	public Sample getS5() {
		return s5;
	}

	/**
	 * @param s5 the s5 to set
	 */
	public void setS5(Sample s5) {
		this.s5 = s5;
	}

	/**
	 * @return the s6
	 */
	public Sample getS6() {
		return s6;
	}

	/**
	 * @param s6 the s6 to set
	 */
	public void setS6(Sample s6) {
		this.s6 = s6;
	}


	

}
