package com.cebul.jez.useful;

import org.springframework.stereotype.Component;

/**
 * reprezentuje checkboxy z widoku klienta
 * uzywany do przekazywania informacji od klienta do serwera o zdjeciach
 * które maja zostać usuniete z bazy
 * @author Mateusz
 *
 */
public class CheckboxZdj 
{

	private Integer checkboxs[];

	public Integer[] getCheckboxs() {
		return checkboxs;
	}

	public void setCheckboxs(Integer[] checkboxs) {
		this.checkboxs = checkboxs;
	}

	
	
}
