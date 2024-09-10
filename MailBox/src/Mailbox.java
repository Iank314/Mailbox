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
 * Represents a mailbox that contains folders, including inbox and trash, and provides methods to manage emails and folders.
 */
public class Mailbox implements Serializable 
{
    private Folder inbox;
    private Folder trash;
    private HashMap<String, Folder> folders;
    public static Mailbox mailbox;
    /**
     * Main method to run the program.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) 
    {
        try 
        {
            FileInputStream fileIn = new FileInputStream("mailbox.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mailbox = (Mailbox) in.readObject();
            in.close();
            fileIn.close();
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            mailbox = new Mailbox();
            System.out.println("Previous save not found, starting with an empty mailbox.");
        }

        Scanner scanner = new Scanner(System.in);
        String choice;
        do 
        {
            System.out.println("\nMailbox:");
            System.out.println("--------");
            for (String folderName : mailbox.folders.keySet()) 
            {
                System.out.println(folderName);
            }
            System.out.println("\nA – Add folder");
            System.out.println("R – Remove folder");
            System.out.println("C – Compose email");
            System.out.println("F – Open folder");
            System.out.println("I – Open Inbox");
            System.out.println("T – Open Trash");
            System.out.println("E – Empty Trash");
            System.out.println("Q – Quit");
            System.out.print("\nEnter a user choice: ");
            choice = scanner.nextLine().toUpperCase();
            String folderName;
            switch (choice) 
            {
                case "A":
                {
                    System.out.println("Enter folder name:");
                    folderName = scanner.nextLine();
                    mailbox.addFolder(new Folder(folderName));
                    break;
                }
                case "R":
                {
                    System.out.println("Enter folder name:");
                    folderName = scanner.nextLine();
                    mailbox.deleteFolder(folderName);
                    break;
                }
                case "C":
                {
                    mailbox.composeEmail();
                    break;
                }
                case "F":               	
                {
                    System.out.println("Enter folder name:");
                    folderName = scanner.nextLine();
                    Folder folder = mailbox.getFolder(folderName);
                    if (folder != null) 
                    {
                        mailbox.folderSubmenu(folder, scanner);
                    } 
                    else 
                    {                    	
                    	System.out.println("Folder not found.");
                    }
                    break;
                }
                case "I":
                {
                    mailbox.folderSubmenu(mailbox.inbox, scanner);
                    break;
                }
                case "T":
                {
                    mailbox.folderSubmenu(mailbox.trash, scanner);
                    break;
                }
                case "E":
                {
                    mailbox.clearTrash();
                    break;
                }
                case "Q":
                {
                    try 
                    {
                        FileOutputStream fileOut = new FileOutputStream("mailbox.obj");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(mailbox);
                        out.close();
                        fileOut.close();
                    } 
                    catch (IOException i) 
                    {
                        i.printStackTrace();
                    }
                    break;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        } 
        while (!choice.equals("Q"));
    }
    /**
     * Constructor for Mailbox.
     */
    public Mailbox() 
    {
        this.inbox = new Folder("Inbox");
        this.trash = new Folder("Trash");
        this.folders = new HashMap<>();
        folders.put("Inbox", inbox);
        folders.put("Trash", trash);
    }

    /**
     * Adds a folder to the list of custom folders.
     * 
     * @param folder the folder to add.
     */
    public void addFolder(Folder folder) 
    {
        if (folders.containsKey(folder.getName())) 
        {
            System.out.println("Folder with this name already exists.");
        }
        else 
        {
            folders.put(folder.getName(), folder);
        }
    }

    /**
     * Removes a folder from the list of custom folders.
     * 
     * @param name the name of the folder to remove.
     */
    public void deleteFolder(String name) 
    {
        if (folders.containsKey(name) && !name.equals("Inbox") && !name.equals("Trash")) 
        {
            folders.remove(name);
        } 
        else 
        {
            System.out.println("Folder not found or cannot delete Inbox/Trash.");
        }
    }

    /**
     * Composes a new email and adds it to the inbox.
     */
    public void composeEmail() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter recipient (To): ");
        String to = scanner.nextLine();
        System.out.println("Enter carbon copy recipients (CC): ");
        String cc = scanner.nextLine();
        System.out.println("Enter blind carbon copy recipients (BCC): ");
        String bcc = scanner.nextLine();
        System.out.println("Enter subject line: ");
        String subject = scanner.nextLine();
        System.out.println("Enter body: ");
        String body = scanner.nextLine();
        
        Email email = new Email(to, cc, bcc, subject, body);
        inbox.addEmail(email);
        System.out.println("Email successfully added to Inbox.");
    }

    /**
     * Moves an email to the trash.
     * 
     * @param email the email to move to the trash.
     */
    public void deleteEmail(Email email) 
    {
        trash.addEmail(email);
    }

    /**
     * Clears all emails from the trash.
     */
    public void clearTrash() 
    {
        int size = trash.getEmails().size();
        trash.getEmails().clear();
        System.out.println(size + " item(s) successfully deleted.");
    }

    /**
     * Moves an email to a target folder.
     * 
     * @param email the email to move.
     * @param target the target folder.
     */
    public void moveEmail(Email email, Folder target) 
    {
        target.addEmail(email);
    }

    /**
     * Gets a folder by name.
     * 
     * @param name the name of the folder.
     * @return the folder with the given name.
     */
    public Folder getFolder(String name) 
    {
        return folders.get(name);
    }

    /**
     * Displays the folder submenu for a given folder.
     * 
     * @param folder the folder to display the submenu for.
     * @param scanner the Scanner object for user input.
     */
    private void folderSubmenu(Folder folder, Scanner scanner) throws IllegalArgumentException
    {
        String choice;
        do 
        {
            System.out.println(folder.getName());
            System.out.println("\nIndex |        Time       | Subject");
            System.out.println("-----------------------------------");
            for (int i = 0; i < folder.getEmails().size(); i++) 
            {
                Email email = folder.getEmails().get(i);
                System.out.printf("  %d   |  %tR %<tm/%<td/%<tY | %s\n", i + 1, email.getTimestamp(), email.getSubject());
            }
            System.out.println();
            System.out.println("M – Move email");
            System.out.println("D – Delete email");
            System.out.println("V – View email contents");
            System.out.println("SA – Sort by subject line in ascending order");
            System.out.println("SD – Sort by subject line in descending order");
            System.out.println("DA – Sort by date in ascending order");
            System.out.println("DD – Sort by date in descending order");
            System.out.println("R – Return to main menu");

            choice = scanner.nextLine().toUpperCase();

            switch (choice) 
            {
                case "M":
                {
                    System.out.println("Enter the index of the email to move:");
                    int moveIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    
                    if (moveIndex >= 0 && moveIndex < folder.getEmails().size()) 
                    {
                        Email emailToMove = folder.removeEmail(moveIndex);
                        System.out.println("Folders:");
                        for (String folderName : folders.keySet()) 
                        {
                            System.out.println(folderName);
                        }
                        System.out.println("\nSelect a folder to move \"" + emailToMove.getSubject() + "\" to:");
                        String targetFolderName = scanner.nextLine();
                        Folder targetFolder = getFolder(targetFolderName);
                        if (targetFolder == null) 
                        {
                            System.out.println("Folder not found. Moving to Inbox.");
                            inbox.addEmail(emailToMove);
                        }
                        else 
                        {
                            targetFolder.addEmail(emailToMove);
                        }
                        System.out.println("\"" + emailToMove.getSubject() + "\" successfully moved to " + targetFolderName + ".");
                    } 
                    else 
                    {
                        throw new IllegalArgumentException("Invalid email index.");
                    }
                    break;
                }
                case "D":
                {
                    System.out.println("Enter email index to delete:");
                    int deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (deleteIndex >= 0 && deleteIndex < folder.getEmails().size()) 
                    {
                        Email emailToDelete = folder.removeEmail(deleteIndex);
                        deleteEmail(emailToDelete);
                        System.out.println("\"" + emailToDelete.getSubject() + "\" has successfully been moved to the trash.");
                    } 
                    else 
                    {
                        System.out.println("Invalid email index.");
                    }
                    break;
                }
                case "V":
                {
                    System.out.println("Enter email index to view:");
                    int viewIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (viewIndex >= 0 && viewIndex < folder.getEmails().size()) 
                    {
                        Email emailToView = folder.getEmails().get(viewIndex);
                        System.out.println("To: " + emailToView.getTo());
                        System.out.println("CC: " + emailToView.getCc());
                        System.out.println("BCC: " + emailToView.getBcc());
                        System.out.println("Subject: " + emailToView.getSubject());
                        System.out.println("Body: " + emailToView.getBody());
                        System.out.println("Timestamp: " + emailToView.getTimestamp().getTime());
                    } 
                    else 
                    {
                        System.out.println("Invalid email index.");
                    }
                    break;
                }
                case "SA":
                {
                    folder.sortBySubjectAscending();
                    System.out.println("Sorted by subject in ascending order.");
                    break;
                }
                case "SD":
                {
                    folder.sortBySubjectDescending();
                    System.out.println("Sorted by subject in descending order.");
                    break;
                }
                case "DA":
                {
                    folder.sortByDateAscending();
                    System.out.println("Sorted by date in ascending order.");
                    break;
                }
                case "DD":
                {
                    folder.sortByDateDescending();
                    System.out.println("Sorted by date in descending order.");
                    break;
                }
                case "R":
                {
                    break;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        } 
        while (!choice.equals("R"));
    }

    
}