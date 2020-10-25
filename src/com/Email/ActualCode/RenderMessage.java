package com.Email.ActualCode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class RenderMessage extends Service {

    private MessageDS messagesds;
    private WebEngine webEngine;
    private StringBuffer sb;

    public RenderMessage(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.sb = new StringBuffer();

    }
    public void setMessages(MessageDS messages)
    {
        this.messagesds = messages;
    }
    private void DisplayMesssage()
    {
        webEngine.loadContent(sb.toString());
    }
    private void loadMessage() throws MessagingException, IOException {
        sb.setLength(0);
        Message message = messagesds.getMessage();
        String contentType = message.getContentType();
        if(Simple(contentType))
        {
            sb.append(message.getContent().toString());
        }
        else if(multipart(contentType))
        {
            Multipart multipart = (Multipart)message.getContent();
            for(int i = multipart.getCount()-1;i>=0;i--) {
                BodyPart body = multipart.getBodyPart(i);
                String type = body.getContentType();
                if(Simple(type)) {
                    sb.append(body.getContent().toString());
                }
            }
        }
    }
    private boolean multipart(String contentType) {
        if(contentType.contains("multipart"))
            return true;
        else
            return false;
    }
    private boolean Simple(String contentType) {
        if(contentType.contains("HTML/TEXT") || contentType.contains("text") || contentType.contains("mixed"))
            return true;
        else
            return false;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    loadMessage();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                return null;
            }
        };
    }
}
