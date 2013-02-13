package Clients;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Catalogue.*;
import Middle.*;

/**
 * Implements the GUI for the Customer client.
 * @author  Michael Alexander Smith
 * @edited_by Joseph Loe
 * @version 2.0
 */


class BackDoorGUI
{
  class Name                              // Names of buttons
  {
    public static final String ADD    = "Add"; //----J.L
  }

  private static final int H = 165;       // Height of window pixels //----J.L
  private static final int W = 350;       // Width  of window pixels //----J.L

  private JLabel      theAction  = new JLabel(); //----J.L
  private JLabel      theLabel   = new JLabel(); //----J.L
   
  private JTextField  prodID     = new JTextField(); //----J.L
  private JTextArea   theOutput  = new JTextArea(); //----J.L
  private JScrollPane theSP      = new JScrollPane(); //----J.L
  private JButton     theBtAdd   = new JButton( Name.ADD ); //----J.L
  private JSpinner 	  spinner    = new JSpinner(new SpinnerNumberModel(0,0,1000,1)); //----J.L

  private Transaction theCB      = new Transaction();
  private StockReadWriter theStock   = null; //----J.L

  public BackDoorGUI( RootPaneContainer rpc, MiddleFactory mf )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReadWriter();             // DataBase Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    spinner.setBounds(new Rectangle(200, 25, 80 , 40)); //add spinner //----J.L
    cp.add( spinner );  //----J.L
    
    theBtAdd.setBounds( 16, 25, 80, 40 );    // Add button //----J.L
    theBtAdd.addActionListener( theCB );          //  Listener //----J.L
    cp.add( theBtAdd );                      //  Add to canvas //----J.L

    theLabel.setBounds( 110, 5 , 270, 20 );       // Message area //----J.L
    theLabel.setText( "Product     |    Quantity" );   //label text //----J.L
    cp.add( theLabel );                            //  Add to canvas //----J.L
    
    theAction.setBounds( 16, 0 , 270, 20 );       // Message area //----J.L
    theAction.setText( "" );                        //  Blank //----J.L
    cp.add( theAction );                            //  Add to canvas //----J.L

    prodID.setBounds( 110, 25, 80, 40 );         // Product no area //----J.L
    prodID.setText("");                           // Blank //----J.L
    cp.add( prodID );                             //  Add to canvas //----J.L
    
    theSP.setBounds( 16 , 75, 270, 40 );          // Scrolling pane //----J.L
    theOutput.setText( "" );                        //  Blank //----J.L
    theOutput.setFont( f );                         //  Uses font   //----J.L
    cp.add( theSP );                                //  Add to canvas //----J.L
    theSP.getViewport().add( theOutput );           //  In TextArea //----J.L
    
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
      String pn  = prodID.getText().trim();       // product no. //----J.L

      try
      {
        
        if ( actionIs.equals( Name.ADD ) )        // Button ADD //----J.L
        {
        	 if ( theStock.exists( pn ) )              // Stock Exists? //----J.L
        	 {
          theAction.setText("Added");				// Set display //----J.L
          
          Integer currentValue = (Integer)spinner.getValue(); // get spinner value //----J.L
           
          theStock.addStock(pn, currentValue);  // add the input to stock //----J.L
          Product prod = theStock.getDetails(pn); //get details of input product //----J.L
          int prodAmount = prod.getQuantity(); //get quantity of product in stock //----J.L
          
          
          theOutput.setText( "Product" + " | " + "Stock \n");      //----J.L
          theOutput.append (     pn + "    | " + prodAmount);   //----J.L
          
        	 } else {                                  // //----J.L
                 theAction.setText("Error"); //----J.L
                 theOutput.setText("Unknown product number: " + pn); //----J.L
        	 }
        }
        
        prodID.requestFocus();                    // Focus        
      }
      catch ( StockException e )                    // Error
      {                                             //  Of course
        theOutput.append( "Error:" + "\n" +         //  Should not
                            e.getMessage() + "\n" );//  happen
      }
    }
    
  }
  
}


