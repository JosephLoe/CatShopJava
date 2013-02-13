package Other;

/**
 * Test the database access
 * @author  Michael Alexander Smith
 * @version 2.0
 */

import Catalogue.*;
import DBAccess.*;
import Middle.*;

class Test
{
  private StockRW theStock      = null;

  public static void main( String args[] )
  {
    Test test = new Test();
    test.process();
  }


  public Test()
  {
  }


  public void process()
  {
    String pn = "0100";
    try
    {
      theStock = new StockRW();
      
      if ( !theStock.exists(pn) )
      {
        System.out.println("Creating " + pn );
        theStock.modifyStock( new Product( pn, "swatch", 100.00, 0 ) );
      }
        
      
      Product pr = theStock.getDetails( pn );
      System.out.println("Stock level = " + pr.getQuantity() );
      
      theStock.addStock( pn, 2 );
      System.out.println("Stock level add 2" );
      
      pr = theStock.getDetails( pn );
      System.out.println("Stock level = " + pr.getQuantity() );
    }
    catch ( StockException e )                      // SQL failure
    {                                               //
      System.out.println( "SQL failure" + "\n"  +   //
                          e.getMessage() + "\n" );
    }
    catch ( Exception e )                           // Unexpected failure
    {                                               //
      System.out.println( "Unexpected failure" + "\n"  +   //
                          e.getMessage() + "\n" );
    }
  }
}


