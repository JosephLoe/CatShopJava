package Clients;

import Middle.*;
import javax.swing.*;

/**
 * The Customer Client as an Applet
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public class WebCustomerClient extends JApplet
{
  private static final long serialVersionUID = 1;
  public void init ()
  {
    String supplied = getParameter( "stock" );  //
    String stockURL = supplied.equals("")       // URL of stock R
                      ? Names.STOCK_R           //  default  location
                      : supplied;               //  supplied location
                     
    System.out.println("URL " + stockURL );
    RemoteMiddleFactory mrf = new RemoteMiddleFactory(); 
    mrf.setStockRInfo( stockURL );
    displayGUI(mrf);                            // Create GUI
  }
   
  public void displayGUI( MiddleFactory mf )
  {
    new CustomerGUI( this, mf ); 
  }
}
