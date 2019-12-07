package edu.iis.mto.time;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import edu.iis.mto.time.Order.State;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

public class OrderTest {

    @Test(expected = OrderExpiredException.class)
    public void confirmCallShouldBeOutOfTimeAndThrowException() {
        Order order = new Order();
        order.addItem(new OrderItem());
        DateTime submissionDate = new DateTime();
        // submissionDate should be pointing at the past event, so the future can happen
        submissionDate = submissionDate.minus(Period.hours(Order.VALID_PERIOD_HOURS + 1));
        order.submit(submissionDate);

        order.confirm();
    }

    @Test
    public void orderWithinTimeFrameShouldBeRealized() {
        Order order = new Order();
        order.addItem(new OrderItem());
        DateTime submissionDate = new DateTime();
        submissionDate = submissionDate.minusHours(Order.VALID_PERIOD_HOURS - 5);

        order.submit(submissionDate);
        order.confirm();
        order.realize();

        assertThat(order.getOrderState(), is(State.REALIZED));
    }

}
