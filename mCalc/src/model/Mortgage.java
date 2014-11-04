/**
 * 
 */
package model;

import java.security.InvalidParameterException;

/**
 * @author rajiteh
 *
 */
public class Mortgage {

	/**
	 * 
	 */
	public Mortgage() {
		// TODO Auto-generated constructor stub
	}

	public double computePayment(String p, String a, String i) throws Exception {
		double principle = this.extractPrinciple(p); 
		double amortization = this.extractAmortization(a);
		double interest = this.extractInterest(i);
		
		double rate = (interest / 12) / 100;
		if (rate > 0) {
			return  (rate * principle) / (1 - Math.pow(1 + rate, -amortization * 12));	
		} else {
			return principle / (amortization * 12);
		}
		
	}

	private double extractPrinciple(String parse) {
		double result = Double.parseDouble(parse);
		if (result <= 0) throw new InvalidParameterException("Principle value is must be positive.");
		
		return result;
	}
	
	private double extractAmortization(String parse) {
		double result = Double.parseDouble(parse);
		if (!(result == 20.0 || result == 25.0 || result == 30.0)) throw new InvalidParameterException("Amortization value must be '20', '25' or '30'.");
		
		return result;
	}
	private double extractInterest(String parse) {
		double result = Double.parseDouble(parse);
		if (result > 100 || result < 0) throw new InvalidParameterException("Interest value must be between '0' and '100'.");
		
		return result;
	}
}
