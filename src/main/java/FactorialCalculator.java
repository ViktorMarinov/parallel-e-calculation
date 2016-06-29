package main.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Map;

public class FactorialCalculator {
	
	private Map<Integer, BigDecimal> memory = new Hashtable<Integer, BigDecimal>();
	
	public BigDecimal getFactorial(int number){
		if(!memory.containsKey(number)){
			memory.put(number, factorial(number));
		}
		return memory.get(number);
	}
	
	private BigDecimal factorial(int number){
		if(number < 0)
			throw new IllegalArgumentException("Number must be non-negative!");
		
		if (number <= 1)
			return new BigDecimal("1");
		
		BigDecimal result = getFactorial(number - 1);
		return new BigDecimal(number).multiply(getFactorial(number - 1));
	}
	
}
