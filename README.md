# Money transfer Rest API

A Java RESTful API for money transfers between users accounts

### Technologies
- Spark Framework for creating microservices(http://sparkjava.com/)
- H2 in memory database
- Log4j
- Jetty Container (for Test and Demo app)
- Rest Assured(for Testing)
- Apache Http Client(for Testing)


### How to run
```sh
mvn exec:java
```

Application starts a jetty server on localhost port 8080 An H2 in memory database initialized with some sample user and account data To view

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
   "userName":"test1",
   "balance":10.0000,
   "currencyCode":"GBP"
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

