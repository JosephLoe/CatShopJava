package Middle;

/**
 * Facade for read/write access to the stock list.
 * The actual implementation of this is held on the middle tier.
 * The actual stock list is held in a relational DataBase on the 
 * third tier.
 * @author  Michael Alexander Smith
 * @version 2.0
 */

import java.rmi.*;
import Catalogue.*;
import Remote.*;

/**
 * Setup connection to the middle tier
 */

public class F_StockRW extends F_StockR 
                       implements StockReadWriter
{
  private RemoteStockRW_I aR_StockRW  = null;
  private String          theStockURL = null;

  public F_StockRW( String url )
  {
    super( url );                                   // Not used
    theStockURL = url;
  }
  
  private void connect() throws StockException
  {
    try                                             // Setup
    {                                               //  connection
      aR_StockRW =                                  //  Connect to
       (RemoteStockRW_I) Naming.lookup(theStockURL);// Stub returned
    }
    catch ( Exception e )                           // Failure to
    {                                               //  attach to the
      aR_StockRW = null;
      throw new StockException( "Com: " + 
                               e.getMessage()  );   //  object
      
    }
  }

  /**
   * Buys stock and hence decrements no in stock list
   * @return StockNumber, Description, Price, Quantity
   */

 
  public boolean buyStock( String number, int amount )
         throws StockException
  {
    System.out.println("F_StockRW:buyStock() " );
    try
    {
      if ( aR_StockRW == null ) connect();
      return aR_StockRW.buyStock( number, amount );
    } catch ( RemoteException e )
    {
      aR_StockRW = null;
      throw new StockException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Adds (Restocks) stock to the product list
   */

  public void addStock( String number, int amount )
         throws StockException
  {
    System.out.println("F_StockRW:addStock() " );
    try
    {
      if ( aR_StockRW == null ) connect();
      aR_StockRW.addStock( number, amount );
    } catch ( RemoteException e )
    {
      aR_StockRW = null;
      throw new StockException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Modifies Stock details for a given product number.
   * Information modified: Description, Price
   */

  public void modifyStock( Product detail )
              throws StockException
  {
    System.out.println("F_StockRW:modifyStock() " );
    try
    {
      if ( aR_StockRW == null ) connect();
      aR_StockRW.modifyStock( detail );
    } catch ( RemoteException e )
    {
      aR_StockRW = null;
      throw new StockException( "Net: " + e.getMessage() );
    }
  }

}
