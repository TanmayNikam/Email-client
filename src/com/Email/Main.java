package com.Email;

import com.Email.View.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage stage) {
        ViewFactory view = new ViewFactory(new EmailManager());
        view.showLoginWindow();
    }
}
