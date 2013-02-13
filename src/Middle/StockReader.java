package Middle;

import Catalogue.*;
import javax.swing.ImageIcon;

/**
  * Interface for read access to the stock list.
  * @author  Michael Alexander Smith
  * @version 2.0
  */

public interface StockReader
{

 /**
   * Checks if the product exits in the stock list
   * @return true if exists otherwise false
   */
  
  boolean exists( String number ) throws StockException;
         
  /**
   * Returns details about the product in the stock list
   * @return StockNumber, Description, Price, Quantity
   */
  
  Product getDetails( String number ) throws StockException;
  
  
  /**
   * Returns an image of the product in the stock list
   * @return Image
   */
  
  ImageIcon getImage( String number ) throws StockException;
}
