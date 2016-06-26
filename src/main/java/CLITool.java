package main.java;

import org.apache.commons.cli.Options;

public class CLITool {
	public static void main(String[] args){
		Options options = new Options();
		options.addOption("p", true, "precision");
		options.addOption("t", true, "Max number of threads");
	}
}
