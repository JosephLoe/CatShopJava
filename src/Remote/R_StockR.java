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
 * Implements Read access to the stock list,
 * the stock list is held in a relational DataBase.
 * @author  Michael Alexander Smith
 * @version 2.0
 */
public class      R_StockR
       extends    java.rmi.server.UnicastRemoteObject
       implements RemoteStockR_I
{
  private static final long serialVersionUID = 1;
  private StockR aStockR = null;

  public R_StockR( String url )
         throws RemoteException, StockException
  {
    aStockR = new StockR();
  }

  /**
   * Checks if the product exits in the stock list
   * @return true if exists otherwise false
   */

  public synchronized boolean exists( String number )
         throws RemoteException, StockException
  {
    return aStockR.exists( number );
  }

  /**
   * Returns details about the product in the stock list
   * @return StockNumber, Description, Price, Quantity
   */

  public synchronized Product getDetails( String number )
         throws RemoteException, StockException
  {
    return aStockR.getDetails( number );
  }
  
  /**
   * Returns an image of the product
   * BUG However this will not work for distributed version
   *     as an instance of an Image is not serializable
   * @return Image
   */
  
  public synchronized ImageIcon getImage( String number )
         throws RemoteException, StockException
  {
    return aStockR.getImage( number );
  }

}
