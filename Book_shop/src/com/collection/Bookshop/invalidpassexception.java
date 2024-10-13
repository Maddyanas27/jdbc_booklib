package com.collection.Bookshop;

public class invalidpassexception extends Exception {
	String send;
	 public invalidpassexception(String send)
	 {
		 this.send=send;
		 System.out.print("invalid password\n");
	 }
	@Override
	public String toString() {
		return "invalidpassexception [send=" + send + "]";
	}
	 

}
