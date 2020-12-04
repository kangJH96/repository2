<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Moew Shop</title>
  <link href="css/shopping.css" rel="stylesheet">  
  <script type="text/javascript" src="js/member.js"></script>
  <script type="text/javascript" src="js/mypage.js"></script> 
</head>

<body>
<div id="wrap">
<!--헤더파일 들어가는 곳 시작 -->
  <header>
    <!--로고 들어가는 곳 시작--->  
    <div id="logo">
      <a href="index">
        <img src="images/logo.jpg" width="180" height="100" alt="moew">
      </a>  
    </div>
    <!--로고 들어가는 곳 끝-->     
    <nav id="catagory_menu">
     <ul>
       <c:choose>
       <c:when test="${empty sessionScope.loginUser}">
       <li>         
         <a href="login_form" style="width:120px;">LOGIN( CUSTOMER</a>   
	     <a href="admin_login_form" style="width:120px;">| &nbsp;&nbsp;ADMIN )</a>
	   </li>		       
       <li>/</li>
       <li><a href="contract">JOIN</a></li>
       </c:when>
       <c:otherwise>
       <li style="color:orange">
         ${sessionScope.loginUser.name}(${sessionScope.loginUser.id})
       </li>
       <li><a href="logout">LOGOUT</a></li>
       </c:otherwise>       
       </c:choose>
       <li>/</li>
       <li>
         <a href="cart_list">CART</a>
       </li><li>/</li>
       <li>
         <a href="mypage">MY PAGE</a>
       </li><li>/</li>
       <li>
         <a href="qna_list">Q&amp;A(1:1)</a>
       </li>
     </ul>
    </nav>

    <nav id="top_menu">
      <ul>
        <li>
          <a href="category?kind=1">Food</a>
        </li>  
        <li>
          <a href="category?kind=2">Home</a>
        </li>   
        <li>
          <a href="category?kind=3">Wear</a>
        </li> 
        <li>
          <a href="category?kind=4">Play</a>
        </li> 
        <li>
          <a href="category?kind=5">Beauty</a>
        </li>
        <li>
          <a href="category?kind=6">Hearth</a>
        </li> 
      </ul>
    </nav>
    <div class="clear"></div>
    <hr>
  </header>
  <!--헤더파일 들어가는 곳 끝 -->