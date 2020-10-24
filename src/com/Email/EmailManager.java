package com.Email;

import com.Email.ActualCode.AccountInfo;
import com.Email.ActualCode.FetchingFolders;
import com.Email.Controllers.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {
    private EmailTreeItem<String> root = new EmailTreeItem<String>("");
    public EmailTreeItem<String> getFolders()
    {
        return root;
    }
    private AccountInfo account;
    public void AddEmailAccount(AccountInfo account)
    {
        this.account = account;
        EmailTreeItem<String> treeitem = new EmailTreeItem<String>(account.getUserid());
        root.getChildren().add(treeitem);
        FetchingFolders fetch = new FetchingFolders(account.getStore(),treeitem);
        fetch.start();
    }

    public AccountInfo getAccount() {
        return account;
    }
}
