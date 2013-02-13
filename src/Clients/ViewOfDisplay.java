package Clients;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.*;

import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;

import Clients.Application.Animate;
import Middle.*;

class ViewOfDisplay extends JFrame implements Observer
{
  private static final int H = 600;       // Height of window pixels
  private static final int W = 600;       // Width  of window pixels

  private Animate animation;                // Active object 
  private String  inQue = "Orders in Queue: ";					// J.L 
  private String  toPro = "Orders being Processed: ";			// J.L 
  private String  toCol = "Orders Waiting to be Collected: ";	// J.L 
  
  private String  que1 = "0";	// J.L 
  private String  que2 = "0";	// J.L 
  private String  col1 = "0";	// J.L 
  
  int boxW = W/11;	// J.L 
  int boxH = H/11;	// J.L 
  
  private  Map<String, List<Integer> > state = null;
  
  private synchronized void setState(  Map<String, List<Integer> > aState )//used to atomically set the state of system
  {
	  state = aState;
  }
  
  private synchronized Map<String, List<Integer> > getTheState() // atomically return the state of system
  {
	  return state;
  }
  
  private Font font = new Font("Monospaced",Font.BOLD,24);
  
  public ViewOfDisplay( RootPaneContainer rpc )
  {
    // Add code to setup a graphical view of the display
	  

	    setSize( W, H );                        // Size of drawing area // J.L 
	    setTitle("Display Client (RMI)");		// J.L 
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    animation = new Animate();              // Start text scrolling // J.L 
	    animation.start();
	    setVisible(true);
	    
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
		  
	    g.setPaint( Color.white );              // Paint Colour  // J.L 
	    g.fill( new Rectangle2D.Double( 0, 0, W, H ) ); // J.L 
	    
	    Rectangle2D waitingBorder = new Rectangle2D.Double(10,64,W-20,boxH+20);
	    g.setPaint(new Color(50,50,50));
	    g.fill(waitingBorder);
	    g.setPaint(Color.black);
	    g.draw(waitingBorder);
	    
	    Rectangle2D processingBorder = new Rectangle2D.Double(10,H/4+64,W-20,boxH+20);
	    g.setPaint(new Color(50,50,50));
	    g.fill(processingBorder);
	    g.setPaint(Color.black);
	    g.draw(processingBorder);
	    
	    Rectangle2D collectBorder = new Rectangle2D.Double(10,H/2+64,W-20,boxH+20);
	    g.setPaint(Color.blue);
	    g.fill(collectBorder);
	    g.setPaint(Color.black);
	    g.draw(collectBorder);

	    try{
	    	
	    	Map<String, List<Integer> > state =  getTheState(); //getState
	    	if ( state == null ) return;//if the state is null exit (should only be null momentarily)
	    	
	    	if(state.get("Waiting").size()!=0 ){ //check that the waitinglist is not empty // J.L 
	    for(int i = 0; i<state.get("Waiting").size(); i++){ //inQueBoxes // J.L 
	    	if(i<9){
	    	int xPos = 24 +(i*boxW)+(i*5);
	    	int yPos = 74;
	    	
	    	que1 = Integer.toString(state.get("Waiting").get(i)); // J.L 
	    	
	    	Rectangle2D waitingBox = new Rectangle2D.Double( xPos, yPos, boxW, boxH );
	    	
	    	g.setPaint( Color.gray );             // Paint Colour // J.L 
	    	g.fill( waitingBox ); // inQue Box4 // J.L 
	    	g.setPaint(Color.black);
	    	g.draw( waitingBox ); // inQue Box4 // J.L //draw black broder around boxes

	    	g.setPaint( Color.red );             // Paint Colour  // J.L 
	    	g.drawString( que1, (xPos+(boxW/2))-(que1.length()*6), yPos+36 ); // inQue text // J.L length*6 as 6 is half font size
	    	}
	    }
	    	}
	    
	    	if(state.get("BeingPicked").size()!=0){ //check that the pickinglist is not empty // J.L 
	    for(int i = 0; i<state.get("BeingPicked").size(); i++){ //toProBoxes // J.L 

	    	int xPos = 24 +(i*boxW)+(i*5);
	    	int yPos = H/4+74;
	    	
	    	que2 = Integer.toString(state.get("BeingPicked").get(i)); // J.L 
	    	
	    	Rectangle2D pickingBox = new Rectangle2D.Double( xPos, yPos, boxW, boxH );
	    	
	    	g.setPaint( Color.gray );             // Paint Colour  // J.L 
	    	g.fill( pickingBox ); // toPro Box // J.L 
	    	g.setPaint( Color.black );             // Paint Colour  // J.L 
	    	g.draw( pickingBox ); // toPro Box // J.L //draw black broder around boxes
	    	
	    	g.setPaint( Color.red );             // Paint Colour // J.L 
	    	g.drawString( que2, (xPos+(boxW/2))-(que2.length()*6), yPos+36 ); // inQue text // J.L length*6 as 6 is half font size
	    }
	    	}
	    
	    	if(state.get("ToBeCollected").size()!=0){ //check that the collectinglist is not empty // J.L 
	    for(int i = 0; i<state.get("ToBeCollected").size(); i++){ //toColBoxess // J.L 
	    	if(i<9){
	    	int xPos = 24 +(i*boxW)+(i*5);
	    	int yPos = H/2+74;
	    	
	    	col1 = Integer.toString(state.get("ToBeCollected").get(i)); // J.L 
	    	
	    	Rectangle2D collectBox = new Rectangle2D.Double( xPos, yPos, boxW, boxH );
	    	
	    	if(i<=2){
	    		g.setPaint(Color.green);		// set box Paint Colour green // J.L 
	    	}else{
	    	g.setPaint( Color.orange );             // Paint Colour  // J.L 
	    	}
	    	g.fill( collectBox ); // toCol Box // J.L 
	    	g.setPaint(Color.black);
	    	g.draw( collectBox ); //draw black broder around boxes
	    	g.setPaint( Color.red );             // Paint Colour  // J.L 
	    	g.drawString( col1, (xPos+(boxW/2))-(col1.length()*6), yPos+36 ); // inQue text // J.L length*6 as 6 is half font size
	    	}
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

	    public synchronized int getPos()
	    {
	      return pos;
	    }

	    public synchronized void moveString()   // Move string 
	    {
	      pos--;                                //  New position 
	      if ( pos < -W ) pos = 0;
	    }

	    public void run()                       // Active part 
	    {
	      while ( true )                        // Forever 
	      {                                     // 
	        try                                 // 
	        {                                   // 
	          moveString();                     //  Move mess 
	          repaint();                        //   -> update 
	          Thread.sleep(25);               //  Sleep 
	        }                                   // loop 
	        catch (InterruptedException e) {}   // Ignore any error 
	      }
	    }
	  }
  
  


  public void update( Observable aModelOfDisplay, Object arg )
  {
     // Code to update the graphical display with the current
     //  state of the system
     //  Orders awaiting processing
     //  Orders being picked in the 'warehouse. 
     //  Orders awaiting collection
	  try
	  {
	    setState( ( (ModelOfDisplay)aModelOfDisplay).getOrderState() ); //setState from observable casted as ModelOfDisplay method; getOrderState();
	  } catch ( Exception e ) {}
  }

}
