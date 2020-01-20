package edu.iis.mto.time;


import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrderTest {
    private Order order = new Order();
    private OrderItem orderItem = new OrderItem();
    @Test(expected = OrderExpiredException.class)
    public void testOrderExpiredExeption(){
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(25)));
        order.confirm();
    }
    @Test
    public void testValidTime(){
        order.addItem(orderItem);
        order.submit(null);
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), equalTo(Order.State.REALIZED));
    }

}
