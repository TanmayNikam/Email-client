package com.Email.ActualCode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import java.util.List;

public class UpdatingFolders extends Service {

    private List<Folder> listFolders;

    public UpdatingFolders(List<Folder> listFolders) {
        this.listFolders = listFolders;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(;;)
                {
                    try
                    {
                        Thread.sleep(1);
                        for(Folder folders:listFolders )
                        {
                            if(folders.getType()!=Folder.HOLDS_FOLDERS && folders.isOpen())
                            {
                                folders.getNewMessageCount();
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
            }
        };
    }
}
// updating