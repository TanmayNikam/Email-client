package com.Email.ActualCode;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import java.util.Date;

public class MessageDS {
    private SimpleStringProperty Subject;
    private SimpleStringProperty Sender;
    private SimpleObjectProperty<Date> date;
    private Message message;
    private Boolean isRead;
    public MessageDS(String subject, String sender, Date date, Message message, Boolean isRead) {
        this.Subject = new SimpleStringProperty(subject);
        this.Sender = new SimpleStringProperty(sender);
        this.date = new SimpleObjectProperty<Date>(date);
        this.message = message;
        this.isRead = isRead;
    }

    public String getSubject() {
        return Subject.get();
    }
    public String getSender() {
        return Sender.get();
    }
    public Date getDate() {
        return date.get();
    }
    public Message getMessage() {
        return message;
    }
    public Boolean getRead() {
        return isRead;
    }
    public void setRead(Boolean read) {
        isRead = read;
    }
}
