package br.com.lelak.teste.util;

abstract public class Validator {
	
	public static boolean isEmpty(String string){
		return string == null || !string.trim().isEmpty();
	}
}
