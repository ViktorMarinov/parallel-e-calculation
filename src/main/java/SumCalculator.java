package main.java;

import java.math.BigInteger;

public class SumCalculator extends Thread {

	private BigInteger totalSum;

	private int step;

	private int start;

	private int precision;

	public SumCalculator(int start, int step, int precision) {
		setStart(start);
		setStep(step);
		setprecision(precision);
		setTotalSum(BigInteger.valueOf(0));
	}

	public BigInteger getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigInteger totalSum) {
		this.totalSum = totalSum;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;

	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getprecision() {
		return precision;
	}

	public void setprecision(int precision) {
		this.precision = precision;
	}

	@Override
	public void run() {
		for(int i = start; i < precision; i += step){
			BigInteger fact = FactorialCalculator.getFactorial(i);
			
			totalSum.add(fact);
		}
	}

}
