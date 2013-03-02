package com.luzi82.hakase.asyn;

public class CMethodCallbackWait<T> implements IMethodCallbackWait<T> {

	public boolean mCallbackDone;
	public T mCallbackReturn;

	public boolean mExceptionDone;
	public Exception mException;

	@Override
	public synchronized void callback(T aResult) {
		mCallbackDone = true;
		mCallbackReturn = aResult;
		CMethodCallbackWait.this.notify();
	}

	@Override
	public synchronized void exception(Exception aResult) {
		mExceptionDone = true;
		mException = aResult;
		CMethodCallbackWait.this.notify();
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
