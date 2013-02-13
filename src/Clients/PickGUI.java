package Clients;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Catalogue.*;
import Middle.*;

/**
 * Implements the GUI for the Pick client.
 * @author  Michael Alexander Smith
 * @version 2.1
 */

class PickGUI
{

  private static final String PICKED = "Picked";

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private JLabel      theAction  = new JLabel();
  private JTextArea   theOutput  = new JTextArea();
  private JScrollPane theSP      = new JScrollPane();
  private JButton     theBtPicked= new JButton( PICKED );
 
  private Transaction     theCB        = new Transaction();
  private OrderProcessing theOrder     = null;

  public PickGUI(  RootPaneContainer rpc, MiddleFactory mf  )
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtPicked.setBounds( 16, 25+60*0, 80, 40 );   // Check Button
    theBtPicked.addActionListener( theCB );         // Listener
    cp.add( theBtPicked );                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    
    BackGroundCheck bc = new BackGroundCheck();
    bc.start();
  }
  
  
  StateOfPick theState = new StateOfPick();
  
  class BackGroundCheck extends Thread
  {
    public void run()
    {
      while ( true )
      {
        try
        {
          boolean claimed = theState.claim();      // 
          if ( claimed )                           // Can we use
          {                                        // T
            SoldBasket sb = 
              theOrder.getOrderToPick();           // Waiting
            if ( sb != null )                      // Items to pick
            {                                      // T
              theState.store( sb );                //  Remember
              theOutput.setText( sb.getDetails() );//  Display
              theAction.setText("Item to pick");   //   what to do
            } else {                               // F
              theState.release();                  //  Free
            }
          }
          sleep(2000);                             // delay
        } catch ( Exception err )
        {
        }
      }
    }
  }


  

  class Transaction implements ActionListener       // Listener
  {
    public void actionPerformed( ActionEvent ae )   // Interaction
    {
      if ( theOrder == null )
      {
        theAction.setText("No connection");
        return;                                     // No connection
      }
      String actionIs = ae.getActionCommand();      // Button
      try
      {
        if ( actionIs.equals( PICKED ) )            // Button PICKED
        {
          boolean beingPicked = theState.inUse();   // State being picked
          if ( beingPicked )                        // T
          {
            SoldBasket sb = theState.release();     // Basket picked
            if ( sb != null )                       // No re-entrancy
            {                                       // T
              int no = sb.getOrderNo();             //  Order no
              theOrder.informOrderPicked( no );     //  Tell system
              theAction.setText("");                //  Inform picker
              theOutput.setText("");                //
              //DisplayGUI.test();
            }
          } else {                                  // F 
            theAction.setText("No order to pick");  //   Not picked order
          }
        }
      }
      catch ( OrderException e )                    // Error
      {                                             //  Of course
        theOutput.append( "Fail Order process:" +   //   Should not
                            e.getMessage() + "\n" );//  happen
      }
    }
  }

  class StateOfPick
  {
    private SoldBasket soldbasket = null;
    private int held = 0;
    
    /**
     * Claim exclusive access
     * @return true if claimed else false
     */
    
    public synchronized boolean claim()           // Semaphore
    {
      if ( held > 0 ) 
      {
        return false;
      } else {
        held = 1;
        return true;
      }
    }
    
    public synchronized boolean inUse()           // Calimed
    {
      return held == 1;
    }
    
    /**
     * Release resource and return held value
     * @return saved SoldBasket or null
     */
    
    public synchronized SoldBasket release()     //
    {
      assert held > 0;
      held = 0;
      SoldBasket tmp = soldbasket;
      soldbasket = null;
      return tmp;
    }
    
    /**
     * Store an instance of a SoldBasket
     * Can only be called if we hold resource
     */
    
    public synchronized void store( SoldBasket sb )
    {
      assert held > 0 && sb != null;
      soldbasket = sb;
    }

  }
}

