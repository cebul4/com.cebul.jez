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

$( window ).load(function() {
	var h = $("#main-right").outerHeight(true);
	 $("#main").css("height", h+20);
	});
function ladujPodkategorie()
{
	var val = $('#kategoria').val();
		
	$.getJSON( "/jez/dodajProdukt/podkategorie.json", {"kategory": parseInt(val)})
		.done(function( json ) {
		    
		    //var resp = "<option value='0'>WYBIERZ PODKATEGORIE</option>";
		    var resp = "";
		    $.each(json.kategorie, function( index, value ) {
		     	resp += '<option value="'+value.id+'">'+value.nazwa+'</option>';
		    	});
		    
		    $('#podkategoria').html("");
		    $('#podkategoria').html(resp);
		    
		  })
		 .fail(function( jqxhr, textStatus, error ) {
		   alert("error="+error);
		 }); 
	
	
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
						 <td>
                                                        <div style="border-left: 1px solid black; padding-left: 10px; ">
                                                                <a href="<c:url value='/kontakt' />" style="font-weight:bold; font-size: 16px;;text-decoration: none; border: none; color: black;">KONTAKT</a>
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
				<sf:form modelAttribute="produkt" action="/jez/produkty/updateProdukt/" method="POST">
					<span style="font-size: 12pt; display: block; color: gray; margin-bottom: 5px;">${path}</span>
					
						<div style='margin-right: 20px; margin-right: 10px; float: left; padding: 5px; border: 1px solid gray; height: 200px;'>
								<c:choose>
								<c:when test="${!empty produkt.zdjecie}">
										<img style="height: 200px;" src="${pageContext.request.contextPath}/prodimag/${produkt.zdjecie.id}" />
								</c:when>
									<c:otherwise>
										<img style="width: 200px; height: 200px;" src="<c:url value='/resources/images/unknownItem.png' />" />
									</c:otherwise>
								</c:choose>
						</div>
						
						</br>
						<sf:input type="hidden" path="id" value="${produkt.id}" />
						<sf:input type="hidden" path="cena" value="${produkt.cena}" />
						<sf:input type="hidden" path="dataDodania" value="${produkt.dataDodania}" />
						<div style='padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Nazwa: </span> <sf:input path="nazwa" value="${produkt.nazwa}" /></div>
						
						</br>
						<div style='padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Cena: </span> <span id="cena">${produkt.cena}</span> zł </div>
						</br>
						<div style='padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Sprzedający:</span> <span>${produkt.user.login} </span></div>
						</br>
						<div style='padding: 3px;'>
							
								<c:choose>
									<c:when test="${!czySprzedane}">
										
									</c:when>
									<c:otherwise>
										<div>
											<span style="color: red; font-weight: bold;">Produkt został sprzedany</span>
										</div>
									</c:otherwise>
								</c:choose>
						</div>
						
				
				</div>
				<div>
					<select id="kategoria" onchange='ladujPodkategorie();' >
					<c:forEach items="${kategoryList}" var="element"> 
						<c:choose>
							<c:when test="${element.id == katGlow}">
								<option value="${element.id}" selected="selected">
									${element.nazwa}
								</option>
							</c:when>
							<c:otherwise>
								<option value="${element.id}" >
									${element.nazwa}
								</option>
							</c:otherwise>
						</c:choose>
										
					</c:forEach>
					</select>
					<sf:select name='podkategoria' id='podkategoria' path='kategorie.id'>
					<c:forEach items="${podkategorie}" var="element"> 
					<c:choose>
							<c:when test="${element.id == podKat}">
								<option value="${element.id}" selected="selected">
									${element.nazwa}
								</option>
							</c:when>
							<c:otherwise>
								<option value="${element.id}" >
									${element.nazwa}
								</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					</sf:select>
					
				</div>
				<div style='display:block; margin-left: 75px; padding: 3px;'><span style="font-size: 14pt; color: #8AC74A;">Opis: </span> </br>
				<sf:textarea cols="50" rows="10" path="opis" value="${produkt.opis}" />
				</div>
				</br>
				<input style="margin-left: 80px;" class="sub" type="submit" value="Zapisz zmiany" />
				
				</sf:form>
				<div style="display:block; margin-left: 75px; margin-top: 30px;">
					<span style="font-size: 14pt; color: #8AC74A; display:block;" >Zdjecia produktu:</span></br>
				</div>
				<div style="display:block; margin-left: 75px; margin-top: 30px;" >
					<sf:form modelAttribute="check" action="/jez/produkty/updateProdukt/usunZdjecie/" method="POST">
					<input type="hidden" name="idProd" value="${produkt.id}" />
					<table>
					<tr>
						<td>
							
						</td>
						<td>
							
						</td>
					</tr>
					<c:choose>
						<c:when test="${!empty zdjecia}">
								<c:forEach items="${zdjecia}" var="zdj"> 
									<tr>
										<td>
											<sf:checkbox path="checkboxs" style="width: 50px;"  value="${zdj}" />
										</td>
										<td>
											<img style="margin: 5px;  height:200px;" src="${pageContext.request.contextPath}/prodimag/${zdj}" />
										</td>
									
									</tr>
								</c:forEach>
						</c:when>
						<c:otherwise>
								<span style="margin-left: 20px; font-size: 14pt;" >brak zdjęć produktu</span>
						</c:otherwise>
					</c:choose>
					
					</table>
					<input class="sub" style="margin-left: 0px; margin-top: 5px; width: 200px;" type="submit" value="Usuń wybrane zdjęcia" />
					</sf:form>
				</div>
				<div></div>
				<div style="margin-left: 75px; margin-top: 5px;" >
				<p style="display: block; font-size: 14pt; margin-top: 30px; color: #8AC74A;" >Wbybierz zdjecie produktu: </p>
					<table class='casualTab' style='margin-top: 20px;'>
						<sf:form  enctype="multipart/form-data" action="/jez/produkty/edytuj/dodajZdjecie/">
							<input type="hidden" id="produktId" name="produktId" value="${produkt.id}" />
							<tr>
								<td style="font-weight: bold;">Plik: </td>
								<td>
									<input  name="image" type="file" />
								</td>
							</tr>
											
							<tr>
								<td cols="2"><input class="sub" style='margin: 0px;' type="submit" value="Dodaj Zdjecie" /></td>
							</tr>
							
						</sf:form>
					</table>
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
