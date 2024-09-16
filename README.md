# ECommerce Project - Notification Service

### Backend Projects: Implementing Kafka [29-02-24]
1. Create the Project - boilerplate code
3. Multiple Notification Consumers : Email, SMS, Push Notification, Social(Whatsapp, Telegram)
2. SendEmailDto (Email Notification Details)
4. SendEmailConsumer : @KafkaListener(topics = "singUp") -> Bind consumer to particular topic
5. EmailService : sendEmail(...) -> MimeMessage
