package Clients;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

import Catalogue.*;
import Middle.*;

/**
 * Implements the GUI for the Customer client.
 * @author  Michael Alexander Smith
 * @version 2.0
 */


class CustomerGUI
{
  class Name                              // Names of buttons
  {
    public static final String CHECK  = "Check";
    public static final String CLEAR  = "Clear";
  }

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private JLabel      theAction  = new JLabel();
  private JTextField  theInput   = new JTextField();
  private JTextArea   theOutput  = new JTextArea();
  private JScrollPane theSP      = new JScrollPane();
  private JButton     theBtCheck = new JButton( Name.CHECK );
  private JButton     theBtClear = new JButton( Name.CLEAR );

  private Picture     thePicture = new Picture(80,80);
  private Transaction theCB      = new Transaction();
  private Basket      theBasket  = new Basket();
  private StockReader theStock   = null;


  private NumberFormat theMoney  =
          NumberFormat.getCurrencyInstance( Locale.UK );

  public CustomerGUI( RootPaneContainer rpc, MiddleFactory mf )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReader();             // DataBase Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check button
    theBtCheck.addActionListener( theCB );          //  Listener
    cp.add( theBtCheck );                           //  Add to canvas

    theBtClear.setBounds( 16, 25+60*1, 80, 40 );    // Clear button
    theBtClear.addActionListener( theCB );          //  Listener
    cp.add( theBtClear );                           //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        //  Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Product no area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    thePicture.setBounds( 16, 25+60*2, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();
    
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
      String actionIs = ae.getActionCommand();      // Which button
      String pn  = theInput.getText().trim();       // product no.
      int    amount  = 1;                           //  & quantity

      try
      {
        if ( actionIs.equals( Name.CHECK ) )        // Button CHECK
        {
          thePicture.clear();                       // Clear picture
          if ( theStock.exists( pn ) )              // Stock Exists?
          {                                         // T
            Product pr = theStock.getDetails( pn ); //  Product
            if ( pr.getQuantity() >= amount )       //  In stock?
            {                                       //  T
              theAction.setText(                    //   Display 
                pr.getDescription() + " : " +       //    description
                theMoney.format(pr.getPrice()) +    //    price
                " (" + pr.getQuantity() + ")"       //    quantity
              );                                    //   of product
              pr.setQuantity( amount );             //   Require 1
              theBasket.add( pr );                  //   Add to basket
              theOutput.setText(                    //   Display
                theBasket.getDetails() );           //   shopping list
              thePicture.set(                       //   Picture of
                theStock.getImage( pn ) );          //    product
            } else {                                //  F
              theAction.setText(                    //   Inform
                pr.getDescription() +               //    product not
                " not in stock"                     //    in stock
              );
            }
          } else {                                  // F
            theAction.setText(                      //  Inform Unknown
              "Unknown product number " + pn        //  product number
            );
          }
        }
  
        if ( actionIs.equals( Name.CLEAR ) )        // Button CLEAR
        {
          theBasket.clear();                        // Clear s. list
          theAction.setText("Enter Product Number");// Set display
          theOutput.setText( "" );                  //  new customer
          thePicture.clear();                       // Clear picture
        }
        
        theInput.requestFocus();                    // Focus        
      }
      catch ( StockException e )                    // Error
      {                                             //  Of course
        theOutput.append( "Error:" + "\n" +         //  Should not
                            e.getMessage() + "\n" );//  happen
      }
    }
    
  }
  
}


