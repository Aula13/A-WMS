package org.wms.model.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Order Unit Testing Class
 * 
 * @author Daniele Ciriello, Stefano Pessina
 *
 */
public class OrderUnitTest {
	
	/**
	 * The object under test
	 */
	private Order orderTest;
	
	/**
	 * The under test order's Identification number
	 */
	private long orderId;
	/**
	 * The order's emission date
	 */
	private Date orderEmissionDate;
	/**
	 * The order's priority
	 */
	private Priority orderPriority;
	/**
	 * The order's materials set
	 */
	private Set<OrderRow> orderMaterials;
	
	/**
	 * Initialize the parameter to be passed to the order's constructors
	 */
	@Before
	public void preareForTest(){
		orderId = 0;
		orderEmissionDate = new Date();
		orderPriority = Priority.HIGH;
		orderMaterials = new HashSet<>();
		
	}

	/**
	 * Basic constructor test
	 */
	@Test
	public void constructorTest() {
		orderTest = new Order(orderId, orderEmissionDate);
		
		assertThat(orderTest.getId(), is(equalTo(orderId)));
		assertThat(orderTest.getEmissionDate(), is(equalTo(orderEmissionDate)));
		
		assertThat(orderTest.getPriority(), is(equalTo(Priority.LOW)));
		assertThat(orderTest.getMaterials(), is(notNullValue()));
		checkOrderInit();
	}
	
	/**
	 * test constructor with order's priority
	 */
	@Test
	public void priorityConstructorTest() {
		orderTest = new Order(orderId, orderEmissionDate, orderPriority);
		
		assertThat(orderTest.getId(), is(equalTo(orderId)));
		assertThat(orderTest.getEmissionDate(), is(equalTo(orderEmissionDate)));
		assertThat(orderTest.getPriority(), is(equalTo(orderPriority)));
		
		assertThat(orderTest.getMaterials(), is(notNullValue()));
		checkOrderInit();
	}
	
	/**
	 * test constructor with order's priority and material set
	 */
	@Test
	public void materialsConstructorTest() {
		orderTest = new Order(orderId, orderEmissionDate, orderPriority, orderMaterials);
		
		assertThat(orderTest.getId(), is(equalTo(orderId)));
		assertThat(orderTest.getEmissionDate(), is(equalTo(orderEmissionDate)));
		assertThat(orderTest.getPriority(), is(equalTo(orderPriority)));
		assertSame(orderTest.getMaterials(), orderMaterials);
		checkOrderInit();
	}
	
	/**
	 * check the order's standard initial values
	 */
	private void checkOrderInit(){
		assertThat(orderTest.getCompletePercentual(), is(equalTo(0.0f)));
		assertThat(orderTest.getAllocationPercentual(), is(equalTo(0.0f)));
		assertThat(orderTest.getOrderStatus(), is(equalTo(OrderStatus.WAITING)));
		assertThat(orderTest.getDoneDate(), is(equalTo(null)));
	}
}
