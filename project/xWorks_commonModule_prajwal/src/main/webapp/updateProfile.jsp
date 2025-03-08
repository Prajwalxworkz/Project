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
    <div class="container border font-family my-5 rounded-4 shadow" style="width:900px;">
        <div class="row justify-content-center my-3">
            <div class="col-md-4">

                <form action="updateProfile" method="post">
                    <input class="form-control" type="text" name="id" hidden id="" value="${dto.getId()}">
                    <label class="form-label my-2" for="">Full Name</label>
                    <input class="form-control" type="text" name="fullName" id="" value="${dto.getFullName()}">
                    <label class="form-label my-2" for="">Email</label>
                    <input class="form-control" type="hidden" name="email" id="" value="${dto.getEmail()}" >
                    <input class="form-control" type="email" name="email" id="" value="${dto.getEmail()}" disabled>
                    <label class="form-label my-2" for="">DoB</label>
                    <input class="form-control" type="date" name="dob" id="" value="${dto.getDob()}">
                    <label class="form-label my-2" for="">Phone Number</label>
                    <input class="form-control" type="text" name="phoneNumber" id="" value="${dto.getPhoneNumber()}">
                    <label >Gender</label> <br>
                    <input  type="radio" name="gender" value="male" id="" ${dto.getGender()=="male"? 'checked="checked"':''}>
                    <label >Male</label> &nbsp;
                    <input  type="radio" name="gender" value="female" id="" ${dto.getGender()=='female'? 'checked="checked"':''}>
                    <label >Female</label> &nbsp;
                    <input  type="radio" name="gender" value="others" id="" ${dto.getGender()=='others'? 'checked="checked"':''}>
                    <label >Others</label> <br>
                    <label class="form-label my-2" for="">Location</label>
                    <select class="form-select" name="location">
                       <option  value="Hassan" ${dto.getLocation()=="Hassan"? 'selected="selected"':''}>Hassan</option>
                       <option  value="Bengaluru" ${dto.getLocation()=="Bengaluru"? 'selected="selected"':''}>Bengaluru</option>
                       <option  value="Mysuru" ${dto.getLocation()=="Mysuru"? 'selected="selected"':''}>Mysuru</option>
                       <option  value="Shivamogga" ${dto.getLocation()=="Shivamogga"? 'selected="selected"':''}>Shivamogga</option>
                       <option  value="Chikkamagaluru" ${dto.getLocation()=="Chikkamagaluru"? 'selected="selected"':''}>Chikkamagaluru</option>
                    </select>
                    <label class="form-label my-2" for="" >Password</label>
                    <input class="form-control" type="password" name="password" id="" value="${dto.getPassword()}">
                    <label class="form-label my-2" for="" >Confirm Password</label>
                    <input class="form-control" type="password" name="confirmPassword" id="" value="${dto.getPassword()}">
                     <p style="color:red; margin-top:20px;">${errorMessage}</p>
<button class="btn btn-primary my-3 " style="width: 100%;" type="submit">Update Profile</button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js
    "></script>
</body>
</html>