package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger( EmailService.class );

    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final Properties props = System.getProperties();

    public boolean sendMail( final String postTitle, final String comment, final User sender, final User recipient) {

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

        try {
            Message msg = new MimeMessage(session);
            // sender
            msg.setFrom(new InternetAddress("parksohyan@gmail.com"));
            // recipient
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("parksohyan@naver.com", false));
            msg.setSubject("회원님의 게시물에 댓글이 등록되었습니다.");
            StringBuilder text = new StringBuilder();
            text.append(recipient.getName()).append("님이 회원님의 게시물(").append(postTitle).append(")").append("에 댓글이 등록되었습니다.").append("\n");
            text.append("댓글 작성자: ").append(sender.getName()).append("\n");
            text.append("[").append(comment).append("]");
            msg.setText(text.toString());
            msg.setSentDate(new Date());
            Transport.send(msg);

            logger.info("send email success - senderId: {}, recipientId: {}", sender.getId(), recipient.getId());
        } catch (MessagingException e) {
            logger.error("send email error: {}", e.getMessage());
            return false;
        }

        return true;
    }
}
