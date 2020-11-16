package com.Email.ActualCode;
import com.Email.Controllers.*;
import com.Email.EmailManager;

import javax.mail.*;

public class Authentication {

    private AccountInfo account;
    private EmailManager emailManager;

    public Authentication(AccountInfo account, EmailManager emailManager) {
        this.account = account;
        this.emailManager = emailManager;
    }
    // checks user credentials

    public String Login()
    {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account.getUserid(), account.getPassword());
            }
        };
        try
        {
            Session session = Session.getInstance(account.getProps(),authenticator);
            account.setSession(session);
            Store store = session.getStore();
            store.connect("imap.gmail.com", account.getUserid(), account.getPassword());
            account.setStore(store);
            emailManager.AddEmailAccount(account);
        }
        catch(NoSuchProviderException e)
        {
            System.out.println(e);
            return "Unexpected Error";
        }
        catch(MessagingException e)
        {
            System.out.println(e);
            return "Invalid Credentials";
        }
        return "Success";
    }
}
