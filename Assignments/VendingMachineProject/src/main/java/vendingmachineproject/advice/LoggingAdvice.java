/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.advice;


import org.aspectj.lang.JoinPoint;
import vendingmachineproject.dao.SnackInventoryPersistenceException;
import vendingmachineproject.dao.VendingMachineAuditDao;

/**
 *
 * @author ctrop
 */
public class LoggingAdvice {
    
    VendingMachineAuditDao auditDao;
    
    //constructor inection to allow LoggingAdvice to use auditDao
    public LoggingAdvice(VendingMachineAuditDao auditDao){
            this.auditDao= auditDao;
    
}
    
  public void createAuditEntry(JoinPoint jp, Throwable ex) {
      
      
        Object[] args = jp.getArgs();
         String auditEntry = 
                "Method: " + jp.getSignature().getName() + " | " + 
                ex.getMessage() + 
                " | Selection ID: " + args[0] + 
                " | User money: " + args[1];
        
        
        
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (SnackInventoryPersistenceException e) {
            System.err.println(
               "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
    
}

