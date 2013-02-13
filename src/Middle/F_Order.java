package Middle;

import Catalogue.*;
import Remote.*;
import java.rmi.*;
import java.util.List;
import java.util.Map;

// There can only be 1 resultset opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

/**
 * Facade for the order processing handling which is implemented on the middle tier.
 * This code is incomplete
 * @author  Michael Alexander Smith
 * @version 2.0
 */


public class F_Order implements OrderProcessing
{
  private RemoteOrder_I aR_Order    = null;
  private String        theOrderURL = null;

  public F_Order(String url)
  {
    theOrderURL = url;
  }
  
  private void connect() throws OrderException
  {
    try                                            // Setup
    {                                              //  connection
      aR_Order =                                   //  Connect to
       (RemoteOrder_I) Naming.lookup(theOrderURL); // Stub returned
    }
    catch ( Exception e )                          // Failure to
    {                                              //  attach to the
      aR_Order = null;
      throw new OrderException( "Com: " + 
                               e.getMessage()  );  //  object
      
    }
  }


  public void newOrder( SoldBasket bought )
         throws OrderException
  {
    System.out.println("F_Order:newOrder() " );
    try
    {
      if ( aR_Order == null ) connect();
      aR_Order.newOrder( bought );
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  public int uniqueNumber()
         throws OrderException
  {
    System.out.println("F_Order:uniqueNumber() " );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.uniqueNumber();
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Returns an order to pick from the warehouse
   * if no order then returns null.
   * @return An order to pick
   */

  public synchronized SoldBasket getOrderToPick()
         throws OrderException
  {
    System.out.println("F_Order:getOrderTioPick() " );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.getOrderToPick();
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Informs the order processing system that the order has been
   * picked and the products are no on the conveyor belt to
   * the shop floor.
   */

  public synchronized void informOrderPicked( int orderNo )
         throws OrderException
  {
    System.out.println("F_Order:informOrderPicked() " );
    try
    {
      if ( aR_Order == null ) connect();
      aR_Order.informOrderPicked(orderNo);
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Informs the order processing system that the order has been
   * collected by the customer
   */

  public synchronized boolean informOrderColected( int orderNo )
         throws OrderException
  {
    System.out.println("F_Order:informOrderColected() " );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.informOrderColected(orderNo);
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Returns information about all orders in the order processing system
   */

  public synchronized Map<String, List<Integer> > getOrderState()
         throws OrderException
  {
    System.out.println("F_Order:getOrderState() " );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.getOrderState();
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }
}
