package Other;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import Catalogue.Product;
import Catalogue.SoldBasket;
import Middle.*;


/**
 * Implements the testing for Order.class.
 * @author  Joseph Loe
 */

public class OrderTest {

	private final int[] ORDERNUMBERS = {5,6,7}; 											// Array of order numbers
	
	private SoldBasket[] theBought = new SoldBasket[3]; 									//Array of SoldBasket's

	private StockReadWriter stockRW = null;													//stock Read Writer
	private OrderProcessing theOrder = null;												//orderProcessing
	private MiddleFactory mf = null;								
	private Product pr = null;																//products
	private Product pr2 = null;																//products
	private Product pr3 = null;																//products
	
	@Test
	public void test() {
		setup();
		testOrder();
	}
	
	public void setup(){
		mf = new LocalMiddleFactory();
		try{
			stockRW = mf.makeStockReadWriter();
			theOrder = mf.makeOrderProcessing();
		}catch(Exception e){
			fail("Stock Exception; "+e.getMessage() );
		}
		
		//a SoldBasket containing 3 products; 0001, 0002, 0003
		
		for(int i=0; i<ORDERNUMBERS.length;i++){  											//populate theBought Array with ORDERNUMBERS[i]
			theBought[i]= new SoldBasket(ORDERNUMBERS[i]);
		}
		
		try{
			pr 	= stockRW.getDetails("0001");  												// Assign pr to 0001
			pr2 = stockRW.getDetails("0002");  												// Assign pr to 0002
			pr3 = stockRW.getDetails("0003");  												// Assign pr to 0003
		}catch(Exception a){
			fail("Stock Exception: "+a.getMessage() );
		}
		
		theBought[0].add(pr);																//add product details to the baskets
		theBought[1].add(pr2);
		theBought[2].add(pr3);
	}
	
	public void testOrder(){
		try{
			
			for(int i =0;i<theBought.length;i++){ 											//input orders to system
			theOrder.newOrder(theBought[i]);
			}
			
			boolean res;
			Map<String,List<Integer>> state = theOrder.getOrderState();
			
			for(int i=0;i<ORDERNUMBERS.length;i++){
			
			theOrder.getOrderToPick(); 														//get order to pick
			System.out.println("Testing Order number; "+ORDERNUMBERS[i]);
			
			//check if ORDER_NUMBER1
			//check if moved to beingProcessedTray
			state=theOrder.getOrderState();
			res = isInList(state.get("BeingPicked"),ORDERNUMBERS[i]);
			assertTrue("Order: "+ORDERNUMBERS[i]+", was not found in BeingPicked list.",res); //if res not inList, FAIL
			
			//check that the ProductNumber is correct
			Integer check = state.get("BeingPicked").get(0);
			assertEquals("wrong", check,(Integer)ORDERNUMBERS[i]);
			
			theOrder.informOrderPicked(ORDERNUMBERS[i]);
			
			//check if moved to waitingToBeCollectedTray
			state = theOrder.getOrderState();
			res = isInList(state.get("ToBeCollected"),ORDERNUMBERS[i]);
			assertTrue("Order: "+ORDERNUMBERS[i]+", was not found in ToBeCollected list.",res);
			
			theOrder.informOrderColected(ORDERNUMBERS[i]);
			
			//check if removed from system
			state = theOrder.getOrderState();
			res = isInList(state.get("BeingPicked"),ORDERNUMBERS[i]);
			assertFalse("Order: "+ORDERNUMBERS[i]+",still in system, was found in BeingPicked list.",res);
			
			state = theOrder.getOrderState();
			res = isInList(state.get("ToBeCollected"),ORDERNUMBERS[i]);
			assertFalse("Order: "+ORDERNUMBERS[i]+", still in system, was found in ToBeCollected list.",res);

			}

			}catch(Exception c){
				fail("Exception: "+c.getMessage());
			}
	}

	private boolean isInList(List<Integer> orderNumbers, int theNumber) {
		// Search through input List, return true if == the Number
		boolean res2=false;
		
		for(Integer n: orderNumbers){  														//loop through ordernumbers list 
			if(n==theNumber){  																//if ordernumber[n] == theNumber, return true
				res2=true;
			}
		}
		
		return res2;
	}
}
