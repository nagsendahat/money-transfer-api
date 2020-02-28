package com.moneytransfer.dao;

import java.math.BigDecimal;
import java.util.List;

import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.TransferRequest;


public interface AccountDAO {

    List<Account> getAllAccounts() throws MoneyTransferAPIException;
    Account getAccountById(long accountId) throws MoneyTransferAPIException;
    long createAccount(Account account) throws MoneyTransferAPIException;
    int deleteAccountById(long accountId) throws MoneyTransferAPIException;

    /**
     *
     * @param accountId user accountId
     * @param deltaAmount amount to be debit(less than 0)/credit(greater than 0).
     * @return no. of rows updated
     */
    int updateAccountBalance(long accountId, BigDecimal deltaAmount) throws MoneyTransferAPIException;
    int transferAccountBalance(TransferRequest userTransaction) throws MoneyTransferAPIException;
}
