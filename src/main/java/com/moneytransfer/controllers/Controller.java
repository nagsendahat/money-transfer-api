package com.moneytransfer.controllers;

public interface Controller
{
  public static final String EMPTY = "";
  public static final String SUCCESSFUL_TRANSFER_BODY = "{\"transferStatus\": \"OK\"}";
  public static final String FAILED_TRANSFER_BODY = "{\"transferStatus\": \"FAILED\"}";
  public static final String EXCEPTION_BODY = "\"%s\"}";

  void start() ;

}
