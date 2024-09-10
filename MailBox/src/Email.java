/*
 * Ian Kaufman
 * 115639955
 * ian.kaufman@stonybrook.edu
 * Homework 5
 * CSE214.R30
 */
import java.io.*;
import java.util.*;

/**
 * Represents an email with fields for to, cc, bcc, subject, body, and timestamp.
 */
public class Email implements Serializable 
{
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private GregorianCalendar timestamp;

    /**
     * Constructor for Email class.
     * 
     * @param to      Recipient email address.
     * @param cc      CC email addresses.
     * @param bcc     BCC email addresses.
     * @param subject Email subject.
     * @param body    Email body.
     */
    public Email(String to, String cc, String bcc, String subject, String body) 
    {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.timestamp = new GregorianCalendar();
    }

    /**
     * Gets the recipient email address.
     * 
     * @return the recipient email address.
     */
    public String getTo() 
    {
        return to;
    }

    /**
     * Sets the recipient email address.
     * 
     * @param to the recipient email address.
     */
    public void setTo(String to) 
    {
        this.to = to;
    }

    /**
     * Gets the CC email addresses.
     * 
     * @return the CC email addresses.
     */
    public String getCc() 
    {
        return cc;
    }

    /**
     * Sets the CC email addresses.
     * 
     * @param cc the CC email addresses.
     */
    public void setCc(String cc) 
    {
        this.cc = cc;
    }

    /**
     * Gets the BCC email addresses.
     * 
     * @return the BCC email addresses.
     */
    public String getBcc() 
    {
        return bcc;
    }

    /**
     * Sets the BCC email addresses.
     * 
     * @param bcc the BCC email addresses.
     */
    public void setBcc(String bcc)
    {
        this.bcc = bcc;
    }

    /**
     * Gets the email subject.
     * 
     * @return the email subject.
     */
    public String getSubject() 
    {
        return subject;
    }

    /**
     * Sets the email subject.
     * 
     * @param subject the email subject.
     */
    public void setSubject(String subject) 
    {
        this.subject = subject;
    }

    /**
     * Gets the email body.
     * 
     * @return the email body.
     */
    public String getBody() 
    {
        return body;
    }

    /**
     * Sets the email body.
     * 
     * @param body the email body.
     */
    public void setBody(String body) 
    {
        this.body = body;
    }

    /**
     * Gets the timestamp when the email was created.
     * 
     * @return the timestamp.
     */
    public GregorianCalendar getTimestamp() 
    {
        return timestamp;
    }

    /**
     * Sets the timestamp when the email was created.
     * 
     * @param timestamp the timestamp.
     */
    public void setTimestamp(GregorianCalendar timestamp) 
    {
        this.timestamp = timestamp;
    }
}