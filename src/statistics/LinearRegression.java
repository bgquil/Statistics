package statistics;

public class LinearRegression {
	
	/* 
	 * 	Of the form y = b0 + b1x
	 * 	Using Least Squares.
	 * 	x - independent variable
	 *  y - dependent variable
	 */
	private Sample x = null; // dependent
	private Sample y = null; // independent
	
	private double b0;	// y-intercept
	private double b1; 	// slope
	private double r;	//correlation coefficient
	private double r2;	// coefficient of determination r**2 or R**2
	
	public LinearRegression(Sample ind, Sample dep){
		x = ind;
		y = dep;
		
		if (ind.getSampleSize() != dep.getSampleSize()){
			System.out.println("The data sets are not the same size.");
		}
		else{
			
			calculateLinearFit();
			
		}
	}
	 
	
	/*
	 * Calculate a linear regression using the least squares method.
	 * Calculate r and r2
	 */
	
	private void calculateLinearFit(){
		double[] xData = x.getSampleData();
		double[] yData = y.getSampleData();
		double n = xData.length;
				
		double xYSum = 0;
		double xSumYSum = 0;
		double xSquaredSum = 0;
		double ySquaredSum = 0;
		double xSum = 0;
		double ySum = 0;
		
		for (int i = 0; i < n; i++){
			
			xYSum += xData[i] * yData[i];
			xSum += xData[i];
			ySum += yData[i];
			xSquaredSum += Math.pow(xData[i], 2d);
			ySquaredSum += Math.pow(yData[i], 2d);
		}
		
		xSumYSum = xSum*ySum;
		
		
		/*
		 * Calculates b1 and b0 for the line of best fit
		 */
		
		
		b1 = ((xYSum - (xSumYSum/n)) / (xSquaredSum - (Math.pow(xSum, 2d))/n) );
		b0 = ((ySum - (b1 * xSum)) / n);
		
		/*
		 * Calculates "r", the correlation coefficient.
		 * aka Pearson correlation coefficient
		 */
		r = ((n*xYSum) - (xSum * ySum) ) 
				/ 
				(Math.sqrt( (n*xSquaredSum) - Math.pow(xSum, 2d)) * Math.sqrt( n*ySquaredSum - Math.pow(ySum, 2d) ) );
		
		/*
		 * Calculates r2, coefficient of determination;
		 * 
		 */
		r2 = Math.pow(r, 2d);
		
		
	}
	
	
	
	/*
	 *  b0 -- y intercept
	 */
	public double getB0(){
		return b0;
	}

	/*
	 *  b1 -- slope of the line
	 */
	public double getB1(){
		return b1;
	}
	
	/*
	 *  r -- correlation coefficient
	 */
	public double getR(){
		return r;
	}
	
	/*
	 *  r2 -- coefficient of determination
	 */
	public double getR2(){
		return r2;
	}
	
	/*
	 * form y = b0 + b1x(non-Javadoc)
	 * 
	 */
	public String toString(){
		
		return "y = "+this.getB0()+" + "+this.getB1()+"x";
		
	}

}
