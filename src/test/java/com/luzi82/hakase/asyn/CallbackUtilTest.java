package com.luzi82.hakase.asyn;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class CallbackUtilTest {

	@Test
	public void testStartCallbackExceptionHandlerNull() {
		Executor exec = Executors.newCachedThreadPool();
		try {
			SCallbackUtil.startCallback(new ICallback<Void>() {
				@Override
				public void callback(Void aResult) {
					throw new YouShouldNotSeeMe();
				}
			}, null, null, exec);
			Assert.fail();
		} catch (NullPointerException npe) {
			// pass
		}
	}

	@Test
	public void testStartCallbackCallbackNull() {
		Executor exec = Executors.newCachedThreadPool();
		SCallbackUtil.startCallback(null, null, null, exec);
		SCallbackUtil.startCallback(null, null, new ICallback<Exception>() {
			@Override
			public void callback(Exception aResult) {
				throw new YouShouldNotSeeMe();
			}
		}, exec);
	}

	@Test
	public void testStartException() {
		YouShouldNotSeeMe ex = new YouShouldNotSeeMe();
		Executor exec = Executors.newCachedThreadPool();
		CMethodCallbackWait<Integer> mcw = new CMethodCallbackWait<Integer>();
		SCallbackUtil.startException(mcw, ex, exec);
		try {
			mcw.waitDone();
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(ex, e);
		}
	}

	@Test
	public void testStartExceptionExceptionCallbackNull() {
		YouShouldNotSeeMe ex = new YouShouldNotSeeMe();
		Executor exec = Executors.newCachedThreadPool();
		SCallbackUtil.startException(null, ex, exec);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	private class YouShouldNotSeeMe extends RuntimeException {

		private static final long serialVersionUID = -1707929982917092456L;

	}

}
