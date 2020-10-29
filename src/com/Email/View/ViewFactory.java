package com.Email.View;

import com.Email.Controllers.BaseController;
import com.Email.Controllers.ComposeMessageController;
import com.Email.Controllers.LoginController;
import com.Email.Controllers.MainWindowController;
import com.Email.EmailManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
    private EmailManager eMailManager;
    private Scene ActiveScene;
    public void setDarktheme(boolean darktheme) {
        this.darktheme = darktheme;
    }
    private boolean darktheme=false;
    public ViewFactory(EmailManager eMailManager) {
        this.eMailManager = eMailManager;
    }

    private void intializeStage(BaseController controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.getFxmlfilename()));
        loader.setController(controller);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }
        Scene sc = new Scene(parent);
        ActiveScene=sc;
        if(darktheme)
            ActiveScene.getStylesheets().add("com/Email/View/dark.css");
        else
            ActiveScene.getStylesheets().remove("com/Email/View/dark.css");
        Stage stage = new Stage();
        stage.setScene(sc);
        stage.setTitle("MyMail");
        stage.show();
    }
    public void closeStage(Stage stage)
    {
        stage.close();
    }
    public void showLoginWindow() {
        BaseController login = new LoginController("Login.fxml", eMailManager, this);
        intializeStage(login);
    }

    public void showMainWindow()
    {
        BaseController Main = new MainWindowController("MainWindow.fxml",eMailManager,this);
        intializeStage(Main);
    }
    public void showComposeMessageWindow()
    {
        BaseController compose = new ComposeMessageController("ComposeMsg.fxml",eMailManager,this);
        intializeStage(compose);
    }
}
