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

function showPodkategorie(ob)
{
	var str = ob.id;
	var res = str.substr(3);
	var i = parseInt(res);
	
	$.getJSON( "/jez/szukaj/podkat.json", {"katId": i})
	.done(function( json ) {
	   // alert("sakjdlkasjla");
	    
		var resp = "<span onclick='hidePodKat();' style='margin-left: 650px;margin-top: 10px; cursor: pointer; color: #8AC74A; font-size: 30pt; font-weight: bold;'> [X] </span>";
		resp += "<span style='margin-left: 100px; font-size: 16pt; color: gray;' >Dostepne podkategorie:</span>";
		resp += "<ul style='margin-left: 120px;'>";
		$.each(json.kategorie, function( index, value ) {
	    	
	    	//alert( index + ": " + value );
	    	var link = "/jez/szukaj/szukajProd/?podkat="+value.id+"&_podkat=on";
	    	resp += '<li><a style="color: gray; text-decoration: none;" href="'+link+'">'+value.nazwa+'</a></li>';
	    	});
		
		resp += "</ul>";
	    
	  // alert(resp);
	  	var ob = $('#main-right');
		var p = ob.position();
		var left = p.left+20;
		var top = p.top+20;
		
	   $("#podKatDiv").html(resp);
	   $('#podKatDiv').css("top", top).css("left", left);
	   $("#podKatDiv").show(500);
	  })
	 .fail(function( jqxhr, textStatus, error ) {
	   alert("error="+error);
	 }); 
	
}
function hidePodKat()
{
	$("#podKatDiv").hide(300);	
}
function highlite(ob)
{
	var str = ob.id;
	$("#"+str).css("background-color", "#8AC74A");
	$("#"+str).css("background-image", "linear-gradient(#8AC74A 0%, #578921 100%");
	
	$("#"+str).css("cursor", "pointer");
	
}
function nothighlite(ob)
{
	var str = ob.id;
	$("#"+str).css("background-color", "white");
	$("#"+str).css("background-image", "linear-gradient(#FFFFFF 0%, #FFFFFF 100%");
	$("#"+str).css("cursor", "auto");
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
						       <c:choose> 
						        <c:when test="${sessionScope.sessionUser.ranga == 'admin'}">
						        <span style='display: block; margin-top: 20px; margin-left: 120px;'>Witaj: <span style='color: blue; font-size: 18px;'>${sessionScope.sessionUser.getLogin()}</span></span>
						        </c:when>
						        <c:otherwise>
							  	<span style='display: block; margin-top: 20px; margin-left: 120px;'>Witaj: <span style='color: red; font-size: 18px;'>${sessionScope.sessionUser.getLogin()}</span></span>
							  	</c:otherwise>
							   </c:choose>
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
			<c:forEach items="${kategoryList}" var="element"> 
				<span  id="kat${element.id}"  class="categorieLeft" onmouseOver="highlite(this);" onmouseout="nothighlite(this);" onclick="showPodkategorie(this)">
					${element.nazwa}
				</span>
				
			</c:forEach>
								
			</div>
			<div id='main-right' align='center' style='padding-left: 10px;'>
				<div align='center' style='margin-left: 75px;'>
					<p style="font-size: 16pt; color: #8AC74A;" ><b>Ostatnio dodane produkty </b></p>
						<c:forEach items="${lastFourItems}" var="element"> 
							<a class='produktLink' href="<c:url value='/produkty/${element.id}/' />">
								<div style='display: block; float: left; padding: 5px; width: 190px; height: 190px;'>
									<div style="height: 150px;">
									<c:choose>
										<c:when test="${!empty element.zdjecie}">
											<img style="max-width: 200px; max-height: 150px;" src="${pageContext.request.contextPath}/imag/${element.id}" />
										</c:when>
										<c:otherwise>
											<img style="max-width: 200px; max-height: 150px;" src="<c:url value='/resources/images/unknownItem.png' />" />
										</c:otherwise>
									</c:choose>
									</div>
									<div style='padding: 3px;'><span>${element.nazwa}</span></div>
									<div style='padding: 3px;'><span>${element.cena} zł</span></div>
								</div>
							</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<div id='bottom'>
			<span align="center" style="color: #578921; display: block;"><b>Copyright Ⓒ Cebul & Jeżyk</b></span>
			
		</div>
	</div>
	<div id='podpowiedzi' >
		
	</div>
	<div id="podKatDiv">
		
	</div>
</body>
</html>
