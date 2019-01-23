package statistics;


/* BQ
 * SpecialMathFunctions.java
 * A static library of math functions used in the calculation and graphing of various statistical tests.
 * The following code is adapted from other authors.
 */

public class SpecialMathFunctions {


    // Used for Z Test
	public static double calculateZTestProbability(double z){
		// Adapted from https://www.fourmilab.ch/rpkp/experiments/analysis/zCalc.js

        /*  The following JavaScript functions for calculating normal and
            chi-square probabilities and critical values were adapted by
            John Walker from C implementations
            written by Gary Perlman of Wang Institute, Tyngsboro, MA
            01879.  Both the original C code and this JavaScript edition
            are in the public domain.  */

        /*  POZ  --  probability of normal z value

            Adapted from a polynomial approximation in:
                    Ibbetson D, Algorithm 209
                    Collected Algorithms of the CACM 1963 p. 616
         */

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



 	//The following code in this class is adapted from Byran Lewis.


        /*	specialFunctions.java
        20-July-1997
        Bryan Lewis
        Department of Mathematics and Computer Science
        Kent State University

        This software is in the public domain and can
        be copied, modified and used without restriction.

        (*) Press, Flannery, Teukolsky, Vetterling, Numerical Recipes,
            Cambridge University Press, 1986
        */

	// Used to graph a chi-squared curve.
	public static  double logGamma( double xx) {
		// An approximation to ln(gamma(x))
	// define some constants...
		int j;
		double stp = 2.506628274650;
		double cof[] = new double[6];
		cof[0]=76.18009173;
		cof[1]=-86.50532033;
		cof[2]=24.01409822;
		cof[3]=-1.231739516;
		cof[4]=0.120858003E-02;
		cof[5]=-0.536382E-05;
		
		double x = xx-1;
		double tmp = x + 5.5;
		tmp = (x + 0.5)*Math.log(tmp) - tmp;
		double ser = 1;
		for(j=0;j<6;j++){
			x++;
			ser = ser + cof[j]/x;
		}
		double retVal = tmp + Math.log(stp*ser);
		return retVal;
	}
	
	public static double gamma( double x) {
		// An approximation of gamma(x)
		double f = 10E99;
		double g = 1;
		if ( x > 0 ) {
			while (x < 3) {
				g = g * x;
				x = x + 1;
			}
			f = (1 - (2/(7*Math.pow(x,2))) * (1 - 2/(3*Math.pow(x,2))))/(30*Math.pow(x,2));
			f = (1-f)/(12*x) + x*(Math.log(x)-1);
			f = (Math.exp(f)/g)*Math.pow(2*Math.PI/x,0.5);
		}
		else {
			Double er = new Double(0);
			f = er.POSITIVE_INFINITY;
		}
		return f;
	}
	
	public static double betacf(double a,double b,double x){
		// A continued fraction representation of the beta function
		int maxIterations = 50, m=1;
		double eps = 3E-5;
		double am = 1;
		double bm = 1;
		double az = 1;
		double qab = a+b;
		double qap = a+1;
		double qam = a-1;
		double bz = 1 - qab*x/qap;
		double aold = 0;
		double em, tem, d, ap, bp, app, bpp;
		while((m<maxIterations)&&(Math.abs(az-aold)>=eps*Math.abs(az))){
			em = m;
			tem = em+em;
			d = em*(b-m)*x/((qam + tem)*(a+tem));
			ap = az+d*am;
			bp = bz+d*bm;
			d = -(a+em)*(qab+em)*x/((a+tem)*(qap+tem));
			app = ap+d*az;
			bpp = bp+d*bz;
			aold = az;
			am = ap/bpp;
			bm = bp/bpp;
			az = app/bpp;
			bz = 1;
			m++;
		}
		return az;
	}
			
	public static double betai(double a, double b, double x) {
		// the incomplete beta function from 0 to x with parameters a, b
	// x must be in (0,1) (else returns error)
		Double er = new Double(0);
		double bt=0, beta=er.POSITIVE_INFINITY;
		if( x==0 || x==1 ){
			bt = 0; } 
		else if((x>0)&&(x<1)) {
			bt = gamma(a+b)*Math.pow(x,a)*Math.pow(1-x,b)/(gamma(a)*gamma(b)); }
		if(x<(a+1)/(a+b+2)){
			beta = bt*betacf(a,b,x)/a; }
		else {
			beta = 1-bt*betacf(b,a,1-x)/b; }
		return beta;
	}
		
	public static double fDist(double v1, double v2, double f) {
		/* 	F distribution with v1, v2 deg. freedom
		P(x>f)
	*/
		double p =	betai(v1/2, v2/2, v1/(v1 + v2*f));
		return p;
	}
	
	public static double student_c(double v) {
		// Coefficient appearing in Student's t distribution
		return Math.exp(logGamma( (v+1)/2)) / (Math.sqrt(Math.PI*v)*Math.exp(logGamma(v/2)));
	}
	
	public static double student_tDen(double v, double t) {
		/* 	Student's t density with v degrees of freedom
		Requires gamma, student_c functions
		Part of Bryan's Java math classes (c) 1997
	*/
		return student_c(v)*Math.pow( 1 + (t*t)/v, -0.5*(v+1) );
	}
	
	public static double stDist(double v, double t) {
		/* 	Student's t distribution with v degrees of freedom
		Requires gamma, student_c functions
		Part of Bryan's Java math classes (c) 1997
		This only uses compound trapezoid, pending a good integration package
		Returned value is P( x > t) for a r.v. x with v deg. freedom. 
		NOTE: With the gamma function supplied here, and the simple trapeziodal
		sum used for integration, the accuracy is only about 5 decimal places.
		Values below 0.00001 are returned as zero.
	*/
		double sm = 0.5;
		double u = 0;
		double sign = 1;
		double stepSize = t/5000;
		if ( t < 0) {
		 sign = -1;
		}
		for (u = 0; u <= (sign * t) ; u = u + stepSize) {
			sm = sm + stepSize * student_tDen( v, u);
		}
		if ( sign < 0 ) {
		 sm = 0.5 - sm;
		}
		else {
		 sm = 1 - sm;
		}
		if (sm < 0) {
		 sm = 0;		// do not allow probability less than zero from roundoff error
		}
		else if (sm > 1) {
		 sm = 1;		// do not allow probability more than one from roundoff error
			}
			return  sm ;
	}
}