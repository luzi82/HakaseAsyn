package com.luzi82.hakase.asyn;

import java.util.concurrent.Executor;

public class CallbackUtil {

	protected CallbackUtil() {
	}

	public static <T> void startCallback(final ICallback<T> aCallback, final T aValue, final ICallback<Exception> aExceptionCallback, Executor aExecutor) {
		if (aCallback == null)
			return;
		if (aExceptionCallback == null)
			throw new NullPointerException();
		aExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					aCallback.callback(aValue);
				} catch (Exception e) {
					aExceptionCallback.callback(e);
				}
			}
		});
	}

}
