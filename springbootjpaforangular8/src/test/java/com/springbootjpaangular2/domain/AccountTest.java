package com.springbootjpaangular2.domain;

import org.junit.Assert;
import org.junit.Test;


class Account {
	private double balance;
	private double overdraftLimit;

	public Account (double overdraftLimit) {
		this.balance = 0;
		this.overdraftLimit = overdraftLimit >0 ? overdraftLimit: 0;
	}
		
		public boolean deposit(double i) {
			return true;
		}
		
		public boolean withdraw(double j) {
			return true;
		}
		
		public double getBalance() {
			return balance;
		}
	
}

public class AccountTest {
	private double epsilon = 1e-6;
	
	@Test
	public void accountCannotHaveNegativeOverdraftLmit () {
		Account account = new Account (-20);	
	}
	
	
	@Test
	public void accountCannotHaveNegative() {
		Account account = new Account (-20);
		
		Assert.assertTrue(!account.deposit(-3));
		Assert.assertTrue(!account.withdraw(-3));
		
	}
	
	@Test
	public void accountOverStepLimit () {
		Account account = new Account (100);
		account.deposit(200);
		Assert.assertTrue(!account.withdraw(-1));
		
	}
	
	@Test
	public void accountDepositAndWithdrawCorrect() {
		Account account = new Account (-20);
		account.deposit(2);
		Assert.assertTrue(account.getBalance() ==2);
		account.withdraw(2);
		Assert.assertTrue(account.getBalance() ==0);
		
	}
	
	@Test
	public void accountDepositAndWithdrawReturnCorrect () {
		Account account = new Account (-20);
		Assert.assertTrue(account.deposit(2));
		Assert.assertTrue(account.withdraw(2));
		
	}
}
