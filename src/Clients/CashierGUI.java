package Clients;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

import Catalogue.*;
import Middle.*;

/**
 * Implements the GUI for the Cashier client.
 * @author  Michael Alexander Smith
 * @version 2.1
 */

class CashierGUI
{
  private enum State { process, checked }

  private static final String CHECK  = "Check";
  private static final String BUY    = "Buy";
  private static final String CANCEL = "Cancel";
  private static final String BOUGHT = "Bought";
 

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private JLabel      theAction  = new JLabel();
  private JTextField  theInput   = new JTextField();
  private JTextArea   theOutput  = new JTextArea();
  private JScrollPane theSP      = new JScrollPane();
  private JButton     theBtCheck = new JButton( CHECK );
  private JButton     theBtBuy   = new JButton( BUY );
  private JButton     theBtCancel= new JButton( CANCEL );
  private JButton     theBtBought= new JButton( BOUGHT );

  private State       theState   = State.process;   // Current state
  private Product     theProduct = null;            // Current product
  private SoldBasket  theBought  = null;            // Bought items

  private Transaction     theCB        = new Transaction();
  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;

  private NumberFormat theMoney  =
          NumberFormat.getCurrencyInstance( Locale.UK );
  
  public CashierGUI(  RootPaneContainer rpc, MiddleFactory mf  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // DataBase access
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

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check Button
    theBtCheck.addActionListener( theCB );          // Listener
    cp.add( theBtCheck );                           //  Add to canvas

    theBtBuy.setBounds( 16, 25+60*1, 80, 40 );      // Buy button 
    theBtBuy.addActionListener( theCB );            //  Listener
    cp.add( theBtBuy );                             //  Add to canvas

    theBtCancel.setBounds( 16, 25+60*2, 80, 40 );   // Cancel Button
    theBtCancel.addActionListener( theCB );         //  Listener
    cp.add( theBtCancel );                          //  Add to canvas

    theBtBought.setBounds( 16, 25+60*3, 80, 40 );   // Clear Button
    theBtBought.addActionListener( theCB );         //  Listener
    cp.add( theBtBought );                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
  }


  class Transaction implements ActionListener       // Listener
  {
    public void actionPerformed( ActionEvent ae )   // Interaction
    {
      if ( theStock == null )
      {
        theAction.setText("No connection");
        return;                                     // No connection
      }
      String actionIs = ae.getActionCommand();      // Button
      try
      {
        if ( theBought == null )
        {                                           // 
          int on    = theOrder.uniqueNumber();      // Unique order no.
          theBought = new SoldBasket( on );         //  Bought list
        }
        
        if ( actionIs.equals( CHECK ) )             // Button CHECK
        {
          theState  = State.process;                // State process
          String pn  = theInput.getText().trim();   // Product no.
          int    amount  = 1;                       //  & quantity
  
          if ( theStock.exists( pn ) )              // Stock Exists?
          {                                         // T
            Product pr = theStock.getDetails(pn);   //  Get details
            if ( pr.getQuantity() >= amount )       //  In stock?
            {                                       //  T
              theAction.setText(                    //   Display 
                pr.getDescription() + " : " +       //    description
                theMoney.format(pr.getPrice()) +    //    price
                " (" + pr.getQuantity() + ")"       //    quantity
              );                                    //   of product
              theProduct = pr;                      //   Remember prod.
              theProduct.setQuantity( amount );     //    & quantity
              theState = State.checked;             //   OK await BUY 
            } else {                                //  F
              theAction.setText(                    //   Not in Stock
                pr.getDescription() +" not in stock"
              );
            }
          } else {                                  // F Stock exists
            theAction.setText(                      //  Unknown
              "Unknown product number " + pn        //  product no.
            );
          }
        }
  
        if ( actionIs.equals( BUY ) )               // Button BUY
        {
  
          if ( theState != State.checked )          // Not checked
          {                                         //  with customer
            theAction.setText("Check if OK with customer first");
            return;
          }
          
          boolean stockBought =                      // Buy
            theStock.buyStock(                       //  however
              theProduct.getProductNo(),             //  may fail              
              theProduct.getQuantity() );            //
          if ( stockBought )                         // Stock bought
          {                                          // T
            theBought.add( theProduct );             //  Add to bought
            theOutput.setText( "" );                 //  clear
            theOutput.append( theBought.getDetails());//  Display
            theAction.setText("Purchased " +         //    details
                       theProduct.getDescription()); //
//          theInput.setText( "" );
          } else {                                   // F
            theAction.setText("!!! Not in stock");   //  Now no stock
          }
          theState = State.process;                  // All Done
        }
  
  
        if ( actionIs.equals( CANCEL ) )            // Button CANCEL
        {
          if ( theBought.number() >= 1 )             // item to cancel
          {                                          // T
            Product dt =  theBought.remove();        //  Remove from list
            theStock.addStock( dt.getProductNo(),    //  Re-stock
                               dt.getQuantity()  );  //   as not sold
            theAction.setText("");                   //
            theOutput.setText(theBought.getDetails());//  display sales
          } else {                                   // F
            theOutput.setText( "" );                 //  Clear
          }
          theState = State.process;
        }
  
        if ( actionIs.equals( BOUGHT ) )             // Button Bought
        { 
          if ( theBought.number() >= 1 )             // items > 1
          {                                          // T
            theOrder.newOrder( theBought );          //  Process order
            theBought = null;                        //  reset
         //   Application.queAL[0]="2";
          }                                          //
          theOutput.setText( "" );                   // Clear
          theInput.setText( "" );                    //
          theAction.setText( "Next customer" );      // New Customer
          theState = State.process;                  // All Done
          
        }
  
        theInput.requestFocus();                     // theInput has Focus

      }
      catch ( StockException e )                     // Error
      {                                              //  Of course
        theOutput.append( "Fail Stock access:" +     //   Should not
                            e.getMessage() + "\n" ); //  happen
      }
      catch ( OrderException e )                     // Error
      {                                              //  Of course
        theOutput.append( "Fail Order process:" +    //   Should not
                            e.getMessage() + "\n" ); //  happen
      }
    }
  }

}
