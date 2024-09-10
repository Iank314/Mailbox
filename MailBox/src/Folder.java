
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a folder that stores emails and provides methods to add, remove, and sort emails.
 */
public class Folder implements Serializable 
{
    private ArrayList<Email> emails;
    private String name;

    /**
     * Constructor for Folder class.
     * 
     * @param name the name of the folder.
     */
    public Folder(String name)
    {
        this.emails = new ArrayList<>();
        this.name = name;
    }

    /**
     * Gets the name of the folder.
     * 
     * @return the name of the folder.
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Adds an email to the folder.
     * 
     * @param email the email to add.
     */
    public void addEmail(Email email) 
    {
        emails.add(email);
    }

    /**
     * Removes an email from the folder by index.
     * 
     * @param index the index of the email to remove.
     * @return the removed email.
     */
    public Email removeEmail(int index) 
    {
        return emails.remove(index);
    }

    /**
     * Gets the list of emails in the folder.
     * 
     * @return the list of emails.
     */
    public ArrayList<Email> getEmails() 
    {
        return emails;
    }

    /**
     * Sorts the emails by subject in ascending order.
     */
    public void sortBySubjectAscending() 
    {
        Collections.sort(emails, Comparator.comparing(Email::getSubject));
    }

    /**
     * Sorts the emails by subject in descending order.
     */
    public void sortBySubjectDescending() 
    {
        Collections.sort(emails, Comparator.comparing(Email::getSubject).reversed());
    }

    /**
     * Sorts the emails by date in ascending order.
     */
    public void sortByDateAscending() 
    {
        Collections.sort(emails, Comparator.comparing(Email::getTimestamp));
    }

    /**
     * Sorts the emails by date in descending order.
     */
    public void sortByDateDescending() 
    {
        Collections.sort(emails, Comparator.comparing(Email::getTimestamp).reversed());
    }
}
