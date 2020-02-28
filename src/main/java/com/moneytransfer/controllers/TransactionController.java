package com.moneytransfer.controllers;

import static com.moneytransfer.utils.JsonUtil.fromJson​;
import static com.moneytransfer.utils.JsonUtil.json;
import static com.moneytransfer.utils.JsonUtil.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.put;

import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.exception.ResponseError;
import com.moneytransfer.model.TransferRequest;
import com.moneytransfer.service.TransactionService;

public class TransactionController implements Controller 
{
  private TransactionService service;
  
  public TransactionController(final TransactionService srvc)
  {
    this.service = srvc;
  }
  
  @Override
  public void start() {
    
    put("/transfer", (req, res) -> service.transferFund(fromJson​(req.body(), TransferRequest.class)), json());

    exception(MoneyTransferAPIException.class, (ex, req, res) -> {
      res.status(400);
      res.body(toJson(new ResponseError(ex.getMessage())));
    });
    
    after((req, res) -> {
      res.type("application/json");
    });
  }
}
