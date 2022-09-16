package test.service;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ij.vendingmachina.dao.VendingMachinaDaoImpl;
import com.ij.vendingmachina.service.VendingMachinaServiceLayer;
import com.ij.vendingmachina.service.VendingMachinaServiceLayerImpl;

import com.ij.vendingmachina.dao.*;
import com.ij.vendingmachina.dto.*;


public class VendingMachinaServiceLayerImplTest {

    private VendingMachinaDaoImpl dao;
    private VendingMachinaServiceLayer service;

    public VendingMachinaServiceLayerImplTest() {
    }

    @BeforeEach
    public void setUp() {

        try {
            dao = new VendingMachinaDaoImpl("testFile.txt");
            service = new VendingMachinaServiceLayerImpl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddCredit() {

        try {
            BigDecimal five = new BigDecimal("5.00");
            service.addCreditToBalance(five);
            assertEquals(service.checkCreditBalance(), five, "Checking the added amount worked");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBuyItem() {
        try {

            // start with 5.00
            BigDecimal value = new BigDecimal("5.0");

            service.addCreditToBalance(value);

            Items item = dao.getItem("A1");
            BigDecimal cost = item.getPrice();

            Items boughtItem = service.buyItem(item.getOrderNumber());

            assertNotNull(boughtItem, "The item should not be null.");

            // 2.99 for A1, val = (5.00 - 2.99) = 2.01
            value = value.subtract(boughtItem.getPrice());
            assertTrue(value.equals(service.checkCreditBalance()), "Value and credit should both be 2.01.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testAvailableItems() {
        try {
            //set credit to 5.00
            service.addCreditToBalance(new BigDecimal("25.00"));

            //get the items available
            List<Items> available = service.purchaseItemsList();
            System.out.println("List of items: " +available.size());

            assertTrue(available.size() == 8, "12 items should be purchaseable with a starting amount of 5.00");

            //reset credit
            service.getCreditBalance();
            
            
            //set credit to 1.00
            service.addCreditToBalance(new BigDecimal("1.00"));

            // get new list of available items
            available = service.purchaseItemsList();
            assertTrue(available.size() == 2, "2 items should be purchaseable with an amount of 1.00. ");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testGetCreditBalance(){
        try{
            
            service.addCreditToBalance(new BigDecimal("3.71"));
            int[] coins = service.getCreditBalance();
            
            //test quarters
            assertEquals(coins[0], 14, "There should be 14 quarters in 3.71");
            //test dimes
            assertEquals(coins[1], 2, "There should be 2 dimes in 3.71");
            // test nickles
            assertEquals(coins[2], 0, "There should be 0 nickles in 3.71");
            //test pennies
            assertEquals(coins[3], 1, "There should be 1 penny in 3.71");
            
            //test valance
            assertEquals(service.checkCreditBalance(), new BigDecimal("0.00"), "The balance should be 0.00");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
