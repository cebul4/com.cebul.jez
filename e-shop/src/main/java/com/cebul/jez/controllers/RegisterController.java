package com.cebul.jez.controllers;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cebul.jez.entity.User;
import com.cebul.jez.service.UserService;
import com.cebul.jez.useful.Mail;

/**
 * 
 * @author Mateusz
  *	Klasa działa jako kontroler w modelu MVC
 *	używa mechanizmu DI do wstrzykiwania zależnosći
 *	obsługuje żądania z ścieżki "/rejestracja/*"
 */
@Controller
public class RegisterController 
{

	@Autowired
	private UserService userService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private Mail mail;
	
	
	/**
	 * obługuje żądanie dostępu do "/rejestracja" 
	 * bez paramterów typu GET lub POST przesyłanych podczas żadania
	 * @param model zawiera referencje do obiektu modelu
	 * @param session zawiera referencję do obiektu sesji
	 * @return zwraca logiczną nazwę widoku
	 */
	@RequestMapping(value = "/rejestracja")
	public String regForm(Model model,  HttpSession session) 
	{
		if(isUserLogged(session))
			return "redirect:/home";
		
		model.addAttribute(new User());
		return "rejestracja";
	}
	/**
	 * obługuje żądanie dostępu do "/rejestracja/active"
	 * w przypadku wystąpienia parametrów GET,
	 *  odpowiada za aktywację konta uzytkownika
	 * @param model zawiera referencje do obiektu modelu
	 * @param userId określa numer identyfikacyjny użytkownika, na podstawie którego następuje aktywacja określonego konta
	 * @param session zawiera referencję do obiektu sesji
	 * @return zwraca logiczną nazwe widoku
	 */
	@RequestMapping(value = "/rejestracja/active", method=RequestMethod.GET)
	public String confirmReg(Model model, @RequestParam Integer userId,  HttpSession session) 
	{
		if(isUserLogged(session))
			return "redirect:/home";
		
		userService.activeUser(userId);
		return "confirmReg";
	}
	/**
	 * * obługuje żądanie dostępu do "/rejestracja/active" w przypadku wystąpnienia parametrów POST
	 * 	poprawne wykonanie metody skutkuje dodaniem użytkownika do bazy i wysłaniem maila na adres podany przy 
	 * 	rejestracji, celem weryfikacji tożsamosci
	 * @param user okiekt użytkownika który ma zostać poddany walidacji. W przypadku powodzenia procesu walidacji
	 * 				następuje dodanie użytkownika do bazdy danych
	 * @param bindingResult za pomocą tego obiektu możemy sprawdzić poprawność danych podpietych do obiektu User
	 * @param model zawiera referencje do obiektu model
	 * @param request zawiera referencję do obiektu HttpServletRequest
	 * @param session zawiera referencję do obiektu sesji
	 * @return zwraca logiczną nazwę widoku, w zależności od powodzenia lub niepowodzenia operacji
	 * 					zwracany jest inny widok
	 * @throws Exception wyrzuca wyjątek w momencie gdy wysyłanie maila na adres podany przy rejetsracji się nie powiedzie
	 */
	@RequestMapping(value="/rejestracja", method=RequestMethod.POST)
	public String addUserForForm(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request,  HttpSession session) throws Exception
	{
		if(isUserLogged(session))
			return "redirect:/home";
		
		if(bindingResult.hasErrors())
		{
			//System.out.println("hasło has error: "+user.getPass());
			return "/rejestracja";
		}
		//System.out.println("hasło: "+user.getPass());
		boolean itsDone = userService.addUser(user);
		if(!itsDone)
		{
			return "/rejestracja";
		}
		
		// jesli wszytsko jest ok to wyslij maila
		try {
			String [] t = {user.getEmail()};
			String str = request.getScheme() +"://"+ request.getServerName()+ ":"+
					request.getServerPort() +request.getContextPath()+
					"/rejestracja/active?userId="+user.getId(); 
			mail.sendMimeMessage(str,"eshoptest@wp.pl", t, "Rejestracja e-shop", "takie sobie cos tam");
		
			// ustawienie sessji
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "redirect:/home/";
	}
	/**
	 * odpowiada za obsługę aynchronicznego żądania do danych. 
	 * Sprawdza czy login jaki wpisał użytkownik podczas procesu rejetsracji jest wolny 
	 * @param username login który wymaga sprawdzenia
	 * @return zwraca true jeśli podany login jest wolny, false gdy jest zajety
	 */
	@RequestMapping(value="/register/ajax.do", method=RequestMethod.POST)
	public @ResponseBody String chenckLogin(@RequestParam String username)
	{
		String response = "";
		boolean isExsistUser = userService.isUserExsist(username);
		if(isExsistUser)
		{
			response = "Taki użytkonik już istnieje!";
		}
		return response;
	}
	/**
	 * sprawdza czy użytkownik jest zalogowany w systemie
	 * @param session zawiera referencję do obiektu sesji
	 * @return zwraca tru jeśli użytkownik jest zalogowany lub false gdy nie jest
	 */
	public boolean isUserLogged(HttpSession session)
	{
		User u = (User) session.getAttribute("sessionUser");
		if(u != null)
			return true;
		return false;
	}
	
}
