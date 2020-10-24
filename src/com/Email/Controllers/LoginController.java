package com.Email.Controllers;

import com.Email.ActualCode.AccountInfo;
import com.Email.ActualCode.Authentication;
import com.Email.EmailManager;
import com.Email.View.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends BaseController{

    @FXML
    private TextField E_Id;

    @FXML
    private PasswordField Pass_Id;

    @FXML
    private Label Error_Id;

    @FXML
    void LoginAction() {
        if(Credentials())
        {
            AccountInfo account = new AccountInfo(E_Id.getText(),Pass_Id.getText());
            Authentication user = new Authentication(account,emailManager);
            Error_Id.setText(user.Login());
            if(Error_Id.getText() == "Success")
            {
                viewFactory.showMainWindow();
                Stage sg = (Stage)E_Id.getScene().getWindow();
                viewFactory.closeStage(sg);
            }
            else
            {
                E_Id.setText("");
                Pass_Id.setText("");
            }
        }

    }
    public boolean Credentials()
    {
        if(E_Id.getText().isEmpty()){
            Error_Id.setText("Please Enter Email Id");
            return false;}
        if(Pass_Id.getText().isEmpty())
        {
            Error_Id.setText("Please Enter Password");
            return false;
        }
        return true;
    }
    public LoginController(String fxmlfilename, EmailManager emailManager, ViewFactory viewFactory) {
        super(fxmlfilename, emailManager, viewFactory);
    }

}
