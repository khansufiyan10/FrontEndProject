<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page isELIgnored="false" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<style>

a
{
 color:white !important;
}

</style>


</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-primary">
  <a class="navbar-brand" href="${pageContext.request.contextPath}/home">MOBI</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <sec:authorize access="!isAuthenticated()">
      <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/home">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/register">Register</a>
      </li>
       </sec:authorize>
      <sec:authorize access="isAuthenticated()">
      	<sec:authorize access="hasAuthority('admin') and isAuthenticated()">
      		<li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/product/add">Add Product</a>
    	  	</li>
    	  </sec:authorize>
      	<li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/product/display">Products</a>
      	</li>
      	<li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
      	</li>
      </sec:authorize>
      
    </ul>
  </div>
</nav>

</body>
</html>