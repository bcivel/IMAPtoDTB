/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imaptodtb.log;

// Import the Commons/Net classes
import com.sun.mail.imap.IMAPFolder;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.BodyTerm;
import javax.mail.search.SentDateTerm;
import org.imaptodtb.entity.Emails;
import org.imaptodtb.service.IEmailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IMAP {

    @Autowired
    IEmailsService emailService;

    private final String host = "mymail.pprgroup.net";
    //private final String host = "172.25.64.170";


    public String retrieveMailFromSearch(String search, String folderName, String user, String password)
            throws Exception {
        StringBuilder retourMsg = new StringBuilder();

        if (user == null || "".equals(user.trim())) {
            user = "preveclire7fonc@siege.red";
        }

        if (password == null || "".equals(password.trim())) {
            password = "Laredoute2014";
        }

        IMAPFolder folder = null;
        Store store = null;

        // Get system properties
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imap");

        //retourMsg.append("line 51\n");

        Session session = Session.getDefaultInstance(props, null);

        // Get a Store object that implements the specified protocol.
        store = session.getStore("imap");

        //Connect to the current host using the specified username and password.
        store.connect(host, user, password);

        //retourMsg.append("line 61\n");

        //Create a Folder object corresponding to the given name.
        folder = (IMAPFolder) store.getFolder("Inbox/" + folderName);

        //retourMsg.append("line 66\n");

        // Open the Folder.
        folder.open(Folder.READ_ONLY);

        /*
         int messageCount = folder.getMessageCount();
         retourMsg.append("Total Messages:- " + messageCount);
         // Get the messages from the server
         Message[] messages = folder.getMessages();
         */
        //retourMsg.append("line 78\n");

        BodyTerm bodyTerm = new BodyTerm(search);

        Message[] messages = folder.search(bodyTerm);

        //retourMsg.append("line 91\n");

        if (messages == null || messages.length <= 0) {
            retourMsg.append("NO MESSAGE FOUND");
        } else {
            //retourMsg.append("No new Messages !");

            // Display message.
            for (Message message : messages) {
                /*
                 retourMsg.append("from =").append(message.getFrom());
                retourMsg.append("replyTo =").append(message.getReplyTo());
                retourMsg.append("to =").append(message.getRecipients(Message.RecipientType.TO));
                retourMsg.append("cc =").append(message.getRecipients(Message.RecipientType.CC));
                retourMsg.append("bcc =").append(message.getRecipients(Message.RecipientType.BCC));
                retourMsg.append("subject =").append(message.getSubject());
                retourMsg.append("sent =").append(message.getSentDate());
                 retourMsg.append("content =");
                 */
                retourMsg.append(message.getContent().toString());

                //retourMsg.append("received =").append(message.getReceivedDate());
            }
        }
        folder.close(true);
        store.close();

        return retourMsg.toString();

    }

    public String test() throws Exception {

        String user = "preveclient@siege.red";
        String password = "Laredoute2014";

        StringBuilder retourMsg = new StringBuilder();
        try {
        retourMsg.append("line 32\n");

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.HOUR, -24);
        Date yesterday = cal.getTime();
        retourMsg.append("Date = " + yesterday);
        // Getting now.

        retourMsg.append("line 42\n");

        IMAPFolder folder = null;
        Store store = null;

        // Get system properties
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        retourMsg.append("line 51\n");

        Session session = Session.getDefaultInstance(props, null);

        // Get a Store object that implements the specified protocol.
        store = session.getStore("imaps");

        //Connect to the current host using the specified username and password.
        store.connect(host, user, password);

        retourMsg.append("line 61\n");

        //Create a Folder object corresponding to the given name.
        folder = (IMAPFolder) store.getFolder("Inbox/Commande");

        retourMsg.append("line 66\n");

        // Open the Folder.
        folder.open(Folder.READ_ONLY);

        /*
         int messageCount = folder.getMessageCount();
         retourMsg.append("Total Messages:- " + messageCount);
         // Get the messages from the server
         Message[] messages = folder.getMessages();
         */
        retourMsg.append("line 78\n");

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.UK);
        Emails emails = emailService.getLastMessage();
        cal.setTime(dateFormat.parse(emails.getSendDate()));
        cal.add(Calendar.HOUR, +1);

        retourMsg.append("line 83\n");

        retourMsg.append("DateMail=").append(cal.getTime()).append("\n");
        SentDateTerm sentDateTerm = new SentDateTerm(SentDateTerm.GT, cal.getTime());

        if (sentDateTerm == null) {
            retourMsg.append("ERROR sentDateTerm NULL !!\n");
        }

        retourMsg.append("line 87\n");

        Message[] messages = folder.search(sentDateTerm);

        retourMsg.append("line 91\n");

        if (messages == null || messages.length <= 0) {
            retourMsg.append("Total new Messages: " + messages.length);
        } else {
            retourMsg.append("No new Messages !");

            Emails email = new Emails();
            // Display message.
            for (Message message : messages) {
                String from = InternetAddress.toString(message.getFrom());
                if (from != null) {
                    email.setFrom(from);
                }
                String replyTo = InternetAddress.toString(message.getReplyTo());
                if (replyTo != null) {
                    email.setReplyTo(replyTo);
                }
                String to = InternetAddress.toString(message.getRecipients(Message.RecipientType.TO));
                if (to != null) {
                    email.setTo(to);
                }
                String cc = InternetAddress.toString(message.getRecipients(Message.RecipientType.CC));
                if (cc != null) {
                    email.setCc(cc);
                }
                String bcc = InternetAddress.toString(message.getRecipients(Message.RecipientType.BCC));
                if (bcc != null) {
                    email.setBcc(bcc);
                }
                String subject = message.getSubject();
                if (subject != null) {
                    email.setSubject(subject);
                }
                Date sent = message.getSentDate();
                if (sent != null) {
                    email.setSendDate(sent.toString());
                }
                String content = message.getContent().toString();
                if (content != null) {
                    email.setMessage(content);
                }
                Date received = message.getReceivedDate();
                if (received != null) {
                    email.setReceivedDate(received.toString());
                }

                emailService.insertEmails(email);
            }
            }
            folder.close(true);
            store.close();
        } catch (MessagingException e) {
            retourMsg.append(e.getMessage());
        } catch (java.text.ParseException e) {
            retourMsg.append(e.getMessage());
        } catch (IOException e) {
            retourMsg.append(e.getMessage());
        } catch (Throwable t) {
            retourMsg.append(t.getMessage());
        } finally {

        }

        return retourMsg.toString();
    }
}
