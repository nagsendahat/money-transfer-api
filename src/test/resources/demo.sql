--This script is used for unit test cases, DO NOT CHANGE!

DROP TABLE IF EXISTS User;

CREATE TABLE User (UserId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 UserName VARCHAR(30) NOT NULL,
 EmailAddress VARCHAR(30) NOT NULL);

CREATE UNIQUE INDEX idx_ue on User(UserName,EmailAddress);

INSERT INTO User (UserName, EmailAddress) VALUES ('yangluo','yangluo@gmail.com');
INSERT INTO User (UserName, EmailAddress) VALUES ('qinfran','qinfran@gmail.com');
INSERT INTO User (UserName, EmailAddress) VALUES ('liusisi','liusisi@gmail.com');

DROP TABLE IF EXISTS Account;

CREATE TABLE Account (AccountId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
UserId Long,
Balance DECIMAL(19,4),
CurrencyCode VARCHAR(30)
);

CREATE UNIQUE INDEX idx_acc on Account(UserId,CurrencyCode);

INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,100.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,200.0000,'USD');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,500.0000,'EUR');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,500.0000,'EUR');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (1,500.0000,'GBP');
INSERT INTO Account (UserId,Balance,CurrencyCode) VALUES (2,500.0000,'GBP');
