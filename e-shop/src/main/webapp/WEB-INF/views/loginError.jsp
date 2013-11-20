<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ page session="false"%>
<html>
<head>
<meta name="description" content="Portal aukcyjny" />
	<meta name="keywords" content="aukcja, kupic, sprzedac, licytacja, portal" />
	<meta name="author" content="Cebul, Jez" />
	<meta content="pl" http-equiv="Content-Language">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>e-shop</title>
<link href="${pageContext.request.contextPath}/resources/css/index.css" type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/reg.css" type="text/css" rel="stylesheet">
<script src="<c:url value='/resources/js/jquery.js' />" type="text/javascript" ></script>
<script src="<c:url value='/resources/js/mainJs.js' />" type="text/javascript" ></script>
</head>
<body>
<div id='contener'>
		<div id='top'>
			<div id="top-left">
				<ul type="square" style="margin-top: 25px; font: 700 12px/22px 'Lato',Arial,sans-serif; list-style-image: url(<c:url value='/resources/images/punktor.jpg' />)">
			        <li>Atrakcyjny ceny</li>
			        <li>Szeroki asortyment</li>
			        <li>Zakupy z Nami to przyjemność</li>
			    </ul>
			</div>
			
			<div id="top-mid">
				<div id='imgCont' align="center">
					<a href="<c:url value='/' />" style="border: none;">
						<img  style='height: 80px;' src="<c:url value='/resources/images/logo.jpg' />">
					</a>
				</div>
			</div>
			
			<div id="top-right">
				
			</div>
		</div>
		<div id='searchDiv'>
			<form action="" method="GET">
				<table>
					<tr>
						<td>
							<input style="padding: 2px; padding-left: 8px;height: 35px; width: 400px; margin-left: 10px; margin-top: 5px;" id='szukanaFraza' type="text" value="Wpisz czego szuaksz..." onfocus="searchFocus();" onfocusout="searchFocusOut();">	
						</td>
						<td>
							<select name='szukanaKat' id='szukanaKat' style="height: 35px; width: 160px; margin-top: 5px; background-color: #EDEDED;">
								<option value="0">WSZĘDZIE
								</option>
								<c:forEach items="${kategoryList}" var="element"> 
									<option value="${element.nazwa}">
										${element.nazwa}
									</option>
									
								</c:forEach>
							</select>	
						</td>
						<td>
							<input type="submit" value="Szukaj" style="height: 35px; width: 100px; margin-top: 5px; background-image: linear-gradient(#6E6E6E 0%, #343434 100%); color: white;">	
						</td>
						<td>
							<div style="border-left: 1px solid black; padding-left: 10px;">
								<a href="<c:url value='/koszyk' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">MOJE KONTO</a>
							</div>
						</td>
						<td>
							<div style="border-left: 1px solid black; padding-left: 10px; ">
								<a href="<c:url value='/koszyk' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KOSZYK</a>
							</div>
						</td>
						
					</tr>
				</table>
			</form>
		</div>
		<div id='main'>
		<div id='main-cenetr' align='center'>
			<div id="box" align="center" style="padding-top: 50px;">
			<div align="center" style="background-color: #FFCCCC; border: 5px solid red; width: 300px; padding: 20px;margin: 0 auto;;">
				<span style="color: red;">Podałeś zły <b>login</b> lub <b>hasło</b>. Wprowadź poprawne dane i spróbuj jeszcze raz. (Twoje konto mogło jeszcze nie zostać aktywowane. W celu aktywacji sprawdź pocztę)</span>
			</div>
			<table style="margin: 0 auto;" id="tabLog">
			<form method="POST" action="j_spring_security_check" name="f">
				
					<tr>
						<td>Login:</td>
						<td><input class="tabLogTd" type="text" name="j_username" /></td>
					</tr>
					<tr>
						<td>Hasło:</td>
						<td><input class="tabLogTd" type="password" name="j_password" /></td>
					</tr>
					<tr>
						<td>Remember me:</td>
						<td><input type="checkbox" name="_spring_security_remember_me" /></td>
					</tr>
					<tr>
						
						<td colspan="2" align="center"><input id="tabLogSub"  type="submit" value="Zaloguj" name="commit" /></td>
					</tr>
				
			</form>
			</table>
			</div>
		</div>
		</div>
</div>

</body>
</html>