package statistics;

import view.SampleOverviewController;

public class Context {
	private final static Context instance = new Context();
	
	private Sample s1 = new Sample();
	private Sample s2 = new Sample();
	private Sample s3 = new Sample();
	private Sample s4 = new Sample();
	
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


	

}
