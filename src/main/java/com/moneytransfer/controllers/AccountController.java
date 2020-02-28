package com.moneytransfer.controllers;

import static com.moneytransfer.utils.JsonUtil.fromJson​;
import static com.moneytransfer.utils.JsonUtil.json;
import static com.moneytransfer.utils.JsonUtil.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import java.math.BigDecimal;

import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.exception.ResponseError;
import com.moneytransfer.model.Account;
import com.moneytransfer.service.AccountService;

public class AccountController implements Controller
{
  private AccountService service;
  
  public AccountController(final AccountService srvc)
  {
    this.service = srvc;
  }
  
  @Override
  public void start() {
    get("/accounts/all", (req, res) -> service.getAllAccounts(), json());
    
    get("/accounts/:accountId", (req, res) -> {
      String accountId = req.params(":accountId");
      return service.getAccount(Long.valueOf(accountId));
      }, json()); 
    
    get("/accounts/balance/:accountId", (req, res) -> { 
      String accountId = req.params(":accountId");
      return service.getBalance(Long.valueOf(accountId));
    }, json());
     
    post("/accounts/create", (req, res) -> service.createAccount(fromJson​(req.body(), Account.class)), json()); 
    
    put("/deposit/:accountId/:amount", (req, res) -> service.deposit(Long.valueOf(req.params("accountId")),new BigDecimal(req.params("amount"))), json()); 

    put("/withdraw/:accountId/:amount", (req, res) -> service.withdraw(Long.valueOf(req.params("accountId")),new BigDecimal(req.params("amount"))), json()); 
    
    delete("/delete/:accountId", (req, res) -> service.deleteAccount(Long.valueOf(req.params("accountId"))), json());

    exception(MoneyTransferAPIException.class, (ex, req, res) -> {
      res.status(400);
      res.body(toJson(new ResponseError(ex.getMessage())));
    });
    
    after((req, res) -> {
      res.type("application/json");
    });

  }
}
