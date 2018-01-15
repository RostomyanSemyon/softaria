package generator;

import java.util.Map;

/**
 * Интерфейс генератора создания Map'ы {url, html-content}
 */
public interface MapGenerator {
    Map<String, String> getMap();
}
