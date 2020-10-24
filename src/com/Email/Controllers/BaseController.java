package com.Email.Controllers;

import com.Email.EmailManager;
import com.Email.View.ViewFactory;

public abstract class BaseController {
    private String fxmlfilename;
    protected EmailManager emailManager;
    protected ViewFactory viewFactory;

    public BaseController(String fxmlfilename, EmailManager emailManager, ViewFactory viewFactory) {
        this.fxmlfilename = fxmlfilename;
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
    }

    public String getFxmlfilename() {
        return fxmlfilename;
    }

    public EmailManager getEmailManager() {
        return emailManager;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
