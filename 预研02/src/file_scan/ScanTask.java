package file_scan;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ScanTask implements Runnable {
    private final File directory;
    private final ExecutorService threadPool;
    private final AtomicInteger counter;
    private final CountDownLatch doneSignal;

    public ScanTask(File directory, ExecutorService threadPool, AtomicInteger counter, CountDownLatch doneSignal) {
        this.directory = directory;
        this.threadPool = threadPool;
        this.counter = counter;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                //System.out.println(file);
                if (file.isDirectory()) {
                    ScanTask task = new ScanTask(file, threadPool, counter, doneSignal);
                    // 打卡
                    counter.incrementAndGet();
                    // 提交给线程池
                    threadPool.execute(task);
                }
            }
        }

        // 销卡
        if (counter.decrementAndGet() == 0) {
            // 代表我就是最后一个任务
            doneSignal.countDown(); // 发送一个结束信号
        }
    }
}
