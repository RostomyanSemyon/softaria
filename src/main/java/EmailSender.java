import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Класс, отправляющий e-mail.
 * Адресс отправителя - 0-ой параметр командной строки
 * Логин и пароль отправителя для авторизации читаются из mail.properties
 */
public class EmailSender {
    private static final String LOGIN_PROPERTY = "login";
    private static final String PASSWORD_PROPERTY = "password";

    private String receiverAddress;

    EmailSender(String receiverAddress){
        this.receiverAddress = receiverAddress;
    }

    /**
     * Отправляе e-mail
     * @param yesterday Map для методота generateMessage
     * @param today Map для методота generateMessage
     */
    public void sendEmail(Map yesterday, Map today)  {
        Properties mailProps = this.readProperties();
        String fromEmail = mailProps.getProperty(LOGIN_PROPERTY);

        Session mailSession = Session.getDefaultInstance(mailProps);
        Message message = null;
        try {
            message = generateMessage(mailSession, fromEmail, yesterday, today);
            Transport.send(message, fromEmail, mailProps.getProperty(PASSWORD_PROPERTY));
        } catch (MessagingException e) {
            System.out.println("E-mail не был сгенерирован и отправлен: " + e.getMessage());
        }

    }

    private Properties readProperties() {
        final Properties mailProps = new Properties();
        try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("mail.properties")){
            mailProps.load(input);
        }
        catch (IOException e){
            System.out.println("Couldn't fully read email properties: " + e.getMessage());
        }
        return mailProps;
    }

    private Message generateMessage(Session mailSession, String fromEmail, Map yesterday, Map today) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress(fromEmail));
        } catch (MessagingException e) {
            System.out.println("Generated message exception: " + e.getMessage());
        }
        message.addRecipients(Message.RecipientType.TO, this.receiverAddress);
        message.setSubject("Test");
        EmailAnswerCreator emailAnswerCreator = new EmailAnswerCreator();
        message.setText(emailAnswerCreator.createAnswer(PagesComparator.addedPages(yesterday, today), PagesComparator.deletedPages(yesterday, today), PagesComparator.modifiedPages(yesterday, today)));
        return message;
    }
}
