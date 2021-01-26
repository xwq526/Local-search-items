package atomic_demo;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFree {
    private static final int COUNT = 10_0000_0000;
    private static final AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        long b = System.currentTimeMillis();
        Thread adder = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                ai.getAndIncrement();
            }
        });
        adder.start();

        Thread suber = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                ai.getAndDecrement();
            }
        });
        suber.start();

        adder.join();
        suber.join();

        System.out.println(ai.get());
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }
}
