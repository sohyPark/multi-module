package com.baemin.server.ceo.board.service;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class EmailServiceTest {
    static final String FROM = "parksohyan@naver.com";
    static final String FROMNAME = "박소현";
    static final String TO = "parksohyan@kakao.com";
    static final String SMTP_USERNAME = "parksohyan";
    static final String SMTP_PASSWORD = "Thgus5647!!";

    static final String HOST = "smtp.live.com";
    static final int PORT = 25;

    static final String SUBJECT = "메일 제목";

    static final String BODY = String.join(
            System.getProperty( "line.separator" ),
            "<h1>메일 내용</h1>",
            "<p>이 메일은 아름다운 사람이 보낸 아름다운 메일입니다!</p>."
    );

    @Test
    void send() throws Exception {
        String host = "smtp.naver.com";
        int port = 25;

        String username = "parksohyan@naver.com";
        String password = "Thgus5647!!";

        // 메일 내용
        String recipient = "parksohyan@gmail.com";
        String subject = "메일테스트";
        String body = "박소현님으로 부터 메일을 받았습니다.";

        Properties props = System.getProperties();
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.starttls.enable", "true" );

        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", "25");
        props.put("defaultEncoding", "utf-8");
        props.put("mail.smtp.auth", "true");


//        Session session = Session.getDefaultInstance( props, new javax.mail.Authenticator() {
//            String un = username;
//            String pw = password;
//
//            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                return new javax.mail.PasswordAuthentication( un, pw );
//            }
//        } );

        Session session = Session.getDefaultInstance( props );
        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(username));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("parksohyan@kakao.com") ); //수신자 셋팅
        mimeMessage.setSubject(subject); //제목 세팅
        mimeMessage.setText(body); //본문 세팅
        Transport.send(mimeMessage);

//        MimeMessage msg = new MimeMessage( session );
//        msg.setFrom(new InternetAddress("XXXXXXXX@naver.com"));
//        //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
//        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//        msg.setSubject( subject );
//        msg.setText( body );

//        Transport.send( msg );

    }

    @Test
    void sendMail() throws Exception {
        Properties props = System.getProperties();
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.port", 25 );
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.starttls.enable", "true" );
        props.put("mail.smtp.host", "smtp.naver.com");

        Session session = Session.getDefaultInstance( props );

        MimeMessage msg = new MimeMessage( session );
        msg.setFrom( new InternetAddress( FROM, FROMNAME ) );
        msg.setRecipient( Message.RecipientType.TO, new InternetAddress( TO ) );
        msg.setSubject( SUBJECT );
        msg.setContent( BODY, "text/html;charset=euc-kr" );

        Transport transport = session.getTransport();
        try {
            System.out.println( "Sending..." );

            transport.connect( HOST, SMTP_USERNAME, SMTP_PASSWORD );
            transport.sendMessage( msg, msg.getAllRecipients() );
            System.out.println( "Email sent!" );
        } catch ( Exception ex ) {
            ex.printStackTrace();
        } finally {
            transport.close();
        }
    }
}