package Remote;

import java.rmi.*;

import Catalogue.*;
import DBAccess.*;
import Middle.*;
import javax.swing.ImageIcon;

// There can only be 1 resultset opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

/**
 * Implements Read/Write access to the stock list,
 * the stock list is held in a relational DataBase.
 * @author  Michael Alexander Smith
 * @version 2.1
 */

public class      R_StockRW
       extends    java.rmi.server.UnicastRemoteObject
       implements RemoteStockRW_I
{
  private static final long serialVersionUID = 1;
  private StockRW aStockRW = null;

  /**
   * All transactions are done via StockRW to ensure
   * that a single connection to the database is used for all transactions
   * @param url of remote object
   * @throws RemoteException
   * @throws StockException
   */
  
  public R_StockRW(String url)
         throws RemoteException, StockException
  {
    aStockRW = new StockRW();
  }
  
  public synchronized boolean exists( String number )
         throws StockException
  {
    return aStockRW.exists( number );
  }

  /**
   * Returns details about the product in the stock list
   * @return StockNumber, Description, Price, Quantity
   */

  public synchronized Product getDetails( String number )
         throws StockException
  {
    return aStockRW.getDetails( number );
  }

  /**
   * Returns an image of the product in the stock list
   * @return image
   */

  public synchronized ImageIcon getImage( String number )
         throws StockException
  {
    return aStockRW.getImage( number );
  }


  /**
   * Buys stock and hence decrements no in stock list
   * @return StockNumber, Description, Price, Quantity
   */

  // Need to Fix
  //  What happens if can not commit data
  //
  public synchronized boolean buyStock( String number, int amount )
         throws StockException
  {
    return aStockRW.buyStock( number, amount );
  }

  /**
   * Adds (Restocks) stock to the product list
   */

  public synchronized void addStock( String number, int amount )
         throws StockException
  {
    aStockRW.addStock( number, amount );
  }


  /**
   * Modifies Stock details for a given product number.
   * Information modified: Description, Price
   */

  public synchronized void modifyStock( Product detail )
              throws StockException
  {
    aStockRW.modifyStock( detail );
  }
}
