package com.luzi82.hakase.asyn;

public interface ICallbackWait<T> extends ICallback<T> {

	public T waitDone() throws InterruptedException;

}
