<?php include("../check.php"); ?>
<html>

<head>

    <title>Page 1</title>
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
                    <strong>Discuter avec des vendeurs d’OR près de chez vous</strong>
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
                            <form action="page1.php" method="POST" class="my-login-validation">
                                <div class="form-group">
                                    <label for="id">Identifiant</label> <input id="id" type="text" class="form-control" name="id" value="" autofocus>
                                </div>

                                <div class="form-group">
                                    <label for="pseudo">Pseudonyme</label> <input id="pseudo" type="text" class="form-control" name="pseudo">
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
                            <th>Id</th>
                            <th>Pseudonyme</th>
                            <th>Téléphone</th>
                            <th>Adresse</th>
                            <th>Code</th>
                        </tr>
                    </thead>

                    <tbody>


                        <?php

                        try {
                            // Connexion à MySQL
                            $urlDB = sprintf('mysql:host=%s;dbname=ContactVendeurs;charset=utf8', $ip);
                            $userDB = 'page1';
                            $passwordDB = 'sLQh7cF8gD246Rd56#@';

                            $BDD = new PDO($urlDB, $userDB, $passwordDB, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_SILENT));
                        } catch (Exception $e) {
                            // En cas d'erreur, on affiche un message et on arrête tout
                            die("Erreur : " . $e->getMessage());
                        }

                        if (isset($_REQUEST['submit'])) {
                            // Récupère les input
                            $pseudonyme = $_REQUEST['pseudo'];
                            $id = $_REQUEST['id'];

                            // Interroge la BDD
                            $query  = "SELECT id,pseudo,tel,adresse,code FROM Contact WHERE pseudo = '$pseudonyme' AND  id = '$id';";

                            // Gère les erreurs de requêtes
                            if (($stmt = $BDD->prepare($query)) === false) {
                                echo "balise 1";
                                print_r($BDD->errorInfo());
                            }

                            if ($stmt->execute() === false) {
                                echo "balise 2";
                                print_r($stmt->errorInfo());
                            }

                            $result = $BDD->prepare($query);
                            $result->execute();
                            $results = $result->fetchAll();

                            // Affiche les résultats un à un
                            foreach ($results as $reponse) {

                                echo '<tr>', '<td>', $reponse[0], '</td>',
                                '<td>', $reponse[1], '</td>',
                                '<td>', $reponse[2], '</td>',
                                '<td>', $reponse[3], '</td>',
                                '<td>', $reponse[4], '</td>',
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