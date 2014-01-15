package com.cebul.jez.useful;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cebul.jez.entity.Hist_Wyszuk;
import com.cebul.jez.entity.Kategoria;
import com.cebul.jez.entity.Produkty;

public class JsonProd implements Serializable{
	
	private PieElement historia[];

	
	public PieElement[] getHistoria() {
		return historia;
	}


	public void setHistoria(PieElement[] historia) {
		this.historia = historia;
	}


	public void generateHistoria(List<Produkty> hist)
	{
		List<PieElement> tmpList = new ArrayList<PieElement>();
		System.out.println("size = "+hist.size());
		for(Produkty p : hist )
		{
			PieElement pie = new PieElement();
			pie.setIloscWystapien(1);
			
			Kategoria kat = p.getKategorie();
			String nazwa;
			if(kat == null)
			{
				nazwa = "Inne";
			}else{
				nazwa = kat.getNazwa();	
			}
			
			pie.setLabel(nazwa);
			
			System.out.println("\t\t nazwa = "+nazwa);
			
			boolean zawiera = tmpList.contains(pie);
			
			System.out.println("zawiera = "+zawiera);
			if(zawiera)
			{
				int index = tmpList.indexOf(pie);
				int ilosc = tmpList.get(index).getIloscWystapien();
				tmpList.get(index).setIloscWystapien(ilosc+1);
				//System.out.println("ile ma elementow"  +tmpList.get(index).getIloscWystapien() );
				
			}else
			{
				tmpList.add(pie);
			}
			
		}
		System.out.println("size nowego = "+tmpList.size());
		
		historia = (PieElement[]) tmpList.toArray(new PieElement[tmpList.size()]);
		//return historia;
	}

}