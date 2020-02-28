package com.moneytransfer.services;


import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import com.moneytransfer.model.Account;

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

public class TestAccountService extends TestService {

  @Before
  public void before() throws Exception {
    Spark.awaitInitialization();
  }

    /*
    TC A1 Positive Category = AccountService
    Scenario: test get user account by user name
              return 200 OK
     */
    @Test
    public void testGetAccountByAccountId() {
     get(baseURL+"/accounts/1").then().statusCode(200).assertThat()
       .body("userId", equalTo(1))
       .body("accountId", equalTo(1))
       .body("balance", is(100.0f))
       .body("currencyCode", equalTo("USD"));
    }

    /*
    TC A2 Positive Category = AccountService
    Scenario: test get all user accounts
              return 200 OK
    */
    @Test
    public void testGetAllAccounts() {
      
      when().
      get(baseURL+"/accounts/all").
      then().statusCode(200).assertThat()
      .body("accountId", hasItems(1,2,3),
            "userId", hasItems(1,2),
            "balance", hasItems(100.0f,200.0f,500.0f),
            "currencyCode", hasItems("USD","EUR"));
    }

    /*
    TC A3 Positive Category = AccountService
    Scenario: test get account balance given account ID
              return 200 OK
    */
    @Test
    public void testGetAccountBalance() {
      String responseString = when().
      get(baseURL+"/accounts/balance/2").
      then().statusCode(200).extract().asString();
      assertEquals(responseString,"200.0000");
    }

    /*
    TC A4 Positive Category = AccountService
    Scenario: test create new user account
              return 200 OK
    */
    @Test
    public void testCreateAccount() {
        BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
        Account acc = new Account(3, balance, "CNY");
        with().body(acc)
        .when()
        .request("POST", baseURL+"/accounts/create")
        .then()
        .statusCode(200)
        .body("userId", equalTo(3))
        .body("accountId", equalTo(7))
        .body("balance", is(10.0f))
        .body("currencyCode", equalTo("CNY"));
    }

    /*
    TC A5 Negative Category = AccountService
    Scenario: test create user account already existed.
              return 400 Bad Request
    */
    @Test
    public void testCreateExistingAccount() {
      BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
      Account acc = new Account(3, balance, "CNY");
      with().body(acc)
      .when()
      .request("POST", baseURL+"/accounts/create")
      .then()
      .statusCode(400);

    }

    /*
    TC A6 Positive Category = AccountService
    Scenario: delete valid user account
              return 200 OK
    */
    @Test
    public void testDeleteAccount() {
      delete(baseURL+"/delete/1").then().statusCode(200);
    }


    /*
    TC A7 Negative Category = AccountService
    Scenario: test delete non-existent account. return 404 NOT FOUND
              return 404 NOT FOUND
    */
    @Test
    public void testDeleteNonExistingAccount() {
      String responseString = delete(baseURL+"/delete/100").then().assertThat().statusCode(400)
      .extract().asString();
      assertTrue(responseString.contains("Account to delete cannot be found"));
    }


}
