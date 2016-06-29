package main.java;

import java.math.BigDecimal;
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
			return BigDecimal.valueOf(1);
		
		BigDecimal result = BigDecimal.valueOf(1);
		for (int i = number; i > 1; --i) {
			if(memory.containsKey(i)){
				return result.multiply(memory.get(i));
			}
			result = result.multiply(BigDecimal.valueOf(i));
		}

		return result;
	}
}
