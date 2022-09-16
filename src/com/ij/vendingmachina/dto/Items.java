package com.ij.vendingmachina.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Items {

	// Item fields
    private String name;
    private BigDecimal price;
    private int inventory;
    private String orderNumber;

    public String getName() {
        return name;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public int getInventory() {
        return inventory;
    }

    public String getOrderNumber(){
        return orderNumber;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
    
    public void setOrderNumber(String orderNum){
        this.orderNumber = orderNum;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    // HashCode, Equals, To String overrides
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.price);
        hash = 67 * hash + this.inventory;
        hash = 67 * hash + Objects.hashCode(this.orderNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Items other = (Items) obj;
        if (this.inventory != other.inventory) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.orderNumber, other.orderNumber)) {
            return false;
        }
        return Objects.equals(this.price, other.price);
    }

    @Override
    public String toString() {
        return "Items{" + "name=" + name + ", price=" + price + ", inventory=" + inventory + ", orderNumber=" + orderNumber + '}';
    }
    
}
