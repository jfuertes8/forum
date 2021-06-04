<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>

<head>
    <title>Forum | My Events</title>
    <meta charset="utf-8">
    <!--Link to Bootstrap's CSS-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <!--Link to Fontawesome-->
    <script src="https://kit.fontawesome.com/fc00ef1987.js" crossorigin="anonymous"></script>
    <!--Link to JQuery repository-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="<c:url value="/css/my_events.css" />">
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
           			<a href="/event/created_events"><i class="fas fa-hammer"></i> Created Events</a>
                    <a id="register_button" class="btn btn-primary" href="/event/create">Create event <i class="fas fa-plus"></i></a>
                    <a href="/user/logout"><i class="fas fa-sign-out-alt"></i></a>
                </div>
            </div>
        </div>
    </header>

    <section class="py-0 text-center main-block">
        <div class="row py-lg-5">
            <div class="col-lg-6 col-md-8 mx-auto">
                <h2><i class="fas fa-hammer"></i></h2>
                <h1>Events I've created</h1>
                <p class="lead">Here's a list of all the events you have created. If it's empty, you can start your own event by clicking on "Create Event"</p>
  				<p>${mensaje}</p>          
            </div>
        </div>
    </section>

    <div class="album py-5 bg-light">
    
    <c:choose>
    
    	<c:when test="${empty listado}">
    
	        <div class="container text-center empty-state-container">
	            <h2><i class="far fa-comment-alt"></i></h2>
	            <h3 id="empty-state-title">Oops! It looks like you haven't created any event yet. </h3>
	            <p id="empty-state-text">If you have an awesome idea, you can create your own event by clicking the button below.</p>
	            <a href="/event/create" class="btn btn-primary">Create event <i class="fas fa-plus"></i></a>
	        </div>
    
		</c:when>
        
            
        <c:otherwise>
        
        <div class="container event-container">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">
        	<c:forEach var="ele" items="${listado}" varStatus="estado">    
            
                <div class="col">
                    <div class="card shadow-sm">
                        
                        <img class="card-img-top" width="100%" height="225" src="<c:url value="${ele.photos}" />"/>
                        <div class="card-body">
                            <h4 class="title-max-chars">${ele.eventName}</h4>
                            <p class="card-text description-max-chars">${ele.eventDetail}</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a class="btn btn-sm btn-outline-primary" href="/event/view/${ele.eventId}"><i class="far fa-eye"></i> View Event</a>
                                </div>
                                <small class="text-muted"><i class="fas fa-hammer"></i></small>
                            </div>
                        </div>
                    </div>
                </div>
                
             </c:forEach>   
        
            </div>
        </div>
   
  	 </c:otherwise>
   
   </c:choose>
        
    </div>

    </main>

    <!-- FOOTER -->
  <hr class="featurette-divider">
  <footer class="container">
    <p class="float-end"><a href="#">Back to top <i class="fas fa-chevron-up"></i></a></p>
    <p>&copy; 2021 Forum, Inc. </p>
  </footer>
</body>

</html>