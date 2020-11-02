package com.Email.Controllers;

import com.Email.ActualCode.DownloadAttachments;
import com.Email.ActualCode.MessageDS;
import com.Email.ActualCode.RenderMessage;
import com.Email.EmailManager;
import com.Email.View.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private MenuItem Attachments = new MenuItem("Attachments");

    @FXML
    private TreeView<String> MailTreeView;

    @FXML
    private TableView<MessageDS> MailTableView;

    @FXML
    private WebView web;
    @FXML

    private TableColumn<MessageDS, String> SenderCol;

    @FXML
    private TableColumn<MessageDS, Date> DateCol;

    @FXML
    private TableColumn<MessageDS, String> SubjectCol;

    @FXML
    private Label Attachments_Label;

    public MainWindowController(String fxmlfilename, EmailManager emailManager, ViewFactory viewFactory) {
        super(fxmlfilename, emailManager, viewFactory);
    }

    @FXML
    void DarkThemeAction() {
        viewFactory.setDarktheme(true);
        MailTableView.getScene().getStylesheets().add("com/Email/View/dark.css");
    }

    @FXML
    void LightThemeAction() {
        MailTableView.getScene().getStylesheets().remove("com/Email/View/dark.css");
        viewFactory.setDarktheme(false);
    }
    @FXML
    void LogoutAction() throws MessagingException {
            emailManager.getAccount().getStore().close();
            Stage stage =(Stage)web.getScene().getWindow();
            stage.close();
    }
    private RenderMessage rm;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTreeview();
        setTableView();
        FolderSelection();
        setrendermessage();
        selectmsg();
        SetupContextMenu();
    }

    private void SetupContextMenu() {
        Attachments.setOnAction(e->
        {
            if(emailManager.getSelectedMessage().HasAttach()) {
                Attachments_Label.setText("Attachments Found");
                DownloadAttachments da = new DownloadAttachments(emailManager);
                da.Download();
            }
            else
                Attachments_Label.setText("No Attachments Found");
        });
    }


    private void selectmsg() {
        MailTableView.setOnMouseClicked(e->
        {
            MessageDS messages = (MessageDS)MailTableView.getSelectionModel().getSelectedItem();
            if(messages != null)
            {
                emailManager.setSelectedMessage(messages);
                rm.setMessages(messages);
                rm.restart();
            }
        });
    }

    private void setrendermessage() {
        rm = new RenderMessage(web.getEngine());
    }

    private void setTableView() {
            SenderCol.setCellValueFactory(new PropertyValueFactory<MessageDS,String>("sender"));
            DateCol.setCellValueFactory(new PropertyValueFactory<MessageDS,Date>("date"));
            SubjectCol.setCellValueFactory(new PropertyValueFactory<MessageDS,String>("subject"));
    }
    private void FolderSelection()
    {
        MailTreeView.setOnMouseClicked(e->
        {
            EmailTreeItem<String>item = (EmailTreeItem<String>) MailTreeView.getSelectionModel().getSelectedItem();
            if(item!=null){
                MailTableView.setItems(item.getMessages());}
        });
        MailTableView.setContextMenu(new ContextMenu(Attachments));
    }
    private void setTreeview() {
        MailTreeView.setRoot(emailManager.getFolders());
        MailTreeView.setShowRoot(false);
    }

    @FXML
    void ComposeMessageAction() {
        viewFactory.showComposeMessageWindow();
    }
}