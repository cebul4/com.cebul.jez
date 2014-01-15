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
<link href="${pageContext.request.contextPath}/resources/css/index.css"
	type="text/css" rel="stylesheet">
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
								<a href="<c:url value='/koszyk' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KOSZYK</a>
							</div>
						</td>
						
					</tr>
				</table>
			
		</div>
		<div id='main'>
			<div id='main-left'>
				<a class="categorieLeft" href="<c:url value='/mojekonto/dodajProdukt' />">Dodaj Produkt</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wystawioneProdukty/' />">Wystawione Produkty</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/sprzedaneProdukty/' />">Sprzedane Produkty</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/kupioneProdukty/' />">Kupione Produkty</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wylicytowaneProdukty/' />">Wylicytowane Produkty</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/modyfikujKonto/' />">Modyfikuj kontot</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wystawKomentarz/' />">Wystaw komentarz</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/otrzymaneKomentarze/' />">Otrzymane Komentarze</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wystawioneKomentarze/' />">Wystawione komentarze</a>				
			</div>
			<div id='main-right'>
			<c:choose>
					<c:when test="${empty sprzedane}">
							<p style="margin-left: 100px; font-size: 18pt; font-weight: bold;">Nie sprzedałeś jeszcze żadnego produktu. </p>  	
					</c:when>
  				<c:otherwise>
				<c:forEach items="${sprzedane}" var="element" varStatus="status"> 
						<a class="greenSelect" style="color: black; text-decoration: none; border: 0px;" href="${pageContext.request.contextPath}/produkty/${element.id}/">
						<div  class="divProd" style=" width: 760px; height: 70px;border-bottom: 1px solid #DFDFDF; ">
							<div style="float:left;">
							<c:choose>
									<c:when test="${!empty element.zdjecie}">
											<img style="width: 65px; height: 65px;" src="${pageContext.request.contextPath}/images/${element.id}" />
									</c:when>
									<c:otherwise>
											<img style="width: 65px; " src="<c:url value='/resources/images/unknownItem.png' />" />
									</c:otherwise>
							</c:choose>
							</div>
								<div style="width: 830px;">
									<div style="padding: 10px; float:left;  width: 460px; height: 30px;">
										
										<span style=" font-size: 12pt; font-weight: bold;">${element.nazwa}</span>
										
										
									</div>
									<div style="text-align:right; padding: 10px; float:left;  width: 180px; height: 30px;">
										<span style="font-size: 12pt; font-weight: bold;">${element.cena} zł</span>
										
										<c:choose>
											<c:when test="${czyKupTeraz[status.count-1]}">
												<span style="display:block; margin-top: 5px; font-size: 10pt;">Kup Teraz</span>
											</c:when>
											<c:otherwise>
												<span style="display:block; margin-top: 5px; font-size: 10pt;">Licytacja</span>
											</c:otherwise>
										</c:choose>
									</div>
									
								</div>
						</div>
						</a>
					</c:forEach>
				</c:otherwise>
			</c:choose>
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
