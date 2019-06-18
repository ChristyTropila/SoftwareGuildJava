/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.dao;

/**
 *
 * @author ctrop
 */
public class MyBeerLibraryDaoException extends Exception{
    
    public MyBeerLibraryDaoException(String message){
        super(message);
    }
    
    public MyBeerLibraryDaoException(String message, Throwable cause){
        super(message, cause);
    
}
}