<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>

  <title>Forum | Event</title>
  <meta chrset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!--Link to Bootstrap's CSS-->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
  <!--Link to Fontawesome-->
  <script src="https://kit.fontawesome.com/fc00ef1987.js" crossorigin="anonymous"></script>
  <!-- Custom styles for this template -->
  <link href="signin.css" rel="stylesheet">
  <link rel="stylesheet" href="<c:url value="/css/event.css" />">
  <link rel="stylesheet" href="<c:url value="/css/my_events.css" />">
  <!--Link to JQuery repository-->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>

  <header class="p-3 bg-dark text-white">
    <div class="container">
      <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

        <h1><i class="fas fa-check-double" aria-hidden="true"></i> Forum</h1>

        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0"></ul>

        <div class="text-end">
           <a href="/event/all"><i class="fas fa-meteor"></i> Events</a>
           <a href="/event/participate"><i class="fas fa-walking"></i> Attending Events</a>
           <a href="/event/created_events"> <i class="fas fa-hammer"></i> Created Events</a>
           <a id="register_button" class="btn btn-primary" href="/event/create">Create event <i class="fas fa-plus"></i></a>
           <a href="/user/logout">Hola ${user.firstName} <i class="fas fa-sign-out-alt"></i></a>
        </div>
      </div>
    </div>
  </header>

  <div class="container first">
    <div class="row justify-content-center">
      <div class="col-sm-8">
        <div class="card block" style="margin-bottom: 30px;">
          <h2 class="my-4">Bookings for ${evento.eventName}</h2>
          
          <table class="table">
          
          <tr>
                <th scope="col">Booking id</th>
                <th scope="col">Booking date</th>
                <th scope="col">User email</th>
                <th scope="col">Event id</th>
                <th scope="col">Actions</th>
          </tr>
          
          <c:forEach var="ele" items="${listadoReservas}" varStatus="estado">
	           
	           	<tr>
	                <td>${ele.bookingId}</td>
	                <td>${ele.bookingDate}</td>
	                <td>${ele.usuario.userEmail}</td>
	                <td>${ele.event.eventId}</td>
	                <td>
		                <a href="/event/burn/${ele.bookingId}"><i class="far fa-edit"></i> Check participant</a>
	                </td>
	            </tr>
	            
	      </c:forEach>
	      
	      </table>
                  
         </div>
         
         
          <div class="card block">
          
          	  <h2 class="my-4">Participants at the event</h2>
          
	          <table class="table">
	          
		          <tr>
		                <th scope="col">Booking id</th>
		                <th scope="col">Booking date</th>
		                <th scope="col">User email</th>
		                <th scope="col">Event id</th>
		          </tr>
		          
		           <c:forEach var="ele" items="${reservasQuemadas}" varStatus="estado">
		           
		           	<tr>
		                <td>${ele.bookingId}</td>
		                <td>${ele.bookingDate}</td>
		                <td>${ele.usuario.userEmail}</td>
		                <td>${ele.event.eventId}</td>
		               
		            </tr>
		            
		      	  </c:forEach>
		      
		      </table>
          
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