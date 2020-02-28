package com.moneytransfer.services;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.moneytransfer.controllers.UserController;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.User;
import com.moneytransfer.service.UserService;
import com.moneytransfer.util.TestRequest;
import com.moneytransfer.util.TestResponse;

import spark.Spark;


/**
 * Integration testing for RestAPI
 * Test data are initialised from src/test/resources/demo.sql
 * INSERT INTO User (UserName, EmailAddress) VALUES ('yangluo','yangluo@gmail.com'); -- ID=1
 * INSERT INTO User (UserName, EmailAddress) VALUES ('qinfran','qinfran@gmail.com'); -- ID=2
 * INSERT INTO User (UserName, EmailAddress) VALUES ('liusisi','liusisi@gmail.com'); -- ID=3
 */
public class TestUserService extends TestService {

    @Before
    public void before() throws Exception {
      Spark.awaitInitialization();
    }
    /*
       TC D1 Positive Category = UserService
       Scenario: test get user by given user name
                 return 200 OK
    */
    
    @Test
    public void testGetUser() throws IOException, URISyntaxException {
      TestResponse res = TestRequest.request("GET", "/users/byUserName/yangluo",null);
      assertEquals(200, res.status);
      Map<String, Object> json = res.json();
      assertEquals("yangluo", json.get("userName"));
      assertEquals("yangluo@gmail.com", json.get("emailAddress"));
      assertTrue(1 == ((Double)json.get("userId")).intValue());
 
    }

     /*
     TC D2 Positive Category = UserService
     Scenario: test get all users
               return 200 OK
      */
    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
      TestResponse res = TestRequest.request("GET", "/users/all",null);
      User[] users = res.fromJson​(res.body, User[].class);
      assertEquals(200, res.status);
      assertTrue(users.length > 0);
    }

    /*
        TC D3 Positive Category = UserService
        Scenario: Create user using JSON
                  return 200 OK
     */
    @Test
    public void testCreateUser() throws IOException {
      
      User user = new User("test1", "test1@gmail.com");
      TestResponse res = TestRequest.request("POST", "/users/create",TestRequest.toJson(user));
      Map<String, Object> json = res.json();
      assertEquals(200, res.status);
      assertEquals("test1", json.get("userName"));
      assertEquals("test1@gmail.com", json.get("emailAddress"));
      assertNotNull(json.get("userId"));
      
    }

    /*
        TC D4 Negative Category = UserService
        Scenario: Create user already existed using JSON
                  return 400 BAD REQUEST
    */
    @Test
    public void testCreateExistingUser() throws IOException {
      User user = new User("test1", "test1@gmail.com");
      String responseString = with().body(user)
      .when()
      .request("POST", baseURL+"/users/create")
      .then()
      .statusCode(400)
      .extract().asString();
      assertTrue(responseString.contains("User name already exist with user name test1"));


    }

    /*
     TC D5 Positive Category = UserService
     Scenario: Update Existing User using JSON provided from client
               return 200 OK
     */
    @Test
    public void testUpdateUser() throws IOException {
      User user = new User("test1_new", "test1_new@gmail.com");
      TestResponse res = TestRequest.request("PUT", "/update/user/3",TestRequest.toJson(user));
      Map<String, Object> json = res.json();
      assertEquals(200, res.status);
      assertEquals("test1_new", json.get("userName"));
      assertEquals("test1_new@gmail.com", json.get("emailAddress"));
      assertTrue(3 == ((Double)json.get("userId")).intValue());
    }


    /*
    TC D6 Negative Category = UserService
    Scenario: Update non existed User using JSON provided from client
              return 404 NOT FOUND
    */
    @Test
    public void testUpdateNonExistingUser() throws IOException {
      User user = new User("test1", "test1@gmail.com");
      String responseString = with().body(user)
      .when()
      .request("PUT", baseURL+"/update/user/20")
      .then()
      .statusCode(400)
      .extract().asString();
      assertTrue(responseString.contains("User with user with Id 20 does not exists"));

    }

}
