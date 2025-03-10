<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page isELIgnored="false" %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link
        rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css
      "
      />
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body>

        <div class="container border my-5 rounded-4 shadow" style="width: 800px;" >
            <div class="text-center my-4 ">
                <h4>Reset your password</h4>
            </div>
            <p style="color:green;">${successMessage}</p>
            <div class="row justify-content-center" >
                <div class="col col-md-6">
                    <form  action="resetPassword" method="post">
                    <c:set var="email" value="${userEmail}"/>
                    <c:set var="count" value="1"/>
                        <c:choose>
                        <c:when test="${count==1}">
                             <div class="my-2">
                                 <input  type="email" name="email" id="" value="${email}" hidden>
                             </div>
                             <div class="my-2">
                                     <label class="form-label" for="">New Password:</label>
                                     <input class="form-control" type="password" name="password" id="">
                             </div>
                             <div class="my-2">
                                     <label class="form-label" for="">Re-enter Password:</label>
                                     <input class="form-control" type="password" name="confirmPassword" id="">
                             </div>
                                  <p style="color:red; margin-top:20px;">${errorMessage}</p>
                             <div class="my-4">
                                     <button class="btn btn-primary"style="width: 80%; margin-left: 35px;" type="submit">Reset Password</button>
                             </div>
                        </c:when>
                        <c:otherwise>
                        <div class="my-2">
                            <label class="form-label" for="">Email:</label>
                            <input class="form-control" type="email" name="email" id="">
                        </div>
                        <div class="my-2">
                            <label class="form-label" for="">New Password:</label>
                            <input class="form-control" type="password" name="password" id="">
                        </div>
                        <div class="my-2">
                            <label class="form-label" for="">Re-enter Password:</label>
                            <input class="form-control" type="password" name="confirmPassword" id="">
                        </div>
                         <p style="color:red; margin-top:20px;">${errorMessage}</p>
                        <div class="my-4">
                            <button class="btn btn-primary"style="width: 80%; margin-left: 35px;" type="submit">Reset Password</button>
                        </div>
                   <div class="mb-5">
                    <i class="bi bi-arrow-left"></i><small>Back to <a href="signIn.jsp">LogIn</a></small>
                   </div>
                   </c:otherwise>
                   </c:choose>
                    </form>
                </div>
            </div>
        </div>

        <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"
      ></script>
    </body>
    </html>