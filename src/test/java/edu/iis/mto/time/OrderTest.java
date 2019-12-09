package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    private static final int VALID_PERIOD_HOURS = 24;
    private Order order;

    @Before
    public void setUpTests() {
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredException () {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(VALID_PERIOD_HOURS + 1)));
        order.confirm();
    }

}