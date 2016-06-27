package main.java;

public class EntryPoint {

	public static int precision;

	public static int tasks;

	public static String logPath = "results";

	public static void main(String[] args) {
		readCLIOptions(args);
		

	}
	
	public static void executeCalculation() {
		
	}

	public static void readCLIOptions(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-p":
					precision = Integer.parseInt(args[i++ + 1]);
					break;

				case "-t":
					tasks = Integer.parseInt(args[i++ + 1]);
					break;

				case "-o":
					logPath = args[i++ + 1];
					break;

				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid input parameters");
			System.exit(1);
		}
	}

}
