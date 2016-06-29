package main.java;

import java.util.concurrent.CountDownLatch;

public class EntryPoint {

	private static int precision;

	private static int tasks = Runtime.getRuntime().availableProcessors();

	private static String logPath = "results";
	
	private static boolean beQuiet = false;

	public static void main(String[] args) {
		readCLIOptions(args);
		executeCalculation();

	}
	
	public static void executeCalculation() {
		CountDownLatch stopLatch = new CountDownLatch(tasks);
		
		SumCalculator[] threadPool = new SumCalculator[tasks];
		for (int i = 0 ; i < tasks ; i++){
			threadPool[i] = new SumCalculator(tasks - 1, tasks, precision, stopLatch, logPath, beQuiet);
			threadPool[i].start();
		}
	}

	public static void readCLIOptions(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-p":
					precision = Integer.parseInt(args[++i]);
					break;

				case "-t":
					tasks = Integer.parseInt(args[++i]);
					break;

				case "-o":
					logPath = args[++i];
					break;
				
				case "-q":
					beQuiet = true;
					break;
				}
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			System.out.println("Invalid input parameters");
			System.exit(1);
		}
	}

}
