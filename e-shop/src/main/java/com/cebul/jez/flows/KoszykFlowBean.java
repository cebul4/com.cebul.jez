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
	
	public KoszykFlowBean()
	{
		
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public List<Produkty> getShoppingCartItems()
	{
		return shoppingCart.getItems();
	}
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
	public boolean isNotShooppingCartEmpty()
	{
		if(shoppingCart.getItems().size() > 0)
			return true;
		
		return false;
	}
	public boolean isUserLoged(Object principal)
	{
		if(principal != null)
			return true;
		return false;
	}
	public List<Platnosc> getPlatnosci()
	{
		return zamowienieService.getPlatnosci();
	}
	public List<DokumentZamowienia> getDokumenty()
	{
		return zamowienieService.getDokumenty();
	}
	public Platnosc getObjPlatnosc(Tmp t)
	{
		
		//Platnosc p = zamowienieService.getPlatnoscLike(t.getPlatnosc());
		//System.out.println(p.getRodzajPlatnosci());
		return zamowienieService.getPlatnoscLike(t.getPlatnosc());
		
	}
	public DokumentZamowienia getObjDokument(Tmp t)
	{
		//System.out.println(t.getDokument());
		return zamowienieService.getDokumentLike(t.getDokument());
	}
	public User getUser(String s)
	{
		
		//System.out.println(s);
		return userService.getUser(s);
	}
	public List<Produkty> getItems()
	{
		return shoppingCart.getItems();
	}
	public Double getKoszt()
	{
		//System.out.println("koszt: "+shoppingCart.getSuma());
		return shoppingCart.getSuma();
	}
	public void zapiszZamowienie(Zamowienie zamowienie)
	{
		zamowienie.setData(new Date());
		zamowienieService.zapiszZamowienie(zamowienie);
		produktyService.setKupione(zamowienie.getProdukty());
		shoppingCart = new ShoppingCart();
	}
	
}
