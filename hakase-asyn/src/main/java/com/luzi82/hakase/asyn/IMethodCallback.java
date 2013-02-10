package com.luzi82.hakase.asyn;

public interface IMethodCallback<T> extends ICallback<T> {

	public void exception(Exception aException);

}
