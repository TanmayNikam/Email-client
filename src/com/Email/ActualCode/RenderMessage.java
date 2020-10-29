package com.Email.ActualCode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

public class RenderMessage extends Service {

    private MessageDS messagesds;
    private WebEngine webEngine;
    private StringBuffer sb;

    public RenderMessage(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.sb = new StringBuffer();
        this.setOnSucceeded(e->{
            DisplayMesssage();
        });
    }
    public void setMessages(MessageDS messages)
    { this.messagesds = messages; }
    private void DisplayMesssage()
    {
        webEngine.loadContent(sb.toString());
    }
    private void loadMessage()  {
        try{
            sb.setLength(0);
            Message message = messagesds.getMessage();
            String contentType= message.getContentType();
            if(Simple(contentType))
            {
                sb.append(message.getContent().toString());
            }
            else if(multipart(contentType))
            {
                Multipart multipart=(Multipart)message.getContent();
                loadMultipart(multipart,sb);

            }
        } catch (MessagingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void loadMultipart(Multipart multipart, StringBuffer sb) throws MessagingException, IOException {
        for(int i=multipart.getCount()-1;i>=0;i--) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if(Simple(bodyPart.getContentType()))
            {
                sb.append(bodyPart.getContent().toString());
            }
            else if(multipart(bodyPart.getContentType()))
            {
                Multipart multipart1 = (Multipart)bodyPart.getContent();
                loadMultipart(multipart1,sb);
            } else if(!PlainText(bodyPart.getContentType()))
            {
                MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
                messagesds.addAttachments(mimeBodyPart);
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
        if(contentType.contains("TEXT/HTML") || contentType.contains("text") || contentType.contains("mixed"))
            return true;
        else
            return false;
    }

    private boolean PlainText(String contentType)
    {
        return (contentType.contains("TEXT/PLAIN"));
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
