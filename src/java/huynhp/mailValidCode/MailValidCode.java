/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhp.mailValidCode;

import huynhp.mailConfig.MailConfig;
import java.io.Serializable;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author MSI
 */
public class MailValidCode implements Serializable {

    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public boolean sendEmail(String code, String email) {
        boolean isSuccessful = false;
        Properties props = new Properties();

        props.put("mail.smtp.host", MailConfig.HOST_NAME);
        props.put("mail.smtp.port", MailConfig.SSL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD);
            }
        });

        try {
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailConfig.APP_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("User Booking Verification");
            message.setText("Your code to verify: " + code);

            Transport.send(message);

            isSuccessful = true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return isSuccessful;
    }
}
