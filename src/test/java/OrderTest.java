
import edu.iis.mto.time.Order;
import edu.iis.mto.time.OrderExpiredException;
import edu.iis.mto.time.OrderItem;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private static final int VALID_PERIOD_HOURS = 24;
    private Order order;

    @Before
    public void setUpTests() {
        order = new Order();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowOrderExpiredException() {
        OrderItem orderItem = new OrderItem();
        order.addItem(orderItem);
        order.setDataTimeForTest(new DateTime().minus(Period.hours(VALID_PERIOD_HOURS + 1)));
        order.submit();
        order.confirm();
    }

} 