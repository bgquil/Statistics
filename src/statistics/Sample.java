package statistics;
/**
 * Sample.java
 * 
 *  Contains the sample data used for more specific calculations
 *  
 *  Methods for 5 number summary and basic statistics for a sample.
 *
 * @author: Benjamin Quilliams
 */

import java.lang.Math.*;
import java.util.Arrays;

import javafx.beans.property.DoubleProperty;

public class Sample<Double> {
	private String name;
	private double[] sampleData;
	private double[] sortedSampleData;
	private int sampleSize;
	private double sampleMean;
	private double sampleMin;
	private double sampleQ1;
	private double sampleMedian;
	private double sampleQ3;
	private double sampleMax;
	private double sampleStdDeviation;
	private double sampleStdError;
	private double sampleRange;
	private double sampleVariance;
	private double sampleSum;

    /**
     * Creates a sample object and calculates statistical information from the provided data array.
     * @param data The sample data to be analyzed.
     */
	public Sample(double[] data){
		this.name = "DEFAULT";
		this.sampleData = data;
		this.sampleSize = data.length;
		sortedSampleData = Arrays.copyOf(data, data.length);
		Arrays.sort(this.sortedSampleData);
		this.calculate();
	}
	
	public Sample() {
	}
	
	public void setData(double[] data){
		this.name = "DEFAULT";
		this.sampleData = data;
		this.sampleSize = data.length;
		sortedSampleData = Arrays.copyOf(data, data.length);
		Arrays.sort(this.sortedSampleData);
		this.calculate();
		
	}


	public void calculate(){
		this.setSampleMean();
		this.setMinQ1Q3Max();
		this.setSampleMedian();
		this.setSampleStdDev();
		this.setSampleRange();
		this.calculateSampleVariance();
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public double[] getSampleData(){
		return this.sampleData;
	}
	
	public double[] getSortedSampleData(){
		return this.sortedSampleData;
	}

	/**
	 * @return the sample Variance
	 */
	public double getSampleVariance() {
		return sampleVariance;
	}

	/**
	 * @param sampleVariance the sample Variance to set
	 */
	public void setSampleVariance(double sampleVariance) {
		this.sampleVariance = sampleVariance;
	}

	/**
	 * @return the sampleSum
	 */
	public double getSampleSum() {
		return sampleSum;
	}

	/**
	 * @param sampleSum the sampleSum to set
	 */
	public void setSampleSum(double sampleSum) {
		this.sampleSum = sampleSum;
	}
	
	public int getSampleSize(){
		return sampleData.length;
	}
	
	public double get(int i){
		return this.sampleData[i];
	}
	
	public void setSampleMean() {
		double tempSum = 0;
		for (double data : sampleData) {
			tempSum += data;
		}
		this.sampleSum = tempSum;
		this.sampleMean = tempSum/sampleSize;
	}
	
	public double getSampleMean() { 
		return this.sampleMean;
		
	}
	
	/*
	 * Test cases for small sets N <= 6
	 */
	public void setMinQ1Q3Max(){
			
			sampleMin = sortedSampleData[0];
			sampleMax = sortedSampleData[sampleSize-1];
			
			// even sample size
			if (sampleSize % 2 == 0){
				int mid = (int) sampleSize / 2;
				sampleQ1 = sortedSampleData[(mid-1)/2];
				sampleQ3 = sortedSampleData[(mid+mid/2)];
				//int Q2 = ;
				
				
			}
			//odd sample size
			else {
				int mid = (int) sampleSize / 2;
				int midLower = mid/2;
				sampleQ1 = (sortedSampleData[midLower] + sortedSampleData[midLower-1])/2.0d;
				// need 6 7
				int midUpper = mid+mid/2;
				sampleQ3 = (sortedSampleData[midUpper] + sortedSampleData[midUpper+1])/2;

			}
			
		}
	
	public double getSampleMin(){
		return this.sampleMin;
	}
	
	public double getSampleQ1() {
		return sampleQ1;
	}
	
	public double getSampleQ3(){
		return this.sampleQ3;
	}
	
	public double getSampleMax(){
		return this.sampleMax;
	}
	
	private void setSampleMedian() {
		if (sampleSize % 2 != 0)
			this.sampleMedian = sortedSampleData[(int) Math.ceil(sampleSize/2)];
		else {
			int position = (int) Math.floor(sampleSize/2)-1;
			
			this.sampleMedian = ( sortedSampleData[position] + sortedSampleData[(int) position + 1] )/2;
		}
	}

	public double getSampleMedian() {
		return sampleMedian;
	}

	private void setSampleStdDev() {
		
		double tempSum = 0;
		double mean = this.getSampleMean();
		for (double data : sampleData) {
			
			tempSum += Math.pow((data - mean), 2);
			
		}
		this.sampleStdDeviation = Math.sqrt(tempSum/(sampleSize-1));
	}
	
	public double getSampleStdDev() {
		return this.sampleStdDeviation;
	}
	
	private void setSampleStdError(){
		this.sampleStdError = sampleStdDeviation/sampleSize;
	}
	
	public double getSampleStdError(){
		return this.sampleStdError;
	}
	
	private void setSampleRange(){
		this.sampleRange = sampleMax-sampleMin;
	}
	
	public double getSampleRange(){
		return this.sampleRange;
	}

	private void calculateSampleVariance() {
		double var = 0;
		for (double d : this.sampleData){
			var += Math.pow(d - this.sampleMean, 2);
		}
		
		this.sampleVariance = (1d/(this.sampleSize-1))*var;
		
	}

	@Override
	public String toString(){

		StringBuilder sb = new StringBuilder();
		
		return 	"Size: "+getSampleSize()+
				"\nMean: "+getSampleMean()+
				"\nMin: "+getSampleMin()+
				"\nQ1: "+getSampleQ1()+
				"\nMedian: "+getSampleMedian()+
				"\nQ3: "+getSampleQ3()+
				"\nMax: "+getSampleMax()+
				"\nSample Standard Deviation: "+getSampleStdDev()+
				"\nSample Data: "+Arrays.toString(sampleData)+
				"\nSorted Sample Data: "+Arrays.toString(sortedSampleData);
				
	}

}
