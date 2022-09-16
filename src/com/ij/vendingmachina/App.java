package com.ij.vendingmachina;

import com.ij.vendingmachina.controller.VendingMachineController;
import com.ij.vendingmachina.dao.VendingMachinaAuditDao;
import com.ij.vendingmachina.dao.VendingMachinaAuditDaoImpl;
import com.ij.vendingmachina.dao.VendingMachinaDao;
import com.ij.vendingmachina.dao.VendingMachinaDaoImpl;
import com.ij.vendingmachina.service.VendingMachinaServiceLayer;
import com.ij.vendingmachina.service.VendingMachinaServiceLayerImpl;
import com.ij.vendingmachina.ui.UserIO;
import com.ij.vendingmachina.ui.UserIOConsoleImpl;
import com.ij.vendingmachina.ui.VendingMachinaView;

public class App {

	 
   public static void main(String[] args) {
	   // Instantiate the UserIO implementation
	    UserIO io = new UserIOConsoleImpl();
	    
	    // Instantiate the view and wire in the UserIO implementation
	    VendingMachinaView view = new VendingMachinaView(io);
	    
	    // Instantaite the DAO
	    VendingMachinaDao dao = new VendingMachinaDaoImpl();
	    
	    // Instantiate the Audit DAO
	    VendingMachinaAuditDao auditDao = new VendingMachinaAuditDaoImpl();
	    
	    // Instantiate the Service Layer and wire in the DAO and Audit DAO
	    VendingMachinaServiceLayer service = new VendingMachinaServiceLayerImpl(dao, auditDao);
	            
	    // Instantiate the Controler and wire int the service layer and the view
	    VendingMachineController controller = new VendingMachineController(service, view);
	    
	    // Run the applicaiton
	    controller.run();
   }

}
