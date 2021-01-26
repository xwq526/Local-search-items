package file_scan;

import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static void scanDirectory(File root) throws InterruptedException {
        ExecutorService threadPool = new ThreadPoolExecutor(
                10, 10,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        //打卡器 counter = new 打卡器();
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch doneSignal = new CountDownLatch(1);



        ScanTask task = new ScanTask(root, threadPool, counter, doneSignal);
        // 打卡
        counter.incrementAndGet();
        threadPool.execute(task);

        // TODO: 想办法，在这里等，直到 root 整棵树都被扫描完成
        doneSignal.await();
        threadPool.shutdown();
    }

    private static void scan(File root) {
        if (!root.isDirectory()) {
            return;
        }

        File[] files = root.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            scanDirectorySingle(file);
        }
    }

    public static void scanDirectorySingle(File root) {
        scan(root);
    }

    public static void main(String[] args) throws InterruptedException {
        File root = new File("C:\\");
        long b = System.currentTimeMillis();
        scanDirectorySingle(root);
        long e = System.currentTimeMillis();
        System.out.println("root 整棵树 下的所有文件全部扫描完成！");
        System.out.println(e - b);
    }
}
