package generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс генерирующий Map'у {url, html-content} используя текстовые файлы со списком url'ов на день
 */
public class FromFileMapGenerator implements MapGenerator {
    private String sourceFileName;

    public FromFileMapGenerator(String filename) {
        this.sourceFileName = filename;
    }

    /**
     * Создает и заполняет Map'у  парами {url, html-content}
     */
    @Override
    public Map<String, String> getMap() {
        Map<String, String> day = new HashMap<>();
        List<URL> urlList = this.URLList();
        for (URL url : urlList) {
            String html = this.readHTMLContent(url);
            day.put(url.toString(), html);
        }
        return day;
    }

    private List<URL> URLList() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(sourceFileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        List<URL> urlList = new ArrayList<>();
        try (bufferedReader){
            while ((inputLine = bufferedReader.readLine()) != null) {
                urlList.add(new URL(inputLine));
            }
            return urlList;
        } catch (IOException e) {
            System.out.println("Couldn't read url from file: "+ e.getMessage());
            return urlList;
        }

    }

    private String readHTMLContent(URL url) {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            System.out.println("Couldn't read html content for: " + url.toString() + "\n" + e.getMessage());
            return null;
        }
    }
}
