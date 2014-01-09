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
var stan = "";
function selectbtnOcena(ob)
{
	var id= ob.id;
	var url = $("#url").html();
	var green = url+"circle.png";
	var gray = url+"circleGray.png";
	
	$("#ocenaBtn1").attr("src", gray);
	$("#ocenaBtn2").attr("src", gray);
	$("#ocenaBtn3").attr("src", gray);
	$("#ocenaBtn4").attr("src", gray);
	$("#ocenaBtn5").attr("src", gray);
	
	if(id == "ocenaBtn1"){
		$("#ocenaBtn1").attr("src", green);
	}else if(id == "ocenaBtn2"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
	}else if(id == "ocenaBtn3"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);

	}else if(id == "ocenaBtn4"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);
		$("#ocenaBtn4").attr("src", green);
	}else if(id == "ocenaBtn5"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);
		$("#ocenaBtn4").attr("src", green);
		$("#ocenaBtn5").attr("src", green);
	}
	
}
function changeOcena(ob)
{
	var id= ob.id;
	var url = $("#url").html();
	var green = url+"circle.png";
	var gray = url+"circleGray.png";
	
	stan = id;
	
	$("#ocenaBtn1").attr("src", gray);
	$("#ocenaBtn2").attr("src", gray);
	$("#ocenaBtn3").attr("src", gray);
	$("#ocenaBtn4").attr("src", gray);
	$("#ocenaBtn5").attr("src", gray);
	
	if(id == "ocenaBtn1"){
		$("#ocenaBtn1").attr("src", green);
	}else if(id == "ocenaBtn2"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
	}else if(id == "ocenaBtn3"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);

	}else if(id == "ocenaBtn4"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);
		$("#ocenaBtn4").attr("src", green);
	}else if(id == "ocenaBtn5"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);
		$("#ocenaBtn4").attr("src", green);
		$("#ocenaBtn5").attr("src", green);
	}
	// wpsianie oceny do pola formularza
	var oc = id.substring(id.length-1, id.length); 
	$("#ocena").val(oc);
	//alert(oc);
}
function resetStan()
{
	
	//alert(stan);
	var url = $("#url").html();
	var green = url+"circle.png";
	var gray = url+"circleGray.png";
	
	$("#ocenaBtn1").attr("src", gray);
	$("#ocenaBtn2").attr("src", gray);
	$("#ocenaBtn3").attr("src", gray);
	$("#ocenaBtn4").attr("src", gray);
	$("#ocenaBtn5").attr("src", gray);
	
	if(stan == "ocenaBtn1"){
		$("#ocenaBtn1").attr("src", green);
	}else if(stan == "ocenaBtn2"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
	}else if(stan == "ocenaBtn3"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);

	}else if(stan == "ocenaBtn4"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);
		$("#ocenaBtn4").attr("src", green);
	}else if(stan == "ocenaBtn5"){
		$("#ocenaBtn1").attr("src", green);
		$("#ocenaBtn2").attr("src", green);
		$("#ocenaBtn3").attr("src", green);
		$("#ocenaBtn4").attr("src", green);
		$("#ocenaBtn5").attr("src", green);
	}
}
function sprawdz()
{
	var ocena = $("#ocena").val();
	var kom = $.trim( $("#komentarz").val() );
	
	if(ocena < 1 || kom == "")
	{
		alert(" Poprawnie wypełnij pola! ");
		return false;
	}
	
	return true;
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
				<a class="categorieLeft" href="<c:url value='/mojekonto/modyfikujKonto/' />">Modyfikuj kontot</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wystawKomentarz/' />">Wystaw komentarz</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/otrzymaneKomentarze/' />">Otrzymane Komentarze</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wystawioneKomentarze/' />">Wystawione komentarze</a>				
			</div>
			<div id='main-right' align="center">
				
						<a class="greenSelect" style="color: black; text-decoration: none; border: 0px;" href="${pageContext.request.contextPath}/produkty/${element.id}/">
						<div  class="divProd" style=" width: 760px; height: 110px;border-bottom: 1px solid #DFDFDF; ">
							<div style="padding-top: 10px; margin-bottom: 10px; font-weight: bold;">Ocena dotyczy produktu:</div>
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
									<div style="padding: 10px; float:left;  width: 420px; height: 30px;">
										
										<span style=" font-size: 12pt;">${element.nazwa}</span>
										
										
									</div>
									<div style="text-align:right; padding: 10px; float:left;  width: 180px; height: 30px;">
										<span style="font-size: 12pt; font-weight: bold;">${element.cena} zł</span>
										
										<c:choose>
											<c:when test="${czyKupTeraz}">
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
						<div  class="divProd" style=" width: 760px; height: 80px;border-bottom: 1px solid #DFDFDF; ">
							<div style="padding-top: 10px; margin-bottom: 10px; font-weight: bold;">Sprzedający:</div>
							<div style="float:left; width: 220px;"> ${wlas.login} </div>
							<div style="float:left; width: 220px;"> ${wlas.email} </div>
							<div style="float:left; width: 300px;"> ${wlas.adres.miejscowosc}, ${wlas.adres.kod_pocztowy}</div>
						</div>
						<div class="divProd" style=" width: 760px; height: 270px;border-bottom: 1px solid #DFDFDF; margin-top: 20px;">
							 <div align="center">
							 	<span style="color: red; margin-right: 20px;">negatywnie</span>
							 	
							 		<img id="ocenaBtn1" onmouseout="resetStan();" onmouseover="selectbtnOcena(this);" onmousedown="changeOcena(this);" style="width: 25px; " src="<c:url value='/resources/images/circleGray.png' />" />
							 		<img id="ocenaBtn2" onmouseout="resetStan();" onmouseover="selectbtnOcena(this);" onmousedown="changeOcena(this);" style="width: 25px; " src="<c:url value='/resources/images/circleGray.png' />" />
							 		<img id="ocenaBtn3" onmouseout="resetStan();" onmouseover="selectbtnOcena(this);" onmousedown="changeOcena(this);" style="width: 25px; " src="<c:url value='/resources/images/circleGray.png' />" />
							 		<img id="ocenaBtn4" onmouseout="resetStan();" onmouseover="selectbtnOcena(this);" onmousedown="changeOcena(this);" style="width: 25px; " src="<c:url value='/resources/images/circleGray.png' />" />
							 		<img id="ocenaBtn5" onmouseout="resetStan();" onmouseover="selectbtnOcena(this);" onmousedown="changeOcena(this);" style="width: 25px; " src="<c:url value='/resources/images/circleGray.png' />" />
							 	
							 	<span id="url" style="display: none;"> <c:url value='/resources/images/' /> </span>
							 	<span style="color: #8AC74A; margin-left: 20px;">pozytywnie</span>
							 	</br>
							 	</br>
							 </div>
							 <form action="/jez/komentarze/zapiszKomentarz/" method="POST" onsubmit="return sprawdz();">
								<input type="hidden" name="ocena" id="ocena" value="0" />
								<input type="hidden" name="idProduktu" id="idProduktu" value="${element.id}" />
								<textarea name="komentarz" id="komentarz" maxlength="250" rows="10" cols="50" ></textarea>
								</br>
								<input style="margin-left: 0px;" class="sub" type="submit" value="Dodaj Komentarz" />
							</form>
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
