package com.ij.vendingmachina.dao;

import java.util.List;

import com.ij.vendingmachina.dto.Items;

public interface VendingMachinaDao {

	/**
     * Adds an item to the vending machine. Returns null if the item associated 
     * with this selection code matches, otherwise returns the item that is 
     * being replaced.
     * 
     * @param selectionCode, the vending machine location for the given item.
     * @param item, the item that will be added to the vending machine.
     * @return null if the associated item matches the one provided, otherwise
     * the item that is being replaced.
     */
    Items addItem(String orderNumber, Items item) throws VendingMachinaPersistenceException;
    
    
    /**
     * Removes the selected item from the vending machine. Returns the item 
     * being removed otherwise returns null if there is no item matching  the
     * selection code.
     * 
     * @param selectionCode, the vending machine location for the given item.
     * @param item, the item that will be removed.
     * @return 
     */
    Items removeItem(String orderNumber, Items item) throws VendingMachinaPersistenceException;
    
    
    /**
     * Gets an item associated with the given selection code.
     * 
     * @param selectionCode, the vending machine location for the given item.
     * @return an Items object that is associated with selection code, otherwise
     * null.
     */
    Items getItem(String orderNumber) throws VendingMachinaPersistenceException;
    
    
    /**
     * Obtains and returns a list of all items within the vending machine.
     * 
     * @return a list of all items in the vending machine.
     */
    List<Items> getAllItems() throws VendingMachinaPersistenceException;
    
    
    /**
     * Increments the stock field of the item associated with selection code. 
     * 
     * @param selectionCode, the code associated with the item whose stock is 
     * going to be incremented.
     * @return the item object whose stock has been incremented.
     */
    Items increaseItemInventory(String orderNumber) throws VendingMachinaPersistenceException;
    
    
    /**
     * Decrements the stock field of the item associated with the provided 
     * selection code. Throws error if the stock is already zero.
     * 
     * @param selectionCode, the code associated with the item whose stock is
     * going to be decremented.
     * @return the item object whose stock has been decremented.
     */
    Items decreaseItemInventory(String orderNumber) throws VendingMachinaPersistenceException;
}
