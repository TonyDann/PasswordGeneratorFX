package xfzliansynot.passwordgenerator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;


public class Main extends Application {

    private int[] parameterValues = {0, 0, 0, 0, 10};
    private TextField password;
    private Button copyBtn;
    private Clipboard clipboard = Clipboard.getSystemClipboard();
    private ClipboardContent content = new ClipboardContent();
    private boolean copied = true;

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(50, 0, 0 ,0));
        root.setSpacing(50);

        Scene scene = new Scene(root);

        Label lengthLbl = new Label("Length: ");
        TextField length = new TextField("10");
        length.setMaxWidth(50);
        length.setAlignment(Pos.CENTER);
        length.setOnKeyTyped(setPasswordLength);

        CheckBox upperChars = new CheckBox("Include A-Z");
        upperChars.setOnAction(this::enableParameter);
        upperChars.setId("upperChars");

        CheckBox lowerChars = new CheckBox("Include a-z");
        lowerChars.setOnAction(this::enableParameter);
        lowerChars.setId("lowerChars");

        CheckBox numbers = new CheckBox("Include 0-9");
        numbers.setOnAction(this::enableParameter);
        numbers.setId("numbers");

        CheckBox specialChars = new CheckBox("Include special characters eg. $#%");
        specialChars.setOnAction(this::enableParameter);
        specialChars.setId("specialChars");

        VBox checkList = new VBox(upperChars, lowerChars, numbers, specialChars);
        checkList.setAlignment(Pos.CENTER_LEFT);
        checkList.setSpacing(20);

        HBox parameters = new HBox(lengthLbl, length, checkList);
        parameters.setAlignment(Pos.TOP_CENTER);
        parameters.setSpacing(30);

        Button generate = new Button("Generate");
        generate.setMinSize(50, 30);
        generate.setOnAction(this::generatePassword);

        password = new TextField("");
        password.setMaxWidth(500);
        password.setMinWidth(300);

        copyBtn = new Button("Copy");
        copyBtn.setMinSize(40, 25);
        copyBtn.setOnAction(this::copyPassword);

        HBox result = new HBox(password, copyBtn);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(20);

        root.getChildren().addAll(parameters, generate, result);

        stage.setTitle("Password Generator");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    void enableParameter(ActionEvent event) {
        CheckBox parameter = (CheckBox) event.getSource();

        String id = parameter.getId();

        if (id.equals("upperChars")) {
            if (parameter.isSelected()) {
                parameterValues[0] = 1;
            } else {parameterValues[0] = 0;}

        } else if (id.equals("lowerChars")) {
            if (parameter.isSelected()) {
                parameterValues[1] = 1;
            } else {parameterValues[1] = 0;}

        } else if (id.equals("numbers")) {
            if (parameter.isSelected()) {
                parameterValues[2] = 1;
            } else {parameterValues[2] = 0;}

        } else if (id.equals("specialChars")) {
            if (parameter.isSelected()) {
                parameterValues[3] = 1;
            } else {parameterValues[3] = 0;}
        }
    }

    void generatePassword(ActionEvent event) {
        int len = parameterValues[4];
        if (parameterValues[0] == 0 && parameterValues[1] == 0 && parameterValues[2] == 0 && parameterValues[3] == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Parameter Selected");
            alert.setContentText("Please Enable At Least One Parameter To Generate A Password");
            alert.show();
            return;
        }
        String result = "";
        int charType = 0, charSelected = 0;
        for (int i = 1; i <= len; i++) {
            while (result.length() < i) {
                charType = (int)(Math.random() * 4) + 1;
                switch (charType) {
                    case 1: {
                        if (parameterValues[0] == 0) break;
                        charSelected = ((int) (Math.random() * 26)) + 65;
                        result += (char) charSelected;
                        break;
                    }
                    case 2: {
                        if (parameterValues[1] == 0) break;
                        charSelected = ((int) (Math.random() * 26)) + 97;
                        result += (char) charSelected;
                        break;
                    }
                    case 3: {
                        if (parameterValues[2] == 0) break;
                        charSelected = ((int) (Math.random() * 10)) + 48;
                        result += (char) charSelected;
                        break;
                    }
                    case 4: {
                        if (parameterValues[3] == 0) break;
                        charSelected = ((int) (Math.random() * 15)) + 33;
                        result += (char) charSelected;
                        break;
                    }
                }
            }

        }
        password.setText(result);
        content.putString(result);
        copyBtn.setDisable(false);
        copyBtn.setText("Copy");
    }

    EventHandler <KeyEvent> setPasswordLength = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            TextField length = (TextField) event.getSource();

            try {
                parameterValues[4] = Integer.parseInt(length.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Input Value");
                alert.setContentText("Password Length Can Only Be an Integer Value");
                alert.show();
                length.setText(length.getText(0, length.getText().length() - 1));
            }


        }
    };

    void copyPassword(ActionEvent event) {

        Button copy = (Button) event.getSource();

        clipboard.setContent(content);
        copyBtn.setDisable(true);
        copyBtn.setText("Copied");

    }

    public static void main(String[] args) {
        launch();
    }
}