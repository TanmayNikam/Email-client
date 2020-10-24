package com.Email.ActualCode;

import com.Email.Controllers.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class FetchingFolders extends Service<Void> {
    private Store store;
    private EmailTreeItem<String> foldersroot;

    public FetchingFolders(Store store, EmailTreeItem<String> foldersroot) {
        this.store = store;
        this.foldersroot = foldersroot;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }

        };
    }

    private void fetchFolders() {
        try {
            Folder folders[] = store.getDefaultFolder().list();
            handleFolders(folders,foldersroot);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersroot) throws MessagingException {
        for (Folder folder:folders)
        {
            EmailTreeItem<String> item = new EmailTreeItem<String>(folder.getName());
            foldersroot.getChildren().add(item);
            foldersroot.setExpanded(true);
            readmessage(folder,item);
            if(folder.getType() == folder.HOLDS_FOLDERS)
            {
               Folder[] subfolders = folder.list();
               handleFolders(subfolders,item);
            }
        }
    }

    private void readmessage(Folder folder, EmailTreeItem<String> emailTreeItem) {
        Service fetchMsg = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if (folder.getType() != Folder.HOLDS_FOLDERS) {
                            folder.open(Folder.READ_WRITE);
                            int size = folder.getMessageCount();
                            for (int i = size - 1; i >0; i--) {
                                emailTreeItem.AddEmail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }

        };
        fetchMsg.start();
    }


}
