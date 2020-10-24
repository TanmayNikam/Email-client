package com.Email.ActualCode;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;
public class AccountInfo {
    private String userid;
    private String password;
    private Store store;
    private Properties props;
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Properties getProps() {
        return props;
    }

    public AccountInfo(String userid, String password) {
        this.userid = userid;
        this.password = password;
        props = new Properties();
        props.put("incomingHost", "imap.gmail.com");
        props.put("mail.store.protocol", "imaps");
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");
        props.put("outgoingHost", "smtp.gmail.com");
    }

}
