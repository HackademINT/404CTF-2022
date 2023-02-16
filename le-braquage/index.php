<!DOCTYPE html>
<html>

<head>

  <title>Menu Principal</title>
  <!-- CSS -->
  <link href="/style/body.css" rel="stylesheet" type="text/css">
  <link href="/style/header.css" rel="stylesheet" type="text/css">
  <!-- jquery  -->
  <script type="text/javascript" src="/lib/jquery-3.5.1.js"></script>
  <!-- Boostrap  -->
  <link href="/lib/dataTables.bootstrap4.min.css" type="text/css" rel="stylesheet" />
  <link href="/lib/bootstrap.css" type="text/css" rel="stylesheet" />

</head>

<body class="my-login-page">
  <header>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container d-flex justify-content-center">
        <div class="navbar-brand d-flex align-items-center">
          <strong>MENU PRINCIPAL</strong>
        </div>
      </div>
    </div>
  </header>
  <br><br><br><br><br>
  <div class="container">
    <div class="row justify-content-md-center h-100">
      <div class="col-4.5">
        <div class="card" style="width: 18rem;">
          <h5 class="card-header text-center">Discussions</h5>
          <div class="card-body">
            <p class="card-text">Discuter avec des vendeurs d’OR près de chez vous.</p>
            <form action="scripts/page1.php">
              <button type="submit" class="btn btn-dark btn-block">Accès</button>
            </form>
          </div>
        </div>
      </div>

      <div class="col-4.5">
        <div class="col-2 align-self-center justify-content-md-center">
          <h3 class="d-flex justify-content-center"></h3>
        </div>
      </div>

      <div class="col-4.5">
        <div class="card" style="width: 18rem;">
          <h5 class="card-header text-center">Informations</h5>
          <div class="card-body">
            <p class="card-text">UNION des vendeurs d’OR de la région.</p>
            <form action="scripts/page2.php">
              <button type="submit" class="btn btn-dark btn-block">Accès</button>
            </form>
          </div>
        </div>
      </div>

      <div class="col-4.5">
        <div class="col-2 align-self-center justify-content-md-center">
          <h3 class="d-flex justify-content-center"></h3>
        </div>
      </div>

      <div class="col-4.5">
        <div class="card" style="width: 18rem;">
          <h5 class="card-header text-center">Rencontres</h5>
          <div class="card-body">
            <p class="card-text">Rencontrez des vendeurs et parler avec eux sans FILTRES.</p>
            <form action="scripts/page3.php">
              <button type="submit" class="btn btn-dark btn-block">Accès</button>
            </form>
          </div>
        </div>
      </div>

    </div>
  </div>
</body>

</html>