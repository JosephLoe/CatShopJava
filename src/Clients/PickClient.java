package Clients;

import javax.swing.*;

import Middle.*;

/**
 * The Display Client.
 * @author  Michael Alexander Smith
 * @version 2.0
 */


public class PickClient
{
   public static void main (String args[])
   {
     String stockURL = args.length < 1     // URL of stock RW
                     ? Names.STOCK_RW      //  default  location
                     : args[0];            //  supplied location
     String orderURL = args.length < 2     // URL of order
                     ? Names.ORDER         //  default  location
                     : args[1];            //  supplied location
     
    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
    mrf.setStockRWInfo( stockURL );
    mrf.setOrderInfo  ( orderURL );        //
    displayGUI(mrf);                       // Create GUI
  }
  
  public static void displayGUI(MiddleFactory mf)
  {     
    JFrame  window = new JFrame();
     
    window.setTitle( "Pick Client (RMI)");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new PickGUI( window, mf );
  }
}
