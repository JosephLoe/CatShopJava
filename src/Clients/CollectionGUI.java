
////

package Clients;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import Middle.*;

/**
 * Implements the GUI for the Collection client.
 * @author  Michael Alexander Smith
 * @version 2.1
 */

class CollectionGUI
{

  private static final String COLLECT = "Collect";
  
  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private JLabel      theAction  = new JLabel();
  private JTextField  theInput   = new JTextField();
  private JTextArea   theOutput  = new JTextArea();
  private JScrollPane theSP      = new JScrollPane();
  private JButton     theBtPicked= new JButton( COLLECT );
 
  private Transaction     theCB        = new Transaction();
  private OrderProcessing theOrder     = null;

  public CollectionGUI(  RootPaneContainer rpc, MiddleFactory mf  )
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

    theBtPicked.setBounds( 16, 25+60*0, 80, 40 );    // Check Button
    theBtPicked.addActionListener( theCB );          // Listener
    cp.add( theBtPicked );                           //  Add to canvas

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
      if ( theOrder == null )
      {
        theAction.setText("No connection");
        return;                                     // No connection
      }
      String actionIs = ae.getActionCommand();      // Button
      try
      {
        if ( actionIs.equals( COLLECT ) )           // Button Collect
        {
        	int orderNo = 0;
            String on  = theInput.getText().trim(); // Product no.
            try
            {
              orderNo = Integer.parseInt(on);       // Convert
            }
            catch ( Exception err) {}

            boolean ok = 
             theOrder.informOrderColected(orderNo);
            if ( ok )
            {
              
              theOutput.append("Collected order #" + orderNo + "\n");
            }
            else
            {
              theAction.setText("No such order");
            }
                     
        }
        theInput.requestFocus();                     // theInput has Focus
      }

      catch ( OrderException e )                     // Error
      {                                              //  Of course
        theOutput.append( "Fail Order process:" +    //   Should not
                            e.getMessage() + "\n" ); //  happen
      }
    }
  }

}




