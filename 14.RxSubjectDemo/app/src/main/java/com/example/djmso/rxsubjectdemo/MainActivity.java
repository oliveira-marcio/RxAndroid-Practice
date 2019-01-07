package com.example.djmso.rxsubjectdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Async subject only emits the last item of the Observable
        asyncSubjectDemo1();
        asyncSubjectDemo2();

        // When an Observer subscribes to a behavior subject it emits the most recently
        // emitted item and all the subsequent items
        behaviorSubjectDemo1();
        behaviorSubjectDemo2();

        // Publish subject emits all the subsequent items of the Observable at the time
        // of subscription
        publishSubjectDemo1();
        publishSubjectDemo2();

        // Replay subject emits all the items of the Observable without considering when
        // the subscriber subscribed
        replaySubjectDemo1();
        replaySubjectDemo2();
    }

    private void asyncSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        observable.subscribe(asyncSubject);

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.subscribe(getThirdObserver());
    }

    private void asyncSubjectDemo2(){

        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.onNext("JAVA");
        asyncSubject.onNext("KOTLIN");
        asyncSubject.onNext("XML");

        asyncSubject.subscribe(getSecondObserver());

        asyncSubject.onNext("JSON");
        asyncSubject.onComplete();

        asyncSubject.subscribe(getThirdObserver());
    }

    private void behaviorSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        observable.subscribe(behaviorSubject);

        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.subscribe(getSecondObserver());
        behaviorSubject.subscribe(getThirdObserver());
    }

    private void behaviorSubjectDemo2(){

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.onNext("JAVA");
        behaviorSubject.onNext("KOTLIN");
        behaviorSubject.onNext("XML");

        behaviorSubject.subscribe(getSecondObserver());

        behaviorSubject.onNext("JSON");
        behaviorSubject.onComplete();

        behaviorSubject.subscribe(getThirdObserver());
    }

    private void publishSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        PublishSubject<String> publishSubject = PublishSubject.create();

        observable.subscribe(publishSubject);

        publishSubject.subscribe(getFirstObserver());
        publishSubject.subscribe(getSecondObserver());
        publishSubject.subscribe(getThirdObserver());
    }

    private void publishSubjectDemo2(){

        PublishSubject<String> publishSubject = PublishSubject.create();

        publishSubject.subscribe(getFirstObserver());
        publishSubject.onNext("JAVA");
        publishSubject.onNext("KOTLIN");
        publishSubject.onNext("XML");

        publishSubject.subscribe(getSecondObserver());

        publishSubject.onNext("JSON");
        publishSubject.onComplete();

        publishSubject.subscribe(getThirdObserver());
    }

    private void replaySubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        ReplaySubject<String> replaySubject = ReplaySubject.create();

        observable.subscribe(replaySubject);

        replaySubject.subscribe(getFirstObserver());
        replaySubject.subscribe(getSecondObserver());
        replaySubject.subscribe(getThirdObserver());
    }

    private void replaySubjectDemo2(){

        ReplaySubject<String> replaySubject = ReplaySubject.create();

        replaySubject.subscribe(getFirstObserver());
        replaySubject.onNext("JAVA");
        replaySubject.onNext("KOTLIN");
        replaySubject.onNext("XML");

        replaySubject.subscribe(getSecondObserver());

        replaySubject.onNext("JSON");
        replaySubject.onComplete();

        replaySubject.subscribe(getThirdObserver());
    }

    private Observer<String> getFirstObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "First Observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "First Observer Received " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "First Observer onSubscribe");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "First Observer onComplete");
            }
        };

        return observer;
    }

    private Observer<String> getSecondObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Second Observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Second Observer Received " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "Second Observer onSubscribe");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Second Observer onComplete");
            }
        };

        return observer;
    }

    private Observer<String> getThirdObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "Third Observer onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Third Observer Received " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "Third Observer onSubscribe");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "Third Observer onComplete");
            }
        };

        return observer;
    }
}
