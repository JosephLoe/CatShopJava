package Middle;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import Remote.*;


/**
 * The server for the middle tier.
 * @author  Michael Alexander Smith
 * @version 2.0
 */

class Server
{
  private R_StockR   theStockR;                // Remote stock object
  private R_StockRW  theStockRW;               // Remote stock object
  private R_Order    theOrder;                 // Remote order object

  public static void main( String args[] )
  {
    String stockR = args.length < 1            // URL of stock R
                   ? Names.STOCK_R             //  default  location
                   : args[0];                  //  supplied location

    String stockRW = args.length < 2           // URL of stock RW
                    ? Names.STOCK_RW           //  default  location
                    : args[1];                 //  supplied location
                      
    String order    = args.length < 3          // URL of order manip
                    ? Names.ORDER              //  default  location
                    : args[2];                 //  supplied location

    (new Server()).bind( stockR, stockRW, order );
  }

  public void bind( String urlStockR, String urlStockRW,
                    String urlOrder )
  {
    // Required for JDK 1.1
    // System.setSecurityManager( new RMISecurityManager() );

    System.out.println( "Server: " );               // Introduction
    try
    {
      LocateRegistry.createRegistry(1099);
      String IPAddress = InetAddress.getLocalHost().getHostAddress();
      System.out.println("Server IP adress " + IPAddress );
    } catch (Exception e)
    {
       System.out.println("Fail Starting rmiregistry" + e.getMessage() );
       System.exit(0);
    }

    try
    {
      theStockR = new R_StockR(urlStockR);            // Stock R
      Naming.rebind( urlStockR, theStockR );          //  bind to url
      System.out.println( "StockR  bound to: " +      //  Inform world
                           urlStockR );               //

      theStockRW = new R_StockRW(urlStockRW);         // Stock RW
      Naming.rebind( urlStockRW, theStockRW );        //  bind to url
      System.out.println( "StockRW bound to: " +      //  Inform world
                           urlStockRW );              // 

      theOrder = new R_Order(urlOrder);               // Order
      Naming.rebind( urlOrder, theOrder );            //  bind to url
      System.out.println( "Order   bound to: " +      //  Inform world
                         urlOrder ); 

    }
    catch ( Exception err )                           // Error
    {                                                 //
       System.out.println( "Fail Server: " +          // Variety of
                           err.getMessage() );        // reasons
    }
  }
}
