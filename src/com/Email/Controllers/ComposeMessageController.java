package com.Email.Controllers;

import com.Email.ActualCode.SendingEmail;
import com.Email.EmailManager;
import com.Email.View.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class ComposeMessageController extends BaseController{
    public ComposeMessageController(String fxmlfilename, EmailManager emailManager, ViewFactory viewFactory) {
        super(fxmlfilename, emailManager, viewFactory);
    }


    @FXML
    private TextField Subject_Id;

    @FXML
    private TextField To_Id;

    @FXML
    void SendAction() {

            SendingEmail send = new SendingEmail(emailManager.getAccount(), Subject_Id.getText(), To_Id.getText(), text.getText());
            send.start();
            send.setOnSucceeded(
                    e->{
                        Error_Label_Id.setText(send.getResult());
                    }
            );
            Stage stage = (Stage)To_Id.getScene().getWindow();
            stage.close();
    }
    @FXML
    private TextArea text;

    @FXML
    private Label Error_Label_Id;
}
