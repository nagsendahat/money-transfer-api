package com.moneytransfer.controllers;

import static com.moneytransfer.utils.JsonUtil.json;
import static com.moneytransfer.utils.JsonUtil.toJson;
import static com.moneytransfer.utils.JsonUtil.fromJson​;
import static spark.Spark.after;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.moneytransfer.exception.MoneyTransferAPIException;
import com.moneytransfer.exception.ResponseError;
import com.moneytransfer.model.User;
import com.moneytransfer.service.UserService;

public class UserController implements Controller {
  
  private UserService service;
  
  public UserController(final UserService srvc)
  {
    this.service = srvc;
  }
 
  @Override
  public void start() {
    get("/users/all", (req, res) -> service.getAllUsers(), json());
    
    get("/users/byUserName/:userName", (req, res) -> {
      String userName = req.params("userName");
      return service.getUserByName(userName);
      }, json()); 
    
    post("/users/create", (req, res) -> { 
      User user = fromJson​(req.body(), User.class);
      return service.createUser(user);
    }, json());
     
    put("/update/user/:id", (req, res) -> service.updateUser(Long.valueOf(req.params("id")), fromJson​(req.body(), User.class)), json()); 
    
    exception(MoneyTransferAPIException.class, (ex, req, res) -> {
      res.status(400);
      res.body(toJson(new ResponseError(ex.getMessage())));
    });

    exception(MoneyTransferAPIException.class, (ex, req, res) -> {
      res.status(404);
      res.body(toJson(new ResponseError(ex.getMessage())));
    });

    after((req, res) -> {
      res.type("application/json");
    });

  }
}
