package Clients;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Catalogue.*;
import Middle.*;
import Processing.Order;

/**
 * Implements the GUI for the Display Client.
 * @author  Joseph Loe
 */

class CopyOfDisplayGUI extends JFrame
{	
	
	  public static MiddleFactory mf2; // J.L 

	public CopyOfDisplayGUI( RootPaneContainer rpc, MiddleFactory mf ){
                    mf2 = mf;                     // J.L 

		  try                                           //  
		    {      
		      
		      (new Application(mf)).setVisible(true);   // Start application // J.L 		   
		      
		    } catch ( Exception e )
		    {
		      System.out.println("Exception: " + e.getMessage() );
		    }

    
	  }// 
}

/**
 * Implements the Jframe for displayGUI.
 * @author  Joseph Loe
 */
class Application extends JFrame 
{
	MiddleFactory mf3 = CopyOfDisplayGUI.mf2;// J.L 
	
	private OrderProcessing theOrder = null;
	
	{
		try{
			  if(theOrder == null){
				theOrder = mf3.makeOrderProcessing();        // Process order // J.L 
			  }
			}catch( Exception a)
			{
				System.out.println("error: " + a.getMessage() );
			}
		  }
		
	
  private static final int H = 600;         // Height of window 
  private static final int W = 600;         // Width  of window 

  private Animate animation;                // Active object 
  private String  inQue = "Orders in Queue: ";					// J.L 
  private String  toPro = "Orders being Processed: ";			// J.L 
  private String  toCol = "Orders Waiting to be Collected: ";	// J.L 
  
  private String  que1 = "0";	// J.L 
  private String  que2 = "0";	// J.L 
  private String  col1 = "0";	// J.L 
  
  int boxW = W/11;	// J.L 
  int boxH = H/11;	// J.L 
  
  private Font font = new Font("Monospaced",Font.BOLD,24);
  
  boolean inPlace =false;
  

  public Application(MiddleFactory mf)
  {
	  

    setSize( W, H );                        // Size of drawing area // J.L 
    setTitle("Display Client (RMI)");		// J.L 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    animation = new Animate();              // Start text scrolling // J.L 
    animation.start();
  }

  public void update( Graphics g )          // Called by repaint 
  {                                         // 
    drawScreen( (Graphics2D) g );           // Draw Screen 
  }

  public void paint( Graphics g )           // When 'Window' is first 
  {                                         //  shown or damaged 
    drawScreen( (Graphics2D) g );           // Draw Screen 
  }

  private Dimension     theAD;              // Alternate Dimension 
  private BufferedImage theAI;              // Alternate Image 
  private Graphics2D    theAG;              // Alternate Graphics 

  public void drawScreen( Graphics2D g )    // Double buffer 
  {                                         //  allow resize 
    Dimension d    = getSize();             // Size of image 

    if (  ( theAG == null )  ||
          ( d.width  != theAD.width ) ||
          ( d.height != theAD.height ) )
    {                                       // New size 
      theAD = d;
      theAI = (BufferedImage) createImage( d.width, d.height );
      theAG = theAI.createGraphics();
      AffineTransform at = new AffineTransform();
      at.setToIdentity();
      at.scale( ((double)d.width)/W, ((double)d.height)/H );
      theAG.transform(at);
    }

    drawActualScreen( theAG );               // Draw actual screen 
    g.drawImage( theAI, 0, 0, this );
  }

  public void drawActualScreen( Graphics2D g )// Actual draw screen 
  {
	  
	  int offset = animation.getPos();  // J.L 
	  int boxOffset = animation.getBoxPos();
	  
    g.setPaint( Color.white );              // Paint Colour  // J.L 
    g.fill( new Rectangle2D.Double( 0, 0, W, H ) ); // J.L 

    try{
    	
    	if(getWaiting().size()!=0){ //check that the waitinglist is not empty // J.L 
    for(int i = 0; i<getWaiting().size(); i++){ //inQueBoxes // J.L 

    	que1 = Integer.toString(getWaiting().get(i)); // J.L 
    	
    	g.setPaint( Color.gray );             // Paint Colour // J.L 
    	g.fill( new Rectangle2D.Double( 24 +(i*boxW)+i, 74, boxW, boxH ) ); // inQue Box4 // J.L 

    	g.setPaint( Color.red );             // Paint Colour  // J.L 
    	g.drawString( que1, 24 +(i*boxW)+i+23, 110 ); // inQue text // J.L 
    
    }
    	}
    	
    	if(getPicking().size()!=0){ //check that the pickinglist is not empty // J.L 
    for(int i = 0; i<getPicking().size(); i++){ //toProBoxes // J.L 
    	
    	
    	
    	que2 = Integer.toString(getPicking().get(i)); // J.L 
    	
    	g.setPaint( Color.gray );             // Paint Colour  // J.L 
    	g.fill( new Rectangle2D.Double( 24 +(i*boxW)+i, H/4+74, boxW, boxH ) ); // toPro Box // J.L 
    	g.setPaint( Color.red );             // Paint Colour // J.L 
    	/*if(W+offset>24 +(i*boxW)+i+23){
    		g.drawString( que2, W+offset, H/4+110 ); // inQue2 text // J.L 
    	}*/
    	int endPos = 24 +(i*boxW)+i+23;
    	if(boxOffset+W>endPos){
    	animation.boxStartEnd(W-endPos);
    	g.drawString( que2, boxOffset+W, H/4+110 ); // inQue2 text // J.L 
    	}
    	g.drawString( que2, 24 +(i*boxW)+i+23, H/4+110 ); // inQue2 text // J.L 
    	//g.drawString( que2, 24 +(i*boxW)+i+23, H/4+110 ); // inQue2 text // J.L 
    }
    	}
    
    	if(getCollecting().size()!=0){ //check that the collectinglist is not empty // J.L 
    for(int i = 0; i<getCollecting().size(); i++){ //toColBoxess // J.L 

    	col1 = Integer.toString(getCollecting().get(i)); // J.L 
    	
    	g.setPaint( Color.gray );             // Paint Colour  // J.L 
    	g.fill( new Rectangle2D.Double( 24 +(i*boxW)+i, H/2+74, boxW, boxH ) ); // toCol Box // J.L 
    	g.setPaint( Color.red );             // Paint Colour  // J.L 
    	g.drawString( col1, 24 +(i*boxW)+i+23, H/2+110 ); // incol1 text // J.L 
    }
    	}
    	
    }catch( Exception b) // J.L 
    {
    	System.out.println("Error: "+b.getMessage() ); // J.L 
    }

    g.setPaint( Color.black );              // Paint Colour 
    g.setFont ( font );

    g.drawString( inQue, offset, 50 );			// J.L 
    g.drawString( inQue, W + offset, 50 );		// J.L 
    
    g.drawString( toPro, offset, H/4+50 );		// J.L 
    g.drawString( toPro, W + offset, H/4+50 );	// J.L 

    g.drawString( toCol, offset, H/2+50 );		// J.L 
    g.drawString( toCol, W + offset, H/2+50 );	// J.L 


  }

  class Animate extends Thread
  {
    private int       pos = 1;              // 
    private int			boxPos=1;
    private int			stopPos = 1;

    public synchronized int getPos()
    {
      return pos;
    }
    public synchronized int getBoxPos(){//JL
      return boxPos;
    }
    public synchronized void resetBoxPos(){//JL
        boxPos=1;
      }
    
    public synchronized void boxStartEnd(int end){//JL
        stopPos=-end;
      }

    public synchronized void moveString()   // Move string 
    {
      pos--;                                //  New position 
      if ( pos < -W ) pos = 0;
    }
    public synchronized void moveBoxes(){//JL
    	if(stopPos != 1){
    		if(boxPos>stopPos){
    			boxPos-=5;
    		} else {
    			resetBoxPos();
    		}
    	}
    }

    public void run()                       // Active part 
    {
      while ( true )                        // Forever 
      {                                     // 
        try                                 // 
        {                                   // 
          moveString();                     //  Move mess 
          moveBoxes();						// move box JL
          repaint();                        //   -> update 
          Thread.sleep( 25 );               //  Sleep 
        }                                   // loop 
        catch (InterruptedException e) {}   // Ignore any error 
      }
    }
  }
  public synchronized Map<String, List<Integer> > getOrderState() throws OrderException{
	  
	    return theOrder.getOrderState();// J.L 
	    
	  }
  public synchronized List<Integer> getWaiting() throws OrderException{
	  
	return getOrderState().get("Waiting");// J.L 
	 
  }
  public synchronized List<Integer> getPicking() throws OrderException{
	  
		return getOrderState().get("BeingPicked");// J.L 
		  
	  }
  public synchronized List<Integer> getCollecting() throws OrderException{
	  
		return getOrderState().get("ToBeCollected");// J.L 
		  
	  }
  
}
  
