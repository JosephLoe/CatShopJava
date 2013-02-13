package Other;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import Catalogue.*;
import Clients.*;
import Middle.*;
import Processing.*;


/**
 * A very simple test of the system
 * @author  Michael Alexander Smith
 * @version 2.2
 *
 */


public class StandAloneTest
{
  private final int       ORDER_NUMBER = 7;         // Order numbers
  private Product         theProduct = null;        // Current product
  private SoldBasket      theBought  = null;        // Bought items
  private StockReadWriter theStock   = null;        // Stock list
  private OrderProcessing theOrder   = null;        // Order processing system
  private MiddleFactory   mf         = null;        // Type of access

  public static void main( String args[] )
  {
    StandAloneTest sat = new StandAloneTest();
    sat.simple();
  }
  
  /**
   * Parts tested: 
   * Add order into the system,
   * Get an order to pick,
   * Inform order picked,
   * Customer collects
   */
  
  public void simple()
  {
    // Setup the environment
      
    mf = new LocalMiddleFactory();                // Local Stock list ...
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // DataBase access object
      theOrder = mf.makeOrderProcessing();        // Process order object
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
      System.exit(-1);
    }
    
    // Create a SoldBasket containing 1 item of product #0001
    
    theBought = new SoldBasket( ORDER_NUMBER );         // Items bought
    Product pr = null;
    try
    {
      pr = theStock.getDetails( "0001" );
    } catch ( StockException e )
    {
      System.out.println("Exception: " + e.getMessage() );
      System.exit(-1);
    }
    System.out.println("Product No  = " + pr.getProductNo());
    System.out.println("Price       = " + pr.getPrice() );
    System.out.println("Stock level = " + pr.getQuantity() );
    System.out.println("Description = " + pr.getDescription() );
  
    pr.setQuantity(1);                      // Wish to buy 1 item
    
    // Now send it to the order processing system
     
    theBought.add( pr );                    // Add to basket of sold items
    try
    {
      theOrder.newOrder( theBought );       // Create new order in the system
    } catch (OrderException e )
    {
      System.out.println("Fail: newOrder" );
      System.out.println("Exception: " + e.getMessage() );
      System.exit(-1);
    }
    
    // Get details about the next order to pick
    
    SoldBasket orderPicked = null;               // Order picked
    try
    {
      orderPicked = theOrder.getOrderToPick();   // Should be an order to pick
    } catch ( OrderException e )
    {
      System.out.println("Exception: " + e.getMessage() );
      System.exit(-1);
    }
    if ( orderPicked != null )                   // Error if no order
    {
      System.out.println( orderPicked.getDetails() );
    } else {
      System.out.println("Fail: No order to pick?");
      System.exit(-1);
    }
  
    // Say that we have picked product from the shelves
    
    int orderNo = orderPicked.getOrderNo();
    if (orderNo != ORDER_NUMBER )
    {
      System.out.println("Fail: Wrong order number found");
      System.exit(-1);  
    }
    
    try
    {
      theOrder.informOrderPicked( orderNo );
    } catch (OrderException e )
    {
      System.out.println("Fail: informOrderPicked(" );
      System.out.println("Exception: " + e.getMessage() );
      System.exit(-1);
    }
      
    // Inform order processing that the customer has picked up the order
    
    boolean ok = true;
    try
    {
      ok = theOrder.informOrderColected( orderNo );
    } catch ( OrderException e )
    {
      System.out.println("Fail: informOrderColected" );
      System.out.println("Exception: " + e.getMessage() );
      System.exit(-1);
    }
    if ( ok )
    {
      System.out.println( "Success: Order collected");
    } else {
      System.out.println("Fail: Order not collected" );
    }
  
  }

}
