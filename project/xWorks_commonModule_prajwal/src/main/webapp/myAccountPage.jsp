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
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css
      "
        />
        <link
          rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
        />
      </head>
      <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
          <div class="container-fluid">
            <img
            class="navbar-brand"
             src="<%= request.getContextPath() %>/resources/images/xworkzLogo.png"
            style="width: 140px; height: 70px"
            alt="Xworkz"
          />
           <ul  class="navbar-nav me-auto mb-2 mb-lg-0">
                          <li class="nav-item"> <a class="nav-link active" aria-current="page" href="#">Home</a></li>
                          <li class="nav-item"> <a class="nav-link active" aria-current="page" href="#">About Us</a></li>
                          <li class="nav-item"> <a class="nav-link active" aria-current="page" href="#">Contact</a></li>
            </ul>
            <button
              class="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul
                class="navbar-nav ms-auto mb-2 mb-lg-0"
              >
                <li class="nav-item dropdown">
                  <a
                    class="nav-link dropdown-toggle"
                    href="#"
                    id="navbarDropdown"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    <i class="bi bi-person-circle fs-2"></i>
                  </a>
                  <ul
                    class="dropdown-menu dropdown-menu-end"
                    aria-labelledby="navbarDropdown"
                  >
                    <li><a class="dropdown-item" href="getUserByEmail?email=${email}&event=update">Update Profile</a></li>
                    <li><a class="dropdown-item" href="getUserByEmail?email=${email}&event=delete">Delete Account</a></li>
                    <li><a class="dropdown-item" href="index.jsp">Logout</a></li>
                  </ul>
                </li>
              </ul>
            </div>
          </div>
        </nav>
         <div class="alert alert-success">
              <p>${successMessage}</p>
          </div> <div class="alert alert-danger">
              <p>${errorMessage}</p>
          </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js
        "></script>
      </body>
    </html>
