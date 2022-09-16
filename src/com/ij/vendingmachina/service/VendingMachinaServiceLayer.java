package com.ij.vendingmachina.service;

import java.math.BigDecimal;
import java.util.List;

import com.ij.vendingmachina.dao.VendingMachinaPersistenceException;
import com.ij.vendingmachina.dto.Items;

public interface VendingMachinaServiceLayer {

	public Items buyItem(String codeToSelect)  throws VendingMachinaPersistenceException, InsufficientFundsException, OutOfStockException;
    
    public int[] getCreditBalance()  throws VendingMachinaPersistenceException;
    
    public List<Items> purchaseItemsList()  throws VendingMachinaPersistenceException;
    
    public void addCreditToBalance(BigDecimal amount)  throws VendingMachinaPersistenceException;
    
    public BigDecimal checkCreditBalance()  throws VendingMachinaPersistenceException;
    
    public List<Items> getAllItems() throws VendingMachinaPersistenceException;
}
