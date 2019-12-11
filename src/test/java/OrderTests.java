import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTests {
    private Order order;

    @Before
    public void setUpTests() {
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void throwOrderExpiredException () {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minusHours(25));
        order.confirm();
    }

    @Test
    public void realisedOrderWithinTime () {
        order.addItem(new OrderItem());
        order.submit(new DateTime().minusHours(3));
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(),is(Order.State.REALIZED));
}
}
