package br.com.lelak.teste.util;

import br.com.lelak.teste.exception.ExtensionNotSuportedRuntimeException;

public enum ExtensionEnum {
	PNG("image/png","89 50 4E 47 "), JPG("image/jpeg","FF D8 FF E0 "), GIF("image/gif","47 49 46 38 ");
	
	private String mimeType;
	private String magicNumber;
	
	ExtensionEnum(String mimeType, String magicNumber){
		this.mimeType = mimeType;
		this.magicNumber = magicNumber;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	public String getExtension(){
		return toString().toLowerCase();
	}
	
	public String putExtensionIn(String name){
		return name + "." + getExtension();
	}
	
	public static ExtensionEnum fromBytes(byte[] bytes){
		String magicNumber = extractMagicNumber(bytes);
		for (ExtensionEnum mime : values()) {
			if(mime.magicNumber.equals(magicNumber)){
				return mime;
			}
		}
		throw new ExtensionNotSuportedRuntimeException();
	}
	
	private static String extractMagicNumber(byte[] bytes){
		String hexadecimal = "";
		for (int i = 0; i < 4; i++) {
			hexadecimal += String.format("%02X ", bytes[i]);
		}
		return hexadecimal;
	}
}
