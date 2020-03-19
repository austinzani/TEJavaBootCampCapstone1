package com.techelevator.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.lang.NumberFormatException;

public class Transaction {
	private static final int QUARTER = 25;
	private static final int DIME = 10;
	private static final int NICKEL = 5;
	private static final int DOLLAR = 100;
	private static final BigDecimal CONVERT_TO_PENNIES = new BigDecimal("100");

	private static BigDecimal balance = new BigDecimal("0.00");

	public void addFunds(String input) {
		try {
		if(input.equals("")) {
			input = "0";
		}
		if(Integer.parseInt(input) < 0) {
			input = "0";
		}
		BigDecimal inputDeposit = new BigDecimal(input);
		balance = balance.add(inputDeposit);
		} catch (NumberFormatException e) {
			//if invalid input do nothing to the balance
		}
	}

	public BigDecimal subtractFunds(BigDecimal input) {
		return balance = balance.subtract(input);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String makeChange() {
		balance = balance.multiply(CONVERT_TO_PENNIES);
		int methodBalance = balance.intValue();
		int methodBalanceDollars = methodBalance / DOLLAR;
		methodBalance -= methodBalanceDollars * DOLLAR;
		int methodBalanceQuarters = methodBalance / QUARTER;
		methodBalance -= methodBalanceQuarters * QUARTER;
		int methodBalanceDimes = methodBalance / DIME;
		methodBalance -= methodBalanceDimes * DIME;
		int methodBalanceNickels = methodBalance / NICKEL;
		methodBalance -= methodBalanceNickels * NICKEL;
		balance = new BigDecimal("0.00");
		return "You were returned " + methodBalanceDollars + " dollar(s), " + methodBalanceQuarters + " quarter(s), "
				+ methodBalanceDimes + " dime(s), and " + methodBalanceNickels + " nickel(s).";

	}

}
