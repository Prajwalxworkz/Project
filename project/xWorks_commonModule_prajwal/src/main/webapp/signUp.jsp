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
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css
  "
  />
  <style>
    .font-family{
       font-family: 'Times New Roman', Times, serif;
   }
</style>
</head>
<body>
   <c:if test="${not empty successMessage}">
          <button type="button" class="d-none" data-bs-toggle="modal" data-bs-target="#successModal" id="autoClick1"></button>
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

   <c:if test="${not empty errorMessage}">
          <button type="button" class="d-none" data-bs-toggle="modal" data-bs-target="#errorModal" id="autoClick2"></button>
      </c:if>
  <div class="modal fade" id="errorModal"   tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
         <div class="modal-body">
               <p style="color:red;">${errorMessage}</p>
               </div>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
      </div>
    </div>
  </div>

    <div class="container border font-family my-5 rounded-4 shadow" style="width:900px;">

        <div class="row justify-content-center my-3">
            <div class="col-md-4">
                <div class="text-center">
                    <h4>Sign Up</h4>
                    <small>Already have an account? <a href="signIn.jsp">LogIn</a></small>
                </div>
                <form id="form" action="signUp" method="post">
                    <div>
                        <label class="form-label my-2" for="fullName">Full Name</label>
                        <input class="form-control" onchange="validateName()" type="text" name="fullName" id="fullName">
                        <span id="ajaxNameValidation"style="color:red;"></span>
                        <div class="error" style="color: red;"></div>
                    </div>
                    <div>
                        <label class="form-label my-2" for="email">Email</label>
                        <input class="form-control" type="email" name="email" id="email" onchange="validateEmail()">
                        <div class="error" style="color: red;"></div>
                        <span id="ajaxEmailValidation"style="color:red;"></span>
                     </div>
                     <div>
                        <label class="form-label my-2" for="">DoB</label>
                        <input class="form-control" type="date" name="dob" id="">
                     </div>
                     <div>
                        <label class="form-label my-2" for="phoneNumber">Phone Number</label>
                        <input class="form-control" type="text" name="phoneNumber" id="phoneNumber" onchange="validatePhoneNumber()">
                        <div class="error" style="color: red;"></div>
                        <span id="ajaxPhoneNumberValidation"style="color:red;"></span>
                    </div>
                    <div>
                        <label >Gender</label> <br>
                        <input  type="radio" name="gender" value="male" id="">
                        <label >Male</label> &nbsp;
                        <input  type="radio" name="gender" value="female" id="">
                        <label >Female</label> &nbsp;
                        <input  type="radio" name="gender" value="others" id="">
                        <label >Others</label> <br>
                    </div>
                    <div>
                        <label class="form-label my-2" for="">Location</label>
                        <select class="form-select" name="location">
                            <c:forEach items="${location}" var="city">
                                <option  value="${city}">${city}</option>
                            </c:forEach>
                        </select>
                    </div>



                    <button class="btn btn-primary my-3 " style="width: 100%;" type="submit">Submit</button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js
    "></script>
          <c:if test="${not empty successMessage}">
              <script>
                      document.getElementById("autoClick1").click();
              </script>
          </c:if>
           <c:if test="${not empty errorMessage}">
              <script>
                      document.getElementById("autoClick2").click();
              </script>
          </c:if>
          <script src="${pageContext.request.contextPath}/resources/js/signUpForm.js"></script>
</body>
</html>