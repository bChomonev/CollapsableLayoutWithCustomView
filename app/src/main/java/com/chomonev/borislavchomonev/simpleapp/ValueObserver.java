package com.chomonev.borislavchomonev.simpleapp;

import rx.Observer;

/**
 * Created by borislav.chomonev on 09/12/2016.
 */
public class ValueObserver<T> implements Observer<T> {

    private T mValue;

    private ValueObserver() {

    }

    public static <T> ValueObserver<T> create() {

        return new ValueObserver<>();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

        mValue = t;
    }

    public T getValue() {

        return mValue;
    }
}