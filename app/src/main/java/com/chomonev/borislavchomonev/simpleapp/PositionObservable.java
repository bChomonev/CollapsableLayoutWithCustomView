package com.chomonev.borislavchomonev.simpleapp;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;

import rx.Observable;

/**
 * Created by borislav.chomonev on 09/12/2016.
 */
public class PositionObservable {

    public static Observable<Integer> create(CarouselLayoutManager layoutManager) {

        return Observable.create(subscriber -> {

            layoutManager.addOnItemSelectionListener(adapterPosition -> {

                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                    subscriber.onNext(adapterPosition);
                }
            });
        });
    }
}
