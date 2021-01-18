package sample;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xuwenqing
 * Date: 2021-01-17
 * Time: 22:22:03
 */
public class ResourcesDemo {
    public static void main(String[] args) {
        URL r1 = ResourcesDemo.class.getResource("sample.fxml");
        URL r2 = ResourcesDemo.class.getClassLoader().getResource("root.fxml");
        System.out.println(r1);
        System.out.println(r2);
    }
}
