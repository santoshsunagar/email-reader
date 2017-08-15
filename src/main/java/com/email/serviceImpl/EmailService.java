package com.email.serviceImpl;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import com.email.constants.EmailConstants;
import com.email.service.IEmailService;

public class EmailService implements IEmailService {

	@Override
	public int getEmailUnReadCount(String emailId, String emailPass) {
		int emailCount = 0;
		try {
			Properties props = System.getProperties();
			Folder inbox;
			Folder destinationFolder;
			// Set manual Properties
	        props.setProperty("mail.pop3.socketFactory.class", EmailConstants.SSL_FACTORY);
	        props.setProperty("mail.pop3.socketFactory.fallback", "false");
	        props.setProperty("mail.pop3.port", "995");
	        props.setProperty("mail.pop3.socketFactory.port", "995");
	        props.put("mail.pop3.host", "pop.gmail.com");
	        Session session = Session.getDefaultInstance(
                    System.getProperties(), null);
	        Store store = session.getStore("pop3");
	        store.connect("pop.gmail.com", 995, EmailConstants.USER_EMAIL,
	        		EmailConstants.USER_EMAIL_PASS);
	        
	        Folder[] folders = store.getDefaultFolder().list();
	        for (Folder folder : folders) {
				System.out.println(folder.getFullName());
			}
	        
	        /*inbox = store.getFolder("INBOX");
	        inbox.open(Folder.READ_ONLY);
	        Message messages[] = inbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
	        emailCount = messages.length;*/
	        
	        /*inbox = store.getFolder( "INBOX" );
	        Folder[] folders = store.getDefaultFolder().list("*");
	        for (Folder folder : folders) {
				System.out.println(folder.getFullName());
			}
	        destinationFolder = store.getFolder( "PERSONAL" );
	        inbox.open( Folder.READ_ONLY );

	        // Fetch unseen messages from inbox folder
	        Message[] messages = inbox.search(
	            new FlagTerm(new Flags(Flags.Flag.SEEN), false));

	        // Sort messages from recent to oldest
	        Arrays.sort( messages, ( m1, m2 ) -> {
	          try {
	            return m2.getSentDate().compareTo( m1.getSentDate() );
	          } catch ( MessagingException e ) {
	            throw new RuntimeException( e );
	          }
	        } );

	        for ( Message message : messages ) {
	          System.out.println( 
	              "sendDate: " + message.getSentDate()
	              + " subject:" + message.getSubject() );
	        }
*/	        
	        
	        //inbox.copyMessages(messages, destinationFolder);
	        System.out.println("emailCount:"+emailCount);
	        //System.out.println("emailCount:"+messages.length);
	        
	        
	        
		} catch(Exception expObj) {
			expObj.printStackTrace();
		}
		
		return emailCount;
	}
	
	public void processEmail(String host, String user, String password) throws MessagingException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Folder inbox;
		Folder destinationFolder;
		try {
		    Session session = Session.getDefaultInstance(props, null);
		    javax.mail.Store store = session.getStore("imaps");
		    store.connect("imap.gmail.com", EmailConstants.USER_EMAIL, EmailConstants.USER_EMAIL_PASS);
		    javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
		    for (Folder folder : folders) {
				System.out.println(folder.getFullName());
			}
		    inbox = store.getFolder("TOWORK");
		    destinationFolder = store.getFolder("PROCESSED");
		    inbox.open(Folder.READ_WRITE);
		    destinationFolder.open(Folder.READ_WRITE);
	        Message messages[] = inbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
	        int emailCount = messages.length;
	        destinationFolder.appendMessages(messages);
		    System.out.println("emailCount:"+emailCount);
		    inbox.close(false);
		    destinationFolder.close(false);
		    
		} catch (MessagingException e) {
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws MessagingException {
		new EmailService().processEmail("", "", "");
	}
	

}
