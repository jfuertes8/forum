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
        <div class="card block">
          <img src="<c:url value="${evento.photos}" />" class="img-fluid" style="background-size: cover;" alt="...">
          <h2 class="my-4">${evento.eventName}</h2>
          
          
          <c:if test="${not empty evento}">
	          <h4 class="my-4">Organizer</h4>
	          <p>${evento.eventOrganizer}</p>
          </c:if>
          
          <h4 class="my-4">Short description</h4>
          <p>${evento.eventDetail}</p>
          <h4 class="my-4">Event information</h4>
          <div class="row justify-content-center">
            <div class="col-sm-4">
              <div class="card" style="width: 15rem;">
                <div class="card-body">
                  <h5 class="card-title"><i class="fas fa-users"></i> Assistants</h5>
                  <p class="card-text">${evento.assistants} / ${evento.maxAssistants}</p>
                </div>
              </div>
            </div>
            <div class="col-sm-4">
              <div class="card" style="width: 15rem;">
                <div class="card-body">
                  <h5 class="card-title"><i class="far fa-calendar-alt"></i> Date</h5>
                  <p class="card-text"><fmt:formatDate pattern = "yyyy-MM-dd" 
         value = "${evento.event_dateTime}" /></p>
                </div>
              </div>
            </div>
            <h4 class="my-4">Location</h4>
            <p>${evento.location}</p>
          </div>
        </div>
      </div>
      <div class="col-sm-4">
      
      <c:if test = "${success_block > 0}">
      
      	<div style="background-color: ${color}; margin-bottom: 20px;" class="card block">
	      	<h2>${success_title}</h2>
	      	<p>${success_description}</p>
      	</div>
      	
      </c:if>
      
        <div class="card block">
          <main class="form-signin" id="login">
            <form>
            
            <c:choose>
            	<c:when test="${CTA > 0}">
		              <h1 class="h3 mb-3 fw-normal"><i class="fas fa-check-double" aria-hidden="true" style="color: green;"></i> <br>You are already registered for this event</h1>
		              <p>If you have changed your mind, you can always cancel your participation.</p>
		              <a class="w-100 btn btn-lg btn-outline-secondary cta" href="/event/cancelparticipation/${evento.eventId}"><i class="far fa-window-close"></i> Cancel participation</a>
            	</c:when>
            	
            	<c:when test="${CTA < 0}">
		              <h1 class="h3 mb-3 fw-normal">You created this event</h1>
		              <p>If you have changed your mind, you can always cancel your event.</p>
		              <a class="w-100 btn btn-lg btn-outline-danger main-button cta" href="/event/cancelevent/${evento.eventId}"><i class="far fa-trash-alt"></i> Cancel event</a>
            	      <a class="w-100 btn btn-lg btn-outline-secondary cta" href="/event/editevent/${evento.eventId}"><i class="fas fa-pen"></i> Edit event</a>
            	      <p>${mensaje}</p>
            	</c:when>
            	
            	<c:otherwise>
            		  <h1 class="h3 mb-3 fw-normal">Register for this event</h1>
		              <p>Since you are already logged in, you can register with one simple click.</p>
		              <a class="w-100 btn btn-lg btn-primary main-button cta" href="/event/register/${evento.eventId}"><i class="fas fa-check-double"></i> Confirm participation</a>
            	</c:otherwise>
            </c:choose>
            
            </form>
          </main>
        </div>
        
        <c:if test = "${CTA < 0}">
        
	        <div class="card block" style="margin-top: 25px;">
	        	<main class="form-signin" id="login">
	        	
	        	<p>You can share the following link with anyone you'd like to register for the event:</p>
	        	<p style="font-weight: 900;">localhost:8084/event/view/${evento.eventId}</p>
	        
	        	</main>
	        </div>
	        
	        <div class="card block" style="margin-top: 25px;">
	        	<main class="form-signin" id="login">
	        	
	        	<h4>Coordinator of the event</h4>
	        	<p>This user will have access to a list of bookings, and will be able to check out participants at the event</p>
	        	
	        	<form action="/event/coordinate/${evento.eventId}" method="Get">
	        	
		        	<div class="mb-3 col-md-6">
	              		 <input type="text" class="form-control my-3" id="exampleInputEmail1" aria-describedby="emailHelp" name="userEmail" placeholder="email here">
	            		 <button type="submit" class="btn btn-primary">Make coordinator</button>
	            	</div>
	        	
	        	</form>
	        
	        	</main>
	        </div>
	        
		        
		        <div class="card block" style="margin-top: 25px;">
		        	<main class="form-signin" id="login">
		        	
		        	<h4>See booking list</h4>
		        	<p>You'll see all the bookings for this event.</p>
		        	
		        	<div class="mb-3 col-md-6">
	            		 <a href="/event/eventbookings/${evento.eventId}" type="submit" class="btn btn-primary" href="">View bookings</a>
	            	</div>
		        
		        	</main>
		        </div>
        
        </c:if>
        
        <c:if test = "${coordinator > 0}">
		        
		        <div class="card block" style="margin-top: 25px;">
		        	<main class="form-signin" id="login">
		        	
		        	<h4>See booking list</h4>
		        	<p>You'll see all the bookings for this event.</p>
		        	
		        	<div class="mb-3 col-md-6">
	            		 <a href="/event/eventbookings/${evento.eventId}" type="submit" class="btn btn-primary" href="">View bookings</a>
	            	</div>
		        
		        	</main>
		        </div>
	    </c:if>
        
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