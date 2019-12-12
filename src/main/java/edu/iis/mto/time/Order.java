package edu.iis.mto.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Hours;

public class Order {
	private static final int VALID_PERIOD_HOURS = 24;
	private State orderState;
	private List<OrderItem> items = new ArrayList<OrderItem>();
	private DateTime subbmitionDate;
	private FakeClock fakeClock = null;

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
		subbmitionDate = new DateTime();

	}

	public void confirm() {
		requireState(State.SUBMITTED);
		int hoursElapsedAfterSubmittion;
		if (fakeClock == null) {
			hoursElapsedAfterSubmittion = Hours.hoursBetween(subbmitionDate, new DateTime()).getHours();
		} else {
			Date date = Date.from(fakeClock.instant());
			DateTime dateTime = new DateTime(date);
			hoursElapsedAfterSubmittion = Hours.hoursBetween(subbmitionDate, dateTime).getHours();
		}
		if(hoursElapsedAfterSubmittion > VALID_PERIOD_HOURS){
			orderState = State.CANCELLED;
			throw new OrderExpiredException();
		}
	}

	public void realize() {
		requireState(State.CONFIRMED);
		orderState = State.REALIZED;
	}

	public void setFakeClock(FakeClock fakeClock) {
		this.fakeClock = fakeClock;
	}

	State getOrderState() {
		return orderState;
	}

	private void requireState(State... allowedStates) {
		for (State allowedState : allowedStates) {
			if (orderState == allowedState)
				return;
		}

		throw new OrderStateException("order should be in state "
				+ allowedStates + " to perform required  operation, but is in "
				+ orderState);

	}

	public static enum State {
		CREATED, SUBMITTED, CONFIRMED, REALIZED, CANCELLED
	}
}
