package Clients;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.*;
import Middle.*;
import DBAccess.*;

/**
 * Starts the Customer Client & Cashier client as a single application.
 * Good for testing the system using a single process and no RMI.
 * @author  Joseph Loe
 * @version 2.0
 */

class Main
{
  public static void main (String args[])
  {
    MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
    displayCustomerGUI( mlf );
    displayCashierGUI( mlf );
    displayBackDoorGUI( mlf );
    displayPickGUI( mlf );             // 2 Pick GUI's
    displayPickGUI( mlf );
    displayDisplayGUI( mlf );  //Dispaly GUI
    displayCollectionGUI( mlf );    
  }

  public static void displayCustomerGUI(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Customer Client");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new CustomerGUI( window, mlf );
 
  }
  public static void displayCashierGUI(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Cashier Client");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new CashierGUI( window, mlf ); 
  
  }

  public static void displayBackDoorGUI(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "BackDoor Client");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new BackDoorGUI( window, mlf ); 
    window.setLocationRelativeTo(null);
  }

  public static void displayPickGUI(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Pick Client");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new PickGUI( window, mlf ); 
  }

  public static void displayDisplayGUI(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Display Client");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new DisplayGUI( window, mlf ); 
  }

  public static void displayCollectionGUI(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Collection Client");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    new CollectionGUI( window, mlf ); 
  }
  
  // Repeat for other clients
}
