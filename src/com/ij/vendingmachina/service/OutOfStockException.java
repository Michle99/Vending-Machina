package com.ij.vendingmachina.service;

public class OutOfStockException extends Exception{

	public  OutOfStockException(String msg){
        super(msg);
    }
}
