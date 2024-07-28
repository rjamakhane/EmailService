# EmailService

## Overview
EmailService is a Spring Boot application designed to send emails using Kafka for message consumption and JavaMail for email sending. The application is configured to use Gmail's SMTP server with STARTTLS for secure email transmission.

## Prerequisites
- Java 17
- Maven
- Kafka
- Gmail account with an app password

## Setup

### Clone the Repository
```sh
git clone git@github.com:rjamakhane/EmailService.git
cd EmailService
```

### Configure Application Properties
Update the `src/main/resources/application.properties` file with your Gmail credentials:
```ini
spring.application.name=EmailService
host_email=your-email@gmail.com
app_password=your-app-password

# Eureka server configuration
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Build the Project
```sh
mvn clean install
```

## Running the Application
Start the application using the following command:
```sh
mvn spring-boot:run
```

## Kafka Configuration
Ensure Kafka is running and a topic named `sendEmail` is created. You can create the topic using the following command:
```sh
kafka-topics.sh --create --topic sendEmail --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

## Usage
The application listens to the `sendEmail` topic for messages containing email details. The message should be in JSON format and match the `SendEmailDTO` structure:
```json
{
  "from": "sender@example.com",
  "to": "recipient@example.com",
  "subject": "Email Subject",
  "message": "Email Body"
}
```

## Project Structure
- `src/main/java/com/example/emailservice/EmailServiceApplication.java`: Main class to run the Spring Boot application.
- `src/main/java/com/example/emailservice/configs/SendEmailConsumer.java`: Kafka listener that processes email messages.
- `src/main/resources/application.properties`: Configuration file for application properties.
- `pom.xml`: Maven configuration file.

## Dependencies
- Spring Boot Starter Web
- Spring Boot DevTools
- Spring Boot Configuration Processor
- Lombok
- Spring Boot Starter Test
- Spring Kafka
- JavaMail
- Spring Cloud Netflix Eureka Client

## License
This project is licensed under the MIT License.
