package com.luzi82.hakase.asyn;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class CallbackWaitTest {

	@Test
	public void test() throws Exception {
		final IMethodCallbackWait<Integer> mcw = newMethodCallbackWait(new Integer[0]);

		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

		ses.schedule(new Runnable() {
			@Override
			public void run() {
				mcw.callback(0);
			}
		}, 100, TimeUnit.MILLISECONDS);

		Assert.assertEquals(0, (int) mcw.waitDone());
	}

	@Test
	public void testException() throws Exception {
		final IMethodCallbackWait<Integer> mcw = newMethodCallbackWait(new Integer[0]);

		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

		ses.schedule(new Runnable() {
			@Override
			public void run() {
				mcw.exception(new Exception());
			}
		}, 100, TimeUnit.MILLISECONDS);

		try {
			mcw.waitDone();
			Assert.fail();
		} catch (Exception e) {
		}
	}

	public <T> IMethodCallbackWait<T> newMethodCallbackWait(T[] aT) {
		return new MethodCallbackWait<T>();
	}

}
