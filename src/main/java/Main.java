import generator.FromFileMapGenerator;
import generator.MapGenerator;
import javax.mail.MessagingException;

public class Main {
    public static final String TODAY_PAGES_FILE_NAME = "urlslistToday";
    public static final String YESTERDAY_PAGES_FILE_NAME = "urlslistYesterday";

    public static void main(String[] args) throws  InterruptedException {
        MapGenerator yesterday = new FromFileMapGenerator(YESTERDAY_PAGES_FILE_NAME);
        MapGenerator today = new FromFileMapGenerator(TODAY_PAGES_FILE_NAME);

        EmailSender emailSender = new EmailSender(args[0]);
        emailSender.sendEmail(yesterday.getMap(), today.getMap());
    }
}
