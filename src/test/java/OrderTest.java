import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class OrderTest {

    private static final int VALID_PERIOD_HOURS = 24;
    private Order order;

    @Before
    public void setUpTests() {
        order = new Order();
    }


    @Test
    public void shouldBeValidOrderForWithinTimeRange () {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(null);
        order.confirm();
        order.realize();
        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }

}