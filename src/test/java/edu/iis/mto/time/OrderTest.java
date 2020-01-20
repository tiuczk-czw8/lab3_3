package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private Order order = new Order();
    
    @Test(expected = OrderExpiredException.class)
    public void orderConfirmationAfterExpirationShouldFail() {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(25)));
        order.confirm();
    }

    @Test
    public void orderConfirmationBeforeBeExpiredShouldPass() {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(null);
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }

}