package com.luzi82.hakase.asyn;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class CallbackListTest {

	@Test
	public void test() throws Exception {
		final ICallbackList cl = newCallbackList();
		final ResultList<Void> rl = new ResultList<>();
		final int[] a = { 0 };
		final int[] b = { 0 };
		final int[] c = { 0 };
		final int[] i = { 10 };
		cl.setExceptionCallback(new ICallback<Exception>() {
			@Override
			public void callback(Exception aResult) {
				aResult.printStackTrace();
				System.exit(1);
			}
		});
		cl.addCallback(new ICallback<Void>() {
			@Override
			public void callback(Void aV) {
				a[0] = i[0]++;
				cl.next(null);
			}
		});
		cl.addRunnable(new Runnable() {
			@Override
			public void run() {
				b[0] = i[0]++;
				cl.next(null);
			}
		});
		cl.addCallback(new ICallback<Void>() {
			@Override
			public void callback(Void aV) {
				c[0] = i[0]++;
				cl.next(null);
			}
		});
		cl.addCallback(rl);
		cl.start();
		rl.waitDone(1);

		Assert.assertEquals(10, a[0]);
		Assert.assertEquals(11, b[0]);
		Assert.assertEquals(12, c[0]);
	}

	@Test
	public void testNoEnd() throws Exception {
		final ICallbackList cl = newCallbackList();
		final int[] a = { 0 };
		final int[] b = { 0 };
		final int[] c = { 0 };
		final int[] i = { 10 };
		cl.setExceptionCallback(new ICallback<Exception>() {
			@Override
			public void callback(Exception aResult) {
				aResult.printStackTrace();
				System.exit(1);
			}
		});
		cl.addCallback(new ICallback<Void>() {
			@Override
			public void callback(Void aV) {
				a[0] = i[0]++;
				cl.next(null);
			}
		});
		cl.addRunnable(new Runnable() {
			@Override
			public void run() {
				b[0] = i[0]++;
				cl.next(null);
			}
		});
		cl.addCallback(new ICallback<Void>() {
			@Override
			public void callback(Void aV) {
				c[0] = i[0]++;
				cl.next(null);
			}
		});
		cl.start();

		Thread.sleep(100);
	}

	@Test
	public void testException() throws Exception {
		final ICallbackList cl = newCallbackList();
		final ResultList<Exception> rl = new ResultList<Exception>();
		cl.addCallback(new ICallback<Void>() {
			@Override
			public void callback(Void aV) {
				throw new RuntimeException();
			}
		});
		cl.setExceptionCallback(rl);
		cl.start();

		rl.waitDone(1);

		Assert.assertEquals(RuntimeException.class, rl.get(0).getClass());
	}

	// @Test
	// public void testNull() throws Exception {
	// final ICallbackList cl = newCallbackList();
	// final ResultList<Exception> rl = new ResultList<Exception>();
	// // cl.add((ICallback<Object>)null);
	// cl.setExceptionCallback(rl);
	// cl.start();
	//
	// rl.waitDone(1);
	//
	// Assert.assertEquals(RuntimeException.class, rl.get(0).getClass());
	// }

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		Constructor<?> constructor = CallbackUtil.class.getDeclaredConstructor();
		Assert.assertFalse(constructor.isAccessible());
		constructor.setAccessible(true);
		constructor.newInstance();
		constructor.setAccessible(false);
	}

	public void testObjectPass() throws Exception {
		final ICallbackList cl = newCallbackList();
		final ResultList<Void> rl = new ResultList<>();
		final int[] a = { 0 };
		final int[] b = { 0 };
		final int[] c = { 0 };
		cl.addCallback(new ICallback<Void>() {
			@Override
			public void callback(Void aV) {
				a[0] = 1;
				cl.next(2);
			}
		});
		cl.addCallback(new ICallback<Integer>() {
			@Override
			public void callback(Integer aV) {
				b[0] = aV;
				cl.next(3);
			}
		});
		cl.addCallback(new ICallback<Integer>() {
			@Override
			public void callback(Integer aV) {
				c[0] = aV;
				cl.next(4);
			}
		});
		cl.addCallback(rl);
		cl.start();

		Assert.assertEquals(4, rl.waitDone(1));
	}

	protected ICallbackList newCallbackList() {
		return new CallbackList(Executors.newCachedThreadPool());
	}

}
