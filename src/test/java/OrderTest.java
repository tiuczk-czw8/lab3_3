import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private static final int VALID_PERIOD_HOURS = 24;
    private Order order;
    private OrderItem orderItem;

    public OrderTest() {
        order = new Order();
        orderItem = new OrderItem();
        order.addItem(orderItem);
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredException() {
        order.setDataTimeForTest(new DateTime().minus(Period.hours(VALID_PERIOD_HOURS + 1)));
        order.submit();
        order.confirm();
    }

    @Test
    public void shouldBeValidOrderForWithinTimeRange() {
        order.submit();
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }

} 