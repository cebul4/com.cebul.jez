package com.cebul.jez.useful;

/**
 * reprezentuje checkboxy ze strony filtrowania wyników wyszukiwania
 *uzywany do przekazywania informacji od klienta do serwera o podkategoriach 
 *oraz opcjach "licytuj" i  "kup teraz" które mają posłużyc do filtrowania wyników wyszukiwania
 * @author Mateusz
 *
 */
public class CheckBoxLicKup 
{
	private String kupLicyt[];
	private Integer podkat[];
	
	
	public String[] getKupLicyt() {
		return kupLicyt;
	}
	public void setKupLicyt(String[] kupLicyt) {
		this.kupLicyt = kupLicyt;
	}
	public Integer[] getPodkat() {
		return podkat;
	}
	public void setPodkat(Integer[] podkat) {
		this.podkat = podkat;
	}

}
