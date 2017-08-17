/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasd.mwc.util.beans;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

/**
 *
 * @author Almir
 */
@Component( value = "mwcMail" )
@Scope( value = "request" )
public class MwcMail
{

    public final Logger logger = Logger.getLogger( MwcMail.class );
    @Autowired
    private JavaMailSender mailSender;

    public void setMailSender( JavaMailSender mailSender )
    {
        //logger.debug( "&&&&& setMailSender/mailSender: " + mailSender );
        this.mailSender = mailSender;
    }

    public void sendMail( final String from, final String to, final String subject, final String msg )
    {

        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {

            @Override
            public void prepare( MimeMessage mimeMessage ) throws Exception
            {
                mimeMessage.setFrom( new InternetAddress( from ) );
                mimeMessage.setRecipient( Message.RecipientType.TO, new InternetAddress( to ) );
                mimeMessage.setRecipient( Message.RecipientType.BCC, new InternetAddress( "javasd.info@gmail.com") );
                mimeMessage.setSubject( subject );
                mimeMessage.setContent( msg, "text/html; charset=utf-8" );
            }
        };
        try
        {
            this.mailSender.send( preparator );
        }
        catch ( MailException mex )
        {
            logger.info( "**** EMAIL SEND ERROR *****\n\n " + mex );
        }

//        message.setFrom( from );
//        message.setTo( to );
//        message.setSubject( subject );
//        message.setText( msg );
//        
//        logger.debug( "======= mailSender: " + mailSender );
//        logger.debug( "======= message: " + message );
//        
//        mailSender.send( message );
    }
}
