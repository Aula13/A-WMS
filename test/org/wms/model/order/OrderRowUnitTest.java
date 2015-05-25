package org.wms.model.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the OrderRow and Material objects
 * 
 * @author Daniele Ciriello, Stefano Pessina
 *
 */
public class OrderRowUnitTest {
	
	/**
	 * The order row under test
	 */
	private OrderRow orderRowTest;
	
	/**
	 * The order row's Order
	 */
	private Order order;
	/**
	 * The order row's orderId
	 */
	private long orderId;
	/**
	 * The order row's material
	 */
	private Material material;
	/**
	 * The order row's quantity
	 */
	private int quantity;
	
	/**
	 * Initialize the parameter to be passed to the order row's constructors
	 */
	@Before
	public void preareForTest(){
		orderId = 1000;
		order = new Order(orderId, new Date());
		material = new Material();
		quantity = 10;
	}

	/**
	 * Basic constructor test
	 */
	@Test
	public void constructorTest(){
		orderRowTest = new OrderRow(order, material, quantity);
		
		assertSame(orderRowTest.getOrder(), order);
		assertSame(orderRowTest.getMaterial(), material);
		assertSame(orderRowTest.getQuantity(), quantity);		
	}
	
	/**
	 * Test if the quantity is being set correctly
	 */
	@Test
	public void setQuantitytest(){
		orderRowTest = new OrderRow(order, material, quantity);
		
		int newQuantity = quantity*10;
		orderRowTest.setQuantity(newQuantity);
		assertThat(orderRowTest.getQuantity(), is(equalTo(newQuantity)));
	}
	
	
}
