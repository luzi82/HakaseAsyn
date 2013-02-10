package com.luzi82.hakase.asyn;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Test;

public class ResultListTest {

	@Test
	public void test() {
		IResultList<Integer> rl = newResultList(new Integer[0]);

		Assert.assertEquals(0, rl.size());

		for (int i = 0; i < 10; ++i) {
			rl.callback(i);
		}

		Assert.assertEquals(10, rl.size());
		for (int i = 0; i < 10; ++i) {
			Assert.assertEquals(i, (int) rl.get(i));
		}

		rl.clear();
		Assert.assertEquals(0, rl.size());
	}

	@Test
	public void testWaitDone() throws Exception {
		final IResultList<Integer> rl = newResultList(new Integer[0]);

		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

		final int[] ii = new int[1];
		final ScheduledFuture<?>[] sf = new ScheduledFuture[1];
		sf[0] = ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				rl.callback(ii[0]);
				++ii[0];
				if (ii[0] >= 10) {
					sf[0].cancel(false);
				}
			}
		}, 100, 10, TimeUnit.MILLISECONDS);

		for (int i = 0; i < 10; ++i) {
			Assert.assertEquals(i, (int) rl.waitDone(i + 1));
		}

		Thread.sleep(500);
		Assert.assertEquals(10, rl.size());
	}

	protected <T> IResultList<T> newResultList(T[] aT) {
		return new ResultList<T>();
	}

}
