package com.cebul.jez.useful;

import java.io.Serializable;

public class PieElement implements Serializable
{
	private String label;
	private Integer iloscWystapien;
	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getIloscWystapien() {
		return iloscWystapien;
	}

	public void setIloscWystapien(Integer iloscWystapien) {
		this.iloscWystapien = iloscWystapien;
	}
	@Override
	public boolean equals(Object o) {
		
		if(o == null)
	        return false;

	    if(!(o instanceof PieElement))
	        return false;
	    
	    PieElement p = (PieElement) o;
	    
	    if(! getLabel().equals(p.getLabel() ) )
	    		return false;
	    
	    return true;
	}
	
	 @Override
	 public int hashCode() {
	    int hash = 3;
	    hash = 47 * hash + getLabel().hashCode();
	    return hash;
	}
	
}
