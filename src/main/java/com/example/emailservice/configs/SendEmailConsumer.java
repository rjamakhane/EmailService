package com.example.emailservice.configs;

import com.example.emailservice.dtos.SendEmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailConsumer {
    private ObjectMapper objectMapper;
    @Value("${host_email}")
    private String hostEmail;
    @Value("${app_password}")
    private String appPassword;

    public SendEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics = "sendEmail", groupId = "emailService")
    public void handleSendEmailMessage(String message) {
        try {
            SendEmailDTO sendEmailDTO = objectMapper.readValue(message, SendEmailDTO.class);
            System.out.println("SimpleEmail Start");

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(hostEmail, appPassword);
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, sendEmailDTO.getTo(),sendEmailDTO.getSubject(), sendEmailDTO.getMessage());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
