package core.habr;
import core.ParserSettings;

/**
 * Отвечает за настройки парсера сайта habr.com.
 */
public class HabrSettings extends ParserSettings {
    /**
     * @param start начало пагинации.
     * @param end конец пагинации.
     */
    public HabrSettings(int start, int end) {
        startPoint = start;
        endPoint = end;
        BASE_URL = "https://habr.com/ru/news";
        PREFIX = "page{CurrentId}";
        SAVE_PATH = "./src/main/resources";
    }
}
