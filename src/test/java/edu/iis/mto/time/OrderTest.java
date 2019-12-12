package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;


public class OrderTest {

    private static final int VALID_TIME = 24;
    private Order order;

    @Before
    public void init(){
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void doesItThrowOrderExpiredException() {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(VALID_TIME + 1)));
        order.confirm();
    }

}