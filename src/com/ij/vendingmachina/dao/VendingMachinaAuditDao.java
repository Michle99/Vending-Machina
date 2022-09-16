package com.ij.vendingmachina.dao;

public interface VendingMachinaAuditDao {

	public void writeAuditEntry(String entry) throws VendingMachinaPersistenceException;

}
