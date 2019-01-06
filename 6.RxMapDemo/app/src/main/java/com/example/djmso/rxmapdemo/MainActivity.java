package com.example.djmso.rxmapdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "myApp";
    private Observable<Student> myObservable;
    private DisposableObserver<Student> myObserver;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                ArrayList<Student> students = getStudents();
                for (Student student : students) {
                    emitter.onNext(student);
                }
                emitter.onComplete();
            }
        });

        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<Student, Student>() {
                            @Override
                            public Student apply(Student student) throws Exception {
                                student.setName(student.getName().toUpperCase());
                                return student;
                            }
                        })
                        .subscribeWith(getObserver())
        );

    }

    private DisposableObserver getObserver() {

        myObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(Student s) {


                Log.i(TAG, " onNext invoked " + s.getName());

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " onError invoked");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " onComplete invoked");
            }
        };

        return myObserver;
    }

    private ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("Student 1", "student1@gmail.com", 27, null));
        students.add(new Student("Student 2", "student2@gmail.com", 20, null));
        students.add(new Student("Student 3", "student3@gmail.com", 20, null));
        students.add(new Student("Student 4", "student4@gmail.com", 20, null));
        students.add(new Student("Student 5", "student5@gmail.com", 20, null));

        return students;
    }
}