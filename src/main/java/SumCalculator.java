package main.java;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class SumCalculator extends Thread {
	
	private CountDownLatch stopLatch;

	private BigDecimal totalSum;

	private int step;

	private int start;

	private int precision;
	
	private String logPath;
	
	private boolean beQuiet;

	public SumCalculator(int start, int step, int precision, CountDownLatch stopLatch, String logPath, boolean beQuiet) {
		this.start = start;
		this.step = step;
		this.precision = precision;
		this.stopLatch = stopLatch;
		this.logPath = logPath;
		this.beQuiet = beQuiet;
		totalSum = BigDecimal.valueOf(0);
	}

	@Override
	public void run() {
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(new FileWriter(logPath));
			for(int i = start; i < precision; i += step){
				BigInteger numerator = BigInteger.valueOf(2*i + 1);
				BigInteger fact = FactorialCalculator.getFactorial(2 * i);
				BigDecimal result = new BigDecimal(numerator.divide(fact));
				totalSum.add(result);
			}
		} catch (IOException e){
			log(new PrintWriter(System.out), "Could not open the file!");
			System.exit(1);
		} catch (Exception e){
			log(writer, "Something went wrong while execution calculation!");
			log(new PrintWriter(System.out), "Something went wrong while execution calculation!");
			System.exit(1);
		} finally {
			stopLatch.countDown();
			writer.close();
		}
	}
	
	private void log(PrintWriter out, String message){
		if(!beQuiet){
			out.println(message);
		}
	}

}










