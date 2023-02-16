USE ContactVendeurs;
INSERT INTO Contact (id,pseudo,tel,adresse,code) VALUES (1,"RIRI", "0145489625","5 avenue des groseilles","OpérationEpervier"),
(2,"FIFI", "0145889625","1 rue des myrtilles","OpérationFaucon"),
(3,"LOULOU", "0115789625","1 rue des pommes","OpérationFaucon"),
(16579,"DIDI", "0145789625","1 rue des pommes","OpérationEpervier"),
(7865,"DEDE", "0145781225","3 avenue des oranges","OpérationMouette"),
(472,"RORO", "0189999625","5 boulevard des poires","OpérationFaucon"),
(456,"JAJA", "0145769625","1 rue des pommes","OpérationGorfou"),
(7456,"TITI", "404CTF{0145769456}","404CTF{21 rue des kiwis}","OpérationGorfou");
USE UnionVendeurs;
INSERT INTO cooperatives (pseudo,cooperative) VALUES ("RIRI","Association des vendeurs d'or de France"),
("FIFI","Corporation des fournisseurs de pierres précieuses d'Île-de-France"),
("LOULOU","Association des vendeurs d'or de France"),
("DIDI","Association des vendeurs d'or de France"),
("DEDE","Collaboration des négociants de minerais de Franche-Comté"),
("RORO","Corporation des fournisseurs de pierres précieuses d'Île-de-France"),
("JAJA","Regroupement des marchands de joyaux d'Ille-et-Vilaine"),
("TITI","Regroupement des marchands de joyaux d'Ille-et-Vilaine");
INSERT INTO Users (id,nom,prenom) VALUES (1,"Assin","Marc"),
(2,"Outan","Laurent"),
(3,"Gator","Ali"),
(16579,"Conda","Anna"),
(7865,"Abbé","Oscar"),
(472,"Culé","Roland"),
(456,"Reptile","Eric"),
(7456,"404CTF{Vereux}","404CTF{UnGorfou}");
USE RencontreVendeurs;
INSERT INTO Rdv (code,dateRdv,heureRdv) VALUES ("OpérationEpervier","2021-11-02","19h"),
("OpérationMouette","2021-09-14","19h"),
("OpérationFaucon","2022-01-01","20h"),
("OpérationGorfou","404CTF{2022-07-14}","404CTF{01hDuMatin}");
INSERT INTO Password (id,mdp) VALUES (1,"d7uA9kYU3"),
(2,"5qKrD4F7p"),
(3,"SXq3rZ35v"),
(16579,"T3h98HdFy"),
(7865,"S5eN2p5Wj"),
(472,"qJB5y45Xe"),
(456,"FGp4Q93tk"),
(7456,"404CTF{GorfousAuPouvoir}");
