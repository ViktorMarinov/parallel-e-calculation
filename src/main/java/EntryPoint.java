package main.java;

public class EntryPoint {

	public static int precision;

	public static int tasks = Runtime.getRuntime().availableProcessors();

	public static String logPath = "results";
	
	public static boolean beQuiet = false;

	public static void main(String[] args) {
		readCLIOptions(args);
		executeCalculation();

	}
	
	public static void executeCalculation() {
		
		SumCalculator[] threadPool = new SumCalculator[tasks];
		for (int i = 0 ; i < tasks ; i++){
			threadPool[i].setStart(tasks - i - 1);
			threadPool[i].setprecision(precision);
			threadPool[i].setStep(tasks);
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

				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input parameters");
			System.exit(1);
		}
	}

}
