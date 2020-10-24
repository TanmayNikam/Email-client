package com.Email.ActualCode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendingEmail extends Service<Void> {

    private AccountInfo account;
    private String Subject, Recipient, Content;
    private String result;
    public SendingEmail(AccountInfo account, String subject, String recipient, String content) {
        this.account = account;
        Subject = subject;
        Recipient = recipient;
        Content = content;
    }

    public String getResult() {
        return result;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                MimeMessage mimeMessage = new MimeMessage(account.getSession());
                try {
                    mimeMessage.setFrom(account.getUserid());
                    mimeMessage.setRecipients(Message.RecipientType.TO, Recipient);
                    mimeMessage.setSubject(Subject);
                    Multipart multipart = new MimeMultipart();
                    BodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setContent(Content, "text/html");
                    multipart.addBodyPart(bodyPart);
                    mimeMessage.setContent(multipart);
                    Transport transport = account.getSession().getTransport();
                    transport.connect(
                            account.getProps().getProperty("outgoingHost"),
                            account.getUserid(),
                            account.getPassword()
                    );
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();
                    result="Success";
                } catch (MessagingException e) {
                    System.out.println(e);
                    result="Provider error";
                } catch (Exception e) {
                    result = "Unexprected error";
                    System.out.println(e);
                }
                return null;
            }
        };
    }
}