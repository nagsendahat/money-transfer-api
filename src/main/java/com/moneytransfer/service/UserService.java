package com.moneytransfer.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.moneytransfer.dao.DAOFactory;
import com.moneytransfer.exception.EntityNotFoundException;
import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.model.User;


public class UserService implements Service {
 
	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
    
	private static Logger log = LogManager.getLogger(UserService.class);

	/**
	 * Find by userName
	 * @param userName
	 * @return
	 * @throws MoneyTransferAPIException
	 */
    public User getUserByName(String userName) throws MoneyTransferAPIException, EntityNotFoundException {
        if (log.isDebugEnabled())
            log.debug("Request Received for get User by Name " + userName);
        final User user = daoFactory.getUserDAO().getUserByName(userName);
        if (user == null) {
            throw new EntityNotFoundException("User with UserName '%s' not found.",userName);
        }
        return user;
    }
    
    /**
	 * Find by all
	 * @param userName
	 * @return
	 * @throws MoneyTransferAPIException
	 */
    public List<User> getAllUsers() throws MoneyTransferAPIException {
        return daoFactory.getUserDAO().getAllUsers();
    }
    
    /**
     * Create User
     * @param user
     * @return
     * @throws MoneyTransferAPIException
     */
    public User createUser(User user) throws MoneyTransferAPIException {
        if (daoFactory.getUserDAO().getUserByName(user.getUserName()) != null) {
            throw new MoneyTransferAPIException("User name already exist with user name "+user.getUserName());
        }
        final long uId = daoFactory.getUserDAO().insertUser(user);
        return daoFactory.getUserDAO().getUserById(uId);
    }
    
    /**
     * Find by User Id
     * @param userId
     * @param user
     * @return
     * @throws MoneyTransferAPIException
     */
    public User updateUser(long userId, User user) throws MoneyTransferAPIException {
      if (daoFactory.getUserDAO().getUserById(userId) == null) {
        throw new MoneyTransferAPIException("User with user with Id "+userId+" does not exists");
      }
      daoFactory.getUserDAO().updateUser(userId, user);
      return daoFactory.getUserDAO().getUserById(userId);
    }

}
