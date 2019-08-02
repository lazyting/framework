package model.mail;

public class MailModel {
    private String sendMailPerson;
    private String receiveMailPerson;
    private String ccMailPerson;
    private String bccMailPerson;
    private String mailTitle;
    private String mailText;

    public String getSendMailPerson() {
        return sendMailPerson;
    }

    public void setSendMailPerson(String sendMailPerson) {
        this.sendMailPerson = sendMailPerson;
    }

    public String getReceiveMailPerson() {
        return receiveMailPerson;
    }

    public void setReceiveMailPerson(String receiveMailPerson) {
        this.receiveMailPerson = receiveMailPerson;
    }

    public String getCcMailPerson() {
        return ccMailPerson;
    }

    public void setCcMailPerson(String ccMailPerson) {
        this.ccMailPerson = ccMailPerson;
    }

    public String getBccMailPerson() {
        return bccMailPerson;
    }

    public void setBccMailPerson(String bccMailPerson) {
        this.bccMailPerson = bccMailPerson;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }
}
