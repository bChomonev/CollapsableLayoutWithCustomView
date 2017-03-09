package com.chomonev.borislavchomonev.simpleapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by borislav.chomonev on 12/10/2016.
 */
public class RxTest {

    public void test() {
//        Observable<String> just = Observable.just("Hello Android Devs");
//        Observable<String> map = just.map(s -> concatToString(s, "! wtf?!"));
//        Observable<Integer> map1 = map.map(s -> s.hashCode());
//        Observable<String> map2 = map1.map(integer -> Integer.toString(integer));
//        map2.subscribe(this::printString);
//
//
//        Observable.just("Hello Android Devs")
//                .map(s -> concatToString(s, " Something usefull"))
//                .map(s -> s.hashCode())
//                .map(integer -> Integer.toString(integer))
//                .subscribe(this::printString);


    }

    void printString(String s) {
        Log.d("test", s);
    }

    String concatToString(String s, String strignToConcat) {
        return s.concat(strignToConcat);
    }


    public void testList() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        urls.add("url3");
        urls.add("url4");
        urls.add("url5");
        urls.add("url6");

        Log.d("test", "--------------------------");

//        //Представете си, че Observable.just(urls) е заявка към бекенда и връща списък със Стрингове.
//        Observable<List<String>> query = Observable.just(urls);
//
//        //Emits the whole list of items
//        query.subscribe(this::workWithList);
//
//        //Emits every item in the list one by one. Not so cool though
//        query.subscribe(list -> {
//            Observable.from(list)
//                    .subscribe(this::printString);
//        });
//
//        query.flatMap(list -> Observable.from(urls))
//                .subscribe(this::printString);
//
//
//        query.flatMap(list -> Observable.from(urls))
//                .flatMap(this::updateTitle)
//                .filter(title -> title.contains(" Food"))
//                .take(4)
//                .doOnNext(s -> Log.d("rx", "invoke separately from observer " + s))
//                .subscribe(this::printString);

    }

//    private Observable<String> updateTitle(String url) {
//        url = "this is new bobi " + url;
////        url = "bobi";
//        return Observable.just(url);
//    }

    void workWithList(List<String> list) {
        for (String item : list) {
            Log.d("test", "item " + item + "asd");
        }
        Log.d("test", "listSize " + list.size());
    }

}

