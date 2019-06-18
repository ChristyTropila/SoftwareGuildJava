/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vendingmachineproject.controller.VendingMachineController;
import vendingmachineproject.dao.VendingMachineAuditDao;
import vendingmachineproject.dao.VendingMachineAuditDaoFileImpl;
import vendingmachineproject.dao.VendingMachineDao;
import vendingmachineproject.dao.VendingMachineDaoFileImpl;
import vendingmachineproject.service.VendingMachineServiceLayer;
import vendingmachineproject.service.VendingMachineServiceLayerImpl;
import vendingmachineproject.ui.UserIO;
import vendingmachineproject.ui.UserIOImpl;
import vendingmachineproject.ui.VendingMachineView;

/**
 *
 * @author ctrop
 */
public class App {
    
    public static void main(String[] args){
         
  /*    UserIO myIo = new UserIOImpl(); 
        VendingMachineView myView = new VendingMachineView(myIo); 
        VendingMachineDao myDao = new VendingMachineDaoFileImpl(); 
        VendingMachineAuditDao auditDao= new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, auditDao); 
        VendingMachineController controller = new VendingMachineController(myView, myService); 
        controller.run();*/
    
    
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller
                = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
   
        
    }
}
    
    

