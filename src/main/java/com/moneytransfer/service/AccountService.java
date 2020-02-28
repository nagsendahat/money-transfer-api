package com.moneytransfer.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.moneytransfer.dao.DAOFactory;
import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.model.Account;
import com.moneytransfer.utils.MoneyUtil;

/**
 * Account Service 
 */
public class AccountService implements Service {
	
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
    private static Logger log = LogManager.getLogger(AccountService.class);

    
    /**
     * Find all accounts
     * @return
     * @throws MoneyTransferAPIException
     */
    public List<Account> getAllAccounts() throws MoneyTransferAPIException {
        return daoFactory.getAccountDAO().getAllAccounts();
    }

    /**
     * Find by account id
     * @param accountId
     * @return
     * @throws MoneyTransferAPIException
     */
    public Account getAccount(long accountId) throws MoneyTransferAPIException {
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }
    
    /**
     * Find balance by account Id
     * @param accountId
     * @return
     * @throws MoneyTransferAPIException
     */
    public BigDecimal getBalance(long accountId) throws MoneyTransferAPIException {
        final Account account = daoFactory.getAccountDAO().getAccountById(accountId);
        if(account == null){
            throw new MoneyTransferAPIException("Account not found");
        }
        return account.getBalance();
    }
    
    /**
     * Create Account
     * @param account
     * @return
     * @throws MoneyTransferAPIException
     */
    public Account createAccount(Account account) throws MoneyTransferAPIException {
        final long accountId = daoFactory.getAccountDAO().createAccount(account);
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }

    /**
     * Deposit amount by account Id
     * @param accountId
     * @param amount
     * @return
     * @throws MoneyTransferAPIException
     */
    public Account deposit(long accountId, BigDecimal amount) throws MoneyTransferAPIException {

        if (amount.compareTo(MoneyUtil.zeroAmount) <= 0){
            throw new MoneyTransferAPIException("Invalid Deposit amount");
        }
        daoFactory.getAccountDAO().updateAccountBalance(accountId,amount.setScale(4, RoundingMode.HALF_EVEN));
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }

    /**
     * Withdraw amount by account Id
     * @param accountId
     * @param amount
     * @return
     * @throws MoneyTransferAPIException
     */
    public Account withdraw(long accountId, BigDecimal amount) throws MoneyTransferAPIException {

        if (amount.compareTo(MoneyUtil.zeroAmount) <=0){
            throw new MoneyTransferAPIException("Invalid Withdrwal amount");
        }
        BigDecimal delta = amount.negate();
        if (log.isDebugEnabled())
            log.debug("Withdraw service: delta change to account  " + delta + " Account ID = " +accountId);
        daoFactory.getAccountDAO().updateAccountBalance(accountId,delta.setScale(4, RoundingMode.HALF_EVEN));
        return daoFactory.getAccountDAO().getAccountById(accountId);
    }
    
    public boolean deleteAccount(long accountId) throws MoneyTransferAPIException {
      int deleteCount = daoFactory.getAccountDAO().deleteAccountById(accountId);
      if (deleteCount == 1) {
          return true;
      } else {
          throw new MoneyTransferAPIException("Account to delete cannot be found");
      }
  }

}
