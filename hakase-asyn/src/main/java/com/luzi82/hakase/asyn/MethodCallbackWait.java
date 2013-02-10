package com.luzi82.hakase.asyn;

public class MethodCallbackWait<T> implements IMethodCallbackWait<T> {

	public boolean mCallbackDone;
	public T mCallbackReturn;

	public boolean mExceptionDone;
	public Exception mException;

	@Override
	public synchronized void callback(T aResult) {
		mCallbackDone = true;
		mCallbackReturn = aResult;
		MethodCallbackWait.this.notify();
	}

	@Override
	public synchronized void exception(Exception aResult) {
		mExceptionDone = true;
		mException = aResult;
		MethodCallbackWait.this.notify();
	}

	public synchronized T waitDone() throws Exception {
		while (!(mCallbackDone || mExceptionDone)) {
			wait(1000);
		}
		if (mCallbackDone)
			return mCallbackReturn;
		throw mException;
	}

}
