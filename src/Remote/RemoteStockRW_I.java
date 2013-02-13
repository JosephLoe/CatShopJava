/**
 * @author  Michael Alexander Smith
 * @version 2.0
 */

package Remote;

import java.rmi.*;
import Catalogue.*;
import Middle.*;

/**
 * Defines the RMI interface for read/write access to the stock object.
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public interface RemoteStockRW_I
       extends   RemoteStockR_I, Remote
{
  boolean buyStock( String number, int amount)
          throws RemoteException, StockException;
  void    addStock( String number, int amount)
          throws RemoteException, StockException;
  void    modifyStock( Product detail )
          throws RemoteException, StockException;
}

