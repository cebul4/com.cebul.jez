<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
$( window ).load(function() {
	var h = $("#main-right").outerHeight(true);
	 $("#main").css("height", h+20);
	});
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
								<a href="<c:url value='/koszyk' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KOSZYK</a>
							</div>
						</td>
						
					</tr>
				</table>
			
		</div>
		<div id='main'>
			<div id='main-left' style="font-size: 10pt;">
				<sf:form modelAttribute="check" method="GET">
					<c:choose>
						<c:when test="${hasPodkategory}">
								<span style="font-weight: bold;">Podkategoria:</span>
								</br>
								<c:forEach items="${podkategorie}" var="element"> 
									<c:choose>
										<c:when test="${!empty checkPod}">
											<c:forEach items="${checkPod}" var="element2"> 
												<c:choose>
													<c:when test="${element2 == element.id}">
														<sf:checkbox checked="checked" path="podkat" style="width: 50px;"  value="${element.id}" />
														
													</c:when>
												</c:choose>
											</c:forEach>
											
										</c:when>
										<c:otherwise>
											<sf:checkbox path="podkat" style="width: 50px;"  value="${element.id}" />
										</c:otherwise>
									</c:choose>
									
									<span style="font-size: 10pt;">${element.nazwa}</span>
									</br>
								</c:forEach>
								</br>
						</c:when>
					</c:choose>
					<span style="font-weight: bold;">Cena:</span>
					</br>
					<input type="hidden" name="szukanaFraza" value="${szukFraz}" />
					<input type="hidden" name="szukanaKat" value="${szukKat}" />
					<c:choose>
							<c:when test="${!empty cenaOd}">
								od:<Input type="text" value="${cenaOd }" name="cenaOd" id="cenaOd" style="width: 40px; height: 20px;" />
							</c:when>
							<c:otherwise>
								od:<Input type="text" name="cenaOd" id="cenaOd" style="width: 40px; height: 20px;" />
							</c:otherwise>
					</c:choose>
					<c:choose>
							<c:when test="${!empty cenaDo}">
								do:<Input value="${cenaDo }" type="text" name="cenaDo" id="cenaDo" style="width: 40px; height: 20px;"/>
							</c:when>
							<c:otherwise>
								do:<Input type="text" name="cenaDo" id="cenaDo" style="width: 40px; height: 20px;"/>
							</c:otherwise>
					</c:choose>
					
					</br>
					</br>
					<span style="font-weight: bold;">Licytuj / KupTeraz</span>
					</br>
					<c:choose>
						<c:when test="${kupCheck}">
							<sf:checkbox checked="checked" path="kupLicyt" style="width: 50px;"  value="kupTeraz" />Kup Teraz
						</c:when>
						<c:otherwise>
							<sf:checkbox path="kupLicyt" style="width: 50px;"  value="kupTeraz" />Kup Teraz
						</c:otherwise>
					</c:choose>
					</br>
					
					<c:choose>
						<c:when test="${licCheck}">
							<sf:checkbox checked="checked" path="kupLicyt" style="width: 50px;"  value="licytuj" />Licytuj
						</c:when>
						<c:otherwise>
							<sf:checkbox path="kupLicyt" style="width: 50px;"  value="licytuj" />Licytuj
						</c:otherwise>
					</c:choose>
					
					</br>
					</br>
					<input class="sub" style="margin-left: 0px;" type="submit" value="Filtruj" />
				</sf:form>
								
			</div>
			<div id='main-right' style='padding-left: 10px; overflow-y: scroll;overflow-x:hidden;'>
				<span style="font-size: 16pt; color: #8AC74A;">Produkty z kategorii: ${szukanaKat}</span>
				<c:forEach items="${szukaneProdukty}" var="element" varStatus="status"> 
					<a style="color: black; text-decoration: none; border: 0px;" href="${pageContext.request.contextPath}/produkty/${element.id}/">
						<div style="float: left; width: 800px; border-bottom: 1px solid #DDDDDD">
							
							<div style="float: left;">
								<c:choose>
									<c:when test="${!empty element.zdjecie}">
										<img style="width: 100px; " src="${pageContext.request.contextPath}/images/${element.id}" />
									</c:when>
									<c:otherwise>
										<img style="width: 100px; " src="<c:url value='/resources/images/unknownItem.png' />" />
									</c:otherwise>
								</c:choose>
							</div>
							<div style="width: 780px;">
								<div style="padding: 10px; float:left;  width: 440px; height: 80px;">
									<img style="width: 20px; " src="<c:url value='/resources/images/ok.gif' />" />
									<span style=" font-size: 16pt; font-weight: bold;">${element.nazwa}</span>
									<c:set var="dat" value="${element.dataDodania}" />
									<span style="display:block; margin-top: 40px; font-size: 12pt;"><fmt:formatDate value="${dat}" /></span>
								</div>
								<div style="text-align:right; padding: 10px; float:left;  width: 180px; height: 80px;">
									<span style="font-size: 16pt; font-weight: bold;">${element.cena} zł</span>
									
									<c:choose>
										<c:when test="${czyKupTeraz[status.count-1]}">
											<span style="display:block; margin-top: 40px; font-size: 12pt;">Kup Teraz</span>
										</c:when>
										<c:otherwise>
											<span style="display:block; margin-top: 40px; font-size: 12pt;">Licytacja</span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</a>
				</c:forEach>
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
