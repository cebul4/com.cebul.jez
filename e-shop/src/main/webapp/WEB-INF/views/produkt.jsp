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

$( document ).ready(function() 
{
	var test = $("#stanAukcji").val();
	//alert(test);
	if(test != null)
	{
		//alert("to jest ukcja");	
		// uruchamiamy co 30s sprawdzanie czy ktos nas nie przebil
		var myVar = setInterval(function(){myTimer()},30000);
	}
	
});
function myTimer()
{
	//alert("hahaha");
	
	$('#loadingGif').css("display", "inline");
	var idProd = $('#id').val();
	
	$.getJSON( "/jez/produkty/sprawdzDostepnosc.json", {"idProd": idProd})
		.done(function( json ) {
		   // alert(json.cena);
				var cenka = json.cena;
				var cenaFloat = cenka.toFixed(1);
				var cenkaSugerowana = (cenka + 1).toFixed(1);
		    	if(json.user.id == $("#sessionUserId").html() )
		    	{
		    		$("#stanAukcji").html("Obecnie wygrywasz licytację.");
		    		$("#cena").html(cenaFloat);
		    		$("#cenaInput").val("");
		    		$("#cenaInput").val(cenkaSugerowana);
		    		//alert("no wygrywam");
		    	}else{
		    		$("#stanAukcji").html("Ktoś inny wygrywa w licytacji.");
		    		$("#cena").html(cenaFloat);
		    		$("#cenaInput").val("");
		    		$("#cenaInput").val(cenkaSugerowana);
		    	}		
		  $('#loadingGif').delay(1000).css("display", "none");   
	})
	.fail(function( jqxhr, textStatus, error ) {
		   //alert("error="+error);
	}); 
}
function sprawdzCene()
{
	var cenaAktu = $("#cena").html();
	var cena = $("#cenaInput").val();
	if(cenaAktu < cena)
	{
		//alert("cenaAkt= "+cenaAktu+" cena= "+cena);
		return true;
	}
	
	return false;
}

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
			<c:forEach items="${podkategorie}" var="element"> 
				<a href="<c:url value='/kategoria/${element.id}' />" class="categorieLeft" onmouseover="showPodkategorie()">
					${element.nazwa}
				</a>
			</c:forEach>
								
			</div>
			<div id='main-right' style='padding-left: 10px; padding-top: 10px;'>
				<div  style='margin-left: 75px;'>
					<span style="font-size: 12pt; display: block; color: gray; margin-bottom: 5px;">${path}</span>
					
						<div style='margin-right: 20px; margin-right: 10px; float: left; padding: 5px; border: 1px solid gray; width: 200px;'>
								<c:choose>
								<c:when test="${!empty produkt.zdjecie}">
										<img style="width: 200px; height: 200px;" src="${pageContext.request.contextPath}/prodimag/${produkt.zdjecie.id}" />
								</c:when>
									<c:otherwise>
										<img style="width: 200px; height: 200px;" src="<c:url value='/resources/images/unknownItem.png' />" />
									</c:otherwise>
								</c:choose>
						</div>
						
						</br>
						<div style='padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Nazwa: </span> <span>${produkt.nazwa}</span></div>
						</br>
						<div style='padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Cena: </span> <span id="cena">${produkt.cena}</span> zł </div>
						</br>
						<div style='padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Sprzedający:</span> <span>${produkt.user.login} </span></div>
						</br>
						<div style='padding: 3px;'>
							
								<c:choose>
									<c:when test="${czySprzedane}">
										<c:choose>
											<c:when test="${czyKupTeraz}">
												<sf:form modelAttribute="produkt" action="/jez/produkty/addToCart/" method="POST">
													<sf:input type="hidden" path="id" value="${produkt.id}" />
													<input style="margin-left: 5px;" class="sub" type="submit" value="Kup teraz" />
												</sf:form>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${!empty sessionScope.sessionUser}">
														<span style="display:none;" id="sessionUserId">${sessionScope.sessionUser.id}</span>
														<sf:form  modelAttribute="produkt" action="/jez/produkty/licytuj/" method="POST" onsubmit="return sprawdzCene();">
															<sf:input type="hidden" path="id" id="id" value="${produkt.id}" />
															<sf:input id="cenaInput" type="text" path="cena" value="${produkt.cena +1}" style="width: 60px;" />
															<input style="margin-left: 5px;" class="sub" type="submit" value="Licytuj" /> 
															 <span>(Do końca: ${roznicaDat} dni)</span>
														</sf:form>
														
														<c:choose>
															<c:when test="${ktosLicytuje}">
																<c:choose>
																	<c:when test="${czyJaWygrywam}">
																		<span id="stanAukcji" style="color: red; font-size: 14pt;">Obecnie wygrywasz licytację.</span>
																	</c:when>
																	<c:otherwise>
																		<span id="stanAukcji" style="color: red; font-size: 14pt;">Ktoś inny wygrywa w licytacji.</span>	
																	</c:otherwise>
																</c:choose>
																
															</c:when>
															<c:otherwise>
																<span>Nikt jeszcze nie licytuje. Bądź pierwszy</span>
															</c:otherwise>
														</c:choose>
														<img style="width: 25px; display: none;" id="loadingGif" src="<c:url value='/resources/images/loading.gif' />" />
													</c:when>
													<c:otherwise>
														<span style="color: red; font-size: 14pt;">Aby móc licytować musisz się zalogować!</span>	
													</c:otherwise>
												</c:choose>
												
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<div>
											<span style="color: red; font-weight: bold;">Produkt został sprzedany</span>
										</div>
									</c:otherwise>
								</c:choose>
						</div>
						
				
				</div>
				</br>
				</br>
				<div style='display:block; margin-left: 75px; padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Opis: </span> </br><span >${produkt.opis}</span></div>
				</br>
				<div style="display:block; margin-left: 75px;">
					<span style="font-size: 14pt; color: #8AC74A;" >Zdjecia produktu:</span></br>
					<c:choose>
						<c:when test="${!empty zdjecia}">
								<c:forEach items="${zdjecia}" var="zdj"> 
									<img style="margin: 5px; width: 200px; height: 200px;" src="${pageContext.request.contextPath}/images/13" />
								</c:forEach>
						</c:when>
						<c:otherwise>
								<span style="margin-left: 20px; font-size: 14pt;" >brak zdjęć produktu</span>
						</c:otherwise>
					</c:choose>
					
					
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
