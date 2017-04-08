package statistics;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ChiSquared {
	
	private double[][] data;
	private double[][] dataHypothesisTotals;
	private double[][] dataExpected;
	private int rSize;
	private int cSize;
	private int hypRSize;
	private int hypCSize;
	private double chiStatistic;
	private int DOF;
	private double pValue;
	


	public ChiSquared(double data[][], int rSize, int cSize){
		this.data = data;
		this.rSize = rSize;
		this.cSize = cSize;
		this.hypRSize = rSize + 1;
		this.hypCSize = cSize + 1;
		dataHypothesisTotals = new double[hypRSize][hypCSize];
		dataExpected  = new double[rSize][cSize];
		System.out.println(rSize);
		System.out.println(cSize);
		System.out.println(hypRSize);
		System.out.println(hypCSize);
		copyDataMatrix();
		calculateDOF();
		calculateTotal();
		calculateExpected();
		pValue = pochisq(chiStatistic, DOF);
	}
	
	
	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public double[][] getDataHypothesis() {
		return dataHypothesisTotals;
	}

	public void setDataHypothesis(double[][] dataHypothesis) {
		this.dataHypothesisTotals = dataHypothesis;
	}

	public int getrSize() {
		return rSize;
	}

	public void setrSize(int rSize) {
		this.rSize = rSize;
	}

	public int getcSize() {
		return cSize;
	}

	public void setcSize(int cSize) {
		this.cSize = cSize;
	}

	public int getHypRSize() {
		return hypRSize;
	}

	public void setHypRSize(int hypRSize) {
		this.hypRSize = hypRSize;
	}

	public int getHypCSize() {
		return hypCSize;
	}

	public void setHypCSize(int hypCSize) {
		this.hypCSize = hypCSize;
	}
	
	public double[][] getDataExpected() {
		return dataExpected;
	}
	

	
	public double getChiStatistic(){
		return this.chiStatistic;
	}
	
	public int getDOF(){
		return this.DOF;
	}

	public double getpValue() {
		return pValue;
	}


	private void calculateTotal(){
		
		GridPane gp = new GridPane();
		double overallTotal = 0;
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			double rowTotal = 0;
			
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
				rowTotal+=data[rowIndex][columnIndex];

			}
			overallTotal += rowTotal;
			//Row Total
			dataHypothesisTotals[rowIndex][cSize] = rowTotal;
			TextField t = new TextField(Double.toString(rowTotal));
			t.setStyle("-fx-background-color: white, white, #c0c0c0; -fx-font-size: 12pt;");
			gp.add(t, cSize, rowIndex );
			
			
			
		}
		//Overall Total
		dataHypothesisTotals[rSize][cSize] = overallTotal;
		TextField t2 = new TextField(Double.toString(overallTotal));
		t2.setStyle("-fx-background-color: white, white, #00ffff; -fx-font-size: 12pt;");
		gp.add(t2, cSize , rSize );
		
		
		for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
			double colTotal = 0;
			
			for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
					colTotal+=data[rowIndex][columnIndex];
			}
					
			//Column Total
			dataHypothesisTotals[rSize][columnIndex] = colTotal;
			TextField t3 = new TextField(Double.toString(colTotal));
			t3.setStyle("-fx-background-color: white, white, #c0c0c0; -fx-font-size: 12pt;");
			gp.add(t3, columnIndex , rSize );
			
		}
		//return gp;
	}
	
	
	
	
	private void calculateDOF(){
		this.DOF = (this.rSize-1)*(this.cSize-1);
	}
	
	/*
	 * Calculates the expected matrix and Chi-statistic.
	 */
	private void calculateExpected(){
		double tmpSum = 0;
		double total = dataHypothesisTotals[hypRSize-1][hypCSize-1]; //index begins at 0
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
				
//				tmpSum += dataExpected[rowIndex][columnIndex] = 
//						dataHypothesisTotals[hypRSize-1][columnIndex] 
//								* (dataHypothesisTotals[rowIndex][hypCSize-1]/total);
				double observed = dataHypothesisTotals[rowIndex][columnIndex];
				double expected = dataExpected[rowIndex][columnIndex] = 
						dataHypothesisTotals[hypRSize-1][columnIndex] 
								* (dataHypothesisTotals[rowIndex][hypCSize-1]/total); 
				
				tmpSum += Math.pow((observed-expected), 2)/expected;
			}
		}	
		this.chiStatistic = tmpSum;
	}
	
	/*
	 * start public domain code
	 */
	
	private double poz(double z) {
        double y, x, w;
        double Z_MAX = 6.0;              /* Maximum meaningful z value */
        
        if (z == 0.0) {
            x = 0.0;
        } else {
            y = 0.5 * Math.abs(z);
            if (y >= (Z_MAX * 0.5)) {
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
	
	private double pochisq(double x, int df) {
        double a, y = 0, s;
        double e, c, z;
        boolean even;                     /* True if df is an even number */

        double LOG_SQRT_PI = 0.5723649429247000870717135; /* log(sqrt(pi)) */
        double I_SQRT_PI = 0.5641895835477562869480795;   /* 1 / sqrt(pi) */
        
        if (x <= 0.0 || df < 1) {
            return 1.0;
        }
        
        a = 0.5 * x;
//        even = !(df & 1);
        even = ((df % 2) == 0  ? true: false);
        if (df > 1) {
            y = ex(-a);
        }
        s = (even ? y : (2.0 * poz(-Math.sqrt(x))));
        if (df > 2) {
            x = 0.5 * (df - 1.0);
            z = (even ? 1.0 : 0.5);
            if (a > BIGX) {
                e = (even ? 0.0 : LOG_SQRT_PI);
                c = Math.log(a);
                while (z <= x) {
                    e = Math.log(z) + e;
                    s += ex(c * z - a - e);
                    z += 1.0;
                }
                return s;
            } else {
                e = (even ? 1.0 : (I_SQRT_PI / Math.sqrt(a)));
                c = 0.0;
                while (z <= x) {
                    e = e * (a / z);
                    c = c + e;
                    z += 1.0;
                }
                return c * y + s;
            }
        } else {
            return s;
        }
    }
	
    double  BIGX = 20.0;                  /* max value to represent exp(x) */

    private double ex(double x) {
        return (x < -BIGX) ? 0.0 : Math.exp(x);
    }  
	
    /*
     * 
     * end public domain code
     */
	private void copyDataMatrix(){
		
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
				dataHypothesisTotals[rowIndex][columnIndex] = data[rowIndex][columnIndex]; 

			}
		}	
		
	}
	
	public void printMatrix(){
		System.out.println("Original Matrix");
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
					System.out.print(" "+data[rowIndex][columnIndex]+" ");

			}
			System.out.println();
		}	
		
	}
	
	public void printHypothesisMatrix(){
		System.out.println("Hypothesis Matrix");
		for (int rowIndex = 0; rowIndex < hypRSize; rowIndex++){
			
			for (int columnIndex = 0; columnIndex < hypCSize; columnIndex++){
					System.out.print(" "+dataHypothesisTotals[rowIndex][columnIndex]+" ");

			}
			System.out.println();
		}	
		
	}
	
	public void printExpectedMatrix(){
		System.out.println("Expected Matrix");
		for (int rowIndex = 0; rowIndex < rSize; rowIndex++){
			
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++){
					System.out.print(" "+dataExpected[rowIndex][columnIndex]+" ");

			}
			System.out.println();
		}	
		
	}
	
	
	

}
