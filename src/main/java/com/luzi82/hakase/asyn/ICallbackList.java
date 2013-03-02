package com.luzi82.hakase.asyn;

import java.util.List;

public interface ICallbackList extends List<ICallback<Object>> {

	public <T> void addCallback(ICallback<T> aMethodCallback);

	public void addRunnable(Runnable aRunnable);

	public void next(Object aObject);

	public void start();

	public void setExceptionCallback(ICallback<Exception> aExceptionCallback);

}
