<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<!DOCTYPE html>
<html>

<head>

  <title>Forum | Create Event</title>
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
  <link rel="stylesheet" href="<c:url value="/css/event_creation.css" />">
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
          <a href="/event/all">Events</a>
          <a href="/event/participate"><i class="fas fa-walking"></i> Attending Events</a>
          <a href="/event/created_events"> <i class="fas fa-hammer"></i> Created Events</a>
          <a id="register_button" class="btn btn-primary">Create event <i class="fas fa-plus"></i></a>
          <a href="/user/logout"><i class="fas fa-sign-out-alt"></i></a>
        </div>
      </div>
    </div>
  </header>

  <div class="container">
    <div class="row">
      <div class="col-sm-12 my-5">
        <div class="card block">
          <p>${mensaje}</p>
          <h1>New Event</h1>
          <p>Fill in the data below to create a new event. All the information is mandatory.</p>
          <hr>
          <form action="/event/create" method="post">
            <div class="row">
            <div class="mb-3 col-md-6">
              <label for="exampleInputEmail1" class="form-label"><i class="fas fa-signature"></i> Name of the event:</label>
              <div id="emailHelp" class="form-text">This is the name participants will see when they click on your registration link. Try to make it catchy.</div>
              <input type="text" class="form-control my-3" id="exampleInputEmail1" aria-describedby="emailHelp" name="eventName">
            </div>

            <div class="mb-3 col-md-6">
              <label for="exampleInputPassword1" class="form-label"><i class="fas fa-user-alt"></i> Organizer</label>
              <div id="emailHelp" class="form-text">Please write here the name of the organizer of the event, either a particular person or a group or organization.</div>
              <input type="text" class="form-control my-3" id="exampleInputPassword1" name="eventOrganizer">
            </div>
          </div>

            <div class="mb-3">
              <label for="exampleInputPassword1" class="form-label"><i class="far fa-file-alt"></i> Short decription</label>
              <div id="emailHelp" class="form-text">Please write a brief description of what the event is about, so participants can have more information about it.</div>
              <input type="text" class="form-control my-3 field-text" id="exampleInputPassword1" name="eventDetail">
            </div>

            <div class="row">

            <div class="mb-3 col-md-4">
              <label for="exampleInputPassword1" class="form-label"><i class="fas fa-users"></i> Assistants</label>
              <div id="emailHelp" class="form-text">Select the maximum number of participants for this event</div>
              <input type="number" class="form-control my-3" id="exampleInputPassword1" name="maxAssistants">
            </div>

            <div class="mb-3 col-md-4">
              <label for="exampleInputPassword1" class="form-label"><i class="far fa-calendar-alt"></i> Date</label>
              <div id="emailHelp" class="form-text">When is the event happening?</div>
              <input type="datetime-local" class="form-control my-3" id="exampleInputPassword1" name="event_dateTimeDate">
            </div>

          </div>

          <div class="row">
            <div class="card block deadline-block col-md-6">
              <h3>Registration deadline</h3>
              <p>Once this time comes, users will not be able to register anymore for this event.</p>
              <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label"><i class="far fa-calendar-alt"></i> Date</label>
                <input type="datetime-local" class="form-control my-3" id="exampleInputPassword1" name="eventDeadlineDate">
              </div>
            </div>
          </div>

            <div class="mb-3">
              <label for="exampleInputPassword1" class="form-label my-3"><i class="fas fa-map-marker-alt"></i> Location</label>
              <div id="emailHelp" class="form-text">Please write down the event address so people will know where to go</div>
              <input type="text" class="form-control my-3" id="exampleInputPassword1" name="location">
            </div>
            
            <button type="submit" class="btn btn-primary">Create event</button>
          </form>

        </div>
      </div>
    </div>
  </div>

  <!-- FOOTER -->
  <hr class="featurette-divider">
  <footer class="container">
    <p class="float-end"><a href="#">Bacl to top <i class="fas fa-chevron-up"></i></a></p>
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
    
    