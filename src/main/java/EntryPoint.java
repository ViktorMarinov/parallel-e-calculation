package main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class EntryPoint {

	private static int precision = 10000;

	private static int tasks = Runtime.getRuntime().availableProcessors();

	private static String filePath = "results";

	private static boolean beQuiet = false;

	public static void main(String[] args) throws InterruptedException {

		if (args.length > 1 && "test".equals(args[0])) {
			readCLIOptions(Arrays.copyOfRange(args, 2, args.length));
			makeTest(Integer.valueOf(args[1]));
			return;
		}
		readCLIOptions(args);
		executeCalculation(tasks, precision, beQuiet, filePath);
	}

	public static long executeCalculation(int tasks, int precision, boolean beQuiet, String filePath)
			throws InterruptedException {
		CountDownLatch stopLatch = new CountDownLatch(tasks);

		long startTime = System.currentTimeMillis();
		SumCalculator[] threadPool = new SumCalculator[tasks];
		for (int i = 0; i < tasks; i++) {
			threadPool[i] = new SumCalculator(tasks - 1 - i, tasks, precision, stopLatch, beQuiet);
			threadPool[i].start();
		}

		stopLatch.await();
		BigDecimal finalResult = new BigDecimal("0");
		for (SumCalculator thread : threadPool) {
			finalResult = finalResult.add(thread.getTotalSum());
		}
		// System.out.println(finalResult);
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filePath);
			writer.println(finalResult.toString());
		} catch (IOException e) {
			System.out.println("Could not open file " + filePath);
		} finally {
			writer.flush();
			writer.close();
		}

		log("Threads used in current run: %d", tasks);
		System.out.println("Total execution time for current run (millis): " + elapsed);
		return elapsed;
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
				case "--out":
					filePath = args[++i];
					break;

				case "-q":
				case "--quiet":
					beQuiet = true;
					break;
				}
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			System.out.println("Invalid input parameters");
			System.exit(1);
		}
	}

	private static void log(String message, Object... args) {
		if (!beQuiet) {
			String formatted = String.format(message, args);
			System.out.println(formatted);
		}
	}

	private static void makeTest(int maxNumberOfThreads) throws InterruptedException {
		try (PrintWriter writer = new PrintWriter("test_results")) {
			for (int i = 1; i <= maxNumberOfThreads; ++i) {
				long timeElapsed = executeCalculation(i, precision, beQuiet, filePath);
				writer.printf("Number of threads: %d Time(millis): %d\n", i, timeElapsed);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file!");
			return;
		}
	}

}
