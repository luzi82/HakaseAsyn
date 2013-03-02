package com.luzi82.hakase.asyn;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

public class CCallbackList extends LinkedList<ICallback<Object>> implements ICallbackList {

	private static final long serialVersionUID = 7971318935647578117L;

	private final Executor mExecutor;

	private ICallback<Exception> mExceptionCallback;

	Iterator<ICallback<Object>> itr = null;

	public CCallbackList(Executor aExecutor) {
		mExecutor = aExecutor;
	}

	@Override
	public <T> void addCallback(final ICallback<T> aMethodCallback) {
		super.add(new ICallback<Object>() {
			@Override
			public void callback(Object aResult) {
				@SuppressWarnings("unchecked")
				T t = (T) aResult;
				aMethodCallback.callback(t);
			}
		});
	}

	@Override
	public void addRunnable(final Runnable aRunnable) {
		super.add(new ICallback<Object>() {
			@Override
			public void callback(Object aResult) {
				aRunnable.run();
			}
		});
	}

	@Override
	public void next(final Object aObject) {
		if (itr.hasNext()) {
			ICallback<Object> next = itr.next();
			SCallbackUtil.startCallback(next, aObject, mExceptionCallback, mExecutor);
		}
	}

	@Override
	public void start() {
		itr = iterator();
		next(null);
	}

	@Override
	public void setExceptionCallback(ICallback<Exception> aExceptionCallback) {
		mExceptionCallback = aExceptionCallback;
	}

}
