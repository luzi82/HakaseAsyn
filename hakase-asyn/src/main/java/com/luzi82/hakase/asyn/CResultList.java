package com.luzi82.hakase.asyn;

import java.util.LinkedList;

public class CResultList<T> extends LinkedList<T> implements IResultList<T> {

	private static final long serialVersionUID = -4774634327996404977L;

	@Override
	public synchronized void callback(T aResult) {
		addLast(aResult);
		notify();
	}

	public synchronized int size() {
		return super.size();
	}

	public synchronized void clear() {
		super.clear();
	}

	public synchronized T waitDone(int aSize) throws InterruptedException {
		while (size() < aSize) {
			wait(1000);
		}
		return get(aSize - 1);
	}

}
