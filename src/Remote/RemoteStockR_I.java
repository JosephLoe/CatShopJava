package Remote;

import Catalogue.*;
import Middle.*;
import java.rmi.*;
import javax.swing.ImageIcon;

/**
 * Defines the RMI interface for read access to the stock object.
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public interface RemoteStockR_I
       extends Remote
{
  boolean   exists( String number )
            throws RemoteException, StockException;
  Product   getDetails( String number )
            throws RemoteException, StockException;
  ImageIcon getImage( String number )
            throws RemoteException, StockException;
}

