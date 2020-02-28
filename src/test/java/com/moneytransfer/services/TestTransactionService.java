package com.moneytransfer.services;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import com.moneytransfer.model.TransferRequest;

import spark.Spark;


/**
 * Integration testing for RestAPI
 * Test data are initialised from src/test/resources/demo.sql
 * <p>
 * INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,100.0000,'USD');
 * INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,200.0000,'USD');
 * INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,500.0000,'EUR');
 * INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,500.0000,'EUR');
 * INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,500.0000,'GBP');
 * INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,500.0000,'GBP');
 */

public class TestTransactionService extends TestService {
  
  @Before
  public void before() throws Exception {
    Spark.awaitInitialization();
  } 
  //test transaction related operations in the account

    /*
       TC B1 Positive Category = AccountService
       Scenario: test deposit money to given account number
                 return 200 OK
    */
    @Test
    public void testDeposit() {
      when()
      .put(baseURL+"/deposit/1/15.25")
      .then().statusCode(200).assertThat()
      .body("userId", equalTo(1))
      .body("balance", is(100.0f))
      .body("currencyCode", equalTo("USD"));


    }

    /*
      TC B2 Positive Category = AccountService
      Scenario: test withdraw money from account given account number, account has sufficient fund
                return 200 OK
    */
    @Test
    public void testWithDrawSufficientFund() {
      when()
      .put(baseURL+"/withdraw/1/15.25")
      .then().statusCode(200).assertThat()
      .body("userId", equalTo(1))
      .body("balance", is(84.75f))
      .body("currencyCode", equalTo("USD"));

    }

    /*
       TC B3 Negative Category = AccountService
       Scenario: test withdraw money from account given account number, no sufficient fund in account
                 return 400 Bad Request
    */
    @Test
    public void testWithDrawNonSufficientFund() {
      
      String responseString = when()
      .put(baseURL+"/withdraw/1/800")
      .then().assertThat().statusCode(400)
      .extract().asString();
      assertTrue(responseString.contains("Not sufficient Fund for account"));
    }

    /*
       TC B4 Positive Category = AccountService
       Scenario: test transaction from one account to another with source account has sufficient fund
                 return 200 OK
    */
    @Test
    public void testTransactionEnoughFund() {
      
      BigDecimal amount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
      TransferRequest transaction = new TransferRequest("EUR", amount, 3L, 4L);
      String responseString = with().body(transaction)
      .when()
      .request("PUT", baseURL+"/transfer")
      .then()
      .statusCode(200)
      .extract().asString();
      assertTrue(Boolean.valueOf(responseString));
    }

    /*
        TC B5 Negative Category = AccountService
        Scenario: test transaction from one account to another with source account has no sufficient fund
                  return 500 INTERNAL SERVER ERROR
     */
    @Test
    public void testTransactionNotEnoughFund() {
      BigDecimal amount = new BigDecimal(10000).setScale(4, RoundingMode.HALF_EVEN);
      TransferRequest transaction = new TransferRequest("EUR", amount, 3L, 4L);
      String responseString = with().body(transaction)
      .when()
      .request("PUT", baseURL+"/transfer")
      .then()
      .statusCode(400)
      .extract().asString();
      assertTrue(responseString.contains("Not enough Fund from source Account"));
    }

    /*
       TC C1 Negative Category = TransactionService
       Scenario: test transaction from one account to another with source/destination account with different currency code
                 return 500 INTERNAL SERVER ERROR
    */
    @Test
    public void testTransactionDifferentCurr() {
      BigDecimal amount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
      TransferRequest transaction = new TransferRequest("EUR", amount, 3L, 5L);
      String responseString = with().body(transaction)
      .when()
      .request("PUT", baseURL+"/transfer")
      .then()
      .statusCode(400)
      .extract().asString();
      assertTrue(responseString.contains("Fail to transfer Fund, the source and destination account are in different currency"));

    }


}
