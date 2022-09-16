package com.ij.vendingmachina.ui;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ij.vendingmachina.dto.Items;

public class VendingMachinaView {
    // UserIO declaration
    private UserIO io;
    
    // Constructor
    public VendingMachinaView(){}
    
    /**
     * Constructor for Dependency Injection
     * 
     * @param io 
     */
    public VendingMachinaView(UserIO io){
        this.io = io;
    }

    // Display, take, and return an integer for a selection from the menu
    public int getMenuSelection() {
        io.print("Main Menu");
        io.print("1. Add Credit");
        io.print("2. Buy an item");
        io.print("3. See what you can afford");
        io.print("4. Get current credit balance after purchase");
        io.print("5. View Credit Balance");
        io.print("6. EXIT");
        return io.readInt("Select one of the choices above:", 1, 6);
    }

    public void displayGetCreditBalanceBanner() {
        io.print("=== Getting Credit Balance ===");
    }

    public void displayAddCreditBanner() {
        io.print("=== Add Credit ===");
    }

    public void displayCurrentCreditBanner() {
        io.print("=== Current Credit Balance ===");
    }

    public String getCreditAdded() {
        return io.readString("Please enter payment amount.");
    }

    public void displayCreditAddedBanner() {
        io.print("=== Your funds have been added! ===");
    }

    public void displayBuyItemBanner() {
        io.print("=== Buy an Item ===");
    }

    public String getItemCode() {
        return io.readString("Please enter the code for the item to be purchased.");
    }

    public void displayItemListBanner() {
        io.print("=== Currently Affordable Items ===");
    }

    public void displayExitBanner() {
        io.print("=== Good Bye! ===");
    }

    public void displayUnknownCommandBanner() {
        io.print("=== Unknown Command ===");
    }

    public void displayEnjoyBanner() {
        io.print("=== Enjoy! ===");
    }

    public void displayInventoryBanner() {
        io.print("=== Inventory ===");
    }

    public void displaySuccessBanner() {
        io.print("=== Thank you! ===");
    }

    public void displayCreditBalAfterPurchase(int[] balAfterPurchase) {
        System.out.println("Your balance after purchase is...\n " + "Quarters: " + balAfterPurchase[0] + "\nDimes: " + balAfterPurchase[1] + "\nNickles: " + balAfterPurchase[2] + "\nPennies: " + balAfterPurchase[3]);
    }

    public void displayTotalBal(BigDecimal currentCredit) {
        System.out.println("Total change: " + currentCredit);
    }

    public void displayViewFunds(BigDecimal currentCredit) {
        System.out.println("Your current balance is: " + currentCredit);
    }

    public void displayItemList(List<Items> itemLists) {
        Collections.sort(itemLists, Comparator.comparing((item) -> item.getOrderNumber()));
        for (Items item : itemLists) {
            String itemInfo = String.format("#%s : %s - x%s - $%s",
                    item.getOrderNumber(),
                    item.getName(),
                    item.getInventory(),
                    item.getPrice());
            io.print(itemInfo);
        }
        io.readString("Please hit enter to continue.");
    }
}

