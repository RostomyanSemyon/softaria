import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

/**
 *Класс генерирует тело ответа для e-mail
 */
public class EmailAnswerCreator {

    private static final String EMAIL_HEADER = "header";
    private static final String EMAIL_FOOTER = "footer";
    private static final String EMAIL_ADDED_PAGES = "added";
    private static final String EMAIL_DELETED_PAGES = "deleted";
    private static final String EMAIL_MODIFIED_PAGES = "modified";

    /**
     *
     * @param addedPages - список добавленных страниц "со вчера"
     * @param deletedPages - список удаленных страниц "со вчера"
     * @param modifiedPages - список можифицированных страниц "со вчера"
     * чтение шаблонных строк e-mail происходит из text.properties
     * @return Строку, готовую для отправления через e-mail
     */
    public String createAnswer(List<String> addedPages, List<String> deletedPages, List<String> modifiedPages) {
        StringBuilder str = new StringBuilder();
        Properties textProps = readProperties();
        str.append(getHeader(textProps));
        str.append(getAddedPages(textProps));
        str.append(urlListForEmail(addedPages));
        str.append(getDeletedPages(textProps));
        str.append(urlListForEmail(deletedPages));
        str.append(getModifiedPages(textProps));
        str.append(urlListForEmail(modifiedPages));
        str.append("\n").append(getFooter(textProps));
        return str.toString();
    }

    private Properties readProperties()  {
        final Properties textProps = new Properties();
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("text.properties")) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "cp1251");
            textProps.load(inputStreamReader);
        } catch (IOException e) {
            System.out.println("Couldn't read email template: " + e.getMessage());
        }
        return textProps;
    }

    private String getHeader(Properties properties) {
        return properties.get(EMAIL_HEADER).toString();
    }

    private String getFooter(Properties properties) {
        return properties.get(EMAIL_FOOTER).toString();
    }

    private String getAddedPages(Properties properties) {
        return properties.get(EMAIL_ADDED_PAGES).toString();
    }

    private String getDeletedPages(Properties properties) {
        return properties.get(EMAIL_DELETED_PAGES).toString();
    }

    private String getModifiedPages(Properties properties) {
        return properties.get(EMAIL_MODIFIED_PAGES).toString();
    }

    private String urlListForEmail(List<String> urlList){
        StringBuilder str = new StringBuilder();
        for(String url: urlList){
            str.append(url).append("\n");
        }
        str.append("\n");
        return str.toString();
    }
}
