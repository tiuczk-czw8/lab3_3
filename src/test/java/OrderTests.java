import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

public class OrderTests {
    private Order order;

    @Before
    public void setUpTests() {
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredException () {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.submit(new DateTime().minusHours(25));
        order.confirm();
    }
}
