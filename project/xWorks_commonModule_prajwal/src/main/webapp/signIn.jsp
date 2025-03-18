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
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css
  "
    />
      <link
              rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
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
    <div class="container border my-5 rounded-4 shadow" style="width: 800px;">

      <div class="text-center my-3">
        <h3>Log In</h3>
        <small> Don't have an account? <a href="signUpPage"> SignUp </a> </small>
      </div>
      <div class="row justify-content-center">
        <div class="col col-md-6">
          <form action="signIn" method="post">
          <input type="text" name="captchaCode" value="${code}" hidden/>
            <label class="form-label my-2" for="">Email: </label>
            <input class="form-control" type="email" name="email" id="" />

            <label class="form-label my-2" for="">Password: </label>
            <input class="form-control" type="password" name="password" id="" />
            <a href="resetPassword.jsp" style="text-decoration: none;" > <small>Forgot password?</small> </a>  </br>

            <img id="captchaImg" src="${pageContext.request.contextPath}/captchaImage" alt="CAPTCHA Image"> &nbsp;
            <button class="btn " type="button" onclick="reloadCaptcha()"><i class="bi bi-arrow-clockwise"></i></button>
            <input class="form-control my-1" type="text" name="enteredCaptcha" placeholder="Enter the captcha">

            <p style="color:red;">${Message}</p>

            <button class="btn btn-primary my-3"style="width: 80%; margin-left: 35px;" value="submit">Log In</button>
            <div class="my-3">
              <div class="text-center">
                or <br>
                <button class="btn btn-light my-3">
                  <img
                    src="<%= request.getContextPath() %>/resources/images/googleLogo.png"
                    style="width: 30px"
                    alt="google"
                  /><small style="margin-left: 20px;">Continue with google</small>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js
    ">
    </script>
        <c:if test="${not empty successMessage}">
             <script>
                 document.getElementById("autoClick").click();
             </script>
         </c:if>
   <script>
               function reloadCaptcha() {
let img = document.getElementById("captchaImg");
        img.src = img.src.split("?")[0] + "?" + new Date().getTime();                }
   </script>
  </body>
</html>
