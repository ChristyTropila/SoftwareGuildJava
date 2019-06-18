/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary;

import mybeerlibrary.controller.MyBeerLibraryController;
import mybeerlibrary.dao.MyBeerLibraryDao;
import mybeerlibrary.dao.MyBeerLibraryDaoFileImpl;
import mybeerlibrary.ui.MyBeerLibraryView;
import mybeerlibrary.ui.UserIO;
import mybeerlibrary.ui.UserIOConsoleImpl;



/**
 *
 * @author ctrop
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      UserIO myIo= new UserIOConsoleImpl();
      MyBeerLibraryView myView= new MyBeerLibraryView(myIo);
      MyBeerLibraryDao myDao= new MyBeerLibraryDaoFileImpl();
      MyBeerLibraryController controller = new MyBeerLibraryController(myDao, myView);
      controller.run();
        
    }
    
}
