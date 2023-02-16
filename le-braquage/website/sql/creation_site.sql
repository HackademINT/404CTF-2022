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
DROP USER IF EXISTS 'page1';
CREATE USER 'page1' IDENTIFIED BY 'sLQh7cF8gD246Rd56#@'; 
GRANT SELECT ON ContactVendeurs.* TO 'page1';
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
DROP USER IF EXISTS 'page2';
CREATE USER 'page2' IDENTIFIED BY 'zBwF7CD7fr2C8v6#@'; 
GRANT SELECT ON UnionVendeurs.* TO 'page2';
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
DROP USER IF EXISTS 'page3';
CREATE USER 'page3' IDENTIFIED BY 'V2Dp657ASz9naQa#@'; 
GRANT SELECT ON RencontreVendeurs.* TO 'page3';