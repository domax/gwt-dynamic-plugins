package org.gwt.dynamic.common.client.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gwt.dynamic.common.client.util.BatchRunner.CommandSimple;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BatchRunnerTestGWT extends GWTTestCase {

	private static final String FAIL_MESSAGE = "Test Failure";
	private static final List<String> PARALLEL_RESULT = Arrays.asList("B", "A", "C");
	private static final List<String> SEQUENTIAL_RESULT = Arrays.asList("A", "B", "C");
	
	private final List<String> resultList = new ArrayList<String>(3);
	
	@Override
	public String getModuleName() {
		return "org.gwt.dynamic.common.DynamicCommon";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		resultList.clear();
	}
	
	private class TestCommand extends CommandSimple {
		
		private final String name;
		private final int delay;
		private final boolean fail;

		TestCommand(String name, int delay, boolean fail) {
			this.name = name;
			this.delay = delay;
			this.fail = fail;
		}
		
		TestCommand(String name, int delay) {
			this(name, delay, false);
		}
		
		@Override
		public void run(final AsyncCallback<Void> callback) {
			new Timer() {
				@Override
				public void run() {
					resultList.add(name);
					if (fail) callback.onFailure(new RuntimeException(FAIL_MESSAGE));
					else callback.onSuccess(null);
				}
			}.schedule(delay);
		}
	}

	public void testDummy() {
		assertTrue(true);
	}
	
	public void testModeParallel() {
		delayTestFinish(5000);
		new BatchRunner(BatchRunner.Mode.PARALLEL) {
			@Override
			public void onFinish() {
				assertEquals(PARALLEL_RESULT, resultList);
				finishTest();
			}
		}.add(new TestCommand("A", 200), new TestCommand("B", 100), new TestCommand("C", 300)).run();
	}
	
	public void testModeSequential() {
		delayTestFinish(5000);
		new BatchRunner(BatchRunner.Mode.SEQUENTIAL) {
			@Override
			public void onFinish() {
				assertEquals(SEQUENTIAL_RESULT, resultList);
				finishTest();
			}
		}.add(new TestCommand("A", 200), new TestCommand("B", 100), new TestCommand("C", 300)).run();
	}
}
