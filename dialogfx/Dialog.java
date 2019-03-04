import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author S3nS3IW00
 */
class Dialog {

    private static VBox dialog;
    private static Button dialogButton;
    private static Label dialogTitle, dialogDescription;
    private static StackPane dialogBody;
    private static boolean canCloseDialog = false;
    private static StackPane root;


    /**
     * Sets root for Dialog.
     *
     * @param root is a StackPane to which you add the dialog.
     */
    static void setRoot(StackPane root){
        Dialog.root = root;
    }

    /**
     * Initiating style for dialog.
     */
    static void initDialog() {
        dialogTitle = new Label();
        dialogTitle.setId("dialog-title");
        dialogTitle.setAlignment(Pos.CENTER);

        dialogDescription = new Label();
        dialogDescription.setId("dialog-description");
        dialogDescription.setAlignment(Pos.CENTER);
        dialogDescription.setWrapText(true);

        dialogButton = new Button();
        dialogButton.setId("dialog-button");

        dialog = new VBox(dialogTitle, dialogDescription, dialogButton);
        dialog.setId("dialog");
        dialog.setAlignment(Pos.TOP_CENTER);

        Region dialogBackground = new Region();
        dialogBackground.setId("dialog-background");

        dialogBody = new StackPane(dialogBackground, dialog);
        dialogBody.getStylesheets().add(Dialog.class.getResource("dialog.css").toExternalForm());
    }

    /**
     * Shows the dialog with the following parameters.
     *
     * @param title is the title text of the dialog.
     *              Should be not null.
     * @param description is the description text of dialog.
     *                    Should be not null.
     * @param buttonText is the text of button. If the text is null,
     *                   the text will be 'OK'.
     * @param buttonOnAction is the action what the button does when it clicked.
     *                       If the action is null, the button will close the dialog.
     */
    static void showDialog(final String title, final String description, final String buttonText, final EventHandler<ActionEvent> buttonOnAction) {
        dialogTitle.setText(title);
        dialogDescription.setText(description);
        dialogTitle.setStyle("-fx-text-fill: white;" +
                             "-fx-background-color: #5352ed;");
        dialogDescription.setStyle("-fx-text-fill: white;");
        dialogButton.setText((buttonText != null ? buttonText : "OK"));
        setDialogButtonStyle("white", "rgba(83, 82, 237,1.0)", "rgba(53, 82, 237,1.0)");
        dialogButton.setOnAction((buttonOnAction != null ? buttonOnAction : event -> closeDialog()));
        if (!root.getChildren().contains(dialogBody)) root.getChildren().add(dialogBody);
        dialogShowAnimation();
    }

    /**
     * Shows the dialog with the following parameters.
     *
     * @param title is the title text of the dialog.
     *              Should be not null.
     * @param description is the description text of dialog.
     *                    Should be not null.
     * @param titleColor is the text color of title text. If the color is null,
     *                   the text will be white.
     * @param descriptionColor is the text color of description text. If the color is null,
     *                         the text will be white.
     * @param titleBackgroundColor is the background color of title text. If the color is null,
     *                             the title background will be default.
     * @param buttonText is the text of button. If the text is null,
     *                   the text will be 'OK'.
     * @param buttonTextColor is the color of button's text. If the color is null,
     *                        the button's text's color will be white.
     * @param buttonBackgroundColor is the background color of button. If the color is null,
     *                              the button's background will be default.
     * @param buttonOnAction is the action what the button does when it clicked.
     *                       If the action is null, the button will close the dialog.
     */
    static void showDialog(final String title, final String description, final Color titleColor, final Color descriptionColor, final Color titleBackgroundColor, final String buttonText, final Color buttonTextColor,
                           final Color buttonBackgroundColor, final EventHandler<ActionEvent> buttonOnAction) {
        dialogTitle.setText(title);
        dialogDescription.setText(description);
        dialogTitle.setStyle("-fx-text-fill: " + (titleColor != null ? colorToRgba(titleColor, false) : "white") + ";" +
                             "-fx-background-color: " + (titleBackgroundColor != null ? colorToRgba(titleBackgroundColor, false) : "#5352ed") + ";");
        dialogDescription.setStyle("-fx-text-fill: "+(descriptionColor != null ? colorToRgba(descriptionColor, false) : "white")+";");
        dialogButton.setText((buttonText != null ? buttonText : "OK"));
        setDialogButtonStyle((buttonTextColor != null ? colorToRgba(buttonTextColor, false) : "white"),
                (buttonBackgroundColor != null ? colorToRgba(buttonBackgroundColor, false) : "rgba(83, 82, 237,1.0)"),
                (buttonBackgroundColor != null ? colorToRgba(buttonBackgroundColor, true) : "rgba(53, 82, 237,1.0)"));
        dialogButton.setOnAction((buttonOnAction != null ? buttonOnAction : event -> closeDialog()));
        if (!root.getChildren().contains(dialogBody)) root.getChildren().add(dialogBody);
        dialogShowAnimation();
    }

    /**
     * Closes the dialog.
     */
    static void closeDialog() {
        if(canCloseDialog) {
            canCloseDialog = false;
            dialogCloseAnimation();
        }
    }

    /**
     * Plays the animation for show the dialog.
     */
    private static void dialogShowAnimation() {
        FadeTransition in = new FadeTransition(Duration.millis(300), dialog);
        in.setFromValue(0);
        in.setToValue(1);
        in.setCycleCount(1);
        in.setAutoReverse(true);
        in.play();
        in.setOnFinished(e -> canCloseDialog = true);
    }

    /**
     * Plays the animation for close the dialog.
     */
    private static void dialogCloseAnimation() {
        FadeTransition out = new FadeTransition(Duration.millis(300), dialog);
        out.setFromValue(1);
        out.setToValue(0);
        out.setCycleCount(1);
        out.setAutoReverse(true);
        out.play();
        out.setOnFinished(e -> root.getChildren().remove(dialogBody));
    }

    /**
     * Sets color for dialog's button.
     *
     * @param color is the button default color.
     *              Should be not null.
     * @param hoverColor is the button hovered color.
     *                   Should be not null.
     */
    private static void setDialogButtonStyle(String textColor, String color, String hoverColor) {
        dialogButton.styleProperty().bind(
                Bindings
                        .when(dialogButton.hoverProperty())
                        .then(
                                new SimpleStringProperty("-fx-background-color: " + hoverColor + ";" +
                                                                    "-fx-text-fill: "+textColor+";"))
                        .otherwise(
                                new SimpleStringProperty("-fx-background-color: " + color + ";" +
                                                                    "-fx-text-fill: "+textColor+";")
                        )
        );
    }

    /**
     * Converts Color to rgba color
     *
     * @param c is the color to convert. Should be not null.
     * @param dark a logical value that makes the color darker.
     *             If true, the color will be darker.
     * @return converted color
     */
    private static String colorToRgba(Color c, boolean dark) {
        return String.format("rgba(%d, %d, %d, %f)",
                (int) ((dark ? 205 : 255) * c.getRed()),
                (int) (255 * c.getGreen()),
                (int) (255 * c.getBlue()),
                c.getOpacity());
    }

}
