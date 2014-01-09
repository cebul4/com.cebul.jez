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
<script src="<c:url value='/resources/js/jquery.flot.js' />" type="text/javascript" ></script>
<script src="<c:url value='/resources/js/jquery.flot.pie.js' />" type="text/javascript" ></script>
<script>

$(function() {

	// Example Data

	//var data = [
	//	{ label: "Series1",  data: 10},
	//	{ label: "Series2",  data: 30},
	//	{ label: "Series3",  data: 90},
	//	{ label: "Series4",  data: 70},
	//	{ label: "Series5",  data: 80},
	//	{ label: "Series6",  data: 110}
	//];

	//var data = [
	//	{ label: "Series1",  data: [[1,10]]},
	//	{ label: "Series2",  data: [[1,30]]},
	//	{ label: "Series3",  data: [[1,90]]},
	//	{ label: "Series4",  data: [[1,70]]},
	//	{ label: "Series5",  data: [[1,80]]},
	//	{ label: "Series6",  data: [[1,0]]}
	//];

	//var data = [
	//	{ label: "Series A",  data: 0.2063},
	//	{ label: "Series B",  data: 38888}
	//];

	// Randomly Generated Data

	var data = [],
		series = Math.floor(Math.random() * 6) + 3;

	for (var i = 0; i < series; i++) {
		data[i] = {
			label: "Series" + (i + 1),
			data: Math.floor(Math.random() * 100) + 1
		}
	}

	var placeholder = $("#placeholder");
	
	
	$.plot(placeholder, data, {
		series: {
			pie: { 
				show: true
			}
		}
	});
})
	
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
							  	<span style='display: block; margin-top: 20px; margin-left: 120px;'>Witaj: <span style='color: blue; font-size: 18px;'>${sessionScope.sessionUser.getLogin()}</span></span>
							  	<a href="/jez/j_spring_security_logout"> wyloguj</a>
						</c:when>
	  					<c:otherwise>
	  							<a style='margin-top: 40px;' href='/jez/logowanie'>Logowanie</a>
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
								<a href="<c:url value='/panel/' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">PANEL ADMINA</a>
							</div>
						</td>
						<td>
							<div style="border-left: 1px solid black; padding-left: 10px; ">
								<a href="<c:url value='/panel/' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KOSZYK</a>
							</div>
						</td>
						
					</tr>
				</table>
			
		</div>
		<div id='main'>
			<div id='main-left'>
				<a class="categorieLeft" href="<c:url value='/panel/dodajAdmina/' />">Dodaj Admina</a>
				<a class="categorieLeft" href="<c:url value='/panel/dodajKategorie/' />">Dodaj podkategorię</a>
				<a class="categorieLeft" href="<c:url value='/panel/' />">Sprzedane Produkty</a>
				<a class="categorieLeft" href="<c:url value='/panel/' />">Modyfikuj kontot</a>
				<a class="categorieLeft" href="<c:url value='/panel/statystyki' />">Statystyki</a>
								
			</div>
			<div id='main-right'>
				<!--<img style="width: 200px; height: 200px;" src="${pageContext.request.contextPath}/images/13" /> -->
				
				<img src="statystyki" width="500" height="300" border="0"/>
				<!--  <div class="demo-container">
						<div id="placeholder" style="width:550px; height:200px"></div>
				</div> -->
				
				<!--  <p>Time between updates: <input id="updateInterval" type="text" value="" style="text-align: right; width:5em"> milliseconds</p>-->
			</div>
		</div>
		<div id='bottom'>
			<span align="center" style="color: #578921; display: block;"><b>Copyright ? Cebul & Jeżyk</b></span>
			
		</div>
	</div>
	<div id='podpowiedzi' >
		
	</div>
</body>
</html>
