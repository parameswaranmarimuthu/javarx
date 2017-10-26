/**
 * Created by m655222 on 10/26/2017.
 *
 */
import io.reactivex.Observable;
import io.reactivex.schedulers.*;
import org.junit.Test;
public class SchedulersTest {

    @Test
    public void sameThread() {

        Observable<String> source = Observable.just("Alpha","Beta","Gamma");

        Observable<Integer> lengths = source.map(String::length);

        lengths.subscribe(l -> System.out.println("Received " + l +
                " on thread " + Thread.currentThread().getName()));
    }

    @Test
    public void withSubscribeOn(){
        Observable<String> source = Observable.just("Alpha", "Beta", "Gamma");

        Observable<Integer> lengths = source
                .subscribeOn(Schedulers.computation())
                .map(String::length).doOnNext(x-> System.out.println("Emitting " + x
                        + " on thread " + Thread.currentThread().getName())); // operations happens on the new thread

        lengths.subscribe(sum -> System.out.println("Received " + sum +
                " on thread " + Thread.currentThread().getName()));

        sleep(3000);
    }

    @Test
    public void withObserveOn(){

        Observable<Integer> source = Observable.range(1,10);

        source.map(i -> i * 100)
                .doOnNext(i -> System.out.println("Emitting " + i
                        + " on thread " + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())// observe on the new Thread
                .map(i -> i * 10)
                .subscribe(i -> System.out.println("Received " + i + " on thread "
                        + Thread.currentThread().getName()));

        sleep(3000);

    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
