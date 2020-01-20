package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class OrderTest {

    private static final int OKRES = 24;
    private Order order;


    @Before
    public void initialize(){
        order = new Order();
    }


    @Test(expected = OrderExpiredException.class)
    public void ThrowOrderExpiredException() {
        OrderItem item = new OrderItem();
        order.addItem(item);
        order.submit(new DateTime().minus(Period.hours(OKRES + 1)));
        order.confirm();
    }
    @Test
    public void NotThrowOrderExpiredException() {
        OrderItem item = new OrderItem();
        order.addItem(item);
        order.submit(new DateTime().minus(Period.minutes(1)));
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }
}