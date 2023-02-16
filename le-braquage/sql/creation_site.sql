DROP DATABASE IF EXISTS ContactVendeurs;
CREATE DATABASE ContactVendeurs;
USE ContactVendeurs ;
CREATE TABLE Contact(
id int, 
pseudo varchar(50) NOT NULL,
tel varchar(20) NOT NULL,
adresse varchar(50) NOT NULL,
code varchar(50) NOT NULL, 
PRIMARY KEY(id,code)
);
DROP USER IF EXISTS 'populate1'@'localhost';
CREATE USER 'populate1'@'localhost' IDENTIFIED BY 'sLQh7cF8gD246Rd57#@'; 
GRANT INSERT ON ContactVendeurs.* TO 'populate1'@'localhost';
DROP USER IF EXISTS 'page1'@'localhost';
CREATE USER 'page1'@'localhost' IDENTIFIED BY 'sLQh7cF8gD246Rd56#@'; 
GRANT SELECT ON ContactVendeurs.* TO 'page1'@'localhost';
DROP DATABASE IF EXISTS UnionVendeurs;
CREATE DATABASE UnionVendeurs;
USE UnionVendeurs ;
CREATE TABLE cooperatives(
pseudo varchar(50) PRIMARY KEY,
cooperative varchar(100) NOT NULL
);
CREATE TABLE Users(
id int PRIMARY KEY, 
nom varchar(50) NOT NULL,
prenom varchar(50) NOT NULL
);
DROP USER IF EXISTS 'populate2'@'localhost';
CREATE USER 'populate2'@'localhost' IDENTIFIED BY 'zBwF7CD7fr2C8v7#@'; 
GRANT INSERT ON UnionVendeurs.* TO 'populate2'@'localhost';
DROP USER IF EXISTS 'page2'@'localhost';
CREATE USER 'page2'@'localhost' IDENTIFIED BY 'zBwF7CD7fr2C8v6#@'; 
GRANT SELECT ON UnionVendeurs.* TO 'page2'@'localhost';
DROP DATABASE IF EXISTS RencontreVendeurs;
CREATE DATABASE RencontreVendeurs;
USE RencontreVendeurs;
CREATE TABLE Rdv(
code varchar(50) NOT NULL, 
dateRdv varchar(30) NOT NULL,
heureRdv varchar(30) NOT NULL
);
CREATE TABLE Password(
id int PRIMARY KEY, 
mdp varchar(100) NOT NULL
);
DROP USER IF EXISTS 'populate3'@'localhost';
CREATE USER 'populate3'@'localhost' IDENTIFIED BY 'V2Dp657ASz9naQb#@'; 
GRANT INSERT ON RencontreVendeurs.* TO 'populate3'@'localhost';
DROP USER IF EXISTS 'page3'@'localhost';
CREATE USER 'page3'@'localhost' IDENTIFIED BY 'V2Dp657ASz9naQa#@'; 
GRANT SELECT ON RencontreVendeurs.* TO 'page3'@'localhost';