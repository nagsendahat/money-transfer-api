package com.moneytransfer.dao;

import java.util.List;

import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.model.User;

public interface UserDAO {
	
	List<User> getAllUsers() throws MoneyTransferAPIException;

	User getUserById(long userId) throws MoneyTransferAPIException;

	User getUserByName(String userName) throws MoneyTransferAPIException;

	/**
	 * @param user:
	 * user to be created
	 * @return userId generated from insertion. return -1 on error
	 */
	long insertUser(User user) throws MoneyTransferAPIException;

	int updateUser(Long userId, User user) throws MoneyTransferAPIException;

	int deleteUser(long userId) throws MoneyTransferAPIException;

}
