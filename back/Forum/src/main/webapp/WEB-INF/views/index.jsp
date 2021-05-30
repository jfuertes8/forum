<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

	<head>
		<title>Forum | Login</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--Link to Bootstrap's CSS-->
  		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
    	integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
		
		<!--Link to Fontawesome-->
  		<script src="https://kit.fontawesome.com/fc00ef1987.js" crossorigin="anonymous"></script>
  		
  		<!-- Custom styles for this template -->
  		<link href="signin.css" rel="stylesheet">
  		
  		<!--Link to JQuery repository-->
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  		
  		<!--Link to JS file-->
  		<script src="<c:url value="/js/login_register.js" />"></script>
  		
  		<link href="heroes.css" rel="stylesheet">
		<link href="<c:url value="/css/login.css" />" rel="stylesheet">
	</head>
	
	<body>
	<header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

        <h1><i class="fas fa-check-double" aria-hidden="true"></i> Forum</h1>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0"></ul>

        <div class="text-end">
          <button type="button" id="login_button" class="btn btn-outline-light me-2">Login</button>
          <button type="button" id="register_button" class="btn btn-primary">Register</button>
        </div>
      </div>
    </div>
  </header>

  <div class="container info-block first">
    <div class="row justify-content-center login_text_container">
      <div class="col-sm-6">
        <h2>Don't miss your spot on the most exciting events</h2>
      </div>
      <div class="col-sm-4">
        <div class="card">
          <main class="form-signin" id="register">
            <form action="/user/register" method="post">
              <p>${mensaje}</p>
              <h1 class="h3 mb-3 fw-normal">Register</h1>
              <p>Just a little bit of information. It'll take you less than a minute</p>
              <div class="form-floating">
                <input type="text" class="form-control" id="floatingInput" name="firstName" placeholder="name@example.com">
                <label for="floatingInput">Name</label>
              </div>
              <div class="form-floating">
                <input type="text" class="form-control" id="floatingInput" name="lastName" placeholder="name@example.com">
                <label for="floatingInput">Surname</label>
              </div>
              <div id="emailHelp" class="form-text">You'll get the event confirmation on your email, so please use a real one. We won't spam you, we
                promise.</div>
              <div class="form-floating">
                <input type="email" class="form-control" id="floatingInput" name="userEmail" placeholder="name@example.com">
                <label for="floatingInput"><i class="far fa-envelope"></i> Email</label>
              </div>
              <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword" name="userPassword" placeholder="Password">
                <label for="floatingPassword"><i class="fas fa-key"></i> Password</label>
              </div>
              <button class="w-100 btn btn-lg btn-primary" type="submit">Register</button>
            </form>
          </main>

          <main class="form-signin" id="login">
            <form action="/user/login" method="post">
              <p>${mensaje}</p>
              <h1 class="h3 mb-3 fw-normal">Log in</h1>
              <p>Login so you can register for any event with one simple click.</p>
              <div class="form-floating">
                <input type="email" class="form-control" id="floatingInput" name="userEmail">
                <label for="floatingInput"><i class="far fa-envelope"></i> Email address</label>
              </div>
              <div class="form-floating">
                <input type="password" class="form-control" id="floatingPassword" name="userPassword"">
                <label for="floatingPassword"><i class="fas fa-key"></i> Password</label>
              </div>
              <div class="checkbox mb-3">
                <label>
                  <input type="checkbox" value="remember-me"> Remember me
                </label>
              </div>
              <button class="w-100 btn btn-lg btn-primary" type="submit">Log in</button>
            </form>
          </main>
        </div>
      </div>
    </div>
  </div>

  <div class="container info-block">
    <div class="px-4 py-5 my-0 text-center">
      <h2><i class="fas fa-check-double" aria-hidden="true"></i></h2>
      <h1 class="display-5 fw-bold">What is Forum?</h1>
      <div class="col-lg-6 mx-auto">
        <p class="lead mb-4">Forum is a website where you can register for any free event with one simple click. Login with your account or create a new one, confirm your assistance and enjoy the event.</p>
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
          <button type="button" class="btn btn-primary btn-lg px-4 me-sm-3">Register</button>
          <button type="button" class="btn btn-outline-secondary btn-lg px-4">Login</button>
        </div>
      </div>
    </div>
  </div>

  <div class="container steps">
    <div class="container py-5" id="featured-3">
      <h2 class="pb-2">How it works in 4 easy steps</h2>
      <div class="row g-5 py-0">
        <div class="feature col-md-3">
          <img src="<c:url value="/resources/step1img.svg" />" class="rounded" alt="...">
          <hr>
          <h3>1. Register</h3>
          <p>You receive a link to register for an event you are interested in.</p>
        </div>
        <div class="feature col-md-3">
          <img src="<c:url value="/resources/step2img.svg" />" class="rounded" alt="...">
          <hr>
          <h3>2. Confirm</h3>
          <p>You click on register, and receive an immediate confirmation.</p>
        </div>
        <div class="feature col-md-3">
          <img src="<c:url value="/resources/step3img.svg" />" class="rounded" alt="...">
          <hr>
          <h3>3. Receive</h3>
          <p>Once you have confirmed, check your email inbox. You'll have received a QR code.</p>
        </div>
        <div class="feature col-md-3">
          <img src="<c:url value="/resources/step4img.svg" />" class="rounded" alt="...">
          <hr>
          <h3>4. Enjoy</h3>
          <p>Scan your QR code at the entrance and enjoy the event.</p>
        </div>
      </div>
    </div>
  </div>

  <!-- FOOTER -->
  <hr class="featurette-divider">
  <footer class="container">
    <p class="float-end"><a href="#">Back to top <i class="fas fa-chevron-up"></i></a></p>
    <p>&copy; 2021 Forum, Inc. </p>
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
    integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
    crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
    integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
    crossorigin="anonymous"></script>
	

	</body>
	
</html>