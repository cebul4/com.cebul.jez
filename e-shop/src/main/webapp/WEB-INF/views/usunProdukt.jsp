<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page contentType="text/html;charset=UTF-8" %> 
<%@ page session="true"%>
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
<script>

</script>
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
				<div>
					<c:choose>
						<c:when test="${!empty sessionScope.sessionUser}">
							  	<span style='display: block; margin-top: 20px; margin-left: 120px;'>Witaj: <span style='color: blue; font-size: 18px;'>${sessionScope.sessionUser.getLogin()}</span></span>
							  	<a href="/jez/j_spring_security_logout"> wyloguj</a>
						</c:when>
	  					<c:otherwise>
	  							<a style='margin-top: 40px;' href='/jez/logowanie'>Zaloguj</a>
								<a href='/jez/rejestracja'>Rejestracja</a>
	  					</c:otherwise>
  					</c:choose>
					
				</div>
			</div>
		</div>
		<div id='searchDiv'>
			
				<table>
					<tr>
						<form action="${pageContext.request.contextPath}/szukaj/szukajProd/" method="get">
							<td>
								<input id='szukanaFraza' name="szukanaFraza" type="text" autocomplete="off" style="padding: 2px; padding-left: 8px;height: 35px; width: 400px; margin-left: 10px; margin-top: 5px;"  value="Wpisz czego szukasz..." onblur="ukryjPodpowiedzi();" onkeyup="sprawdzSlowo();" onfocus="searchFocus();" >	
							</td>
							<td>
								<select name='szukanaKat' id='szukanaKat' style="height: 35px; width: 160px; margin-top: 5px; background-color: #EDEDED;">
									<option value="0">WSZĘDZIE
									</option>
									<c:forEach items="${kategoryList}" var="element"> 
										<option value="${element.id}">
											${element.nazwa}
										</option>
										
									</c:forEach>
								</select>	
							</td>
							
							<td>
								<input type="submit" value="Szukaj" style="height: 35px; width: 100px; margin-top: 5px; background-image: linear-gradient(#6E6E6E 0%, #343434 100%); color: white;">	
							</td>
						</form>
						<td>
							<div style="border-left: 1px solid black; padding-left: 10px;">
									<c:choose>
									<c:when test="${sessionScope.sessionUser.ranga == 'admin'}">
										  	<a href="<c:url value='/panel/' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">PANEL ADMINA</a>
									</c:when>
				  					<c:otherwise>
				  							<a href="<c:url value='/mojekonto/' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">MOJE KONTO</a>
				  					</c:otherwise>
			  					</c:choose>
								
							</div>
						</td>
								<td>
							<div style="border-left: 1px solid black; padding-left: 10px; ">
								<c:choose>
									<c:when test="${sessionScope.sessionUser.ranga == 'admin'}">
										  
									</c:when>
				  					<c:otherwise>
				  							<a href="<c:url value='/koszyk' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KOSZYK</a>
				  					</c:otherwise>
			  					</c:choose>
								
							</div>
						</td>
						
					</tr>
				</table>
			
		</div>
		<div id='main'>
		<div id='main-left'>
				<a class="categorieLeft" href="<c:url value='/panel/dodajAdmina/' />">Dodaj Admina</a>
				<a class="categorieLeft" href="<c:url value='/panel/dodajKategorie/' />">Dodaj Kategorię</a>
				<a class="categorieLeft" href="<c:url value='/panel/edytujKategorie/' />">Edytuj Kategorię</a>
				<a class="categorieLeft" href="<c:url value='/panel/blockUser' />">Zablokuj Użytkownika</a>
				<a class="categorieLeft" href="<c:url value='/panel/usunProdukt' />">Usuń Produkt</a>
				<a class="categorieLeft" href="<c:url value='/panel/statystyki' />">Statystyki</a>
								
			</div>
		<div id='main-right' align='center'>
		<p style="font-size: 16pt; color: #8AC74A;" ><b>Podaj ID Produktu: </b></p>
		
		<sf:form method="POST" action="/jez/panel/usunProdukt">
			<fieldset style='border: none;'>
			<table class='casualTab'>
			<tr>
				<td> <label><b><i>ID Produktu</i></b></label> </td>
				<td>
					<input style='width: 90px;' type="text" name="id" />
				</td>	
			</tr>
			
			<tr>
				<td cols="2" align='center'>
					<input class="sub" style='margin-left: 50px;' type="submit" value="Usuń" /> 
				</td>
			</tr>
			</table>
			</fieldset>
		</sf:form>
	
	</div>
</div>
</body>
</html>