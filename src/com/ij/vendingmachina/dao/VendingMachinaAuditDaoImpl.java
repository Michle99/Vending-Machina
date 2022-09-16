package com.ij.vendingmachina.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;



public class VendingMachinaAuditDaoImpl implements VendingMachinaAuditDao {
	public static final String AUDIT_FILE = "audit.txt";
	   
    public void writeAuditEntry(String entry) throws VendingMachinaPersistenceException {
        PrintWriter out;
       
        
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachinaPersistenceException("Could not persist audit information.", e);
        }
 
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
        out.close();
    }

}
