package edu.iis.mto.time;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import edu.iis.mto.time.Order.State;

public class OrderTest {

    private Order order = new Order();
    private OrderItem orderItem = new OrderItem();

    @Test(expected = OrderExpiredException.class)
    public void testExpiredOrder() {
        order.addItem(orderItem);
        order.submit(new DateTime().minusHours(Order.VALID_PERIOD_HOURS + 1));
        order.confirm();
    }

    @Test
    public void testOrderWithinTime() {
        order.addItem(orderItem);
        order.submit(new DateTime().minusHours(Order.VALID_PERIOD_HOURS - 1));
        order.confirm();
        order.realize();

        assertEquals(order.getOrderState(), State.REALIZED);
    }

    @Test
    public void testOrderJustWithinTime() {
        order.addItem(orderItem);
        order.submit(new DateTime().minusHours(Order.VALID_PERIOD_HOURS));
        order.confirm();
        order.realize();

        assertEquals(order.getOrderState(), State.REALIZED);
    }

    @Test
    public void testOrderWithinTimeUseSystemClock() {
        order.addItem(orderItem);
        order.submit(null);
        order.confirm();
        order.realize();

        assertEquals(order.getOrderState(), State.REALIZED);
    }
}
