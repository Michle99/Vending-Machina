package com.ij.vendingmachina.service;

public class InsufficientFundsException extends Exception {

	public InsufficientFundsException(String msg){
        super(msg);
    }
}
