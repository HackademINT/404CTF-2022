<?php include("../check.php"); ?>
<html>

<head>

    <title>Page 3</title>
    <!-- CSS -->
    <link href="/style/body.css" rel="stylesheet" type="text/css">
    <link href="/style/header.css" rel="stylesheet" type="text/css">
    <!-- jquery  -->
    <script type="text/javascript" src="/lib/jquery-3.5.1.js"></script>
    <script src="/lib/jquery.dataTables.min.js"></script>
    <!-- Boostrap  -->
    <link href="/lib/dataTables.bootstrap4.min.css" type="text/css" rel="stylesheet" />
    <link href="/lib/bootstrap.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="/lib/dataTables.bootstrap4.min.js"></script>

    <script>
        $('#example').dataTable({});
    </script>

</head>


<body>
    <header>
        <div class="navbar navbar-dark bg-dark shadow-sm">
            <div class="container d-flex justify-content-center">
                <a href="../index.php" class="previous round">&#8249;</a>
                <div> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
                <div class="navbar-brand d-flex align-items-center">
                    <strong>Rencontrez des vendeurs et parler avec eux sans FILTRES</strong>
                </div>
            </div>
        </div>
    </header>
    <br><br><br>
    <div class="container h-50">
        <div class="row justify-content-md-center h-100">
            <div class="col-4.5">
                <div class="card-wrapper">
                    <div class="card fat">
                        <h4 class="card-header">Formulaire</h4>
                        <div class="card-body">
                            <form action="page3.php" method="POST" class="my-login-validation">
                                <div class="form-group">
                                    <label for="code">Code</label> <input id="code" type="text" class="form-control" name="code" value="" autofocus>
                                </div>
                                <div class="form-group m-0">
                                    <input type="submit" value="Valider" name="submit" class="btn btn-dark btn-block">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-4.5">

                <div class="col-2 align-self-center justify-content-md-center">
                    <h3 class="d-flex justify-content-center"></h3>
                </div>


            </div>
            <div class="col-4.5">
                <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Date</th>
                            <th>Heure</th>
                        </tr>
                    </thead>

                    <tbody>


                        <?php

                        try {
                            // Connexion à MySQL
                            $urlDB = sprintf('mysql:host=%s;dbname=RencontreVendeurs;charset=utf8', $ip);
                            $userDB = 'page3';
                            $passwordDB = 'V2Dp657ASz9naQa#@';

                            $BDD = new PDO($urlDB, $userDB, $passwordDB, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_SILENT));
                        } catch (Exception $e) {
                            // En cas d'erreur, on affiche un message et on arrête tout
                            die("Erreur : " . $e->getMessage());
                        }

                        if (isset($_REQUEST['submit'])) {
                            // Récupère les input
                            $code = $_REQUEST['code'];
                            // Interroge la BDD
                            $query  = "SELECT code,dateRdv,heureRdv FROM Rdv WHERE code = '$code';";
                            // Gère les erreurs de requêtes
                            if (($stmt = $BDD->prepare($query)) === false) {
                                print_r($BDD->errorInfo());
                            }

                            if ($stmt->execute() === false) {
                                print_r($stmt->errorInfo());
                            }


                            // On rejette toute requête présentant des espaces
                            $testEspace = str_replace(' ', '-', $code);
                            if ($testEspace != $code) {
                                exit("Requête invalide : ne pas mettre d'espace");
                            }

                            // On rejette toute requête présentant le mot clé SELECT
                            $testSELECT = strtoupper($code);
                            $filtre = strpos($testSELECT, "SELECT");
                            if ($filtre == true) {
                                exit("Attaque détectée : SELECT utilisé ");
                            }

                            $code2 = urldecode($code);
                            $query  = "SELECT code,dateRdv,heureRdv FROM Rdv WHERE code = '$code2';";
                            $result = $BDD->prepare($query);
                            $result->execute();
                            $results = $result->fetchAll();



                            // Affiche les résultats un à un
                            foreach ($results as $reponse) {

                                echo '<tr>', '<td>', $reponse[0], '</td>',
                                '<td>', $reponse[1], '</td>',
                                '<td>', $reponse[2], '</td>',
                                '</tr>';
                            }
                        }
                        ?>

                    </tbody>
                </table>

            </div>
        </div>
    </div>

</body>

</html>