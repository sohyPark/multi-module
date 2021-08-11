package com.baemin.server.ceo.board.service;

import org.junit.jupiter.api.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

class EmailServiceTest {
    @Test
    void sendMailTest() throws MessagingException{

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
// 이메일 객체생성하기
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "parksohyan@gmail.com";//
        final String password = "Thgus5647!!";

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message msg = new MimeMessage(session);
        // sender
        msg.setFrom(new InternetAddress("parksohyan@gmail.com"));
        // recipient
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("parksohyan@naver.com", false));
        msg.setSubject("제목입니다");
        msg.setText("\n내용입니다");
        msg.setSentDate(new Date());
        Transport.send(msg);

    }
}