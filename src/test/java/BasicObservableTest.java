/**
 * Created by m655222 on 10/26/2017.
 */

import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicObservableTest {

    @Test
    public void testBasicObservable() {
        Observable.<Integer>create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> s) throws Exception {
                System.out.println(" Observable Source ................");
                System.out.println(s instanceof Observer);
                s.onNext(50);
                s.onNext(150);
                s.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        });
    }

    @Test
    public void testBasicObservableWithActions() {

        Observable<Integer> observable = Observable.<Integer>create(s -> {
            System.out.println("Inside Observable" + Thread.currentThread().getName());
            s.onNext(50);
            // s.onError(new Throwable("Oops"));
            s.onNext(150);
            s.onComplete();
        });

        observable.subscribe(x -> {
                    System.out.println("Inside OnNext #1:" + Thread.currentThread().getName());
                    System.out.println(x);
                },
                t -> System.out.format("Got an error with message #1: %s",
                        t.getMessage()), () -> System.out.println("Done!"));

        observable.subscribe(x -> {
            System.out.println("Inside OnNext #2:" + Thread.currentThread().getName());
            System.out.println(x);
        }, t -> System.out.format("Got an error with message #2: %s", t.getMessage()), () -> System.out.println("Done!"));
    }
}