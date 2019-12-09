package edu.iis.mto.time;

import org.junit.Test;
import org.joda.time.Period;
import org.joda.time.DateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class OrderTest {

    private static final int VALID_PERIOD_HOURS = 24 ;

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowException(){
        Order order = new Order();
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(VALID_PERIOD_HOURS  +1)));
        order.confirm();
    }

    @Test
    public void shouldAcceptUsingSystemClock(){
        Order order = new Order();
        order.addItem(new OrderItem());
        DateTime submissionDate = new DateTime();
        submissionDate = submissionDate.minus(Period.millis(1));

        order.submit(submissionDate);
        order.confirm();
        order.realize();

        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }


}