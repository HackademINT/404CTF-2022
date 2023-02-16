<?php
$ip = "bdd-sql-injection";
// Création des BDD
try {
    $urlDB0 = sprintf("mysql:host=%s;charset=utf8", $ip);
    $userDB0 = 'root';
    $passwordDB0 = 'sLQh7cF8gD246Rd57#@';
    $BDD = new PDO($urlDB0, $userDB0, $passwordDB0, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_SILENT));

    $query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = \'UnionVendeurs\'";
    $result = $BDD->prepare($query);
    $result->execute();
    $results = $result->fetchAll();

    if ($results[1] === null) {

        $creation_site = file_get_contents('/var/www/html/sql/creation_site.sql');
        $creation_bdd = str_replace("\n", "", $creation_site);
        $BDD->exec($creation_bdd);

        $populate = file_get_contents('/var/www/html/sql/populate.sql');
        $populate_bdd = str_replace("\n", "", $populate);
        $BDD->exec($populate_bdd);
    }
} catch (Exception $e) {
    // En cas d'erreur, on affiche un message et on arrête tout
    die("Erreur : " . $e->getMessage());
}
