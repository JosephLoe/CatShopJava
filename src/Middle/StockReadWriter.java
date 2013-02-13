package Middle;

import Catalogue.*;

/**
  * Interface for read/write access to the stock list.
  * @author  Michael Alexander Smith
  * @version 2.0
  */
 
public interface StockReadWriter extends StockReader
{
  
 /**
   * Customer buys stock,
   * stock level is thus decremented by amount bought.
   * @return StockNumber, Description, Price, Quantity
   */

  boolean buyStock( String number, int amount ) throws StockException;

  /**
   * Adds stock (Restocks) to store.
   */
  
  void addStock( String number, int amount ) throws StockException;
  
  /**
   * Modifies Stock details for a given product number.
   * Information modified: Description, Price
   */
  
  void modifyStock( Product detail ) throws StockException;

}
