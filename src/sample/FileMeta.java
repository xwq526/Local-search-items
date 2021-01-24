package sample;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-24
 * Time: 23:20:52
 */
public class FileMeta {
    private final File file;

    public FileMeta(File file) {
        this.file = file;
    }
    public String getDirectory() {
        if (file.isDirectory()) {
            return "文件夹";
        } else {
            return "普通文件";
        }
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
