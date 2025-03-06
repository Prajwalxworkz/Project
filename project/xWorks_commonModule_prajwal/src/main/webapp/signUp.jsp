<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page isELIgnored="false" %>
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
    <div class="container border font-family my-5" style="width:900px;">

        <div class="row justify-content-center">
            <div class="col-md-4">
                <div class="text-center">
                    <h4>Sign Up</h4>
                    <small>Already have an account? <a href="signIn.jsp">LogIn</a></small>
                </div>
                <form action="signUp" method="post">
                    <label class="form-label my-2" for="">Full Name</label>
                    <input class="form-control" type="text" name="fullName" id="">
                    <label class="form-label my-2" for="">Email</label>
                    <input class="form-control" type="email" name="email" id="">
                    <label class="form-label my-2" for="">DoB</label>
                    <input class="form-control" type="date" name="dob" id="">
                    <label class="form-label my-2" for="">Phone Number</label>
                    <input class="form-control" type="text" name="phoneNumber" id="">
                    <label >Gender</label> <br>
                    <input  type="radio" name="gender" value="male" id="">
                    <label >Male</label> &nbsp;
                    <input  type="radio" name="gender" value="female" id="">
                    <label >Female</label> &nbsp;
                    <input  type="radio" name="gender" value="others" id="">
                    <label >Others</label> <br>
                    <label class="form-label my-2" for="">Location</label>
                    <select class="form-select" name="location">
                       <option  value="Hassan">Hassan</option>
                       <option  value="Bengaluru">Bengaluru</option>
                       <option  value="Mysuru">Mysuru</option>
                       <option  value="Shivamogga">Shivamogga</option>
                       <option  value="Chikkamagaluru">Chikkamagaluru</option>
                    </select>
                    <label class="form-label my-2" for="">Password</label>
                    <input class="form-control" type="password" name="password" id="">
                    <label class="form-label my-2" for="">Confirm Password</label>
                    <input class="form-control" type="password" name="confirmPassword" id="">
                    <p style="color:orange; margin-top:20px;">${Message}</p>
<button class="btn btn-primary my-3 " style="width: 100%;" type="submit">Submit</button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js
    "></script>
</body>
</html>