package layer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import framework.layer.Layer;
import framework.layer.TickListener;

public class DispatcherTest implements TickListener {

	private int ticksReceived;

	@Before
	public void pullUp() {
		ticksReceived = 0;
	}

	@Test
	public void testDispatcherNormal() {
		int turnoverCount = 13;
		int ticksToSend = 100;
		simulateTicks(turnoverCount, ticksToSend);
		assertEquals((int) Math.ceil((double) ticksToSend / (double) turnoverCount), ticksReceived);
	}

	@Test
	public void testDispatcherZeroTicks() {
		int turnoverCount = 13;
		int ticksToSend = 0;
		simulateTicks(turnoverCount, ticksToSend);
		assertEquals(0, ticksReceived);
	}

	@Test
	public void testDispatcherEveryTicks() {
		int turnoverCount = 1;
		int ticksToSend = 100;
		simulateTicks(turnoverCount, ticksToSend);
		assertEquals(ticksToSend, ticksReceived);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDispatcherIllArg() {
		int turnoverCount = 0;
		int ticksToSend = 100;
		simulateTicks(turnoverCount, ticksToSend);
	}

	private void simulateTicks(int turnoverCount, int ticksToSend) {
		DummyLayer layer = new DummyLayer(turnoverCount);
		layer.getRegistry().register(this);
		for (int i = 0; i < ticksToSend; i++) {
			layer.getDispatcher().notifyTickUpdate();
		}
	}

	public void processTick(long timePassed) {
		ticksReceived++;
	}

	private class DummyLayer extends Layer {

		public DummyLayer(int turnoverCount) {
			super(turnoverCount);
		}

	}

}
