package com.Email.ActualCode;

import com.Email.Controllers.BaseController;
import com.Email.EmailManager;
import com.Email.View.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.internet.MimeBodyPart;
import java.io.File;

public class DownloadAttachments{
    private EmailManager emailManager;
    private String location = System.getProperty("user.home") + "/Downloads/";
    private MessageDS currMessage;
    public DownloadAttachments(EmailManager emailManager) {
        this.emailManager = emailManager;
        this.currMessage = emailManager.getSelectedMessage();
    }
    public void Download()
    {
        File file = new File(location+currMessage.getSubject());
        file.mkdir();
        location = location + "/" +currMessage.getSubject() + "/";
        Service service = new Service() {
           @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        for(MimeBodyPart mimeBodyPart: currMessage.getAttachments())
                        {
                            mimeBodyPart.saveFile(location+mimeBodyPart.getFileName());
                        }
                        return null;
                    }
                };
            }
        };
        service.restart();
    }

}
