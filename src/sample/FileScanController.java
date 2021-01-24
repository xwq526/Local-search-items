package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-24
 * Time: 23:23:37
 */
public class FileScanController {

    @FXML
    public GridPane rootGridPane;
    @FXML
    public TableView tableView;

    @FXML
    public void 选择文件夹(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        File root = chooser.showDialog(rootGridPane.getScene().getWindow());
        if (root == null) {
            return;
        }
        Thread t = new Thread(() ->{
            scan(root);
        });
        t.setDaemon(true);
        t.start();
    }

    // 调用该方法的，既有文件夹，也有普通文件
    private void scan(File root) {
        Platform.runLater(() -> {
            FileMeta fileMeta = new FileMeta(root);
            tableView.getItems().add(fileMeta); // 涉及 UI 部分的，放到主线程执行
        });

        if (!root.isDirectory()) {
            return;
        }

        File[] children = root.listFiles();
        if (children == null) {
            return;
        }

        for (File child : children) {
            scan(child);
        }
    }


}
