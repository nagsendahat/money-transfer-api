package com.moneytransfer.services;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.moneytransfer.controllers.AccountController;
import com.moneytransfer.controllers.TransactionController;
import com.moneytransfer.controllers.UserController;
import com.moneytransfer.dao.DAOFactory;
import com.moneytransfer.service.AccountService;
import com.moneytransfer.service.TransactionService;
import com.moneytransfer.service.UserService;

import spark.Spark;


public abstract class TestService {
  
    protected static DAOFactory h2DaoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
    protected final String baseURL = "http://localhost:4567";
    
    @BeforeClass
    public static void setup() throws Exception {
        h2DaoFactory.populateTestData();
        new UserController(new UserService()).start();
        new AccountController(new AccountService()).start();
        new TransactionController(new TransactionService()).start();
    }

    @AfterClass
    public static void stopService() throws Exception {
      Spark.stop();
    }
    
}
