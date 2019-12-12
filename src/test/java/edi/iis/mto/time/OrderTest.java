package edi.iis.mto.time;

import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

public class OrderTest {
    private Order order = new Order();

    @Test(expected = OrderExpiredException.class)
    public void testOrderExpiredExeption(){
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minus(Period.hours(25)));
        order.confirm();
    }
}
