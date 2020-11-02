package com.Email;

import com.Email.ActualCode.AccountInfo;
import com.Email.ActualCode.FetchingFolders;
import com.Email.ActualCode.MessageDS;
import com.Email.ActualCode.UpdatingFolders;
import com.Email.Controllers.EmailTreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {
    private MessageDS Selectedmessage;
    private EmailTreeItem<String> root = new EmailTreeItem<String>("");
    public EmailTreeItem<String> getFolders()
    {
        return root;
    }
    private AccountInfo account;
    private UpdatingFolders update;
    public List<Folder> getFolderList() {
        return this.folderList;
    }

    public MessageDS getSelectedMessage() {
        return Selectedmessage;
    }

    public void setSelectedMessage(MessageDS message) {
        this.Selectedmessage = message;
    }

    public EmailManager()
    {
        update = new UpdatingFolders(folderList);
        update.start();
    }
    private List<Folder> folderList=new ArrayList<Folder>();
    public void AddEmailAccount(AccountInfo account)
    {
        this.account = account;
        EmailTreeItem<String> treeitem = new EmailTreeItem<String>(account.getUserid());
        root.getChildren().add(treeitem);
        FetchingFolders fetch = new FetchingFolders(account.getStore(),treeitem,folderList);
        fetch.start();
    }

    public AccountInfo getAccount() {
        return account;
    }
}
//Em