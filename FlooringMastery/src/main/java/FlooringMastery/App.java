/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery;

import FlooringMastery.controller.FlooringMasteryController;
import FlooringMastery.dao.FlooringMasteryPersistenceException;

import FlooringMastery.dao.OrdersTrainDaoFileImpl;
import FlooringMastery.dao.OrdersDao;
import FlooringMastery.dao.OrdersProdDaoFileImpl;
import FlooringMastery.dao.ProductsDao;
import FlooringMastery.dao.ProductsDaoFileImpl;
import FlooringMastery.dao.TaxesDao;
import FlooringMastery.dao.TaxesDaoFileImpl;
import FlooringMastery.service.FlooringService;
import FlooringMastery.service.FlooringServiceImpl;
import FlooringMastery.ui.FlooringMasteryView;
import FlooringMastery.ui.UserIO;
import FlooringMastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ctrop
 */
public class App {
    
  
    public static void main(String[] args) throws FlooringMasteryPersistenceException{

        
       
    /* //declare a UserIO variable and itialize it with userioconsoleimpl reference
     UserIO myIO= new UserIOConsoleImpl();
     
     //declare and instantiate the view oject and pass the userIO into constructor
     FlooringMasteryView myView= new FlooringMasteryView(myIO);
     
     //declare ordersDAo variable and initialize it with daoProd file impl 
     OrdersDao dao= new OrdersProdDaoFileImpl();
  
     ProductsDao prodDao= new ProductsDaoFileImpl();
     TaxesDao taxDao= new TaxesDaoFileImpl();
     
     
     FlooringService service= new FlooringServiceImpl(dao, prodDao,taxDao);
     
     //Instantiate the controller
     FlooringMasteryController controller= new FlooringMasteryController(myView, service);
     
     controller.run();
    } 
        */
  
  
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller
                = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
    
    }
}
    




