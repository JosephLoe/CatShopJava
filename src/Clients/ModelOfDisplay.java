package Clients;

import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;

import Middle.*;

// File is complete but not optimal
//  Will force update of display every 2 seconds
//  Could be clever & only ask for an update of the display 
//   if it really has changed

class ModelOfDisplay extends Observable implements Runnable
{
  private OrderProcessing theOrder = null;

  public ModelOfDisplay( MiddleFactory mf  )
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    
  }

  
  
  public void run()
  {
    while ( true )                               // Forever                    
    {
     try
      {
        Thread.sleep(2000);
        setChanged(); notifyObservers();
      }
      catch ( InterruptedException err )
      {
      }
    }
  }
  
 // Will be called by the viewOfDisplay
 //   when it is told that the view has changed
 public synchronized Map<String, List<Integer> > getOrderState()
       throws OrderException
 {
   return theOrder.getOrderState();
 }

}
