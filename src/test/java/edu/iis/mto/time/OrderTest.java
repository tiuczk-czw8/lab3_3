package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class OrderTest {

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredException() {
        OrderItem item = new OrderItem();
        order.addItem(item);
        order.submit(new DateTime().minus(Period.hours(26)));
        order.confirm();
    }

    @Test
    public void testValidHours() {
        OrderItem item = new OrderItem();
        order.addItem(item);
        order.submit(null);
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), equalTo(Order.State.REALIZED));
    }

}
