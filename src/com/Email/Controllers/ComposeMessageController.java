package com.Email.Controllers;

import com.Email.ActualCode.SendingEmail;
import com.Email.EmailManager;
import com.Email.View.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ComposeMessageController extends BaseController{
    public ComposeMessageController(String fxmlfilename, EmailManager emailManager, ViewFactory viewFactory) {
        super(fxmlfilename, emailManager, viewFactory);
    }

    private List<File> attachments = new ArrayList<File>();
    @FXML
    private TextField Subject_Id;

    @FXML
    private TextField To_Id;

    @FXML
    void AttachAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file!=null)
            attachments.add(file);
    }

    @FXML
    void SendAction() {

        try{
            SendingEmail send = new SendingEmail(emailManager.getAccount(), Subject_Id.getText(), To_Id.getText(),text.getHtmlText().toString(),attachments);
            send.start();
            send.setOnSucceeded(
                    e->{
                        Error_Label_Id.setText(send.getResult());
                    }
            );
            if(Error_Label_Id.getText()=="Success") {
                Stage stage = (Stage) To_Id.getScene().getWindow();
                stage.close();
            }}
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    @FXML
    private HTMLEditor text;

    @FXML
    private Label Error_Label_Id;

}
