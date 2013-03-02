package com.luzi82.hakase.asyn;

public interface IMethodCallbackWait<T> extends IMethodCallback<T> {

	public T waitDone() throws Exception;

}
