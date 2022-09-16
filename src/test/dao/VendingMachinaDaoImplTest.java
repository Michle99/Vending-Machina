package test.dao;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.ij.vendingmachina.dao.VendingMachinaDao;
import com.ij.vendingmachina.dao.VendingMachinaDaoImpl;
import com.ij.vendingmachina.dao.VendingMachinaPersistenceException;
import com.ij.vendingmachina.dto.*;


public class VendingMachinaDaoImplTest {
    // Declare instance of DAO
    VendingMachinaDao testDao; 
    
    public VendingMachinaDaoImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        // Create string for test file
        String testFile = "testFile.txt";
        
        // Use file write to blank the file
        new FileWriter(testFile);
        
        // Instantiate the testDao
        testDao = new VendingMachinaDaoImpl(testFile);        
    }
    
    @AfterEach
    public void tearDown() {
    }

    
    @Test
    public void testAddGetItemMethods() throws VendingMachinaPersistenceException {
        // Create a new Item object
        Items item = new Items();
        
        // Populate object fields
        String orderNum = "E4";
        item.setName("Coke");
        item.setPrice(new BigDecimal("2.99"));
        item.setInventory(5);
        item.setOrderNumber(orderNum);
        
        // Add the student to the DAO
        testDao.addItem(item.getOrderNumber(), item);
        
        // Retrieve the object at the key code
        Items retrievedItem = testDao.getItem(orderNum);
        
        // Compare the two objects via hash codes 
        assertEquals(item.hashCode(), retrievedItem.hashCode());
        
        // Compare the two objects via equals
        assertTrue(item.equals(retrievedItem));        
    }
    
    
    @Test
    public void testRemoveItemMethod() throws VendingMachinaPersistenceException {
        // Create three new items objects
        Items item0 = new Items();
        item0.setName("Coke");
        item0.setPrice(new BigDecimal("2.99"));
        item0.setInventory(5);
        item0.setOrderNumber("A2");
        
        Items item1 = new Items();
        item1.setName("PepperMint Gum");
        item1.setPrice(new BigDecimal("1.99"));
        item1.setInventory(12);
        item1.setOrderNumber("D3");
        
        Items item2 = new Items();
        item2.setName("Giggles");
        item2.setPrice(new BigDecimal("1.99"));
        item2.setInventory(3);
        item2.setOrderNumber("E8");
                
        // Add all three items to the DAO
        testDao.addItem(item0.getOrderNumber(), item0);
        testDao.addItem(item1.getOrderNumber(), item1);
        testDao.addItem(item2.getOrderNumber(), item2);
        
        // Remove the first item
        Items removedItem = testDao.removeItem(item0.getOrderNumber(), item0);
        
        // Assert returnedItem is equal to item0
        assertEquals(item0, removedItem, "Coke should be removed");
        
        // Generate a list of all remaining item objects
        List<Items> itemsList = testDao.getAllItems();
        
        // Test to ensure the list is not null
        assertNotNull(itemsList, "Items list should not be null");
        
        // Test to ensure the list size is correct, at two
        assertEquals(itemsList.size(), 2, "List started with three items, "
                + "one was removed, two should remain");
        
        // Assert false that the list does not contains the first item
        assertFalse(itemsList.contains(item0), "The first item (item0) was "
                + "removed, thus the items list should not contain item0");
        
        // Assert true that the list contains the other two items
        assertTrue(itemsList.contains(item1), "Item 1 was added, and untouched"
                + " thus should stil be in items list");        
        assertTrue(itemsList.contains(item2), "Item 2 was added, and untouched"
                + " the items list should contain item2");
        
        // Remove second item (item1)
        removedItem = testDao.removeItem(item1.getOrderNumber(), item1);
        
        // Update the itemsList
        itemsList = testDao.getAllItems();
        
        // Assert equals that the removed item is the second item (item1)
        assertEquals(removedItem, item1, "Returned item should be item1");
                
        // Assert true that the list does not contain the second item
        assertTrue(!itemsList.contains(item1), "Item1 was removed from the list"
                + " thus items list should not contain item1");
        
        // Remove the third and last item
        removedItem = testDao.removeItem(item2.getOrderNumber(), item2);
        
        // Update the itemsList
        itemsList = testDao.getAllItems();
        
        // Assert equals that the removed item is the third item (item2)
        assertEquals(removedItem, item2, "Returned item should be item2");
        
        // Assert true that the list does not contain the last item
        assertTrue(!itemsList.contains(item2), "Item2 was removed from the list"
                + " thus items list should not contain item2");
        
        // Assert true that the list is empty
        assertTrue(itemsList.isEmpty(), "Items List should be empty");
        
        // Try to obtain first student via order Number, assert null 
        removedItem = testDao.getItem(item0.getOrderNumber());
        assertNull(removedItem, "Item0 should not exist thus null");
        
        // Try to obtain second student via order Number, assert null
        removedItem = testDao.getItem(item1.getOrderNumber());
        assertNull(removedItem, "Item1 should not exist thus null");
        
        // Try to obtain third student via order Number, assert null
        removedItem = testDao.getItem(item2.getOrderNumber());
        assertNull(removedItem, "Item2 should not exist thus null");
    }
    
    
    @Test
    public void testGetAllItemsMethod() throws VendingMachinaPersistenceException {
        // Create three new items objects
        Items item0 = new Items();
        item0.setName("Coke");
        item0.setPrice(new BigDecimal("3.99"));
        item0.setInventory(5);
        item0.setOrderNumber("A2");
        
        Items item1 = new Items();
        item1.setName("PepperMint Gum");
        item1.setPrice(new BigDecimal("1.99"));
        item1.setInventory(12);
        item1.setOrderNumber("D3");
        
        Items item2 = new Items();
        item2.setName("Giggles");
        item2.setPrice(new BigDecimal("1.99"));
        item2.setInventory(3);
        item2.setOrderNumber("E8");
        
        // Add all items to the DAO
        testDao.addItem(item0.getOrderNumber(), item0);
        testDao.addItem(item1.getOrderNumber(), item1);
        testDao.addItem(item2.getOrderNumber(), item2);
        
        // Obtain a list of all items from the DAO
        List<Items> itemsList = testDao.getAllItems();
        
        // Assert that the list is not null
        assertNotNull(itemsList.isEmpty(), "Item list should not be null");
        
        // Assert that the list has a size of three
        assertEquals(itemsList.size(), 3, "There should be three items in the"
                + " list");
        
        // Assert true that the list contains the first item
        assertTrue(itemsList.contains(item0), "Item0 should be in the list");
        
        // Assert true that the list contains the second item
        assertTrue(itemsList.contains(item1), "Item1 should be in the list");
        
        // Assert true that the list contain the third item
        assertTrue(itemsList.contains(item2), "Item2 should be in the list");
    }
    
    
    @Test
    public void testIncrementDecrementItemStockMethod() throws VendingMachinaPersistenceException {
        // Create a new Item object
        Items item = new Items();
        
        // Populate items object fields, set stock as two
        String orderNum = "E4";
        item.setName("Coke");
        item.setPrice(new BigDecimal("3.99"));
        item.setInventory(2);
        item.setOrderNumber(orderNum);
                
        // Add items object to DAO
        Items returnedItem = testDao.addItem(item.getOrderNumber(), item);
        
        // Assert null due to no collisions in the hashmap
        assertEquals(returnedItem, null, "Returned item should be null since "
                + "there were no collisions, due to empty HashMap");
        
        // Increment item stock by one, return to new item object
        returnedItem = testDao.increaseItemInventory(orderNum);
        
        // Assert returned items object stock is eqaul to three
        assertEquals(returnedItem.getInventory(), 3, "Item started with a stock of"
                + " two, adding one should yeild a stock of three");
        
        // Decrement items stock by two, return to new item object
        returnedItem = testDao.decreaseItemInventory(orderNum);
        returnedItem = testDao.decreaseItemInventory(orderNum);
        
        // Assert returned item stock is equal to 1
        assertEquals(returnedItem.getInventory(), 1, "Item had a stock of 3 post "
                + "incrment, decrementing twice should yeild a stock of 1");
        
        // Decrement items stock by one, return to new item object
        returnedItem = testDao.decreaseItemInventory(orderNum);
        
        // Assert returned item object stock is equal to 0
        assertEquals(0, returnedItem.getInventory(), "Item had a stock of 1 post "
                + "double decrement, decrementing one more should yeild zero");
    }
}
