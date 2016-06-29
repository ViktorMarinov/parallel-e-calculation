package main.java;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.CountDownLatch;

public class SumCalculator extends Thread {
	
	private CountDownLatch stopLatch;

	private BigDecimal totalSum;

	private int step;

	private int start;

	private int precision;
	
	private boolean beQuiet;

	public SumCalculator(int start, int step, int precision, CountDownLatch stopLatch, boolean beQuiet) {
		this.start = start;
		this.step = step;
		this.precision = precision;
		this.stopLatch = stopLatch;
		this.beQuiet = beQuiet;
		totalSum = new BigDecimal("0.0");
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	@Override
	public void run() {
		log("Thread %s has started,", this.getName());
		long startTime = System.currentTimeMillis();
		try{
			FactorialCalculator factCalc = new FactorialCalculator();
			for(int i = start; i < precision; i += step){
				BigDecimal numerator = BigDecimal.valueOf(2*i + 1).setScale(precision);
				BigDecimal fact = factCalc.getFactorial(2 * i);
				BigDecimal result = numerator.divide(fact,  RoundingMode.CEILING);
				totalSum = totalSum.add(result).setScale(precision);
			}
		} catch (Exception e){
			log("Something went wrong while executing calculation in thread %s", this.getName());
			e.printStackTrace(System.out);
			throw new RuntimeException("Something went wrong while executing calculation in thread" + this.getName());
		} finally {
			long endTime = System.currentTimeMillis();
			log("Thread %s has ended.", this.getName());
			log("Thread %s execution time was %d milliseconds.", this.getName(), endTime - startTime);
			stopLatch.countDown();
		}
	}
	
	private void log(String message, Object... args){
		if(!beQuiet){
			String formatted = String.format(message, args);
			System.out.println(formatted);
		}
	}

}










