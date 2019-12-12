package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


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


    @Test
    public void doesItNotThrowOrderExpiredException() {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.minutes(1)));
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }

}