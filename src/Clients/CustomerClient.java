package Clients;

import javax.swing.*;

import Middle.*;

/**
 * The Customer Client
 * @author  Michael Alexander Smith
 * @version 2.0
 */

public class CustomerClient
{
  public static void main (String args[])
  {
    String stockURL = args.length < 1         // URL of stock R
                    ? Names.STOCK_R           //  default  location
                    : args[0];                //  supplied location
    
    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
    mrf.setStockRInfo( stockURL );
    displayGUI(mrf);                          // Create GUI
  }
   
  public static void displayGUI(MiddleFactory mf)
  {
    JFrame  window1 = new JFrame();     
    window1.setTitle( "Customer Client (RMI)");
    window1.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new CustomerGUI( window1, mf );
  }
}
