package com.ij.vendingmachina.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import com.ij.vendingmachina.dao.VendingMachinaAuditDao;
import com.ij.vendingmachina.dao.VendingMachinaDao;
import com.ij.vendingmachina.dao.VendingMachinaDaoImpl;
import com.ij.vendingmachina.dao.VendingMachinaPersistenceException;
import com.ij.vendingmachina.dto.Items;

public class VendingMachinaServiceLayerImpl implements VendingMachinaServiceLayer {

	enum Coins {
        QUARTER(25),
        DIME(10),
        NICKLE(5),
        PENNY(1);

        private int value;

        Coins(int amount) {
            value = amount;
        }

        int getValue() {
            return value;
        }

    }
    
    // Declare Dao, Audit Dao
    private VendingMachinaDao dao;
    private VendingMachinaAuditDao audit;

    //Dao and local variables
    private BigDecimal credit = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
    private BigDecimal zero = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);

    // constructors
    public VendingMachinaServiceLayerImpl() throws VendingMachinaPersistenceException {
        dao = new VendingMachinaDaoImpl();
    }
    
    /**
     * Constructor for Dependency Injection
     */
    
    public VendingMachinaServiceLayerImpl(VendingMachinaDao dao, VendingMachinaAuditDao auditDao) {
        this.dao = dao;
        this.audit = auditDao;
    }

    /*
        This method takes in a code and will check if it is a valid code.
        If the code is invalid a null value is returned. If the cod esi valid
        then we will see if the item is purchaseable. If it is not then an exception will be
        thrown. If it is purchaseable it will decrement the available stock and update the 
        customer's credit. If the purchase is complete, we will write out to a log.
     */
    @Override
    public Items buyItem(String codeToSelect) throws VendingMachinaPersistenceException, InsufficientFundsException, OutOfStockException {

        Items item = dao.getItem(codeToSelect);

        if (item == null) {
            return null;
        }

        if (item.getInventory() < 1) {
            throw new OutOfStockException("Out of stock");
        }

        BigDecimal newBalance = credit.subtract(item.getPrice().setScale(2, RoundingMode.HALF_UP));
        

        if (newBalance.compareTo(zero) != -1) {
            item = dao.decreaseItemInventory(codeToSelect);
            credit = newBalance;
            audit.writeAuditEntry("Purchased: " + item.getName() + "|| Stock: " + item.getInventory());
            return item;
        }

        throw new InsufficientFundsException("Not enough funds");
    }

    /*
        This method will return the amount of coins to give to the user.
        The return value is an int[] where int[0] is quarters followed by
        dimes, nickles and pennies in order.
     */
    @Override
    public int[] getCreditBalance() throws VendingMachinaPersistenceException {
        
        int[] coins = new int[4];

        credit = credit.multiply(new BigDecimal("100"));
        int total = credit.intValue();

        int quarters = total / Coins.QUARTER.value;
        total = total % Coins.QUARTER.value;
        coins[0] = quarters;

        int dimes = total / Coins.DIME.value;
        total = total % Coins.DIME.value;
        coins[1] = dimes;
        
        int nickles = total / Coins.NICKLE.value;
        total = total % Coins.NICKLE.value;
        coins[2] = nickles;

        int pennies = total / Coins.PENNY.value;
        total = total % Coins.PENNY.value;
        coins[3] = pennies;

        credit = new BigDecimal("0.00");

       return coins;

    }

    /*
        This will display all the items the user can purchase with their current
        balance.
     */
    @Override
    public List<Items> purchaseItemsList() throws VendingMachinaPersistenceException {

        return dao.getAllItems().stream().filter((item) -> item.getPrice().compareTo(credit) != 1).collect(Collectors.toList());

    }

    /*
        this method allows the user to add funds to the vending machine.
        negative values are not accepted, nothing happens if a negative value is passed in.
     */
    @Override
    public void addCreditToBalance(BigDecimal amount) throws VendingMachinaPersistenceException {

        if (amount.compareTo(zero) == 1) {
            credit = credit.add(amount);
        }

    }

    /*
        this method returns the ammount of credit the user has
     */
    @Override
    public BigDecimal checkCreditBalance() throws VendingMachinaPersistenceException {

        return credit;

    }

    /*
        this will return the available items in the vending machine.
     */
    @Override
    public List<Items> getAllItems() throws VendingMachinaPersistenceException {

        return dao.getAllItems();

    }
}
