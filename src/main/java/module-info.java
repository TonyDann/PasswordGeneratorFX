module xfzliansynot.passwordgenerator {
    requires javafx.controls;
    requires javafx.fxml;


    opens xfzliansynot.passwordgenerator to javafx.fxml;
    exports xfzliansynot.passwordgenerator;
}