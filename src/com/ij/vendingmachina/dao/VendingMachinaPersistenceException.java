package com.ij.vendingmachina.dao;

public class VendingMachinaPersistenceException extends Exception {
	 /**
     * Custom exception for vending machine project.
     * 
     * @param message 
     */
    public VendingMachinaPersistenceException(String message) {
        super(message);
    }
    
    /**
     * Custom exception for vending machine project.
     * 
     * @param message
     * @param cause 
     */
    public VendingMachinaPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
