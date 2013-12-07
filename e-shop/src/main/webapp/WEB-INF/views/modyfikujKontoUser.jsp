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
	<link href="${pageContext.request.contextPath}/resources/css/reg.css" type="text/css" rel="stylesheet">
<script src="<c:url value='/resources/js/jquery.js' />" type="text/javascript" ></script>
<script src="<c:url value='/resources/js/mainJs.js' />" type="text/javascript" ></script>
<script>
$(function() 
{
	month();
	var d = $("#dzien");
	var dUser = parseInt($("#dzienUsera").html());
	$('[name=dzien]').val( dUser );
});
function month()
{
var m = $("#miesiac").val();
var r = $("#rok").val();
var d = $("#dzien");
var str = "";
str = "<option value='null'>dzień:</option>";
if(m != 2 && (m % 2 == 0))
{
for(var i=1; i<=31; i++)
{
str += "<option value='"+i+"'> "+i+"</option>";
}
d.html(str);
}else if(m ==2 && (r % 4 == 0)){
for(var i=1; i<=29; i++)
{
str += "<option value='"+i+"'> "+i+"</option>";
}
d.html(str);
}else if(m ==2){
for(var i=1; i<=28; i++)
{
str += "<option value='"+i+"'> "+i+"</option>";
}
d.html(str);
}else if(m % 2 == 1){
for(var i=1; i<=30; i++)
{
str += "<option value='"+i+"'> "+i+"</option>";
}
d.html(str);
}
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
				<a class="categorieLeft" href="<c:url value='/mojekonto/dodajProdukt' />">Dodaj Produkt</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/wystawioneProdukty/' />">Wystawione Produkty</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/sprzedaneProdukty/' />">Sprzedane Produkty</a>
				<a class="categorieLeft" href="<c:url value='/mojekonto/modyfikujKonto/' />">Modyfikuj kontot</a>
								
			</div>
			<div id='main-right' align="center">
				<sf:form method="POST" modelAttribute="user" action="/jez/mojekonto/modyfikujKonto/zapisz/">
					<fieldset style='border: none;'>
					<table id='tabReg'>
						<tr>
									<td>
										<label for="login">Login:</label>
									</td>
									<td>
										<sf:input  type="hidden" id="id" path="id"  />
										<input  disabled="true" value="${user.login}" />
										<sf:input  type="hidden" id="login" path="login"  />
										<sf:errors  path="login" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td>
										<label for="imie"> Imie:</label>
									</td>
									<td>
										<sf:input  id="imie" path="imie" />
										<sf:errors path="imie" cssClass="error" />
									</td>
									
								</tr>
								<tr>
									<td>
										<label for="nazwisko"> Nazwisko:</label>
									</td>
									<td>
										<sf:input  id="nazwisko" path="nazwisko" />
										<sf:errors path="nazwisko" cssClass="error" />
									</td>
									
								</tr>
								<tr>
									<td>
										<label for="email"> Email:</label>
									</td>
									<td>
										<input  disabled="true" value="${user.email }"  />
										<sf:input  type="hidden" id="email" path="email" />
										<sf:errors path="email" cssClass="error" />
									</td>
									
								</tr>
								<tr>
									<td>
										<label for="adres.miejscowosc"> Miejsce zamieszkania:</label>
									</td>
									<td>
										<sf:input id="miejscowosc" path="adres.miejscowosc" />
										<sf:errors path="adres.miejscowosc" cssClass="error" />
									</td>
									
								</tr>
								<tr>
									<td>
										<label for="adres.kod_pocztowy"> Kod Pocztowy:</label>
									</td>
									<td>
										<sf:input  id="kod_pocztowy" path="adres.kod_pocztowy" />
										<sf:errors path="adres.kod_pocztowy" cssClass="error" />
									</td>
									
								</tr>
								<tr>
									<td>
										<label for="plec"> Płeć:</label>
									</td>
									<td>
										
										<sf:select id="plec" path="plec">
											<sf:option value="null">wybież płeć:</sf:option>
											<sf:option value="m">mężczyzna</sf:option>
											<sf:option value="k">kobieta</sf:option>
										</sf:select>
										<sf:errors path="plec" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td>
										<label> Data urodzenia:</label>
									</td>
									<td>
										
										<sf:select id="rok" path="rok" onchange="month();">
											<sf:option value="">rok:</sf:option>
											<c:forEach var="j" begin="1960" end="2013">
												<sf:option value="${j}"></sf:option>
											</c:forEach>
										</sf:select>
										<sf:select id="miesiac" path="miesiac" onchange="month();">
											<sf:option value="">miesiąć:</sf:option>
											<c:forEach var="i" begin="1" end="12">
												<sf:option value="${i}"></sf:option>
											</c:forEach>
										</sf:select>
										
										<sf:select id="dzien" path="dzien">
											<sf:option value="">dzień:</sf:option>
											
										</sf:select>
										<sf:errors path="dzien" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input align='center' type="submit" value="Dalej" class="sub" id="sub" >
										<span id="dzienUsera" style="display: none;" >${user.dzien}</span>
									</td>
								</tr>
					</table>
					</fieldset>
				</sf:form>
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
