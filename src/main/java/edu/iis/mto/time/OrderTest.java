package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;
import org.joda.Period;
import org.joda.DateTime;

import java.time.Period;

import static org.junit.Assert.*;

public class OrderTest {

    private static final int VALID_PERIOD_HOURS  = 24;
    private Order order;

    @Before
    public void setTests(){
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowException(){
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(VALID_PERIOD_HOURS  +1)));
        order.confirm();
    }

}