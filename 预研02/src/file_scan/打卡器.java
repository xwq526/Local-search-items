package file_scan;

public class 打卡器 {
    private int count = 0;

    public void add() {
        count++;
    }

    public void sub() {
        count--;
    }

    public int get() {
        return count;
    }
}
