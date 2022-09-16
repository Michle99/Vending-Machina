package com.ij.vendingmachina.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.ij.vendingmachina.dao.VendingMachinaDao;
import com.ij.vendingmachina.dao.VendingMachinaDaoImpl;
import com.ij.vendingmachina.dao.VendingMachinaPersistenceException;
import com.ij.vendingmachina.dto.Items;
import com.ij.vendingmachina.service.InsufficientFundsException;
import com.ij.vendingmachina.service.OutOfStockException;
import com.ij.vendingmachina.service.VendingMachinaServiceLayer;
import com.ij.vendingmachina.service.VendingMachinaServiceLayerImpl;
import com.ij.vendingmachina.ui.VendingMachinaView;

public class VendingMachineController {
	
    private VendingMachinaView view;
    private VendingMachinaDao dao;
    private VendingMachinaServiceLayer service;

    // Constructors
    public VendingMachineController() throws VendingMachinaPersistenceException{
        view = new VendingMachinaView();
        dao = new VendingMachinaDaoImpl();
        service = new VendingMachinaServiceLayerImpl();
    }
    
    // Constructor for Dependency Injection
 
    public VendingMachineController(VendingMachinaServiceLayer service, VendingMachinaView view) {
        this.service = service;
        this.view = view;
    }
    
    
    // Function that controls program flow
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {

                listInventory(); //Begins with Inventor Banner and list of inventory every time

                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                    	addCredits();
                        break;
                    case 2:
                        buyItem();
                        break;
                    case 3:
                    	browseItemsLists();
                        break;
                    case 4:
                    	getCreditBalance();
                        break;
                    case 5:
                    	viewCredits();
                        break;
                    case 6:
                    	getCreditBalance();
                        keepGoing = false;
                        exitMessage();
                        break;
                    default:
                        unknownCommand();
                }
            }
        } catch (VendingMachinaPersistenceException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException in) {
            System.out.println("Sorry, you have insufficient funds for this item.");
            run();
        } catch (OutOfStockException st) {
            System.out.println("Sorry, this item is currently out of stock.");
            run();
        }
    }

    private void listInventory() throws VendingMachinaPersistenceException {
        view.displayInventoryBanner();
        List<Items> allItems = service.getAllItems();
        view.displayItemList(allItems);
    }

    private void addCredits() throws VendingMachinaPersistenceException {
        view.displayAddCreditBanner();
        String creditAmount = view.getCreditAdded();
        BigDecimal creditBigDecimal = new BigDecimal(creditAmount).setScale(2, RoundingMode.HALF_UP);
        service.addCreditToBalance(creditBigDecimal);
        view.displayCreditAddedBanner();
    }

    private void buyItem() throws VendingMachinaPersistenceException, InsufficientFundsException, OutOfStockException {
        view.displayBuyItemBanner();
        String itemCode = view.getItemCode();
        service.buyItem(itemCode);
        view.displayEnjoyBanner();
    }

    private void browseItemsLists() throws VendingMachinaPersistenceException {
        view.displayItemListBanner();
        view.displayItemList(service.purchaseItemsList());        
    }

    private void getCreditBalance() throws VendingMachinaPersistenceException {
        view.displayGetCreditBalanceBanner();
        BigDecimal currentCredit = service.checkCreditBalance();
        int[] change = service.getCreditBalance();
        view.displayCreditBalAfterPurchase(change);
        view.displayTotalBal(currentCredit);
        view.displaySuccessBanner();
    }

    private void viewCredits() throws VendingMachinaPersistenceException {
        view.displayCurrentCreditBanner();
        BigDecimal currentCredit = service.checkCreditBalance();
        view.displayViewFunds(currentCredit);
    }

    // Let's user know they've exited
    private void exitMessage() {
        view.displayExitBanner();
    }

    // Prompts user for a known command
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    // Requests view to display the menu
    private int getMenuSelection() {
        return view.getMenuSelection();
    }

}
