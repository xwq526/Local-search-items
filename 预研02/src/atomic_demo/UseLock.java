package atomic_demo;

public class UseLock {
    private static final int COUNT = 10_0000_0000;
    private static int ai = 0;

    public static void main(String[] args) throws InterruptedException {
        long b = System.currentTimeMillis();
        Thread adder = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                synchronized (UseLock.class) {
                    ai++;
                }
            }
        });
        adder.start();

        Thread suber = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                synchronized (UseLock.class) {
                    ai--;
                }
            }
        });
        suber.start();

        adder.join();
        suber.join();

        System.out.println(ai);
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }
}