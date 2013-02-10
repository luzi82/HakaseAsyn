package com.luzi82.hakase.asyn;

import java.util.List;

public interface IResultList<T> extends ICallback<T>, List<T> {

	public T waitDone(int aSize) throws InterruptedException;

}
