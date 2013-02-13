package Processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import Catalogue.*;
import Middle.*;

/**
  * The order processing system.
  * @author  Joseph Loe
  * @version 2.0
  */
 
public class Order implements OrderProcessing
{
  private static int theNextNumber = 1;          // Start at 1
  // Orders entered but waiting to be processed (picked)
  private ArrayList<SoldBasket>  theWaitingTray 
                                 = new ArrayList<SoldBasket>(10);
  // Orders being processed (currently being picked)
  private ArrayList<SoldBasket>  theBeingProcessedTray 
                                 = new ArrayList<SoldBasket>(10);
  // Orders waiting to be collected by the customer
  private ArrayList<SoldBasket>  theWaitingToBeCollectedTray 
                                 = new ArrayList<SoldBasket>(10);

  /**
   * Used for debug information
   *
   */

  private String asString( SoldBasket bl )
  {
    ByteArrayOutputStream bos = new ByteArrayOutputStream( 1024 );
    PrintStream ps = new PrintStream( bos );
    ps.printf( "#%d (", bl.getOrderNo() );
    ArrayList<Product> p = bl.getProducts();
    for ( Product pr: p )
    {
       ps.printf( "%s:%d ", pr.getDescription(), pr.getQuantity() );
    }
    ps.printf( ")" );
    return bos.toString();
  }

  /**
   * Generates a unique order number
   * @return A unique order number
   */

  public synchronized int uniqueNumber()
  {
    return theNextNumber++;
  }

  /**
   * Add a new order to the order processing system
   * @author Joseph Loe
   */

  public synchronized void newOrder( SoldBasket bought )
         throws OrderException
  {
    // You need to modify and fill in the correct code
	  theWaitingTray.add(bought);  //add bought to ArrayList   J.L
	System.out.println( "DEBUG: New order" );
  }

  /**
   * Returns an order to pick from the warehouse
   *  if no order then returns null.
   * @return An order to pick or null if none
   * @author Joseph Loe
   */

  public synchronized SoldBasket getOrderToPick()
         throws OrderException
  {
	  System.out.println( "DEBUG: Get order to pick" );
	  SoldBasket removed = null;
	  if(!(theWaitingTray.isEmpty())) // if theWaitingTray is not empty, remove the first index and add to next list J.L
	  {
	  removed = theWaitingTray.remove(0);  	// J.L
	  theBeingProcessedTray.add(removed);	// J.L
	  System.out.println("DEBUG: Picked");	// J.L
	  
		  return removed;
	  
	  }	  else{
    
    return null;
	  }
  }

  /**
   * Informs the order processing system that the order has been
   * picked and the products are now on the conveyor belt to
   * the shop floor.#
   * @author Joseph Loe
   */

  public synchronized void informOrderPicked( int orderNo )
         throws OrderException
  {
	SoldBasket processRemove = null;  // J.L                                     
    for (int i = 0; i<theBeingProcessedTray.size(); i++ ) //loop through the arrayList // J.L
    {
        if (theBeingProcessedTray.get(i).getOrderNo() == orderNo){ //if found the index remove the elements and add to collected
            processRemove = theBeingProcessedTray.remove(i);            // J.L
            theWaitingToBeCollectedTray.add(processRemove); 			// J.L
            System.out.println("DEBUG: found order and moved to collected tray");	// J.L
        }
    }
  }

  /**
   * Informs the order processing system that the order has been
   * collected by the customer
   * @return true -> Order in system false -> no such order
   * @author Joseph Loe
   */

  public synchronized boolean informOrderColected( int orderNo )
         throws OrderException
  {

		  for (int i = 0; i<theWaitingToBeCollectedTray.size(); i++ ) //loop through waiting tray and remove the orderNo // J.L
	      {
	          if (theWaitingToBeCollectedTray.get(i).getOrderNo() == orderNo){ //if OrderNo in array, Remove it // J.L
	              theWaitingToBeCollectedTray.remove(i);	// J.L
	              System.out.println("DEBUG: order removed from Collected ArrayList");	// J.L
	              return true;                                            //if removed order, return True // J.L
	          }
	      }
		  System.out.println( "DEBUG: "+ orderNo + " Not being processed." ); // J.L
	return false;
  }

  /**
   * Returns information about all orders in the order processing system
   * This will consist of a map with the order numbers in the 3 queue
   * waiting, being picked, to be collected.
   * 1> Key "Waiting"       -> a list of orders waiting to be processed
   * 2> Key "BeingPicked"   -> a list of orders that are currently being picked
   * 3> Key "ToBeCollected" -> a list of orders that can now be collected
   * @return a Map with the keys: Waiting, BeingPicked, ToBeCollected
   */

  public synchronized Map<String, List<Integer> > getOrderState()
         throws OrderException
  {
    System.out.println( "DEBUG: get state of order system" );
    Map < String, List<Integer> > res = 
      new HashMap< String, List<Integer> >();
    res.put( "Waiting",       orderNos(theWaitingTray) );
    res.put( "BeingPicked",   orderNos(theBeingProcessedTray) );
    res.put( "ToBeCollected", orderNos(theWaitingToBeCollectedTray) );

    return res;
  }
  
  private List< Integer > orderNos( ArrayList<SoldBasket> queue )
  {
    List <Integer> res = new ArrayList<Integer>();
    for ( SoldBasket sb: queue )
    {
      res.add( sb.getOrderNo() );
    }
    return res;
  }


}
