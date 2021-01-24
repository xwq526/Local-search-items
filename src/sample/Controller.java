package sample;

import file_scan.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;


import java.io.File;
import java.util.Date;
import java.util.Random;

public class Controller {
    @FXML
    public TextField inputField;
    @FXML
    public Label outputLabel;
    @FXML
    public GridPane rootGridPane;

    // thread 的所有读写操作其实都在主线程中，所以不需要考虑线程安全的问题
    private Thread thread = null;

    @FXML
    public TableView<Student> tableView;

    private int id = 1;
    private final Random random = new Random(20210118);
    private final String[] GENDERS = {"女","男"};
    private void addStudent(String name) {
        int age = random.nextInt(50) + 20;
        String gender = GENDERS[random.nextInt(2)];
        Student student = new Student(id++,name,age,gender);

        // Observable  —— 可以被观察的/具备被观察能力的
        // ObservableList 是一个 List，同时也是 Observable 的
        // Observable: 可以有观众（类似 up 主）
        // item：项    指的是表中的数据们
        ObservableList<Student> items = tableView.getItems();
        items.add(student);
        System.out.println(student);
    }

    @FXML
    public void 点击事件(MouseEvent mouseEvent) {
        System.out.println(new Date() + ": 按钮被点击");
        //从输入框中读取文字内容

        String inputText = inputField.getText();
        System.out.println("用户输入了：" + inputText);

        addStudent(inputText);

        try {
            int n = Integer.parseInt(inputText.trim());
            //主线程
            if (thread != null) {
                thread.interrupt();
            }
            thread = new Thread(() ->{
                long r = fib(n);    // <--- 目前在子线程中进行
                // <--- 在 UI 中，主线程的核心工作是响应事件
                // <--- 解决办法：不要把可能耗时比较久的操作，放到主线程中进行

                // 涉及 UI 修改的，最好回到主线程中进行
                // 如果多个线程同时操作 UI 界面，可能有线程安全问题
                System.out.printf("fib(%d) = %d\n", n, r);
                if (Thread.interrupted()) {
                    // 前提是 thread == this。thread 是我这个线程
                    // 如果 thread 指向别的线程了，我没有权力把 thread 修改
                    // 由于当前线程被中断了，所以很大可能 thread 已经指向了其他线程了
                    // 没有权力修改 thread 了
                    // thread = null;  // <-- 这里加这句代码对不对？   所以不对
                    return;
                }
                Platform.runLater(() ->{
                    // 以事件方式通知主线程执行该操作
                    // 主线程中
                    outputLabel.setText(String.valueOf(r));
                    // 能保证 thread 就是 当前线程么？
                    //thread = null;    // 不行，所以有 BUG
                });
            });
            thread.start();


        } catch (NumberFormatException e) {
           outputLabel.setText("请输入合法的数字");
        }

    }


    //故意使用特别慢的计算方式，时间复杂度是O（2^n)
    private long fib(int n) {
        if (n < 2) {
            return 1;
        }
        return fib(n-1) + fib(n - 2);
    }

    private Thread scanThread = null;
    @FXML
    public void 选择文件夹(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        Window window = rootGridPane.getScene().getWindow();
        File root = chooser.showDialog(window);
        System.out.println(root);
        if (root == null) {
            return;
        }
        scanThread = new Thread(() ->{
            Main.traversalDepth(root);
            //把结果放入表中
        });
        // Daemon —— 精灵
        // 默默守护着你 —— 后台线程
        scanThread.setDaemon(true);
        scanThread.start();
        // JVM 退出条件：所有前台线程都退掉
        // Main.traversalDepth(root);  // 运行时间的边界是否明确 —— 可能运行时间过长的


    }
}
