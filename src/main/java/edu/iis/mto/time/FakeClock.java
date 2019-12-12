package edu.iis.mto.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public final class FakeClock extends Clock {

    public FakeClock(int hoursBetween) {
        this.hoursBetween = hoursBetween;
    }
    @Override public ZoneId getZone() {
        return DEFAULT_TZONE;
    }

    @Override public Clock withZone(ZoneId zone) {
        return Clock.fixed(WHEN_STARTED, zone);
    }

    @Override public Instant instant() {
        return nextInstant();
    }

    private final Instant WHEN_STARTED = Instant.now();
    private final ZoneId DEFAULT_TZONE = ZoneId.systemDefault();
    private long count = 0;
    private int hoursBetween = 0;

    private Instant nextInstant() {
        count = count + (60 * 60 * hoursBetween);
        return WHEN_STARTED.plusSeconds(count);
    }
}
