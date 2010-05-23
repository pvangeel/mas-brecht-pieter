package core;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import framework.core.VirtualClock;

public class VirtualClockTest {

	private VirtualClock clock;

	@Before
	public void pullUp() {
		clock = VirtualClock.getClock();
		clock.reset();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testClockIncrease() {
		int increase = 1000000;
		clock.increaseTime(increase);
		assertEquals(increase, clock.getCurrentTime());

		long now = clock.getCurrentTime();
		clock.increaseTime(increase);
		assertEquals(now + increase, clock.getCurrentTime());
	}

	@Test
	public void testClockConversion() {
		int increase = Integer.MAX_VALUE;
		long total = 0;
		for (int i = 0; i < 100; i++) {
			clock.increaseTime(increase);
			total += increase;
		}
		int[] conversion = getConversion(total);
		assertEquals(conversion[0], clock.getTimeObject().getDays());
		assertEquals(conversion[1], clock.getTimeObject().getHours());
		assertEquals(conversion[2], clock.getTimeObject().getMinutes());
		assertEquals(conversion[3], clock.getTimeObject().getSeconds());
		assertEquals(conversion[4], clock.getTimeObject().getMilliseconds());
		assertEquals(conversion[5], clock.getTimeObject().getMicroseconds());
	}

	private int[] getConversion(long time) {
		long millisecmicrosecs = 1000;
		long secmicrosecs = 1000 * millisecmicrosecs;
		long minutemicrosecs = 60 * secmicrosecs;
		long hourmicrosecs = 60 * minutemicrosecs;
		long daymicrosecs = 24 * hourmicrosecs;

		int[] conversion = new int[6];
		conversion[0] = (int) (time / daymicrosecs);
		conversion[1] = (int) ((time % daymicrosecs) / hourmicrosecs);
		conversion[2] = (int) ((time % hourmicrosecs) / minutemicrosecs);
		conversion[3] = (int) ((time % minutemicrosecs) / secmicrosecs);
		conversion[4] = (int) ((time % secmicrosecs) / millisecmicrosecs);
		conversion[5] = (int) (time % millisecmicrosecs);
		return conversion;
	}
	

}
