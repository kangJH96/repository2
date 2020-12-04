<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Moew Admin</title>
<link rel="stylesheet" href="admin/css/admin.css">
<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="admin/product/product.js"></script>
</head>

<body onload="go_ab()">  <!-- 페이지 로드시에 제품의 순매출 계산 -->
	<div id="wrap">
		<header>			
			<div id="logo">
				<a href="admin_login_form"> 
					<img src="admin/images/admin_logo.PNG" width="209" height="37">
					<img src="admin/images/text.gif">
				</a>
			</div>	
			<input class="btn" type="button"  value="logout"  style="float: right;"
			   onClick="location.href='admin_logout'">			
		</header>
		<div class="clear"></div>