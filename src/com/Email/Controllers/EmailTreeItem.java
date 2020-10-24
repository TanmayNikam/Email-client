package com.Email.Controllers;

import com.Email.ActualCode.MessageDS;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

public class EmailTreeItem<String> extends TreeItem<String> {
    private String name;
    private ObservableList<MessageDS> messages;
    private int count;
    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.messages = FXCollections.observableArrayList();
    }

    public ObservableList<MessageDS> getMessages() {
        return messages;
    }

    public void AddEmail(Message message)
    {
        try {
            boolean isRead = message.getFlags().contains(Flags.Flag.SEEN);
            MessageDS Messages = new MessageDS(message.getSubject(),message.getFrom()[0].toString(),message.getSentDate(),message,isRead);
            messages.add(Messages);
            if(!isRead)
            {
                upadteCount();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void updateName() {
        if(count>0)
            this.setValue((String) (this.name + "(" + count+ ")" ));
        else
            this.setValue(this.name);
    }
    public void upadteCount()
    {
        count+=1;
        updateName();
    }

}
