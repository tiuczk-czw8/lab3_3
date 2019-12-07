package edu.iis.mto.time;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Hours;

public class Order {

    private static final int VALID_PERIOD_HOURS = 24;
    private State orderState;
    private List<OrderItem> items = new ArrayList<>();
    private DateTime submissionDate;

    public Order() {
        orderState = State.CREATED;
    }

    public void addItem(OrderItem item) {
        requireState(State.CREATED, State.SUBMITTED);
        items.add(item);
        orderState = State.CREATED;
    }

    public void submit() {
        requireState(State.CREATED);

        orderState = State.SUBMITTED;
        submissionDate = new DateTime();

    }

    public void confirm() {
        requireState(State.SUBMITTED);
        int hoursElapsedAfterSubmission = Hours.hoursBetween(submissionDate, new DateTime())
                                               .getHours();

        if (hoursElapsedAfterSubmission > VALID_PERIOD_HOURS) {
            orderState = State.CANCELLED;
            throw new OrderExpiredException();
        }
    }

    public void realize() {
        requireState(State.CONFIRMED);
        orderState = State.REALIZED;
    }

    State getOrderState() {
        return orderState;
    }

    private void requireState(@NotNull State... allowedStates) {
        for (State allowedState : allowedStates) {
            if (orderState == allowedState) {
                return;
            }
        }

        throw new OrderStateException("order should be in state "
                                      + allowedStates
                                      + " to perform required  operation, but is in "
                                      + orderState);

    }

    public static enum State {
        CREATED,
        SUBMITTED,
        CONFIRMED,
        REALIZED,
        CANCELLED
    }
}
