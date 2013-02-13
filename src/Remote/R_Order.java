package Remote;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import Catalogue.*;
import Processing.*;
import Middle.*;

/**
 * The order processing handling.
 * This code is incomplete
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public class      R_Order
       extends    UnicastRemoteObject
       implements RemoteOrder_I
{
  private static final long serialVersionUID = 1;
  private Order aOrder = null;

  public R_Order( String url )
         throws RemoteException, OrderException
  {
    aOrder = new Order();
  }

  public void newOrder( SoldBasket bought )
         throws RemoteException, OrderException
  {
     aOrder.newOrder( bought );
    
  }

  public int uniqueNumber()
         throws RemoteException, OrderException
  {
    return aOrder.uniqueNumber();
  }
  
  public SoldBasket getOrderToPick()
         throws RemoteException, OrderException
  {
      return aOrder.getOrderToPick();
  }
  
  public void informOrderPicked( int orderNo )
         throws RemoteException, OrderException
  {
     aOrder.informOrderPicked(orderNo);
  }
  
  public boolean informOrderColected( int orderNo )
         throws RemoteException, OrderException
  {
      return aOrder.informOrderColected(orderNo);
  }
  
  
  public Map<String, List<Integer>> getOrderState()
          throws RemoteException, OrderException
  {
    return aOrder.getOrderState();
  }

}
