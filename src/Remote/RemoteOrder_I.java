package Remote;

import java.rmi.*;
import java.util.List;
import java.util.Map;

import Catalogue.*;
import Middle.*;

/**
 * Defines the RMI interface for the Order object.
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public interface RemoteOrder_I extends Remote
{
  public void newOrder( SoldBasket order ) 
         throws RemoteException, OrderException;
  public int  uniqueNumber() 
         throws RemoteException, OrderException;
  public SoldBasket getOrderToPick() 
         throws  RemoteException, OrderException;
  public void informOrderPicked( int orderNo ) 
         throws  RemoteException, OrderException;
  public boolean informOrderColected( int orderNo )
         throws RemoteException, OrderException;
  public Map<String, List<Integer>> getOrderState() 
         throws  RemoteException, OrderException;
}

