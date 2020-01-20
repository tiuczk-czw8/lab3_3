package edu.iis.mto.time;

import org.junit.Test;

public class OrderTest {

    @Test(expected = OrderExpiredException.class)
    public void orderExpired() {
        Order order = new Order();
        order.addItem(new OrderItem());
        order.submit();
        order.setFakeClock(new FakeClock(30));
        order.confirm();
    }

}