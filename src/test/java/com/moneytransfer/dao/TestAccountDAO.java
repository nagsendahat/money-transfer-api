package com.moneytransfer.dao;

import com.moneytransfer.dao.DAOFactory;
import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.model.Account;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestAccountDAO {

	private static final DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);

	@BeforeClass
	public static void setup() {
		// prepare test database and test data. Test data are initialised from
		// src/test/resources/demo.sql
		h2DaoFactory.populateTestData();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testGetAllAccounts() throws MoneyTransferAPIException {
		List<Account> allAccounts = h2DaoFactory.getAccountDAO().getAllAccounts();
		assertTrue(allAccounts.size() > 1);
	}

	@Test
	public void testGetAccountById() throws MoneyTransferAPIException {
		Account account = h2DaoFactory.getAccountDAO().getAccountById(2L);
		assertTrue(account.getUserId() == 2);
	}

	@Test
	public void testGetNonExistingAccById() throws MoneyTransferAPIException {
		Account account = h2DaoFactory.getAccountDAO().getAccountById(100L);
		assertTrue(account == null);
	}

	@Test
	public void testCreateAccount() throws MoneyTransferAPIException {
		BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
		Account a = new Account(3, balance, "CNY");
		long aid = h2DaoFactory.getAccountDAO().createAccount(a);
		Account afterCreation = h2DaoFactory.getAccountDAO().getAccountById(aid);
		assertTrue(afterCreation.getUserId() == 3);
		assertTrue(afterCreation.getCurrencyCode().equals("CNY"));
		assertTrue(afterCreation.getBalance().equals(balance));
	}

	@Test
	public void testDeleteAccount() throws MoneyTransferAPIException {
		int rowCount = h2DaoFactory.getAccountDAO().deleteAccountById(2L);
		// assert one row(user) deleted
		assertTrue(rowCount == 1);
		// assert user no longer there
		assertTrue(h2DaoFactory.getAccountDAO().getAccountById(2L) == null);
	}

	@Test
	public void testDeleteNonExistingAccount() throws MoneyTransferAPIException {
		int rowCount = h2DaoFactory.getAccountDAO().deleteAccountById(500L);
		// assert no row(user) deleted
		assertTrue(rowCount == 0);

	}

	@Test
	public void testUpdateAccountBalanceSufficientFund() throws MoneyTransferAPIException {

		BigDecimal deltaDeposit = new BigDecimal(50).setScale(4, RoundingMode.HALF_EVEN);
		BigDecimal afterDeposit = new BigDecimal(150).setScale(4, RoundingMode.HALF_EVEN);
		int rowsUpdated = h2DaoFactory.getAccountDAO().updateAccountBalance(1L, deltaDeposit);
		assertTrue(rowsUpdated == 1);
		assertTrue(h2DaoFactory.getAccountDAO().getAccountById(1L).getBalance().equals(afterDeposit));
		BigDecimal deltaWithDraw = new BigDecimal(-50).setScale(4, RoundingMode.HALF_EVEN);
		BigDecimal afterWithDraw = new BigDecimal(100).setScale(4, RoundingMode.HALF_EVEN);
		int rowsUpdatedW = h2DaoFactory.getAccountDAO().updateAccountBalance(1L, deltaWithDraw);
		assertTrue(rowsUpdatedW == 1);
		assertTrue(h2DaoFactory.getAccountDAO().getAccountById(1L).getBalance().equals(afterWithDraw));

	}

	@Test(expected = MoneyTransferAPIException.class)
	public void testUpdateAccountBalanceNotEnoughFund() throws MoneyTransferAPIException {
		BigDecimal deltaWithDraw = new BigDecimal(-50000).setScale(4, RoundingMode.HALF_EVEN);
		int rowsUpdatedW = h2DaoFactory.getAccountDAO().updateAccountBalance(1L, deltaWithDraw);
		assertTrue(rowsUpdatedW == 0);

	}

}