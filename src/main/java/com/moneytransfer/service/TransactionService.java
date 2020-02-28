package com.moneytransfer.service;

import com.moneytransfer.dao.DAOFactory;
import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.model.TransferRequest;
import com.moneytransfer.utils.MoneyUtil;


public class TransactionService implements Service {

	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
	
	/**
	 * Transfer fund between two accounts.
	 * @param transaction
	 * @return
	 * @throws MoneyTransferAPIException
	 */
	public Boolean transferFund(TransferRequest transaction) throws MoneyTransferAPIException {

		String currency = transaction.getCurrencyCode();
		if (MoneyUtil.INSTANCE.validateCcyCode(currency)) {
			int updateCount = daoFactory.getAccountDAO().transferAccountBalance(transaction);
			if (updateCount == 2) {
				return true;
			} else {
				// transaction failed
				throw new MoneyTransferAPIException("Transaction failed");
			}

		} else {
			throw new MoneyTransferAPIException("Currency Code Invalid ");
		}
	}

}
