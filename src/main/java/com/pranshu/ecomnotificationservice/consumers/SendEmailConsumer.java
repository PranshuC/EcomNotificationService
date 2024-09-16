package com.pranshu.ecomnotificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pranshu.ecomnotificationservice.dto.SendEmailDto;
import com.pranshu.ecomnotificationservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import java.util.Properties;

@Service
public class SendEmailConsumer {
    private ObjectMapper objectMapper;
    private EmailService emailService;

    public SendEmailConsumer(ObjectMapper objectMapper, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    /**
     * This method should be called if we receive an event for sending an email(SignUp)
     * This method/consumer should register itself to the signUp topic.
     * - When messages are published to the "topics", the annotated method will be invoked to process the messages.
     * - "groupId" in the @KafkaListener annotation specifies the Kafka consumer group that the listener belongs to.
     * (Kafka consumer groups allow multiple consumers to coordinate and share the consumption of messages from topics.
     * Each consumer in a group reads a subset of the partitions in the topic, ensuring that each message is processed
     * by only one consumer in the group.)
     */
    @KafkaListener(topics = "signUp", groupId = "emailService")
    public void handleSignUpEvent(String message) {
        try {
            // Convert input string message to object using ObjectMapper
            SendEmailDto sendEmailDto = objectMapper.readValue(message, SendEmailDto.class);

            String smtpHostServer = "smtp.gmail.com";
            String emailID = "sampleid@gmail.com";

            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpHostServer);
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Authenticator auth = new Authenticator() {
                // Override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sendEmailDto.getFrom(), "");
                }
            };

            Session session = Session.getInstance(props, auth);
            emailService.sendEmail(session, sendEmailDto.getTo(), sendEmailDto.getSubject(), sendEmailDto.getBody());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
