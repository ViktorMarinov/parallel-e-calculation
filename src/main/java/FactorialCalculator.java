package main.java;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FactorialCalculator {
	
	private static Map<Integer, BigInteger> memory = new HashMap<Integer, BigInteger>();
	
	public static BigInteger getFactorial(int number){
		if(!memory.containsKey(number)){
			memory.put(number, factorial(number));
		}
		return memory.get(number);
	}
	
	private static BigInteger factorial(int number){
		if(number < 0)
			throw new IllegalArgumentException("Number must be non-negative!");
		
		if (number <= 1)
			return BigInteger.valueOf(1);
		
		BigInteger result = getFactorial(number - 1);
		return BigInteger.valueOf(number).multiply(result);
	}
	
}
