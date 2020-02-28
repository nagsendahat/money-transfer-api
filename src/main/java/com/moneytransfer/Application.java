package com.moneytransfer;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.moneytransfer.controllers.AccountController;
import com.moneytransfer.controllers.TransactionController;
import com.moneytransfer.controllers.UserController;
import com.moneytransfer.dao.DAOFactory;
import com.moneytransfer.service.AccountService;
import com.moneytransfer.service.TransactionService;
import com.moneytransfer.service.UserService;

/**
 * Main Class (Starting point) 
 */
public class Application {

	private static Logger log = LogManager.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		// Initialize H2 database with demo data
		log.info("Initialize demo .....");
		DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
		h2DaoFactory.populateTestData();
		log.info("Initialisation Complete....");
		new UserController(new UserService()).start();
        new AccountController(new AccountService()).start();
		new TransactionController(new TransactionService()).start();
	}


}
