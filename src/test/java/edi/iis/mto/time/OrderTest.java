package edi.iis.mto.time;

import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    private Order order = new Order();

    @Test(expected = OrderExpiredException.class)
    public void testOrderExpired(){
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(25)));
        order.confirm();
    }

    @Test
    public void testValidTime(){
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(null);
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), equalTo(Order.State.REALIZED));
    }

    @Test
    public void TestTimeWithSystemClock() {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(null);
        order.confirm();
        order.realize();

        assertEquals(order.getOrderState(), Order.State.REALIZED);
    }
}