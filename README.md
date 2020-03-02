# Money transfer Rest API

A Java Spark framework RESTful money transfer API between user accounts

### Technologies
- Spark Framework for creating microservices(http://sparkjava.com/)
- H2 in memory database
- Log4j
- Rest Assured(for Testing)
- Apache Http Client(for Testing)


### How to run
```sh
mvn exec:java
```

Application starts using the Spark microservices framework on localhost port 4567. Once the server is started some of the data is loaded into the H2 in memory database to be able to view using following services

- http://localhost:4567/users/all
- http://localhost:4567/users/byUserName/test1
- http://localhost:4567/accounts/all
- http://localhost:4567/accounts/1

### Available Services

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /users/byUserName/{userName} | get user by user name | 
| GET | /users/all | get all users | 
| POST | /users/create | create a new user | 
| PUT | /update/user/{userId} | update user | 
| GET | /accounts/{accountId} | get account by accountId | 
| GET | /accounts/all | get all accounts | 
| GET | /accounts/balance/{accountId} | get account balance by accountId | 
| POST | /accounts/create | create a new account
| DELETE | /delete/{accountId} | remove account by accountId | 
| PUT | /withdraw/{accountId}/{amount} | withdraw money from account | 
| PUT | /deposit/{accountId}/{amount} | deposit money to account | 
| PUT | /transfer | perform fund transfer between 2 user accounts | 

### Http Status
- 200 OK: The request has succeeded
- 400 Bad Request: The request could not be understood by the server 
- 404 Not Found: The requested resource cannot be found
- 500 Internal Server Error: The server encountered an unexpected condition 

### Sample JSON for User and Account
##### User : 
```sh
{  
  "userName":"test1",
  "emailAddress":"test1@gmail.com"
} 
```
##### User Account: : 

```sh
{  
   "userId":1,
   "balance":10.0000,
   "currencyCode":"USD"
} 
```

#### User Transaction:
```sh
{  
   "currencyCode":"EUR",
   "amount":100000.0000,
   "fromAccountId":1,
   "toAccountId":2
}
```
#### Database Schema:
```sh
CREATE TABLE User (UserId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 UserName VARCHAR(30) NOT NULL,
 EmailAddress VARCHAR(30) NOT NULL);

CREATE UNIQUE INDEX idx_ue on User(UserName,EmailAddress);

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
UserId Long,
Balance DECIMAL(19,4),
CurrencyCode VARCHAR(30)
);

CREATE UNIQUE INDEX idx_acc on Account(UserId,CurrencyCode);
```
