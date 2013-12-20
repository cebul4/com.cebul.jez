package com.cebul.jez.flows;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cebul.jez.entity.DokumentZamowienia;
import com.cebul.jez.entity.Platnosc;
import com.cebul.jez.entity.Produkty;
import com.cebul.jez.entity.ProduktyKupTeraz;
import com.cebul.jez.entity.User;
import com.cebul.jez.entity.Zamowienie;
import com.cebul.jez.service.ProduktyService;
import com.cebul.jez.service.UserService;
import com.cebul.jez.service.ZamowienieService;
import com.cebul.jez.useful.ShoppingCart;

/**
 * główny obiekt uzywany w przepływie do wykonywania operacji na 
 * bazie oraz innych obiektach pomocniczych
 * 
 * uzywa mechanimu wstrzykiwania zalezności. Wstrzykiwane zależności to:
 * ShoppingCart, ZamowienieService, ProduktyService, UserService
 * @author Mateusz
 *
 */
@Component("koszykBeanFlow")
public class KoszykFlowBean 
{
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private ZamowienieService zamowienieService;
	
	@Autowired
	private ProduktyService produktyService;
	
	@Autowired
	private UserService userService;
	
	private List<Produkty> juzKupione;
	
	public KoszykFlowBean()
	{
		
	}
/**
 * pobiera wlasnosc juz kupione, która świadczy o tym czy w danym zamówieniu znajdują się przedmioty, 
 * które zostały już kupione przez jakiegoś użytkonwika
 * @return
 */
	public List<Produkty> getJuzKupione() {
		return juzKupione;
	}
/**
 * ustawia własnosc juzKupione
 * @param juzKupione 
 */
	public void setJuzKupione(List<Produkty> juzKupione) {
		this.juzKupione = juzKupione;
	}
/**
 * zwraca obiekt koszyka
 * @return obiekt koszyka
 */
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
/**
 * ustawia obiekt koszyka na podany jako paramter
 * @param shoppingCart obiekt koszyka
 */
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	/**
	 * zwraca listę przedmiotów z koszyka
	 * @return lista produktów
	 */
	public List<Produkty> getShoppingCartItems()
	{
		return shoppingCart.getItems();
	}
	/**
	 * sprawdza czy produkty znajdujące sie w koszyku są typu "Kup teraz"
	 * @return lista zawierajaca elementy true lub false. 
	 * element jest true jesli obiekt koszyka jest typu "kup teraz", w przeciwnym wypadku false
	 */ 
	public List<Boolean> getCzyKupTerazList()
	{
		List<Boolean> czyKupTeraz = new ArrayList<Boolean>();
		
		for(Produkty p : shoppingCart.getItems())
		{
			if(p instanceof ProduktyKupTeraz)
			{
				czyKupTeraz.add(true);
				//System.out.println("kup teraz");
			}
			else
				czyKupTeraz.add(false);
		}
		return czyKupTeraz;
	}
	/**
	 * sprawdz czy koszyk jest pusty
	 * @return true jeśli koszyk nie jest pusty false w przeciwnym przypadku
	 */
	public boolean isNotShooppingCartEmpty()
	{
		if(shoppingCart.getItems().size() > 0)
			return true;
		
		return false;
	}
	/**
	 * sprawdza czy uzytkownik jest zalogowany
	 * @param principal obiekt zawierajacy dane zalogowanyego użytkonwika
	 * @return treue jeśli użytkownik zalogowany w przeciwnym rpzypadku false
	 */
	public boolean isUserLoged(Object principal)
	{
		if(principal != null)
			return true;
		return false;
	}
	/**
	 * zwraca wszystkie mozliwe platnosci z bazy dancyh
	 * @return lista Platnosci
	 */
	public List<Platnosc> getPlatnosci()
	{
		return zamowienieService.getPlatnosci();
	}
	/**
	 * zwraca wszytskie możliwe dokumenty z bazy dancyh
	 * @return lista dokumentów
	 */
	public List<DokumentZamowienia> getDokumenty()
	{
		return zamowienieService.getDokumenty();
	}
	/**
	 * pobiera obiekt Platnosc na podstawie nazwy
	 * @param t obiekt tymczasowy zawierajacy nazwe
	 * @return obiekt Platnosc
	 */
	public Platnosc getObjPlatnosc(Tmp t)
	{
		
		//Platnosc p = zamowienieService.getPlatnoscLike(t.getPlatnosc());
		//System.out.println(p.getRodzajPlatnosci());
		return zamowienieService.getPlatnoscLike(t.getPlatnosc());
		
	}
	/**
	 * pobiera obiekt Dokument na podstawie nazwy
	 * @param t obiekt tymczasowy zawierający nazwę 
	 * @return obiekt Dokument
	 */
	public DokumentZamowienia getObjDokument(Tmp t)
	{
		//System.out.println(t.getDokument());
		return zamowienieService.getDokumentLike(t.getDokument());
	}
	/**
	 * pobiera obiekt użytkownika z bazy danych na podstawie przekazanego loginu
	 * @param s login użytkownika
	 * @return obiekt użytkownika (User)
	 */
	public User getUser(String s)
	{
		
		//System.out.println(s);
		return userService.getUser(s);
	}
	/**
	 * zwraca produkty z aktualnie znajdujace sie w koszyku
	 * @return produkty aktualnie znajdujace sie  w koszyku
	 */
	public List<Produkty> getItems()
	{
		return shoppingCart.getItems();
	}
	/**
	 * zwracja koszt produktw znajdujacych się w koszuku
	 * @return koszt produktów znajdujacych się w koszyku
	 */
	public Double getKoszt()
	{
		//System.out.println("koszt: "+shoppingCart.getSuma());
		return shoppingCart.getSuma();
	}
	/**
	 * Komepletuje zamówienie, sprawdza jego poprawność oraz 
	 * gdy wszytsko jest ok utrwala je w bazie dnaych
	 * w razie wystąpienia konfliktów proruktów w zamówieniu, 
	 * usówa produkty powodujace konflikt z koszyka
	 * 
	 * @param zamowienie zamówienie które ma zostać zapisane do bazy
	 */
	public void zapiszZamowienie(Zamowienie zamowienie)
	{
		zamowienie.setData(new Date());
		this.juzKupione = zamowienieService.zapiszZamowienie(zamowienie);
		if(juzKupione == null)
		{
			shoppingCart.clear();
		}else{
			//shoppingCart.getItems().removeAll(juzKupione);
			// zrobione w ten sposb poniewaz podczas usuwania rzuca wyjatkiem 
			//java.util.ConcurrentModificationException
			for(Produkty p : juzKupione)
			{
				shoppingCart.addToSum(-p.getCena());
			}
			List<Produkty> good = shoppingCart.getItems();
			good.removeAll(juzKupione);
			//System.out.println("good size = "+good.size());
			shoppingCart.setItems(good);
			//System.out.println("shopping cart size = "+shoppingCart.getItems().size());
			
		}
		
	}
	/**
	 * sprawdza czy transakcja się udała, czyli czy podczas zapisywania zamówienia nie wysąpiły
	 * problemy z produktami (czy ktoś nam ich nie podkupił w momencie gdy prdukt 
	 * był w naszym koszyku a my zastanawialismy sie czy ko kupić)
	 * @return zwraca true gdy transakcja przebiegła pomyślnie, false gdy wystąpił problem
	 */
	public boolean isTransactionOK()
	{
		if(juzKupione == null)
		{
			return true;
		}
		return false;
	}
	
}
