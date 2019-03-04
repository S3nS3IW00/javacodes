import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DialogTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Dialog.initDialog();

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("wp.jpg"), 600, 470, false, false));
        background.setFitHeight(470);
        background.setFitWidth(600);

        StackPane body = new StackPane(background);
        Dialog.setRoot(body);

        Button button = new Button("Open dialog");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(30);
        button.setOnAction(e -> Dialog.showDialog("Title for dialog!", "Description for dialog!", Color.YELLOW, Color.GREEN, Color.RED, "Click me", Color.BLACK, Color.YELLOW, event -> {
            System.out.println("Dialog closed");
            Dialog.closeDialog();
        }));

        VBox bodyBox = new VBox(body, button);

        stage.setScene(new Scene(bodyBox, 600, 500));
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.show();
    }
}
