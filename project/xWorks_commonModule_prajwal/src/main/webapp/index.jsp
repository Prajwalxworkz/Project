<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page isELIgnored="false" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
      crossorigin="anonymous"
    />
  </head>
  <body>
   <c:if test="${not empty successMessage}">
            <button type="button" class="d-none" data-bs-toggle="modal" data-bs-target="#successModal" id="autoClick"></button>
     </c:if>
    <div class="modal fade" id="successModal"   tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
           <div class="modal-body">
                 <p style="color:green;">${successMessage}</p>
                 </div>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
        </div>
      </div>
    </div>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark">
      <div class="container-fluid">
       <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
         <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <img
            class="navbar-brand"
            src="<%= request.getContextPath() %>/resources/images/xworkzLogo.png"
            width="140" height="70"
            alt="Xworkz"
          />

            <ul  class="navbar-nav me-auto mb-2 mb-lg-0 ">
                <li class="nav-item"> <a class="nav-link active" aria-current="page" href="#">Home</a></li>
                <li class="nav-item"> <a class="nav-link active" aria-current="page" href="#">About Us</a></li>
                <li class="nav-item"> <a class="nav-link active" aria-current="page" href="#">Contact</a></li>
            </ul>

            <div class="d-flex">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"> <a class="btn btn-primary "  href="signUpPage">SignUp</a></li> &nbsp;
                <li class="nav-item"> <a class="btn btn-primary "  href="signInPage">SignIn</a></li>
            </ul>
            </div>
         </div>
      </div>
    </nav>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
      crossorigin="anonymous"
    ></script>
    <c:if test="${not empty successMessage}">
             <script>
                 document.getElementById("autoClick").click();
             </script>
    </c:if>

  </body>
</html>

