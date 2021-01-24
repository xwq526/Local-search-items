package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Main extends Application {

    private static Parent build() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField textField = new TextField();
        gridPane.add(textField,0,0);

        Button button = new Button();
        button.setText("click me");
        gridPane.add(button,0,1);

        Label label = new Label();
        label.setPrefWidth(200);
        label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5");
        gridPane.add(label,0,2);

        Controller controller = new Controller();
        controller.inputField = textField;
        controller.outputLabel = label;

//        button.setOnMouseClicked(controller::点击事件);
//        button.setOnMouseClicked(event -> controller.点击事件(event));
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.点击事件(event);

            }
        });



        //关于"表"的实验

        TableView<Student> tableView = new TableView<Student>();
        gridPane.add(tableView,0,3);
        controller.tableView = tableView;

        //表中有哪些列
        TableColumn<Student,String> idColum = new TableColumn<>();
        idColum.setText("#id");

//        idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> param) {
//                Student student = param.getValue();
//                String idStr = String.valueOf(student.id);
//                return new SimpleStringProperty(idStr);
//            }
//        });
        idColum.setCellValueFactory(param -> param.getValue().getIdValue());
        tableView.getColumns().add(idColum);

        TableColumn<Student,String> nameColum = new TableColumn<>();
        nameColum.setText("姓名");
        nameColum.setCellValueFactory(param -> param.getValue().getNameValue());
        tableView.getColumns().add(nameColum);

        TableColumn<Student,String> ageColum = new TableColumn<>();
        ageColum.setText("年龄");
        ageColum.setCellValueFactory(param -> param.getValue().getAgeValue());
        tableView.getColumns().add(ageColum);

        TableColumn<Student,String> genderColum = new TableColumn<>();
        genderColum.setText("性别");
        genderColum.setCellValueFactory(param -> param.getValue().getGenderValue());
        tableView.getColumns().add(genderColum);
        //关于”表“的实验结束








        return gridPane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("file-scan.fxml"));
//        Parent root = build();
        primaryStage.setTitle("徐文青");
        primaryStage.setScene(new Scene(root, 1100, 550));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
