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
					<a href="<c:url value='/' />" style="border: 0 px solid black; text-decoration: none;">
						<img  style='border: 0 px solid black; height: 80px;' src="<c:url value='/resources/images/logo.jpg' />">
					</a>
				</div>
			</div>
			
			<div id="top-right">
				<div>
					<c:choose>
						<c:when test="${!empty sessionScope.sessionUser}">
							  	<span style='display: block; margin-top: 20px; margin-left: 120px;'>Witaj: <span style='color: red; font-size: 18px;'>${sessionScope.sessionUser.getLogin()}</span></span>
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
								<input id='szukanaFraza' name="szukanaFraza" type="text" autocomplete="off" style="padding: 2px; padding-left: 8px;height: 35px; width: 400px; margin-left: 10px; margin-top: 5px;"  value="Wpisz czego szuaksz..." onblur="ukryjPodpowiedzi();" onkeyup="sprawdzSlowo();" onfocus="searchFocus();" >	
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
								<a href="<c:url value='/mojekonto/' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">MOJE KONTO</a>
							</div>
						</td>
						<td>
							<div style="border-left: 1px solid black; padding-left: 10px; ">
								<a href="<c:url value='/koszyk/' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KOSZYK</a>
							</div>
						</td>
						
					</tr>
				</table>
			
		</div>
		<div id='main'>
			<div align="center" id='main-right' style='width: 980px; padding-left: 10px; padding-top: 10px;'>
				
				<div  style='border: 1px solid #F2FFE1 ;background-color:#F2FFE1; -moz-border-radius: 30px; border-radius: 30px; min-height:300px; width: 920px; padding: 30px;'>
				<div>
					<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
						<span style="margin-left: 5px; font-size: 16pt; font-weight: bold;">Dane użytkownika</span>
					</div>
					<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
						<span>Login:</span>
						<span style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.nabywca.login}</span>
					</div>
					<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
						<span>Imię:</span>
						<span  style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.nabywca.imie}</span>
						
					</div>
					<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
						<span>Nazwisko:</span>
						<span  style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.nabywca.nazwisko}</span>
					</div>
					<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
						<span>Adres:</span>
						<span  style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.nabywca.adres.miejscowosc}, ${zamowienie.nabywca.adres.kod_pocztowy}</span>
					</div>
				</div>	
				<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
					<span>Do zapłaty:</span>
					<span  style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.koszt} zł</span>
				</div>
				<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
					<span>Sposób zapłaty:</span>
					<span  style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.platnosc.rodzajPlatnosci}</span>
				</div>
				<div style="margin-left: 10px; text-align: left; margin-bottom: 10px;">
					<span>Dokument kupna:</span>
					<span  style="margin-left: 5px; font-size: 14pt; font-weight: bold;">${zamowienie.dokumentZamowienia.nazwa }</span>
				</div>
				<div style="margin-top: 30px; margin-left: 10px; text-align: left; margin-bottom: 20px;">
					<span style="margin-left: 5px; font-size: 16pt; font-weight: bold;">Wybrane produkty:</span>
				</div>
					<c:forEach items="${zamowienie.produkty}" var="element" varStatus="status"> 
						<div  class="divProd" style=" width: 900px; height: 35px;">
							<div style="float:left;">
							<c:choose>
									<c:when test="${!empty element.zdjecie}">
											<img style="width: 30px; " src="${pageContext.request.contextPath}/images/${element.id}" />
									</c:when>
									<c:otherwise>
											<img style="width: 30px; " src="<c:url value='/resources/images/unknownItem.png' />" />
									</c:otherwise>
							</c:choose>
							</div>
								<div style="width: 830px;">
									<div style="padding: 10px; float:left;  width: 460px; height: 25px;">
										<span style=" font-size: 12pt; font-weight: bold;">${element.nazwa}</span>
									</div>
									<div style="text-align:right; padding: 10px; float:left;  width: 180px; height: 25px;">
										<span style="font-size: 12pt; font-weight: bold;">${element.cena} zł</span>
									</div>
									
								</div>
						</div>
						
					</c:forEach>
			</div>
			<div>
				<div style="margin-left:580px;">
					
					<a id="zatw" href="${flowExecutionUrl}&_eventId=zatwierdzZamowienie" style="color: white; fony-weight: bold; background-color: #8AC74A; text-decoration: none; width: 200px; text-align: center; padding: 5px;" >Zatwierdź zamówienie</a>
					<a id="zatw" href="${flowExecutionUrl}&_eventId=cancel" style="color: white; fony-weight: bold; background-color: #8AC74A; text-decoration: none; width: 200px; text-align: center; padding: 5px;" >Anuluj</a>
				</div>
			</div>
		</div>
		</div>
		<div id='bottom'>
			<span align="center" style="color: #578921; display: block;"><b>Copyright Ⓒ Cebul & Jeżyk</b></span>
			
		</div>
	</div>
	<div id='podpowiedzi' >
		
	</div>
</body>
</html>
